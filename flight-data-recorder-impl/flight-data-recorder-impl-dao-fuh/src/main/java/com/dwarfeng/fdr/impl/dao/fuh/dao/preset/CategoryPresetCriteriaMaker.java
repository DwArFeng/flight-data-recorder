package com.dwarfeng.fdr.impl.dao.fuh.dao.preset;

import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.Objects;

public class CategoryPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case CategoryMaintainService.CHILD_FOR_PARENT:
                childForParent(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForParent(DetachedCriteria detachedCriteria, Object[] objects) {
        Object object = objects[0];
        if (Objects.isNull(object)) {
            detachedCriteria.add(Restrictions.isNull("parentLongId"));
        } else if (object instanceof LongIdKey) {
            detachedCriteria.add(Restrictions.eqOrIsNull("parentLongId", ((LongIdKey) object).getLongId()));
        } else {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
