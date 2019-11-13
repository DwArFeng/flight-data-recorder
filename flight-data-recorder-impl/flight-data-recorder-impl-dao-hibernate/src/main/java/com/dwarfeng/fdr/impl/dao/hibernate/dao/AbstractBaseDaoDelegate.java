package com.dwarfeng.fdr.impl.dao.hibernate.dao;

import com.dwarfeng.fdr.stack.bean.constraint.Constraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 抽象基础数据访问层代理。
 *
 * @param <K> 主键对应的类。
 * @param <E> 实体对应的类。
 * @param <C> 约束对应的类。
 */
@Validated
public abstract class AbstractBaseDaoDelegate<K extends Key, E extends Entity<K>, C extends Constraint<E>> {

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

    public abstract E get(@NotNull K key) throws DaoException;

    public abstract K insert(@NotNull E element) throws DaoException;

    public abstract K update(@NotNull E element) throws DaoException;

    public abstract void delete(@NotNull K key) throws DaoException;

    public abstract List<E> select(@NotNull C constraint, @NotNull LookupPagingInfo lookupPagingInfo) throws DaoException;

    public boolean exists(@NotNull K key) throws DaoException {
        return Objects.nonNull(get(key));
    }

    public boolean existsAll(@NotNull Collection<K> c) throws DaoException {
        for (K key : c) {
            if (Objects.isNull(get(key))) return false;
        }
        return true;
    }

    public boolean existsNon(@NotNull Collection<K> c) throws DaoException {
        for (K key : c) {
            if (Objects.nonNull(get(key))) return false;
        }
        return true;
    }

    public void batchInsert(@NotNull Collection<E> c) throws DaoException {
        for (E element : c) {
            insert(element);
        }
    }

    public void batchUpdate(@NotNull Collection<E> c) throws DaoException {
        for (E element : c) {
            update(element);
        }
    }

    public void batchDelete(@NotNull Collection<K> c) throws DaoException {
        for (K key : c) {
            delete(key);
        }
    }

    public int selectCount(@NotNull C constraint) throws DaoException {
        return select(constraint, DISABLED_LOOKUP_PAGING_INFO).size();
    }
}
