package com.VarandaCafeteria.service.command;

import com.VarandaCafeteria.model.entity.ItemPedido;
import com.VarandaCafeteria.model.entity.Pedido;

public class AdicionarItemCommand implements PedidoCommand {

    private Pedido pedido;
    private ItemPedido item;

    public AdicionarItemCommand(Pedido pedido, ItemPedido item) {
        this.pedido = pedido;
        this.item = item;
    }

    @Override
    public void executar() {
        pedido.getItens().add(item);
    }
}
