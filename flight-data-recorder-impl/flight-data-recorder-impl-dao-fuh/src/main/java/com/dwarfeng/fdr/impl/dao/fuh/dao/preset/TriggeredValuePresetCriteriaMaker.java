package com.dwarfeng.fdr.impl.dao.fuh.dao.preset;

import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TriggeredValuePresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case TriggeredValueMaintainService.CHILD_FOR_POINT:
                childForPoint(detachedCriteria, objects);
                break;
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER:
                childForTrigger(detachedCriteria, objects);
                break;
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER_SET:
                childForTriggerSet(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForPoint(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.isNull("pointLongId"));
        } else if (object instanceof LongIdKey) {
            detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", ((LongIdKey) object).getLongId()));
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForTrigger(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.isNull("triggerLongId"));
        } else if (object instanceof LongIdKey) {
            detachedCriteria.add(Restrictions.eqOrIsNull("triggerLongId", ((LongIdKey) object).getLongId()));
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForTriggerSet(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.isNull("triggerLongId"));
        } else if (object instanceof List) {
            if (((List<?>) object).isEmpty()) {
                detachedCriteria.add(Restrictions.isNull("longId"));
            } else {
                //noinspection unchecked
                detachedCriteria.add(Restrictions.in("triggerLongId", longList((List<LongIdKey>) object)));
            }
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private List<Long> longList(List<LongIdKey> list) {
        return list.stream().map(LongIdKey::getLongId).collect(Collectors.toList());
    }
}
