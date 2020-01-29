package com.dwarfeng.fdr.impl.dao.fuh.dao.preset;

import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PointPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case PointMaintainService.CHILD_FOR_CATEGORY:
                childForParent(detachedCriteria, objects);
                break;
            case PointMaintainService.CHILD_FOR_CATEGORY_SET:
                childForParentSet(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForParent(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.isNull("categoryLongId"));
        } else if (object instanceof LongIdKey) {
            detachedCriteria.add(Restrictions.eqOrIsNull("categoryLongId", ((LongIdKey) object).getLongId()));
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForParentSet(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.isNull("categoryLongId"));
        } else if (object instanceof List) {
            if (((List<?>) object).isEmpty()) {
                detachedCriteria.add(Restrictions.isNull("longId"));
            } else {
                //noinspection unchecked
                detachedCriteria.add(Restrictions.in("categoryLongId", longList((List<LongIdKey>) object)));
            }
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private List<Long> longList(List<LongIdKey> list) {
        return list.stream().map(LongIdKey::getLongId).collect(Collectors.toList());
    }
}
