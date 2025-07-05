package com.VarandaCafeteria.dto;

import lombok.Data;
import java.util.List;

@Data
public class ItemPedidoResponseDTO {
    private String bebidaBase;
    private List<String> adicionais;
    private Double preco;
}
