package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.impl.handler.FilterMaker;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 使用Groovy脚本的过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.5.2
 */
@Component
public class GroovyFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "groovy_filter";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            // 通过Groovy脚本生成处理器。
            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class<?> aClass = classLoader.parseClass(filterInfo.getContent());
            Processor processor = (Processor) aClass.newInstance();
            // 构建过滤器对象。
            GroovyFilter filter = ctx.getBean(GroovyFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
            filter.setProcessor(processor);
            return filter;
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Groovy过滤器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，判断数据点是否通过过滤";
    }

    @Override
    public String provideExampleContent() {
        return "import com.dwarfeng.dcti.stack.bean.dto.DataInfo\n" +
                "import com.dwarfeng.fdr.impl.handler.filter.GroovyFilterMaker\n" +
                "import com.dwarfeng.fdr.stack.bean.entity.FilteredValue\n" +
                "import com.dwarfeng.fdr.stack.exception.FilterException\n" +
                "import com.dwarfeng.subgrade.stack.bean.key.LongIdKey\n" +
                "\n" +
                "/**\n" +
                " * 通过DataInfo的值的长度判断数据信息是否通过过滤的脚本。\n" +
                " * <p> 如果DataInfo中数据的长度大于5，则不通过，否则通过。\n" +
                " */\n" +
                "@SuppressWarnings(\"GrPackage\")\n" +
                "class ExampleFilterProcessor implements GroovyFilterMaker.Processor {\n" +
                "\n" +
                "    @Override\n" +
                "    FilteredValue test(LongIdKey pointIdKey, LongIdKey filterIdKey, DataInfo dataInfo) throws FilterException {\n" +
                "        try {\n" +
                "            def size = dataInfo.getValue().size();\n" +
                "            if (size > 5) {\n" +
                "                return new FilteredValue(\n" +
                "                        null,\n" +
                "                        pointIdKey,\n" +
                "                        filterIdKey,\n" +
                "                        dataInfo.getHappenedDate(),\n" +
                "                        dataInfo.getValue(),\n" +
                "                        \"DataInfo 的值大于 5 个字符\"\n" +
                "                );\n" +
                "            } else {\n" +
                "                return null;\n" +
                "            }\n" +
                "        } catch (Exception e) {\n" +
                "            throw new FilterException(e);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyFilter implements Filter {

        private static final Logger LOGGER = LoggerFactory.getLogger(GroovyFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;
        private Processor processor;

        public GroovyFilter() {
        }

        @Override
        public FilteredValue test(DataInfo dataInfo) throws FilterException {
            try {
                return processor.test(pointKey, filterInfoKey, dataInfo);
            } catch (Exception e) {
                throw new FilterException(e);
            }
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public LongIdKey getFilterInfoKey() {
            return filterInfoKey;
        }

        public void setFilterInfoKey(LongIdKey filterInfoKey) {
            this.filterInfoKey = filterInfoKey;
        }

        public Processor getProcessor() {
            return processor;
        }

        public void setProcessor(Processor processor) {
            this.processor = processor;
        }

        @Override
        public String toString() {
            return "GroovyFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    ", groovyProcessor=" + processor +
                    '}';
        }
    }

    /**
     * Groovy处理器。
     *
     * @author DwArFeng
     * @since 1.5.2
     */
    public interface Processor {

        /**
         * 测试一个数据是否能通过过滤器。
         *
         * <p> 如果指定的数据不能通过过滤器，则返回被过滤的数据值;否则返回 null。</>
         *
         * @param pointIdKey  数据点的主键。
         * @param filterIdKey 过滤器的主键。
         * @param dataInfo    指定的数据。
         * @return 被过滤的数据值，其主键为 null 即可。
         * @throws FilterException 过滤器异常。
         */
        FilteredValue test(LongIdKey pointIdKey, LongIdKey filterIdKey, DataInfo dataInfo) throws FilterException;
    }
}
