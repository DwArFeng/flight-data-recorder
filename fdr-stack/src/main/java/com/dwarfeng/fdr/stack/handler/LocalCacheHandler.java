package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 本地缓存处理器。
 * <p>处理器在本地保存数据，缓存中的数据可以保证与数据源保持同步。</p>
 * <p>数据存放在本地，必要时才与数据访问层通信，这有助于程序效率的提升。</p>
 * <p>该处理器线程安全。</p>
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface LocalCacheHandler extends Handler {

    /**
     * 是否包含指定的数据点。
     *
     * @param pointKey 指定的数据点。
     * @return 是否包含指定的数据点。
     * @throws HandlerException 处理器异常。
     */
    boolean existsPoint(LongIdKey pointKey) throws HandlerException;

    /**
     * 获取指定数据点的记录上下文。
     *
     * @param pointKey 指定数据点的记录上下文，或者是null。
     * @return 指定数据点的记录上下文。
     * @throws HandlerException 处理器异常。
     */
    RecordContext getRecordContext(LongIdKey pointKey) throws HandlerException;

    /**
     * 清除本地缓存。
     *
     * @throws HandlerException 处理器异常。
     */
    void clear() throws HandlerException;

    /**
     * 数据记录上下文。
     *
     * @author DwArFeng
     * @since 1.2.0.a
     */
    class RecordContext implements Bean {

        private static final long serialVersionUID = -3206751219977190478L;

        private Point point;
        private List<Filter> filters;
        private List<Trigger> triggers;

        public RecordContext() {
        }

        public RecordContext(Point point, List<Filter> filters, List<Trigger> triggers) {
            this.point = point;
            this.filters = filters;
            this.triggers = triggers;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }

        public List<Trigger> getTriggers() {
            return triggers;
        }

        public void setTriggers(List<Trigger> triggers) {
            this.triggers = triggers;
        }

        @Override
        public String toString() {
            return "RecordContext{" +
                    "point=" + point +
                    ", filters=" + filters +
                    ", triggers=" + triggers +
                    '}';
        }
    }
}
