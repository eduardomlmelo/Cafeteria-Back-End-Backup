package com.VarandaCafeteria.service.observer;

import com.VarandaCafeteria.model.entity.Pedido;

public class ClienteObserver implements PedidoObserver {

    private Long idCliente;

    public ClienteObserver(Long idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("Cliente " + idCliente + " foi notificado: Pedido #" + pedido.getId()
                + " está agora em: " + pedido.getEstado());
    }
}
