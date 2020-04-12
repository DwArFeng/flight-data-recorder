import com.dwarfeng.dcti.stack.bean.dto.TimedValue
import com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperMaker
import com.dwarfeng.fdr.stack.exception.MapperException

/**
 * 截取 timedValues 前几个值的映射器脚本。
 * <p> 截取的个数由 args[0] 确定。
 */
@SuppressWarnings("GrPackage")
class ExampleMapperProcessor implements GroovyMapperMaker.Processor {

    @Override
    List<TimedValue> map(List<TimedValue> timedValues, Object[] args) throws MapperException {
        try {
            int size = args[0] as int
            size = Math.min(timedValues.size(), size)
            return timedValues.subList(0, size)
        } catch (Exception e) {
            throw new MapperException(e)
        }
    }
}
