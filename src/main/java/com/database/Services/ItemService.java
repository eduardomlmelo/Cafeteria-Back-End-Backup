package com.database.Services;

import com.database.Entities.Item;
import com.database.Entities.Adicional;
import com.database.Entities.Interfaces.ItemRepository;
import com.database.Entities.Interfaces.AdicionalRepository;
import com.database.dto.ItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashSet;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AdicionalRepository adicionalRepository;

    public List<ItemDTO> listarTodos() {
        return itemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemDTO buscarPorId(Integer id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(this::convertToDTO).orElse(null);
    }

    public ItemDTO salvar(ItemDTO itemDTO) {
        Item item = convertToEntity(itemDTO);
        Item savedItem = itemRepository.save(item);
        return convertToDTO(savedItem);
    }

    public ItemDTO atualizar(ItemDTO itemDTO) {
        if (itemRepository.existsById(itemDTO.getId())) {
            Item item = convertToEntity(itemDTO);
            Item updatedItem = itemRepository.save(item);
            return convertToDTO(updatedItem);
        }
        return null;
    }

    public boolean deletar(Integer id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean adicionarAdicional(Integer itemId, Integer adicionalId) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        Optional<Adicional> adicionalOpt = adicionalRepository.findById(adicionalId);
        
        if (itemOpt.isPresent() && adicionalOpt.isPresent()) {
            Item item = itemOpt.get();
            Adicional adicional = adicionalOpt.get();
            
            if (item.getAdicionais() == null) {
                item.setAdicionais(new HashSet<>());
            }
            
            item.getAdicionais().add(adicional);
            itemRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean removerAdicional(Integer itemId, Integer adicionalId) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        Optional<Adicional> adicionalOpt = adicionalRepository.findById(adicionalId);
        
        if (itemOpt.isPresent() && adicionalOpt.isPresent()) {
            Item item = itemOpt.get();
            Adicional adicional = adicionalOpt.get();
            
            if (item.getAdicionais() != null) {
                item.getAdicionais().remove(adicional);
                itemRepository.save(item);
                return true;
            }
        }
        return false;
    }

    private ItemDTO convertToDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setNome(item.getNome());
        dto.setPreco(item.getPreco());
        
        if (item.getAdicionais() != null) {
            dto.setAdicionalIds(item.getAdicionais().stream()
                    .map(adicional -> adicional.getId())
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private Item convertToEntity(ItemDTO dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setNome(dto.getNome());
        item.setPreco(dto.getPreco());
        return item;
    }
}