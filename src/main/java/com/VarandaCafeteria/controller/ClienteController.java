package com.VarandaCafeteria.controller;

import com.VarandaCafeteria.dto.CarteiraResponseDTO;
import com.VarandaCafeteria.dto.ClienteLoginDTO;
import com.VarandaCafeteria.dto.ClienteRequestDTO;
import com.VarandaCafeteria.dto.ClienteResponseDTO;
import com.VarandaCafeteria.model.entity.Cliente;
import com.VarandaCafeteria.dao.ClienteDAO;

import com.VarandaCafeteria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cadastro de novo cliente
     */
    @PostMapping
    public ClienteResponseDTO criarConta(@RequestBody ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha());
        cliente.setCarteiraDigital(dto.getCarteiraDigital());

        Cliente salvo = clienteRepository.save(cliente);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setId(salvo.getId());
        response.setEmail(salvo.getEmail());
        return response;
    }

    /**
     * Login simples (sem autenticação real por enquanto)
     */
    @PostMapping("/login")
    public ResponseEntity<ClienteResponseDTO> login(@RequestBody ClienteLoginDTO loginDTO) {
        Cliente cliente = clienteRepository.findByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setEmail(cliente.getEmail());
        return ResponseEntity.ok(dto);
    }

    /**
     * Consulta o saldo da carteira digital de um cliente
     */
    @GetMapping("/{id}/carteira")
    public ResponseEntity<CarteiraResponseDTO> consultarCarteira(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        CarteiraResponseDTO dto = new CarteiraResponseDTO();
        dto.setIdCliente(cliente.getId());
        dto.setSaldo(cliente.getCarteiraDigital());

        return ResponseEntity.ok(dto);
    }
}
