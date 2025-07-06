package com.database.Services;

import com.database.Entities.Item;
import com.database.Entities.Interfaces.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Optional<Item> buscarPorId(Integer id) {
        return itemRepository.findById(id);
    }

    public Item salvar(Item item) {
        return itemRepository.save(item);
    }

    public void deletar(Integer id) {
        itemRepository.deleteById(id);
    }
}
