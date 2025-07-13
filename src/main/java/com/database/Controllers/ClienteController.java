package com.database.Controllers;

import com.database.Services.ClienteService;
import com.database.dto.ClienteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id) {
        ClienteDTO cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO novoCliente = clienteService.salvar(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteDTO.setId(id);
            ClienteDTO clienteAtualizado = clienteService.atualizar(clienteDTO);
            if (clienteAtualizado != null) {
                return ResponseEntity.ok(clienteAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = clienteService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}