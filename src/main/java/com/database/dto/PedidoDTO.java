package com.database.dto;

import lombok.Data;

@Data
public class PedidoDTO {
    private Integer id;
    private Integer clienteId;
    private String clienteNome;
    private Integer itemId;
    private String itemNome;
    private Float itemPreco;
}