package com.VarandaCafeteria.service.bo;

import com.VarandaCafeteria.dto.*;
import com.VarandaCafeteria.model.entity.*;
import com.VarandaCafeteria.model.enums.EstadoPedido;
import com.VarandaCafeteria.model.enums.TipoPagamento;
import com.VarandaCafeteria.dao.ClienteDAO;
import com.VarandaCafeteria.dao.PedidoDAO;
import com.VarandaCafeteria.repository.PedidoRepository;
import com.VarandaCafeteria.repository.ProdutoRepository;
import com.VarandaCafeteria.service.decorator.AdicionalApplier;
import com.VarandaCafeteria.service.factory.Bebida;
import com.VarandaCafeteria.service.factory.BebidaFactoryProvider;
import com.VarandaCafeteria.service.bo.PagamentoBO;
import com.VarandaCafeteria.service.observer.ClienteObserver;
import com.VarandaCafeteria.service.observer.CozinhaObserver;
import com.VarandaCafeteria.service.state.EstadoPedidoState;
import com.VarandaCafeteria.service.state.EstadoPedidoStateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.VarandaCafeteria.service.command.AdicionarItemCommand;
import com.VarandaCafeteria.service.command.FinalizarPedidoCommand;
import com.VarandaCafeteria.service.command.PedidoInvoker;




import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoBO {

    @Autowired
    private BebidaFactoryProvider bebidaFactoryProvider;

    @Autowired
    private AdicionalApplier adicionalApplier;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private PagamentoBO pagamentoBO;

    @Autowired
    private EstadoPedidoStateFactory estadoPedidoStateFactory;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteDAO.buscarPorId(dto.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItens(new ArrayList<>());
        pedido.setPagamento(dto.getTipoPagamento());

        double precoTotal = 0.0;

        // ⏺ Criamos o invocador de comandos
        PedidoInvoker invoker = new PedidoInvoker();

        for (ItemPedidoDTO itemDto : dto.getItens()) {
            Bebida bebida = bebidaFactoryProvider.criarBebida(itemDto.getBebidaBase());
            bebida = adicionalApplier.aplicarAdicionais(bebida, itemDto.getAdicionais());

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setDescricao(bebida.getDescricao());
            item.setPreco(bebida.getPreco());

            List<Adicional> adicionais = new ArrayList<>();
            for (String nomeAdicional : itemDto.getAdicionais()) {
                Produto adicionalProduto = produtoRepository.findByNome(nomeAdicional)
                        .orElseThrow(() -> new IllegalArgumentException("Adicional não encontrado: " + nomeAdicional));

                Adicional adicional = new Adicional();
                adicional.setItemPedido(item);
                adicional.setProduto(adicionalProduto);

                adicionais.add(adicional);
            }
            item.setAdicionais(adicionais);

            // ⏺ Encapsula a ação de adicionar item
            AdicionarItemCommand cmdAdicionar = new AdicionarItemCommand(pedido, item);
            invoker.executarComando(cmdAdicionar);

            precoTotal += bebida.getPreco();
        }

        pedido.setPrecoInicial(precoTotal);

        // ⏺ Aplica desconto com Strategy
        double precoFinal = pagamentoBO.calcularPrecoFinal(precoTotal, dto.getTipoPagamento());
        pedido.setPrecoFinal(precoFinal);
        pedido.setDesconto(precoTotal - precoFinal);

        // ⏺ Define estado inicial
        pedido.setEstado(EstadoPedido.REALIZADO);

        // ⏺ Atualiza a carteira do cliente
        cliente.setCarteiraDigital(cliente.getCarteiraDigital() - precoFinal);
        clienteDAO.salvar(cliente);


        // ⏺ Adiciona observers
        pedido.adicionarObservador(new CozinhaObserver(messagingTemplate, this));
        pedido.adicionarObservador(new ClienteObserver(cliente.getId()));
        pedido.notificarObservadores();

        // ⏺ Finaliza o pedido usando Command
        FinalizarPedidoCommand cmdFinalizar = new FinalizarPedidoCommand(pedido, pedidoDAO);
        invoker.executarComando(cmdFinalizar);

        //notifica o Observer
        messagingTemplate.convertAndSend(
                "/topic/pedidos/" + pedido.getCliente().getId(),
                pedido.getEstado().name()
        );

        messagingTemplate.convertAndSend(
                "/topic/cozinha",
                "Novo pedido #" + pedido.getId() + " - " + pedido.getEstado().name()
        );


        return toResponseDTO(pedido);
    }

    public Bebida montarBebida(String tipoBebida, List<String> adicionais) {
        // Factory Method
        Bebida base = bebidaFactoryProvider.criarBebida(tipoBebida);

        // Decorator
        Bebida completa = adicionalApplier.aplicarAdicionais(base, adicionais);

        return completa;
    }

    public Pedido avancarEstado(Long id){
        Pedido pedido = pedidoDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        EstadoPedido estadoAtual = pedido.getEstado();
        EstadoPedidoState estado = estadoPedidoStateFactory.getEstado(estadoAtual);

        estado.proximo(pedido); // muda o estado no pedido
        pedidoDAO.salvar(pedido);

        pedido.notificarObservadores(); // notifica cliente e/ou cozinha

        messagingTemplate.convertAndSend(
                "/topic/pedidos/" + pedido.getCliente().getId(),
                pedido.getEstado().name()
        );

        return pedido;
    }

//    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
//        PedidoResponseDTO dto = new PedidoResponseDTO();
//        dto.setId(pedido.getId());
//        dto.setPrecoFinal(pedido.getPrecoFinal());
//        dto.setEstado(pedido.getEstado().name());
//
//        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();
//        clienteDTO.setId(pedido.getCliente().getId());
//        clienteDTO.setEmail(pedido.getCliente().getEmail());
//        dto.setCliente(clienteDTO);
//
//        List<ItemPedidoResponseDTO> itens = pedido.getItens().stream().map(item -> {
//            ItemPedidoResponseDTO i = new ItemPedidoResponseDTO();
//            i.setDescricao(item.getDescricao());
//            i.setPreco(item.getPreco());
//            return i;
//        }).toList();
//
//        dto.setItens(itens);
//
//        return dto;
//    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setPrecoFinal(pedido.getPrecoFinal());
        dto.setEstado(pedido.getEstado().name());

        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();
        clienteDTO.setId(pedido.getCliente().getId());
        clienteDTO.setEmail(pedido.getCliente().getEmail());
        dto.setCliente(clienteDTO);

        List<ItemPedidoResponseDTO> itens = pedido.getItens().stream().map(item -> {
            ItemPedidoResponseDTO i = new ItemPedidoResponseDTO();

            // Separar a bebida base da descrição
            String descricao = item.getDescricao(); // Ex: "CAFÉ com LEITE e AÇÚCAR"
            String[] partes = descricao.split(" com ");
            i.setBebidaBase(partes[0]);

            // Adicionais (vêm da lista)
            List<String> adicionais = item.getAdicionais().stream()
                    .map(ad -> ad.getProduto().getNome())
                    .toList();

            i.setAdicionais(adicionais);
            i.setPreco(item.getPreco());

            return i;
        }).toList();

        dto.setItens(itens);
        return dto;
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    public PedidoResponseDTO buscarPorIdDTO(Long id) {
        Pedido pedido = pedidoDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        return toResponseDTO(pedido);
    }

    public PedidoResponseDTO avancarEstadoDTO(Long id) {
        Pedido pedido = avancarEstado(id);
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> buscarTodosDTO() {
        return buscarTodos().stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
