package com.VarandaCafeteria.model.entity;

import com.VarandaCafeteria.model.enums.EstadoPedido;
import com.VarandaCafeteria.model.enums.TipoPagamento;
import com.VarandaCafeteria.service.observer.PedidoObserver;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itens;

    @Transient
    private List<PedidoObserver> observadores = new ArrayList<>();

    private Double precoInicial;
    private Double desconto;
    private Double precoFinal;

    @Enumerated(EnumType.STRING)
    private TipoPagamento pagamento;

    public void adicionarObservador(PedidoObserver observer) {
        observadores.add(observer);
    }

    public void notificarObservadores() {
        for (PedidoObserver obs : observadores) {
            obs.atualizar(this);
        }
    }
}
