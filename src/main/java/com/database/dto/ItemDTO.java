package com.database.dto;

import lombok.Data;
import java.util.List;

@Data
public class ItemDTO {
    private Integer id;
    private String nome;
    private Float preco;
    private List<Integer> adicionalIds;
}