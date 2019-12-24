package com.dwarfeng.fdr.impl.cache.redis.formatter;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import org.springframework.stereotype.Component;

@Component("uuidKeyFormatter")
public class UuidKeyFormatter implements Formatter<UuidKey> {

    @Override
    public String format(String prefix, UuidKey target) {
        return prefix + target.getUuid();
    }
}
