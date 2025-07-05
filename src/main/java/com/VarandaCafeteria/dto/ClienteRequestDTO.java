package com.VarandaCafeteria.dto;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String email;
    private String senha;
    private Double carteiraDigital;
}
