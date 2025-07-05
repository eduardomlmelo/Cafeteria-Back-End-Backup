package com.VarandaCafeteria.dao;

import com.VarandaCafeteria.model.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoDAO {
    List<Produto> buscarTodos();
    Optional<Produto> buscarPorId(Long id);
    Produto salvar(Produto produto);
}
