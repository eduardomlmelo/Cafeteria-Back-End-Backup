package com.VarandaCafeteria.dao;

import com.VarandaCafeteria.model.entity.Cliente;

import java.util.Optional;

public interface ClienteDAO {
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorEmailESenha(String email, String senha);
    Cliente salvar(Cliente cliente);
}
