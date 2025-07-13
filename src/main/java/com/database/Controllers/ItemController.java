package com.database.Controllers;

import com.database.Services.ItemService;
import com.database.dto.ItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarTodos() {
        List<ItemDTO> itens = itemService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> buscarPorId(@PathVariable Integer id) {
        ItemDTO item = itemService.buscarPorId(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ItemDTO> criar(@RequestBody ItemDTO itemDTO) {
        try {
            ItemDTO novoItem = itemService.salvar(itemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> atualizar(@PathVariable Integer id, @RequestBody ItemDTO itemDTO) {
        try {
            itemDTO.setId(id);
            ItemDTO itemAtualizado = itemService.atualizar(itemDTO);
            if (itemAtualizado != null) {
                return ResponseEntity.ok(itemAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = itemService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{itemId}/adicionais/{adicionalId}")
    public ResponseEntity<Void> adicionarAdicional(@PathVariable Integer itemId, @PathVariable Integer adicionalId) {
        try {
            boolean adicionado = itemService.adicionarAdicional(itemId, adicionalId);
            if (adicionado) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{itemId}/adicionais/{adicionalId}")
    public ResponseEntity<Void> removerAdicional(@PathVariable Integer itemId, @PathVariable Integer adicionalId) {
        try {
            boolean removido = itemService.removerAdicional(itemId, adicionalId);
            if (removido) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}