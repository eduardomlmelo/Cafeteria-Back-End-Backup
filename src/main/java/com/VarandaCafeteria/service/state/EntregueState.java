package com.VarandaCafeteria.service.state;

import com.VarandaCafeteria.model.entity.Pedido;

public class EntregueState implements EstadoPedidoState {

    @Override
    public void proximo(Pedido pedido) {
        System.out.println("Pedido já foi entregue. Não há próximos estados.");
    }

    @Override
    public String getNome() {
        return "ENTREGUE";
    }
}
