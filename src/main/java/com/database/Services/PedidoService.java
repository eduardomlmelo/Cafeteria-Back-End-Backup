package com.database.Services;

import com.database.Entities.Pedido;
import com.database.Entities.Cliente;
import com.database.Entities.Item;
import com.database.Entities.Interfaces.PedidoRepository;
import com.database.Entities.Interfaces.ClienteRepository;
import com.database.Entities.Interfaces.ItemRepository;
import com.database.dto.PedidoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO buscarPorId(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(this::convertToDTO).orElse(null);
    }

    public List<PedidoDTO> buscarPorCliente(Integer clienteId) {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getCliente() != null && pedido.getCliente().getId().equals(clienteId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO salvar(PedidoDTO pedidoDTO) {
        Pedido pedido = convertToEntity(pedidoDTO);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return convertToDTO(savedPedido);
    }

    public PedidoDTO atualizar(PedidoDTO pedidoDTO) {
        if (pedidoRepository.existsById(pedidoDTO.getId())) {
            Pedido pedido = convertToEntity(pedidoDTO);
            Pedido updatedPedido = pedidoRepository.save(pedido);
            return convertToDTO(updatedPedido);
        }
        return null;
    }

    public boolean deletar(Integer id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        
        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setClienteNome(pedido.getCliente().getNome());
        }
        
        if (pedido.getItem() != null) {
            dto.setItemId(pedido.getItem().getId());
            dto.setItemNome(pedido.getItem().getNome());
            dto.setItemPreco(pedido.getItem().getPreco());
        }
        
        return dto;
    }

    private Pedido convertToEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        
        if (dto.getClienteId() != null) {
            Optional<Cliente> cliente = clienteRepository.findById(dto.getClienteId());
            cliente.ifPresent(pedido::setCliente);
        }
        
        if (dto.getItemId() != null) {
            Optional<Item> item = itemRepository.findById(dto.getItemId());
            item.ifPresent(pedido::setItem);
        }
        
        return pedido;
    }
}