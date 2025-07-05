package com.VarandaCafeteria.service.state;

import com.VarandaCafeteria.model.entity.Pedido;
import com.VarandaCafeteria.model.enums.EstadoPedido;

public class RealizadoState implements EstadoPedidoState {

    @Override
    public void proximo(Pedido pedido) {
        pedido.setEstado(EstadoPedido.EM_PREPARO);
    }

    @Override
    public String getNome() {
        return "REALIZADO";
    }
}
