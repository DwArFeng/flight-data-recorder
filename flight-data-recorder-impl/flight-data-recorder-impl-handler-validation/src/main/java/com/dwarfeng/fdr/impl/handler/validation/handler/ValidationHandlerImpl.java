package com.dwarfeng.fdr.impl.handler.validation.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ValidationException;
import com.dwarfeng.fdr.stack.handler.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationHandlerImpl implements ValidationHandler {

    @Autowired
    private ValidationHandlerDelegate delegate;

    @Override
    public void uuidKeyValidation(UuidKey uuidKey) throws ValidationException {
        delegate.uuidKeyValidation(uuidKey);
    }

    @Override
    public void categoryValidation(Category category) throws ValidationException {
        delegate.categoryValidation(category);
    }

    @Override
    public void lookupPagingInfoValidation(LookupPagingInfo lookupPagingInfo) throws ValidationException {
        delegate.lookupPagingInfoValidation(lookupPagingInfo);
    }

    @Override
    public void pointValidation(Point point) throws ValidationException {
        delegate.pointValidation(point);
    }

    @Override
    public void filterInfoValidation(FilterInfo filterInfo) throws ValidationException {
        delegate.filterInfoValidation(filterInfo);
    }

    @Override
    public void triggerInfoValidation(TriggerInfo triggerInfo) throws ValidationException {
        delegate.triggerInfoValidation(triggerInfo);
    }
}
