package com.database.Controllers;

import com.database.Services.PedidoService;
import com.database.dto.PedidoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Integer id) {
        PedidoDTO pedido = pedidoService.buscarPorId(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> buscarPorCliente(@PathVariable Integer clienteId) {
        List<PedidoDTO> pedidos = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoDTO pedidoDTO) {
        try {
            PedidoDTO novoPedido = pedidoService.salvar(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizar(@PathVariable Integer id, @RequestBody PedidoDTO pedidoDTO) {
        try {
            pedidoDTO.setId(id);
            PedidoDTO pedidoAtualizado = pedidoService.atualizar(pedidoDTO);
            if (pedidoAtualizado != null) {
                return ResponseEntity.ok(pedidoAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = pedidoService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}