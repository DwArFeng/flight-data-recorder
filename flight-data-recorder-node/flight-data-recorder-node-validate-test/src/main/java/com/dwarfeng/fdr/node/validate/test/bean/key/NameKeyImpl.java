package com.dwarfeng.fdr.node.validate.test.bean.key;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class NameKeyImpl implements NameKey {

    private static final long serialVersionUID = -9073593020575230461L;

    @NotBlank
    private String name;

    public NameKeyImpl() {
    }

    public NameKeyImpl(String name) {
        this.name = name;
    }

    public static NameKeyImpl of(String name) {
        return new NameKeyImpl(name);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o)) return false;
        if (!(o instanceof NameKey)) return false;

        NameKey that = (NameKey) o;

        return Objects.equals(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NameKeyImpl [name=" + name + "]";
    }

}
