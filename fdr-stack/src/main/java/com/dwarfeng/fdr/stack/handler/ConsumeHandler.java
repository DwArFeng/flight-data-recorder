package com.dwarfeng.fdr.stack.handler;

/**
 * 消费处理器。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface ConsumeHandler<E> {

    void accept(E element);

    int usedBufferSize();

    int totalBufferSize();

    boolean canGracefulShutdown();
}
