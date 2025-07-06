package com.database.Controllers;

import com.database.Entities.Item;
import com.database.Services.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> listar() {
        return itemService.listarTodos();
    }

    @GetMapping("/{id}")
    public Item buscar(@PathVariable Integer id) {
        return itemService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Item criar(@RequestBody Item item) {
        return itemService.salvar(item);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Integer id) {
        itemService.deletar(id);
    }
}
