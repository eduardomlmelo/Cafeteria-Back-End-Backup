package com.database.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClienteDTO {
    private Integer id;
    private String nome;
    private Integer cpf;
    private List<Integer> pedidoIds;
}