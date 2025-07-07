package com.database.Controllers;

import com.database.Entities.Adicional;
import com.database.Services.AdicionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adicionais")
public class AdicionalController {

    @Autowired
    private AdicionalService adicionalService;

    @GetMapping
    public List<Adicional> listar() {
        return adicionalService.listarTodos();
    }

    @GetMapping("/{id}")
    public Adicional buscar(@PathVariable Integer id) {
        return adicionalService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Adicional criar(@RequestBody Adicional adicional) {
        return adicionalService.salvar(adicional);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Integer id) {
        adicionalService.deletar(id);
    }
}
