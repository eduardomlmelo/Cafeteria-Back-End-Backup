package com.VarandaCafeteria.dto;

import com.VarandaCafeteria.model.enums.Role;
import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String email;
    private String senha;
    private Double carteiraDigital;
    private Role role;
}
