package com.database.Controllers;

import com.database.Entities.Pedido;
import com.database.Services.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return pedidoService.salvar(pedido);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Integer id) {
        pedidoService.deletar(id);
    }
}
