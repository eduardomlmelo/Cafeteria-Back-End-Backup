package com.VarandaCafeteria.service.command;

import com.VarandaCafeteria.dao.PedidoDAO;
import com.VarandaCafeteria.model.entity.Pedido;

public class FinalizarPedidoCommand implements PedidoCommand {

    private Pedido pedido;
    private PedidoDAO pedidoDAO;

    public FinalizarPedidoCommand(Pedido pedido, PedidoDAO pedidoDAO) {
        this.pedido = pedido;
        this.pedidoDAO = pedidoDAO;
    }

    @Override
    public void executar() {
        pedidoDAO.salvar(pedido);
    }
}
