package com.VarandaCafeteria.service.command;

import java.util.ArrayList;
import java.util.List;

public class PedidoInvoker {

    private List<PedidoCommand> historico = new ArrayList<>();

    public void executarComando(PedidoCommand comando) {
        comando.executar();
        historico.add(comando); // opcional: manter histórico
    }

    public List<PedidoCommand> getHistorico() {
        return historico;
    }
}
