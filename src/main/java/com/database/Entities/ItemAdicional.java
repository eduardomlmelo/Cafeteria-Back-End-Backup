package com.database.Entities;

import jakarta.persistence.*;

@Entity
@IdClass(ItemAdicionalId.class)
public class ItemAdicional {

    @Id
    @ManyToOne
    @JoinColumn(name = "idITEM")
    private Item item;

    @Id
    @ManyToOne
    @JoinColumn(name = "idADICIONAL")
    private Adicional adicional;

    // Construtores, getters e setters

    public ItemAdicional() {}

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Adicional getAdicional() {
        return adicional;
    }

    public void setAdicional(Adicional adicional) {
        this.adicional = adicional;
    }
}
