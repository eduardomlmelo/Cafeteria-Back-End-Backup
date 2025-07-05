package com.VarandaCafeteria.controller;

import com.VarandaCafeteria.dto.PedidoRequestDTO;
import com.VarandaCafeteria.dto.PedidoResponseDTO;
import com.VarandaCafeteria.service.bo.PedidoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoBO pedidoBO;

    /**
     * Endpoint para clientes criarem novos pedidos
     */
    @PostMapping
    public PedidoResponseDTO criarPedido(@RequestBody PedidoRequestDTO dto) {
        return pedidoBO.criarPedido(dto); // Retorna DTO diretamente
    }

    /**
     * Endpoint para a cozinha avançar o estado de um pedido
     */
    @PutMapping("/{id}/avancar-estado")
    public PedidoResponseDTO avancarEstado(@PathVariable Long id) {
        return pedidoBO.avancarEstadoDTO(id); // novo método que retorna DTO direto
    }

    /**
     * Endpoint para listar todos os pedidos (ex: painel da cozinha)
     */
    @GetMapping
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoBO.buscarTodosDTO();
    }

    /**
     * Endpoint para buscar um pedido específico por ID
     */
    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoBO.buscarPorIdDTO(id);
    }
}
