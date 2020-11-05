package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.MapperHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class MapLocalCacheHandlerImpl implements MapLocalCacheHandler {

    @Autowired
    private MapperHandler mapperHandler;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, Mapper> contextMap = new HashMap<>();
    private final Set<String> notExistTypes = new HashSet<>();

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Mapper getMapper(String mapperType) throws HandlerException {
        try {
            lock.readLock().lock();
            try {
                if (contextMap.containsKey(mapperType)) {
                    return contextMap.get(mapperType);
                }
                if (notExistTypes.contains(mapperType)) {
                    return null;
                }
            } finally {
                lock.readLock().unlock();
            }
            lock.writeLock().lock();
            try {
                if (contextMap.containsKey(mapperType)) {
                    return contextMap.get(mapperType);
                }
                if (notExistTypes.contains(mapperType)) {
                    return null;
                }
                Mapper mapper;
                try {
                    mapper = mapperHandler.make(mapperType);
                } catch (UnsupportedMapperTypeException e) {
                    notExistTypes.add(mapperType);
                    return null;
                }
                contextMap.put(mapperType, mapper);
                return mapper;
            } finally {
                lock.writeLock().unlock();
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            contextMap.clear();
            notExistTypes.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
