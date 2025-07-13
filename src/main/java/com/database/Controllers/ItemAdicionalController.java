package com.database.Controllers;

import com.database.Services.ItemAdicionalService;
import com.database.dto.ItemAdicionalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-adicionais")
@CrossOrigin(origins = "*")
public class ItemAdicionalController {

    @Autowired
    private ItemAdicionalService itemAdicionalService;

    @GetMapping
    public ResponseEntity<List<ItemAdicionalDTO>> listarTodos() {
        List<ItemAdicionalDTO> itemAdicionais = itemAdicionalService.listarTodos();
        return ResponseEntity.ok(itemAdicionais);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<ItemAdicionalDTO>> buscarPorItem(@PathVariable Integer itemId) {
        List<ItemAdicionalDTO> itemAdicionais = itemAdicionalService.buscarPorItem(itemId);
        return ResponseEntity.ok(itemAdicionais);
    }

    @GetMapping("/adicional/{adicionalId}")
    public ResponseEntity<List<ItemAdicionalDTO>> buscarPorAdicional(@PathVariable Integer adicionalId) {
        List<ItemAdicionalDTO> itemAdicionais = itemAdicionalService.buscarPorAdicional(adicionalId);
        return ResponseEntity.ok(itemAdicionais);
    }

    @GetMapping("/item/{itemId}/adicional/{adicionalId}")
    public ResponseEntity<ItemAdicionalDTO> buscarPorItemEAdicional(@PathVariable Integer itemId, @PathVariable Integer adicionalId) {
        ItemAdicionalDTO itemAdicional = itemAdicionalService.buscarPorItemEAdicional(itemId, adicionalId);
        if (itemAdicional != null) {
            return ResponseEntity.ok(itemAdicional);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ItemAdicionalDTO> criar(@RequestBody ItemAdicionalDTO itemAdicionalDTO) {
        try {
            ItemAdicionalDTO novoItemAdicional = itemAdicionalService.salvar(itemAdicionalDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoItemAdicional);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/item/{itemId}/adicional/{adicionalId}")
    public ResponseEntity<Void> deletar(@PathVariable Integer itemId, @PathVariable Integer adicionalId) {
        try {
            boolean deletado = itemAdicionalService.deletar(itemId, adicionalId);
            if (deletado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}