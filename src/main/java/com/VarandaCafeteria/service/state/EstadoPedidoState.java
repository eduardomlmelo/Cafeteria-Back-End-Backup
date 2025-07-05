package com.VarandaCafeteria.service.state;

import com.VarandaCafeteria.model.entity.Pedido;

public interface EstadoPedidoState {
    void proximo(Pedido pedido);
    String getNome();
}
