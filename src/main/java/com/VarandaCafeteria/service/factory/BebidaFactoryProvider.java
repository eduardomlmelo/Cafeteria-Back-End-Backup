package com.VarandaCafeteria.service.factory;

// service/factory/BebidaFactoryProvider.java
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class BebidaFactoryProvider {

    private final Map<String, BebidaFactory> fabricaPorNome;

    public BebidaFactoryProvider() {
        fabricaPorNome = new HashMap<>();
        fabricaPorNome.put("CAFE", new CafeFactory());
        fabricaPorNome.put("CAPPUCCINO", new CapuccinoFactory());
    }

    public Bebida criarBebida(String tipo) {
        BebidaFactory factory = fabricaPorNome.get(tipo.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException("Tipo de bebida não suportado: " + tipo);
        }
        return factory.criarBebida();
    }
}
