package com.VarandaCafeteria.service.decorator;

import com.VarandaCafeteria.service.factory.Bebida;

// service/decorator/BebidaDecorator.java
public abstract class BebidaDecorator implements Bebida {
    protected Bebida bebida;

    public BebidaDecorator(Bebida bebida) {
        this.bebida = bebida;
    }
}
