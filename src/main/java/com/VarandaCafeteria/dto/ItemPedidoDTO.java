package com.VarandaCafeteria.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    private String bebidaBase;
    private List<String> adicionais;

    // Getters e Setters
}
