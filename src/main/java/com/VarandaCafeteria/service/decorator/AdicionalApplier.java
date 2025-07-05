package com.VarandaCafeteria.service.decorator;

// service/decorator/AdicionalApplier.java
import com.VarandaCafeteria.service.factory.Bebida;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AdicionalApplier {

    public Bebida aplicarAdicionais(Bebida bebidaBase, List<String> adicionais) {
        for (String adicional : adicionais) {
            switch (adicional.toUpperCase()) {
                case "LEITE":
                    bebidaBase = new ComLeite(bebidaBase);
                    break;
                case "CANELA":
                    bebidaBase = new ComCanela(bebidaBase);
                    break;
                case "ACUCAR":
                    bebidaBase = new ComAcucar(bebidaBase);
                    break;
                default:
                    throw new IllegalArgumentException("Adicional inválido: " + adicional);
            }
        }
        return bebidaBase;
    }
}
