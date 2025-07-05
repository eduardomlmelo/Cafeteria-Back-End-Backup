package com.VarandaCafeteria.service.state;


import com.VarandaCafeteria.model.entity.Pedido;
import com.VarandaCafeteria.model.enums.EstadoPedido;

public class ProntoState implements EstadoPedidoState {

    @Override
    public void proximo(Pedido pedido) {
        pedido.setEstado(EstadoPedido.ENTREGUE);
    }

    @Override
    public String getNome() {
        return "PRONTO";
    }
}