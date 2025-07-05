package com.VarandaCafeteria.dao.impl;

import com.VarandaCafeteria.dao.ProdutoDAO;
import com.VarandaCafeteria.model.entity.Produto;
import com.VarandaCafeteria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoDAOImpl implements ProdutoDAO {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
