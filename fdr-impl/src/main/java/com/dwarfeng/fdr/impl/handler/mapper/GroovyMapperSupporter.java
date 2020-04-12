package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.impl.handler.MapperSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 使用Groovy脚本的映射器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class GroovyMapperSupporter implements MapperSupporter {

    public static final String SUPPORT_TYPE = "groovy_mapper";
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyMapperSupporter.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Groovy映射器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，实现对带有时间数据的映射";
    }

    @Override
    public String provideArgsIllustrate() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("------------------------第0个元素------------------------\n");
            Resource resource = applicationContext.getResource("classpath:groovy/ExampleFilterProcessor.groovy");
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                sb.append(sout.toString());
            }
            sb.append("------------------------第1个元素------------------------\n");
            sb.append(5);
            return sb.toString();
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleFilterProcessor.groovy 时出现异常", e);
            return "";
        }
    }
}
