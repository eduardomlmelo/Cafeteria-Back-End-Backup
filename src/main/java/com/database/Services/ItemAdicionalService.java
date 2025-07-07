package com.database.Services;

import com.database.Entities.ItemAdicional;
import com.database.Entities.Interfaces.ItemAdicionalRepository;
import com.database.Entities.ItemAdicionalId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemAdicionalService {

    @Autowired
    private ItemAdicionalRepository itemAdicionalRepository;

    public List<ItemAdicional> listarTodos() {
        return itemAdicionalRepository.findAll();
    }

    public Optional<ItemAdicional> buscarPorId(ItemAdicionalId id) {
        return itemAdicionalRepository.findById(id);
    }

    public ItemAdicional salvar(ItemAdicional itemAdicional) {
        return itemAdicionalRepository.save(itemAdicional);
    }

    public void deletar(ItemAdicionalId id) {
        itemAdicionalRepository.deleteById(id);
    }
}
