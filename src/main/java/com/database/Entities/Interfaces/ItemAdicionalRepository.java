package com.database.Entities.Interfaces;

import com.database.Entities.ItemAdicional;
import com.database.Entities.ItemAdicionalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAdicionalRepository extends JpaRepository<ItemAdicional, ItemAdicionalId> {
    // Aqui você pode adicionar métodos customizados se quiser, como:
    // List<ItemAdicional> findByItem_Id(Integer itemId);
}
