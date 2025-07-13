package com.database.dto;

import lombok.Data;

@Data
public class ItemAdicionalDTO {
    private Integer itemId;
    private String itemNome;
    private Integer adicionalId;
    private String adicionalNome;
    private Float adicionalValor;
}