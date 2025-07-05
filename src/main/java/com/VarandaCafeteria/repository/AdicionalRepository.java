package com.VarandaCafeteria.repository;

import com.VarandaCafeteria.model.entity.Adicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {}
