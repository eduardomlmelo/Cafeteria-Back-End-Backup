package com.VarandaCafeteria.service.bo;

// service/PagamentoBO.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.VarandaCafeteria.model.enums.TipoPagamento;
import com.VarandaCafeteria.service.strategy.*;

@Service
public class PagamentoBO {

    @Autowired
    private DescontoStrategyFactory strategyFactory;

    public double calcularPrecoFinal(double precoInicial, TipoPagamento tipoPagamento) {
        DescontoStrategy strategy = strategyFactory.getStrategy(tipoPagamento);
        return strategy.aplicarDesconto(precoInicial);
    }
}
