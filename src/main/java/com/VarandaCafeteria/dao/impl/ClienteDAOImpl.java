package com.VarandaCafeteria.dao.impl;

import com.VarandaCafeteria.dao.ClienteDAO;
import com.VarandaCafeteria.model.entity.Cliente;
import com.VarandaCafeteria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    @Override
    public Optional<Cliente> buscarPorEmailESenha(String email, String senha) {
        return clienteRepository.findByEmailAndSenha(email, senha);
    }
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
