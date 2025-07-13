package com.database.Services;

import com.database.Entities.Cliente;
import com.database.Entities.Interfaces.ClienteRepository;
import com.database.dto.ClienteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(this::convertToDTO).orElse(null);
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    public ClienteDTO atualizar(ClienteDTO clienteDTO) {
        if (clienteRepository.existsById(clienteDTO.getId())) {
            Cliente cliente = convertToEntity(clienteDTO);
            Cliente updatedCliente = clienteRepository.save(cliente);
            return convertToDTO(updatedCliente);
        }
        return null;
    }

    public boolean deletar(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        
        if (cliente.getPedidos() != null) {
            dto.setPedidoIds(cliente.getPedidos().stream()
                    .map(pedido -> pedido.getId())
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        return cliente;
    }
}