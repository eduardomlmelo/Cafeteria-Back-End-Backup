package com.database.Services;

import com.database.Entities.Adicional;
import com.database.Entities.Interfaces.AdicionalRepository;
import com.database.dto.AdicionalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdicionalService {

    @Autowired
    private AdicionalRepository adicionalRepository;

    public List<AdicionalDTO> listarTodos() {
        return adicionalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdicionalDTO buscarPorId(Integer id) {
        Optional<Adicional> adicional = adicionalRepository.findById(id);
        return adicional.map(this::convertToDTO).orElse(null);
    }

    public AdicionalDTO salvar(AdicionalDTO adicionalDTO) {
        Adicional adicional = convertToEntity(adicionalDTO);
        Adicional savedAdicional = adicionalRepository.save(adicional);
        return convertToDTO(savedAdicional);
    }

    public AdicionalDTO atualizar(AdicionalDTO adicionalDTO) {
        if (adicionalRepository.existsById(adicionalDTO.getId())) {
            Adicional adicional = convertToEntity(adicionalDTO);
            Adicional updatedAdicional = adicionalRepository.save(adicional);
            return convertToDTO(updatedAdicional);
        }
        return null;
    }

    public boolean deletar(Integer id) {
        if (adicionalRepository.existsById(id)) {
            adicionalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private AdicionalDTO convertToDTO(Adicional adicional) {
        AdicionalDTO dto = new AdicionalDTO();
        dto.setId(adicional.getId());
        dto.setNome(adicional.getNome());
        dto.setValor(adicional.getValor());
        return dto;
    }

    private Adicional convertToEntity(AdicionalDTO dto) {
        Adicional adicional = new Adicional();
        adicional.setId(dto.getId());
        adicional.setNome(dto.getNome());
        adicional.setValor(dto.getValor());
        return adicional;
    }
}