import com.dwarfeng.dcti.stack.bean.dto.TimedValue
import com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry
import com.dwarfeng.fdr.stack.exception.MapperException

import static com.dwarfeng.fdr.stack.handler.Mapper.MapData

/**
 * 截取 timedValues 前几个值的映射器脚本。
 * <p> 截取的个数由 args[0] 确定。
 */
@SuppressWarnings('GrPackage')
class ExampleMapperProcessor implements GroovyMapperRegistry.Processor {

    @Override
    List<TimedValue> map(MapData mapData) throws MapperException {
        try {
            int size = mapData.getArgs()[0] as int
            size = Math.min(mapData.getTimedValues().size(), size)
            return mapData.getTimedValues().subList(0, size)
        } catch (Exception e) {
            throw new MapperException(e)
        }
    }
}
