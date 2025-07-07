package com.database.Entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Float preco;

    @ManyToMany
    @JoinTable(
            name = "ItemAdicional",
            joinColumns = @JoinColumn(name = "idITEM"),
            inverseJoinColumns = @JoinColumn(name = "idADICIONAL")
    )
    private Set<Adicional> adicionais;

    // Getters e setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Set<Adicional> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(Set<Adicional> adicionais) {
        this.adicionais = adicionais;
    }
}