package com.dwarfeng.fdr.impl.handler;

import java.util.List;

/**
 * 消费者。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface Consumer<E> {

    int getConsumerThread();

    int getBufferSize();

    int getBatchSize();

    long getMaxIdleTime();

    void doConsume(List<E> list);
}
