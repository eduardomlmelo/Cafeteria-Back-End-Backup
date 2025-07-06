package com.VarandaCafeteria.service.observer;

import com.VarandaCafeteria.dto.PedidoResponseDTO;
import com.VarandaCafeteria.model.entity.Pedido;
import com.VarandaCafeteria.service.bo.PedidoBO;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CozinhaObserver implements PedidoObserver {

    private final SimpMessagingTemplate messagingTemplate;
    private final PedidoBO pedidoBO;

    public CozinhaObserver(SimpMessagingTemplate messagingTemplate, PedidoBO pedidoBO) {
        this.messagingTemplate = messagingTemplate;
        this.pedidoBO = pedidoBO;
    }

    @Override
    public void atualizar(Pedido pedido) {
        // 📦 DTO completo do pedido
        PedidoResponseDTO dto = pedidoBO.toResponseDTO(pedido);

        // 🔔 Notificação geral (ex: para exibir toast ou badge)
        messagingTemplate.convertAndSend(
                "/topic/cozinha",
                "Novo pedido #" + pedido.getId() + " - " + pedido.getEstado().name()
        );

        // 📋 Novo pedido completo para exibição imediata na lista
        messagingTemplate.convertAndSend(
                "/topic/cozinha/novo-pedido",
                dto
        );

        System.out.println("📢 Cozinha notificada: Pedido #" + pedido.getId());
    }
}
