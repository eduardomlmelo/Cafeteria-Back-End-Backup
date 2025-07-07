package com.database.Controllers;

import com.database.Entities.ItemAdicional;
import com.database.Entities.ItemAdicionalId;
import com.database.Services.ItemAdicionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-adicionais")
public class ItemAdicionalController {

    @Autowired
    private ItemAdicionalService itemAdicionalService;

    @GetMapping
    public List<ItemAdicional> listar() {
        return itemAdicionalService.listarTodos();
    }

    @GetMapping("/item/{itemId}/adicional/{adicionalId}")
    public ItemAdicional buscar(@PathVariable Integer itemId, @PathVariable Integer adicionalId) {
        ItemAdicionalId id = new ItemAdicionalId(itemId, adicionalId);
        return itemAdicionalService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public ItemAdicional criar(@RequestBody ItemAdicional itemAdicional) {
        return itemAdicionalService.salvar(itemAdicional);
    }

    @DeleteMapping("/item/{itemId}/adicional/{adicionalId}")
    public void remover(@PathVariable Integer itemId, @PathVariable Integer adicionalId) {
        ItemAdicionalId id = new ItemAdicionalId(itemId, adicionalId);
        itemAdicionalService.deletar(id);
    }
}
