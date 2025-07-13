package com.database.Controllers;

import com.database.Entities.Adicional;
import com.database.Services.AdicionalService;
import com.database.dto.AdicionalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adicionais")
@CrossOrigin(origins = "*")
public class AdicionalController {

    @Autowired
    private AdicionalService adicionalService;

    @GetMapping
    public ResponseEntity<List<AdicionalDTO>> listarTodos() {
        List<AdicionalDTO> adicionais = adicionalService.listarTodos();
        return ResponseEntity.ok(adicionais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdicionalDTO> buscarPorId(@PathVariable Integer id) {
        AdicionalDTO adicional = adicionalService.buscarPorId(id);
        if (adicional != null) {
            return ResponseEntity.ok(adicional);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AdicionalDTO> criar(@RequestBody AdicionalDTO adicionalDTO) {
        try {
            AdicionalDTO novoAdicional = adicionalService.salvar(adicionalDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAdicional);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdicionalDTO> atualizar(@PathVariable Integer id, @RequestBody AdicionalDTO adicionalDTO) {
        try {
            adicionalDTO.setId(id);
            AdicionalDTO adicionalAtualizado = adicionalService.atualizar(adicionalDTO);
            if (adicionalAtualizado != null) {
                return ResponseEntity.ok(adicionalAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = adicionalService.deletar(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}