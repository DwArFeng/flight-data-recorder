package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
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
public class FilterHandlerImpl implements FilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandlerImpl.class);

    @Autowired
    private List<FilterMaker> filterMakers;
    @Autowired
    private Map<LongIdKey, FilterMetaData> map;

    @Override
    public Filter make(FilterInfo filterInfo) throws FilterException {
        try {
            LOGGER.debug("在缓存中查找主键为 " + filterInfo.getKey() + " 的过滤器...");
            Filter filter = findFilterFromCache(filterInfo);
            if (Objects.nonNull(filter)) {
                LOGGER.debug("在缓存中找到了主键为 " + filterInfo.getKey() + " 的过滤器...");
            } else {
                LOGGER.debug("在缓存中没有找到主键为 " + filterInfo.getKey() + " 的过滤器，尝试通过过滤器信息构建新的的过滤器...");
                filter = makeFilter(filterInfo);
                LOGGER.debug("过滤器构建成功!");
                LOGGER.debug("过滤器: " + filter);
            }
            return filter;
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }

    private Filter findFilterFromCache(FilterInfo filterInfo) {
        // 从缓存中查找过滤器元数据。
        FilterMetaData filterMetaData = map.getOrDefault(filterInfo.getKey(), null);
        // 根据元数据判断不存在过滤器的情况。
        if (Objects.isNull(filterMetaData)) {
            return null;
        }
        if (!Objects.equals(filterMetaData.getContent(), filterInfo.getContent())) {
            return null;
        }
        // 向缓存中写入更新信息。
        filterMetaData.setLastCalledDate(new Date());
        map.put(filterInfo.getKey(), filterMetaData);
        // 返回过滤器。
        return filterMetaData.getFilter();
    }

    private Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        // 生成过滤器。
        FilterMaker filterMaker = filterMakers.stream().filter(maker -> maker.supportType(filterInfo.getType()))
                .findFirst().orElseThrow(() -> new UnsupportedFilterTypeException(filterInfo.getType()));
        FilterMetaData filterMetaData = map.getOrDefault(filterInfo.getKey(), new FilterMetaData());
        Filter filter = filterMaker.makeFilter(filterInfo);
        // 向缓存中写入信息。
        filterMetaData.setFilter(filter);
        filterMetaData.setContent(filterInfo.getContent());
        filterMetaData.setLastCalledDate(new Date());
        map.put(filterInfo.getKey(), filterMetaData);
        // 返回过滤器。
        return filter;
    }

    public static class FilterMetaData implements Bean {

        private static final long serialVersionUID = -6808717557659573172L;

        private Filter filter;
        private String content;
        private Date lastCalledDate;

        public FilterMetaData() {
        }

        public FilterMetaData(Filter filter, String content, Date lastCalledDate) {
            this.filter = filter;
            this.content = content;
            this.lastCalledDate = lastCalledDate;
        }

        public Filter getFilter() {
            return filter;
        }

        public void setFilter(Filter filter) {
            this.filter = filter;
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

            FilterMetaData that = (FilterMetaData) o;

            if (filter != null ? !filter.equals(that.filter) : that.filter != null) return false;
            if (content != null ? !content.equals(that.content) : that.content != null) return false;
            return lastCalledDate != null ? lastCalledDate.equals(that.lastCalledDate) : that.lastCalledDate == null;
        }

        @Override
        public int hashCode() {
            int result = filter != null ? filter.hashCode() : 0;
            result = 31 * result + (content != null ? content.hashCode() : 0);
            result = 31 * result + (lastCalledDate != null ? lastCalledDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "FilterMetaData{" +
                    "filter=" + filter +
                    ", content='" + content + '\'' +
                    ", lastCalledDate=" + lastCalledDate +
                    '}';
        }
    }

    @Component
    public static class CleanupTask {

        @Autowired
        private Map<LongIdKey, FilterMetaData> map;

        @Value("${task.filter_cache_cleanup.offset}")
        private long offset;

        @Scheduled(cron = "${task.filter_cache_cleanup.cron}")
        public void runTask() {
            long currentTimeMillis = System.currentTimeMillis();
            List<LongIdKey> keys2Remove = map.entrySet().stream()
                    .filter(entry -> (currentTimeMillis - entry.getValue().getLastCalledDate().getTime()) >= offset)
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            map.keySet().removeAll(keys2Remove);
        }
    }
}
