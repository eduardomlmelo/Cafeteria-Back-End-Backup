package com.VarandaCafeteria.service.strategy;

// service/strategy/DescontoNormal.java
public class DescontoNormal implements DescontoStrategy {
    public double aplicarDesconto(double valor) {
        return valor; // sem desconto
    }
}
