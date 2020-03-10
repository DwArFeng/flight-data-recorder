package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.LocalResourceException;

/**
 * 本地资源。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface LocalResourceHandler<E> {

    void append(E element, int retryTimes) throws LocalResourceException;


//    void backup(E element) throws LocalResourceException;
//
//    void check(E element) throws LocalResourceException;
//
//    void check(List<E> elements) throws LocalResourceException;
//
//    void fail(E element) throws LocalResourceException;
//
//    void fail(List<E> elements) throws LocalResourceException;
//
//    void commit()
}
