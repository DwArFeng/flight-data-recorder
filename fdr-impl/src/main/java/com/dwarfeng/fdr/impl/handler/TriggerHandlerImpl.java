package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.UnsupportedTriggerTypeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TriggerHandlerImpl implements TriggerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerHandlerImpl.class);

    @Autowired
    private List<TriggerMaker> triggerMakers;
    @Autowired
    private Map<LongIdKey, TriggerMetaData> map;

    @Override
    public Trigger make(TriggerInfo triggerInfo) throws TriggerException {
        try {
            LOGGER.debug("在缓存中查找主键为 " + triggerInfo.getKey() + " 的触发器...");
            Trigger trigger = findTriggerFromCache(triggerInfo);
            if (Objects.nonNull(trigger)) {
                LOGGER.debug("在缓存中找到了主键为 " + triggerInfo.getKey() + " 的触发器...");
            } else {
                LOGGER.debug("在缓存中没有找到主键为 " + triggerInfo.getKey() + " 的触发器，尝试通过触发器信息构建新的的触发器...");
                trigger = makeTrigger(triggerInfo);
                LOGGER.debug("触发器构建成功!");
                LOGGER.debug("触发器: " + trigger);
            }
            return trigger;
        } catch (TriggerException e) {
            throw e;
        } catch (Exception e) {
            throw new TriggerException(e);
        }
    }

    private Trigger findTriggerFromCache(TriggerInfo triggerInfo) {
        // 从缓存中查找触发器元数据。
        TriggerMetaData triggerMetaData = map.getOrDefault(triggerInfo.getKey(), null);
        // 根据元数据判断不存在触发器的情况。
        if (Objects.isNull(triggerMetaData)) {
            return null;
        }
        if (!Objects.equals(triggerMetaData.getContent(), triggerInfo.getContent())) {
            return null;
        }
        // 向缓存中写入更新信息。
        triggerMetaData.setLastCalledDate(new Date());
        map.put(triggerInfo.getKey(), triggerMetaData);
        // 返回触发器。
        return triggerMetaData.getTrigger();
    }

    private Trigger makeTrigger(TriggerInfo triggerInfo) throws TriggerException {
        // 生成触发器。
        TriggerMaker triggerMaker = triggerMakers.stream().filter(maker -> maker.supportType(triggerInfo.getType()))
                .findFirst().orElseThrow(() -> new UnsupportedTriggerTypeException(triggerInfo.getType()));
        TriggerMetaData triggerMetaData = map.getOrDefault(triggerInfo.getKey(), new TriggerMetaData());
        Trigger trigger = triggerMaker.makeTrigger(triggerInfo);
        // 向缓存中写入信息。
        triggerMetaData.setTrigger(trigger);
        triggerMetaData.setContent(triggerInfo.getContent());
        triggerMetaData.setLastCalledDate(new Date());
        map.put(triggerInfo.getKey(), triggerMetaData);
        // 返回触发器。
        return trigger;
    }

    public static class TriggerMetaData implements Bean {

        private static final long serialVersionUID = -6808717557659573172L;

        private Trigger trigger;
        private String content;
        private Date lastCalledDate;

        public TriggerMetaData() {
        }

        public TriggerMetaData(Trigger trigger, String content, Date lastCalledDate) {
            this.trigger = trigger;
            this.content = content;
            this.lastCalledDate = lastCalledDate;
        }

        public Trigger getTrigger() {
            return trigger;
        }

        public void setTrigger(Trigger trigger) {
            this.trigger = trigger;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getLastCalledDate() {
            return lastCalledDate;
        }

        public void setLastCalledDate(Date lastCalledDate) {
            this.lastCalledDate = lastCalledDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TriggerMetaData that = (TriggerMetaData) o;

            if (trigger != null ? !trigger.equals(that.trigger) : that.trigger != null) return false;
            if (content != null ? !content.equals(that.content) : that.content != null) return false;
            return lastCalledDate != null ? lastCalledDate.equals(that.lastCalledDate) : that.lastCalledDate == null;
        }

        @Override
        public int hashCode() {
            int result = trigger != null ? trigger.hashCode() : 0;
            result = 31 * result + (content != null ? content.hashCode() : 0);
            result = 31 * result + (lastCalledDate != null ? lastCalledDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "TriggerMetaData{" +
                    "trigger=" + trigger +
                    ", content='" + content + '\'' +
                    ", lastCalledDate=" + lastCalledDate +
                    '}';
        }
    }

    @Component
    public static class CleanupTask {

        @Autowired
        private Map<LongIdKey, TriggerMetaData> map;

        @Value("${task.trigger_cache_cleanup.offset}")
        private long offset;

        @Scheduled(cron = "${task.trigger_cache_cleanup.cron}")
        public void runTask() {
            long currentTimeMillis = System.currentTimeMillis();
            List<LongIdKey> keys2Remove = map.entrySet().stream()
                    .filter(entry -> (currentTimeMillis - entry.getValue().getLastCalledDate().getTime()) >= offset)
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            map.keySet().removeAll(keys2Remove);
        }
    }
}
