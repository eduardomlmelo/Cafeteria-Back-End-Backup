package com.VarandaCafeteria.service.strategy;

// service/strategy/DescontoFidelidade.java
public class DescontoFidelidade implements DescontoStrategy {
    public double aplicarDesconto(double valor) {
        return valor * 0.9; // 10% de desconto
    }
}
