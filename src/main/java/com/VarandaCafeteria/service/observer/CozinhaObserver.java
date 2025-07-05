package com.VarandaCafeteria.service.observer;

import com.VarandaCafeteria.model.entity.Pedido;

public class CozinhaObserver implements PedidoObserver {

    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("Cozinha notificada: Novo pedido #" + pedido.getId() + " criado ou atualizado!");
    }
}
