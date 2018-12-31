package com.team404.bookstore.entity;

import java.util.Objects;

public class CountsEntity {
    private int id;
    private int counts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountsEntity that = (CountsEntity) o;
        return id == that.id &&
                counts == that.counts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, counts);
    }
}
