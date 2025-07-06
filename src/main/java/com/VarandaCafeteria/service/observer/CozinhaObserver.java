package com.VarandaCafeteria.service.observer;

import com.VarandaCafeteria.dto.PedidoResponseDTO;
import com.VarandaCafeteria.model.entity.Pedido;
import com.VarandaCafeteria.service.bo.PedidoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CozinhaObserver implements PedidoObserver {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PedidoBO pedidoBO;

    @Override
    public void atualizar(Pedido pedido) {

        PedidoResponseDTO dto = pedidoBO.toResponseDTO(pedido);
        messagingTemplate.convertAndSend("/topic/cozinha", dto);


        System.out.println("Cozinha notificada: Novo pedido #" + pedido.getId() + " criado ou atualizado!");
    }
}
