package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * 使用Groovy脚本的过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class GroovyFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "groovy_filter";

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
}
