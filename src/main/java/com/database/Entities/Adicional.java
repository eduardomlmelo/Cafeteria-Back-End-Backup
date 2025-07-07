package com.database.Entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Adicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Float valor;

    @ManyToMany(mappedBy = "adicionais")
    private Set<Item> itens;



    // Getters e setters * --> verificar modificadores de acesso

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

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Set<Item> getItens() {
        return itens;
    }

    public void setItens(Set<Item> itens) {
        this.itens = itens;
    }
}
