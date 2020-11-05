import com.dwarfeng.dcti.stack.bean.dto.DataInfo
import com.dwarfeng.fdr.impl.handler.trigger.GroovyTriggerRegistry
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue
import com.dwarfeng.fdr.stack.exception.TriggerException
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey

/**
 * 通过 DataInfo 的值的长度判断是否触发的脚本。
 * <p> 如果DataInfo 中数据的长度小于 5，则触发，否则不触发。
 */
@SuppressWarnings("GrPackage")
class ExampleTriggerProcessor implements GroovyTriggerRegistry.Processor {

    @Override
    TriggeredValue test(LongIdKey pointIdKey, LongIdKey triggerIdKey, DataInfo dataInfo) throws TriggerException {
        try {
            def size = dataInfo.getValue().size()
            if (size < 5) {
                return new TriggeredValue(
                        null,
                        pointIdKey,
                        triggerIdKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue(),
                        "DataInfo 的值小于 5 个字符"
                )
            } else {
                return null
            }
        } catch (Exception e) {
            throw new TriggerException(e)
        }
    }
}
