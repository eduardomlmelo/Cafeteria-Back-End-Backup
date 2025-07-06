package com.database.Services;

import com.database.Entities.Adicional;
import com.database.Entities.Interfaces.AdicionalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdicionalService {

    @Autowired
    private AdicionalRepository adicionalRepository;

    public List<Adicional> listarTodos() {
        return adicionalRepository.findAll();
    }

    public Optional<Adicional> buscarPorId(Integer id) {
        return adicionalRepository.findById(id);
    }

    public Adicional salvar(Adicional adicional) {
        return adicionalRepository.save(adicional);
    }

    public void deletar(Integer id) {
        adicionalRepository.deleteById(id);
    }
}
