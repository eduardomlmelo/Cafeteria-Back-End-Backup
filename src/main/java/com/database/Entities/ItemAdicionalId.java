package com.database.Entities;

import java.io.Serializable;
import java.util.Objects;

public class ItemAdicionalId implements Serializable {

    private Integer item;
    private Integer adicional;

    // Construtores, equals e hashCode obrigatórios

    public ItemAdicionalId() {}

    public ItemAdicionalId(Integer item, Integer adicional) {
        this.item = item;
        this.adicional = adicional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemAdicionalId)) return false;
        ItemAdicionalId that = (ItemAdicionalId) o;
        return Objects.equals(item, that.item) &&
                Objects.equals(adicional, that.adicional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, adicional);
    }
}
