package com.database.Entities.Interfaces;

import com.database.Entities.ItemAdicional;
import com.database.Entities.ItemAdicionalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAdicionalRepository extends JpaRepository<ItemAdicional, ItemAdicionalId> {
    // métodos prontos como findAll(), findById(), save(), deleteById() já estão implementados por JpaRepository

    // Possíveis métodos customizados, como:
    // List<ItemAdicional> findByItem_Id(Integer itemId);
}
