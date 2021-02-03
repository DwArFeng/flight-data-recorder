package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TriggerInfoPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case TriggerInfoMaintainService.CHILD_FOR_POINT:
                childForPoint(detachedCriteria, objects);
                break;
            case TriggerInfoMaintainService.CHILD_FOR_POINT_SET:
                childForPointSet(detachedCriteria, objects);
                break;
            case TriggerInfoMaintainService.ENABLED_CHILD_FOR_POINT:
                enabledChildForPoint(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForPoint(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("pointLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForPointSet(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("pointLongId"));
            } else {
                @SuppressWarnings("unchecked")
                List<LongIdKey> longIdKeys = (List<LongIdKey>) objects[0];
                if (longIdKeys.isEmpty()) {
                    detachedCriteria.add(Restrictions.isNull("pointLongId"));
                } else {
                    detachedCriteria.add(Restrictions.in("pointLongId", longList(longIdKeys)));
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void enabledChildForPoint(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.eqOrIsNull("enabled", true));
            detachedCriteria.add(Restrictions.isNull("pointLongId"));
        } else if (object instanceof LongIdKey) {
            detachedCriteria.add(Restrictions.eqOrIsNull("enabled", true));
            detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", ((LongIdKey) object).getLongId()));
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private List<Long> longList(List<LongIdKey> list) {
        return list.stream().map(LongIdKey::getLongId).collect(Collectors.toList());
    }
}
