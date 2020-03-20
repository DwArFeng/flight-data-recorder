package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.exception.RecordDisabledException;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RecordHandlerImpl implements RecordHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordHandlerImpl.class);

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;
    @Autowired
    private RecordLocalCacheHandler recordLocalCacheHandler;

    @Autowired
    @Qualifier("filteredEventConsumeHandler")
    private ConsumeHandler<FilteredValue> filteredEventConsumeHandler;
    @Autowired
    @Qualifier("filteredValueConsumeHandler")
    private ConsumeHandler<FilteredValue> filteredValueConsumeHandler;
    @Autowired
    @Qualifier("triggeredEventConsumeHandler")
    private ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler;
    @Autowired
    @Qualifier("triggeredValueConsumeHandler")
    private ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler;
    @Autowired
    @Qualifier("realtimeEventConsumeHandler")
    private ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler;
    @Autowired
    @Qualifier("realtimeValueConsumeHandler")
    private ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler;
    @Autowired
    @Qualifier("persistenceEventConsumeHandler")
    private ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler;
    @Autowired
    @Qualifier("persistenceValueConsumeHandler")
    private ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler;

    private final Lock lock = new ReentrantLock();

    private boolean enabledFlag = false;

    @Override
    public boolean isEnabled() {
        lock.lock();
        try {
            return enabledFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void enable() {
        lock.lock();
        try {
            if (!enabledFlag) {
                LOGGER.info("启用 record handler...");
                enabledFlag = true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void disable() {
        lock.lock();
        try {
            if (enabledFlag) {
                LOGGER.info("禁用 record handler...");
                enabledFlag = false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void record(String message) throws HandlerException {
        record(DataInfoUtil.fromMessage(message));
    }

    /**
     * 优化的记录方法。
     * <p>该记录方法经过优化，在记录期间，绝大部分数据不需要与缓存和数据访问层进行任何交互。尽一切可能的优化了执行效率。</p>
     * <p>仅当数据点第一次被调用的时候，该方法才会访问缓存和数据访问层，将元数据取出并缓存在内存后便不再需要继续访问。</p>
     *
     * @param dataInfo 指定的数据信息。
     * @throws HandlerException 处理器异常。
     * @since 1.4.0
     */
    @Override
    public void record(DataInfo dataInfo) throws HandlerException {
        try {
            // 判断是否允许记录，如果不允许，直接报错。
            if (!isEnabled()) {
                throw new RecordDisabledException();
            }

            // 0. 记录日志，准备工作。
            LOGGER.debug("记录数据信息: " + dataInfo);
            LongIdKey pointKey = new LongIdKey(dataInfo.getPointLongId());
            // 1. 获取 RecordContext。
            RecordLocalCacheHandler.RecordContext recordContext = recordLocalCacheHandler.getRecordContext(pointKey);
            if (Objects.isNull(recordContext)) {
                throw new PointNotExistsException(pointKey);
            }
            // 1. 判断数据点是否通过所有的过滤器，任意一个过滤器未通过时，记录并广播过滤点信息并中止整个记录过程。
            for (Filter filter : recordContext.getFilters()) {
                FilteredValue filteredValue = filter.test(dataInfo);
                if (Objects.nonNull(filteredValue)) {
                    filteredValue.setKey(keyFetcher.fetchKey());
                    LOGGER.debug("数据信息未通过过滤, 过滤数据点信息: " + filteredValue);
                    filteredEventConsumeHandler.accept(filteredValue);
                    filteredValueConsumeHandler.accept(filteredValue);
                    return;
                }
            }
            // 3. 遍历所有触发器，获取所有的触发数据点。记录并广播触发信息。
            for (Trigger trigger : recordContext.getTriggers()) {
                TriggeredValue triggeredValue = trigger.test(dataInfo);
                if (Objects.nonNull(triggeredValue)) {
                    triggeredValue.setKey(keyFetcher.fetchKey());
                    LOGGER.debug("数据信息满足触发条件, 触发数据点信息: " + triggeredValue);
                    triggeredEventConsumeHandler.accept(triggeredValue);
                    triggeredValueConsumeHandler.accept(triggeredValue);
                }
            }
            // 4. 如果数据点的实时数据使能且数据的发生时间晚于之前的实时数据发生时间，记录实时数据并广播。
            if (recordContext.getPoint().isRealtimeEnabled()) {
                RealtimeValue realtimeValue = new RealtimeValue(
                        recordContext.getPoint().getKey(),
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                LOGGER.debug("数据点实时数据记录使能, 实时数据信息: " + realtimeValue);
                realtimeEventConsumeHandler.accept(realtimeValue);
                realtimeValueConsumeHandler.accept(realtimeValue);
            }
            // 5. 如果数据点的持久数据使能，记录持久数据并广播。
            if (recordContext.getPoint().isPersistenceEnabled()) {
                PersistenceValue persistenceValue = new PersistenceValue(
                        keyFetcher.fetchKey(),
                        recordContext.getPoint().getKey(),
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                LOGGER.debug("数据点持久数据记录使能, 持久数据信息: " + persistenceValue);
                persistenceEventConsumeHandler.accept(persistenceValue);
                persistenceValueConsumeHandler.accept(persistenceValue);
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
