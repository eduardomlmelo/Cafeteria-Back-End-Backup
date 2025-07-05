package com.VarandaCafeteria.dto;

import lombok.Data;

@Data
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private Double preco;
}
