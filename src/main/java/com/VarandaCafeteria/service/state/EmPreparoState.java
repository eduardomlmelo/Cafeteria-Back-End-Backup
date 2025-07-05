package com.VarandaCafeteria.service.state;

import com.VarandaCafeteria.model.entity.Pedido;
import com.VarandaCafeteria.model.enums.EstadoPedido;

public class EmPreparoState implements EstadoPedidoState {

    @Override
    public void proximo(Pedido pedido) {
        pedido.setEstado(EstadoPedido.PRONTO);
    }

    @Override
    public String getNome() {
        return "EM_PREPARO";
    }
}
