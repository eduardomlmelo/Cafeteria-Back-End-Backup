package com.VarandaCafeteria.service.strategy;

// service/strategy/DescontoPix.java
public class DescontoPix implements DescontoStrategy {
    public double aplicarDesconto(double valor) {
        return valor * 0.95; // 5% de desconto
    }
}

