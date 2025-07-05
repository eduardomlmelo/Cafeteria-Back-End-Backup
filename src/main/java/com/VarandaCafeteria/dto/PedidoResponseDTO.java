package com.VarandaCafeteria.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private Double precoFinal;
    private String estado;
    private List<ItemPedidoResponseDTO> itens;
}
