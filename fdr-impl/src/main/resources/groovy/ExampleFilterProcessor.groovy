import com.dwarfeng.dcti.stack.bean.dto.DataInfo
import com.dwarfeng.fdr.impl.handler.filter.GroovyFilterRegistry
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue
import com.dwarfeng.fdr.stack.exception.FilterException
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey

/**
 * 通过 DataInfo 的值的长度判断数据信息是否通过过滤的脚本。
 * <p> 如果 DataInfo 中数据的长度大于 5，则不通过，否则通过。
 */
@SuppressWarnings("GrPackage")
class ExampleFilterProcessor implements GroovyFilterRegistry.Processor {

    @Override
    FilteredValue test(LongIdKey pointIdKey, LongIdKey filterIdKey, DataInfo dataInfo) throws FilterException {
        try {
            def size = dataInfo.getValue().size()
            if (size > 5) {
                return new FilteredValue(
                        null,
                        pointIdKey,
                        filterIdKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue(),
                        "DataInfo 的值大于 5 个字符"
                )
            } else {
                return null
            }
        } catch (Exception e) {
            throw new FilterException(e)
        }
    }
}
