package com.dwarfeng.fdr.impl.dao.fuh.dao.preset;

import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class CategoryPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        if (CategoryMaintainService.CHILD_FOR_PARENT.equals(s)) {
            childForParent(detachedCriteria, objects);
        } else {
            throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForParent(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            if (Objects.isNull(objects[0])) {
                detachedCriteria.add(Restrictions.isNull("parentLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                detachedCriteria.add(Restrictions.eqOrIsNull("parentLongId", longIdKey.getLongId()));
            }
            detachedCriteria.addOrder(Order.asc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
