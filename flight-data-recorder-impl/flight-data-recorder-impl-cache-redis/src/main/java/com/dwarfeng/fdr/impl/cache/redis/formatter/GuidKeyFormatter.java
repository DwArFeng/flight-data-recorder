package com.dwarfeng.fdr.impl.cache.redis.formatter;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import org.springframework.stereotype.Component;

@Component("guidKeyFormatter")
public class GuidKeyFormatter implements Formatter<GuidKey> {

    @Override
    public String format(String prefix, GuidKey target) {
        return prefix + target.getGuid();
    }
}
