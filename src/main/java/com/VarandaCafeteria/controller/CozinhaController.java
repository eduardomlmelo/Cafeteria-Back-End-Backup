package com.VarandaCafeteria.controller;

import com.VarandaCafeteria.dto.PedidoResponseDTO;
import com.VarandaCafeteria.model.entity.Pedido;
import com.VarandaCafeteria.service.bo.PedidoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CozinhaController {

    @Autowired
    private PedidoBO pedidoBO;

    @PutMapping("/{id}/avancar-estado")
    public PedidoResponseDTO avancarEstado(@PathVariable Long id) {
        return pedidoBO.avancarEstadoDTO(id);
    }
}