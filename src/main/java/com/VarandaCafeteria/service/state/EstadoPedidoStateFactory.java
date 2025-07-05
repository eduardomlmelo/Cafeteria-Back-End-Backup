package com.VarandaCafeteria.service.state;

import com.VarandaCafeteria.model.enums.EstadoPedido;
import org.springframework.stereotype.Component;

@Component
public class EstadoPedidoStateFactory {

    public EstadoPedidoState getEstado(EstadoPedido estadoAtual) {
        switch (estadoAtual) {
            case REALIZADO:
                return new RealizadoState();
            case EM_PREPARO:
                return new EmPreparoState();
            case PRONTO:
                return new ProntoState();
            case ENTREGUE:
                return new EntregueState();
            default:
                throw new IllegalStateException("Estado desconhecido: " + estadoAtual);
        }
    }
}
