package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.handler.LocalCacheHandler.RecordContext;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ProviderModel;
import org.apache.dubbo.rpc.model.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class RecordServiceImpl implements RecordService, RecordControlHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);
    private static final String SERVICE_NAME = RecordService.class.getCanonicalName();
    private static final RegistryFactory REGISTER_FACTORY = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
    private static final ServiceRepository SERVICE_REPOSITORY = ApplicationModel.getServiceRepository();

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;
    @Autowired
    private LocalCacheHandler localCacheHandler;

    @Autowired
    private RealtimeValueConsumeHandler realtimeValueConsumeHandler;
    @Autowired
    private RealtimeEventConsumeHandler realtimeEventConsumeHandler;
    @Autowired
    private PersistenceValueConsumeHandler persistenceValueConsumeHandler;
    @Autowired
    private PersistenceEventConsumeHandler persistenceEventConsumeHandler;
    @Autowired
    private FilteredValueConsumeHandler filteredValueConsumeHandler;
    @Autowired
    private FilteredEventConsumeHandler filteredEventConsumeHandler;
    @Autowired
    private TriggeredValueConsumeHandler triggeredValueConsumeHandler;
    @Autowired
    private TriggeredEventConsumeHandler triggeredEventConsumeHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    @BehaviorAnalyse
    public void record(String message) throws ServiceException {
        try {
            record(DataInfoUtil.fromMessage(message));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    /**
     * 优化的记录方法。
     * <p>该记录方法经过优化，在记录期间，绝大部分数据不需要与缓存和数据访问层进行任何交互。尽一切可能的优化了执行效率。</p>
     * <p>仅当数据点第一次被调用的时候，该方法才会访问缓存和数据访问层，将元数据取出并缓存在内存后便不再需要继续访问。</p>
     *
     * @param dataInfo 指定的数据信息。
     * @throws ServiceException 服务异常。
     * @since 1.2.0
     */
    @Override
    @BehaviorAnalyse
    public void record(@NotNull DataInfo dataInfo) throws ServiceException {
        try {
            // 0. 记录日志，准备工作。
            LOGGER.debug("记录数据信息: " + dataInfo);
            LongIdKey pointKey = new LongIdKey(dataInfo.getPointLongId());
            // 1. 获取 RecordContext。
            RecordContext recordContext = localCacheHandler.getRecordContext(pointKey);
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
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    /**
     * 注意: 该方法的实现与 dubbo 版本有关，更改dubbo的版本有可能导致该方法需要重写。
     *
     * @since 1.3.1
     */
    @Override
    public void online() throws HandlerException {
        try {
            Collection<ProviderModel> providerModelList = SERVICE_REPOSITORY.getExportedServices();
            ProviderModel providerModel = providerModelList.stream()
                    .filter(p -> p.getServiceMetadata().getDisplayServiceKey().startsWith(SERVICE_NAME))
                    .findAny().orElseThrow(() -> new HandlerException("找不到 dubbo 提供者 " + SERVICE_NAME));
            List<ProviderModel.RegisterStatedURL> statedUrls = providerModel.getStatedUrl();
            for (ProviderModel.RegisterStatedURL statedURL : statedUrls) {
                if (!statedURL.isRegistered()) {
                    Registry registry = REGISTER_FACTORY.getRegistry(statedURL.getRegistryUrl());
                    registry.register(statedURL.getProviderUrl());
                    statedURL.setRegistered(true);
                }
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    /**
     * 注意: 该方法的实现与 dubbo 版本有关，更改dubbo的版本有可能导致该方法需要重写。
     *
     * @since 1.3.1
     */
    @Override
    public void offline() throws HandlerException {
        try {
            Collection<ProviderModel> providerModelList = SERVICE_REPOSITORY.getExportedServices();
            ProviderModel providerModel = providerModelList.stream()
                    .filter(p -> p.getServiceMetadata().getDisplayServiceKey().startsWith(SERVICE_NAME))
                    .findAny().orElseThrow(() -> new HandlerException("找不到 dubbo 提供者 " + SERVICE_NAME));
            List<ProviderModel.RegisterStatedURL> statedUrls = providerModel.getStatedUrl();
            for (ProviderModel.RegisterStatedURL statedURL : statedUrls) {
                if (statedURL.isRegistered()) {
                    Registry registry = REGISTER_FACTORY.getRegistry(statedURL.getRegistryUrl());
                    registry.unregister(statedURL.getProviderUrl());
                    statedURL.setRegistered(false);
                }
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
