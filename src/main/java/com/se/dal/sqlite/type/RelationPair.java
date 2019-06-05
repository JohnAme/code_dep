package com.se.dal.sqlite.type;

import java.io.Serializable;

/**
 * Created by niejia on 15/2/28.
 */
public class RelationPair implements Serializable {
    private Integer key;
    private Integer value;

    public RelationPair(Integer integer1, Integer integer2) {
//        super(integer1, integer2);
        this.key = integer1;
        this.value = integer2;
    }

    public Integer getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        RelationPair that = (RelationPair) y;
        if (this.key != that.key) return false;
        if (this.value != that.value) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + key.hashCode();
        hash = 31 * hash + value.hashCode();
        return hash;
    }
}
