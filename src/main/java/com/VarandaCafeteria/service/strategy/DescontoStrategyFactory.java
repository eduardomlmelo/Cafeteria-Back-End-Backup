package com.VarandaCafeteria.service.strategy;

// service/strategy/DescontoStrategyFactory.java
import com.VarandaCafeteria.model.enums.TipoPagamento;
import org.springframework.stereotype.Component;

@Component
public class DescontoStrategyFactory {

    public DescontoStrategy getStrategy(TipoPagamento tipoPagamento) {
        switch (tipoPagamento) {
            case CARTAO_FIDELIDADE:
                return new DescontoFidelidade();
            case PIX:
                return new DescontoPix();
            case NORMAL:
            default:
                return new DescontoNormal();
        }
    }
}

