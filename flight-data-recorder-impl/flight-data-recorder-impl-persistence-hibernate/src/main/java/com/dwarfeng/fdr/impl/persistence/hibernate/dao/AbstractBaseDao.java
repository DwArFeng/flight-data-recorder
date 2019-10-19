package com.dwarfeng.fdr.impl.persistence.hibernate.dao;

import com.dwarfeng.fdr.stack.bean.constraint.Constraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;
import com.dwarfeng.fdr.stack.dao.BaseDao;
import com.dwarfeng.fdr.stack.exception.DaoException;

import java.util.Collection;
import java.util.Objects;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public abstract class AbstractBaseDao<K extends Key, E extends Entity<K>, C extends Constraint<E>>
        implements BaseDao<K, E, C> {

    private final static LookupPagingInfo DISABLED_LOOKUP_PAGING_INFO = new LookupPagingInfo() {
        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public int getPage() {
            return 0;
        }

        @Override
        public int getRows() {
            return 0;
        }
    };

    @Override
    public boolean exists(K key) throws DaoException {
        return Objects.nonNull(get(key));
    }

    @Override
    public boolean existsAll(Collection<K> c) throws DaoException {
        for (K key : c) {
            if (Objects.isNull(get(key))) return false;
        }
        return true;
    }

    @Override
    public boolean existsNon(Collection<K> c) throws DaoException {
        for (K key : c) {
            if (Objects.nonNull(get(key))) return false;
        }
        return true;
    }

    @Override
    public void batchInsert(Collection<E> c) throws DaoException {
        for (E element : c) {
            insert(element);
        }
    }

    @Override
    public void batchUpdate(Collection<E> c) throws DaoException {
        for (E element : c) {
            update(element);
        }
    }

    @Override
    public void batchDelete(Collection<K> c) throws DaoException {
        for (K key : c) {
            delete(key);
        }
    }

    @Override
    public int selectCount(C constraint) throws DaoException {
        return select(constraint, DISABLED_LOOKUP_PAGING_INFO).size();
    }
}
