package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CategoryMaintainImpl implements CategoryMaintainService {

    @Override
    public Entity get(Key key) throws ServiceException {
        return null;
    }

    @Override
    public List batchGet(List keys) throws ServiceException {
        return null;
    }

    @Override
    public Key insert(Entity element) throws ServiceException {
        return null;
    }

    @Override
    public void batchInsert(Collection c) throws ServiceException {

    }

    @Override
    public Key update(Entity element) throws ServiceException {
        return null;
    }

    @Override
    public void batchUpdate(Collection c) throws ServiceException {

    }

    @Override
    public void delete(Key key) throws ServiceException {

    }

    @Override
    public void batchDelete(Collection c) throws ServiceException {

    }
}
