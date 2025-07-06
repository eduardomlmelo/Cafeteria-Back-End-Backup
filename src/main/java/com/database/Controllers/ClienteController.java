package com.database.Controllers;

import com.database.Entities.Cliente;
import com.database.Services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Integer id) {
        return clienteService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Integer id) {
        clienteService.deletar(id);
    }
}
