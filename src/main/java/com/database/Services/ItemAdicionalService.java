package com.database.Services;

import com.database.Entities.ItemAdicional;
import com.database.Entities.ItemAdicionalId;
import com.database.Entities.Item;
import com.database.Entities.Adicional;
import com.database.Entities.Interfaces.ItemAdicionalRepository;
import com.database.Entities.Interfaces.ItemRepository;
import com.database.Entities.Interfaces.AdicionalRepository;
import com.database.dto.ItemAdicionalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemAdicionalService {

    @Autowired
    private ItemAdicionalRepository itemAdicionalRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AdicionalRepository adicionalRepository;

    public List<ItemAdicionalDTO> listarTodos() {
        return itemAdicionalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ItemAdicionalDTO> buscarPorItem(Integer itemId) {
        return itemAdicionalRepository.findAll().stream()
                .filter(ia -> ia.getItem().getId().equals(itemId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ItemAdicionalDTO> buscarPorAdicional(Integer adicionalId) {
        return itemAdicionalRepository.findAll().stream()
                .filter(ia -> ia.getAdicional().getId().equals(adicionalId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemAdicionalDTO buscarPorItemEAdicional(Integer itemId, Integer adicionalId) {
        ItemAdicionalId id = new ItemAdicionalId(itemId, adicionalId);
        Optional<ItemAdicional> itemAdicional = itemAdicionalRepository.findById(id);
        return itemAdicional.map(this::convertToDTO).orElse(null);
    }

    public ItemAdicionalDTO salvar(ItemAdicionalDTO itemAdicionalDTO) {
        ItemAdicional itemAdicional = convertToEntity(itemAdicionalDTO);
        if (itemAdicional != null) {
            ItemAdicional savedItemAdicional = itemAdicionalRepository.save(itemAdicional);
            return convertToDTO(savedItemAdicional);
        }
        return null;
    }

    public boolean deletar(Integer itemId, Integer adicionalId) {
        ItemAdicionalId id = new ItemAdicionalId(itemId, adicionalId);
        if (itemAdicionalRepository.existsById(id)) {
            itemAdicionalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ItemAdicionalDTO convertToDTO(ItemAdicional itemAdicional) {
        ItemAdicionalDTO dto = new ItemAdicionalDTO();
        
        if (itemAdicional.getItem() != null) {
            dto.setItemId(itemAdicional.getItem().getId());
            dto.setItemNome(itemAdicional.getItem().getNome());
        }
        
        if (itemAdicional.getAdicional() != null) {
            dto.setAdicionalId(itemAdicional.getAdicional().getId());
            dto.setAdicionalNome(itemAdicional.getAdicional().getNome());
            dto.setAdicionalValor(itemAdicional.getAdicional().getValor());
        }
        
        return dto;
    }

    private ItemAdicional convertToEntity(ItemAdicionalDTO dto) {
        if (dto.getItemId() == null || dto.getAdicionalId() == null) {
            return null;
        }

        Optional<Item> itemOpt = itemRepository.findById(dto.getItemId());
        Optional<Adicional> adicionalOpt = adicionalRepository.findById(dto.getAdicionalId());
        
        if (itemOpt.isPresent() && adicionalOpt.isPresent()) {
            ItemAdicional itemAdicional = new ItemAdicional();
            itemAdicional.setItem(itemOpt.get());
            itemAdicional.setAdicional(adicionalOpt.get());
            return itemAdicional;
        }
        
        return null;
    }
}