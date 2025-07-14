# 📊 Documentação da Estrutura de Banco de Dados - VarandaCafeteria

## Visão Geral

Este documento descreve a estrutura completa do banco de dados do sistema VarandaCafeteria, implementada seguindo os padrões de arquitetura em camadas com Spring Boot. O sistema utiliza uma abordagem robusta com separação clara entre entidades, repositórios, serviços, controladores e DTOs.

---

## 🏗️ Arquitetura do Sistema

O sistema segue uma arquitetura em camadas bem definida:

```
Controllers (API REST) 
    ↓
Services (Lógica de Negócio)
    ↓
Repositories (Acesso a Dados)
    ↓
Entities (Modelo de Dados)
```

---

## 📚 Bibliotecas Utilizadas

### 1. **Jakarta Persistence API (JPA)**
- **Propósito**: Padrão Java para mapeamento objeto-relacional (ORM)
- **Uso no projeto**: Anotações como `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`, `@OneToMany`, `@ManyToMany`
- **Benefício**: Abstração do banco de dados, permitindo trabalhar com objetos Java ao invés de SQL direto

### 2. **Spring Framework**
- **Spring Boot**: Framework principal para criação de aplicações Java
- **Spring Data JPA**: Simplifica o acesso a dados com repositórios automáticos
- **Spring Web**: Fornece funcionalidades para APIs REST
- **Uso no projeto**: Anotações como `@RestController`, `@Service`, `@Repository`, `@Autowired`
- **Benefício**: Injeção de dependência, configuração automática e redução de código boilerplate

### 3. **Lombok**
- **Propósito**: Redução de código boilerplate através de anotações
- **Uso no projeto**: `@Data` para gerar automaticamente getters, setters, toString, equals e hashCode
- **Benefício**: Código mais limpo e menos verboso

---

## 🗃️ Estrutura das Entidades

### 1. **Cliente**
```java
@Entity
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Integer cpf;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
}
```

**Características:**
- Entidade principal que representa os clientes da cafeteria
- Relacionamento **Um-para-Muitos** com Pedido
- Cascade ALL: operações no cliente afetam seus pedidos
- Chave primária auto-incrementável

### 2. **Item**
```java
@Entity
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Float preco;
    @ManyToMany
    @JoinTable(name = "ItemAdicional", ...)
    private Set<Adicional> adicionais;
}
```

**Características:**
- Representa os produtos/itens disponíveis na cafeteria
- Relacionamento **Muitos-para-Muitos** com Adicional através da tabela intermediária ItemAdicional
- Utiliza `Set` para evitar duplicatas de adicionais

### 3. **Adicional**
```java
@Entity
public class Adicional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Float valor;
    @ManyToMany(mappedBy = "adicionais")
    private Set<Item> itens;
}
```

**Características:**
- Representa complementos que podem ser adicionados aos itens
- Lado inverso do relacionamento Muitos-para-Muitos com Item
- `mappedBy` indica que Item é o proprietário da relação

### 4. **Pedido**
```java
@Entity
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;
}
```

**Características:**
- Entidade que conecta Cliente e Item
- Relacionamento **Muitos-para-Um** com Cliente
- Relacionamento **Muitos-para-Um** com Item
- Representa uma transação/pedido específico

### 5. **ItemAdicional** (Entidade de Junção)
```java
@Entity
@IdClass(ItemAdicionalId.class)
public class ItemAdicional {
    @Id @ManyToOne @JoinColumn(name = "idITEM")
    private Item item;
    @Id @ManyToOne @JoinColumn(name = "idADICIONAL")
    private Adicional adicional;
}
```

**Características:**
- Entidade intermediária para o relacionamento Muitos-para-Muitos
- Chave composta usando `@IdClass`
- Permite controle granular sobre a relação Item-Adicional

### 6. **ItemAdicionalId** (Chave Composta)
```java
public class ItemAdicionalId implements Serializable {
    private Integer item;
    private Integer adicional;
    // equals() e hashCode() obrigatórios
}
```

**Características:**
- Classe que define a chave composta para ItemAdicional
- Implementa `Serializable` (requisito JPA)
- Sobrescreve `equals()` e `hashCode()` para comparação correta

---

## 🔄 Camada de Repositórios

Todos os repositórios estendem `JpaRepository<Entity, ID>`, fornecendo operações CRUD automáticas:

### Métodos Automáticos Disponíveis:
- `findAll()` - Lista todas as entidades
- `findById(ID)` - Busca por ID
- `save(Entity)` - Salva/atualiza entidade
- `deleteById(ID)` - Remove por ID
- `existsById(ID)` - Verifica existência

### Repositórios Implementados:
- **AdicionalRepository**: Gerencia entidades Adicional
- **ClienteRepository**: Gerencia entidades Cliente
- **ItemRepository**: Gerencia entidades Item
- **PedidoRepository**: Gerencia entidades Pedido
- **ItemAdicionalRepository**: Gerencia a relação Item-Adicional

---

## 🔧 Camada de Serviços

Os serviços implementam a lógica de negócio e fazem a conversão entre Entities e DTOs.

### Padrão Implementado:
```java
@Service
public class ExemploService {
    @Autowired
    private ExemploRepository repository;
    
    // Métodos CRUD + conversões DTO
    public List<ExemploDTO> listarTodos() { ... }
    public ExemploDTO buscarPorId(Integer id) { ... }
    public ExemploDTO salvar(ExemploDTO dto) { ... }
    public ExemploDTO atualizar(ExemploDTO dto) { ... }
    public boolean deletar(Integer id) { ... }
    
    // Métodos de conversão
    private ExemploDTO convertToDTO(Exemplo entity) { ... }
    private Exemplo convertToEntity(ExemploDTO dto) { ... }
}
```

### Serviços Específicos:

#### **ItemService**
- Funcionalidades especiais para gerenciar adicionais:
  - `adicionarAdicional(itemId, adicionalId)`
  - `removerAdicional(itemId, adicionalId)`

#### **PedidoService**
- Método específico: `buscarPorCliente(clienteId)`
- Filtragem de pedidos por cliente

#### **ItemAdicionalService**
- Gerencia relacionamentos complexos:
  - `buscarPorItem(itemId)`
  - `buscarPorAdicional(adicionalId)`
  - `buscarPorItemEAdicional(itemId, adicionalId)`

---

## 📡 Camada de Controladores (API REST)

Todos os controladores seguem o padrão REST com endpoints padronizados:

### Endpoints Padrão:
- `GET /api/{recurso}` - Lista todos
- `GET /api/{recurso}/{id}` - Busca por ID
- `POST /api/{recurso}` - Cria novo
- `PUT /api/{recurso}/{id}` - Atualiza existente
- `DELETE /api/{recurso}/{id}` - Remove

### Controladores Específicos:

#### **ItemController**
Endpoints adicionais para gerenciar adicionais:
- `POST /api/itens/{itemId}/adicionais/{adicionalId}` - Adiciona adicional ao item
- `DELETE /api/itens/{itemId}/adicionais/{adicionalId}` - Remove adicional do item

#### **PedidoController**
Endpoint específico:
- `GET /api/pedidos/cliente/{clienteId}` - Lista pedidos de um cliente

#### **ItemAdicionalController**
Endpoints especializados:
- `GET /api/item-adicionais/item/{itemId}` - Adicionais de um item
- `GET /api/item-adicionais/adicional/{adicionalId}` - Itens que usam um adicional
- `GET /api/item-adicionais/item/{itemId}/adicional/{adicionalId}` - Relação específica

---

## 📋 Data Transfer Objects (DTOs)

Os DTOs servem como contratos de API, separando a representação interna das entidades da exposição externa.

### Características dos DTOs:
- Utilizam `@Data` do Lombok para geração automática de métodos
- Contêm apenas os campos necessários para a API
- Alguns incluem IDs de relacionamentos ao invés de objetos completos

### DTOs Implementados:

#### **ClienteDTO**
```java
@Data
public class ClienteDTO {
    private Integer id;
    private String nome;
    private Integer cpf;
    private List<Integer> pedidoIds; // IDs dos pedidos relacionados
}
```

#### **ItemDTO**
```java
@Data
public class ItemDTO {
    private Integer id;
    private String nome;
    private Float preco;
    private List<Integer> adicionalIds; // IDs dos adicionais relacionados
}
```

#### **PedidoDTO**
```java
@Data
public class PedidoDTO {
    private Integer id;
    private Integer clienteId;
    private String clienteNome;
    private Integer itemId;
    private String itemNome;
    private Float itemPreco;
}
```

#### **ItemAdicionalDTO**
```java
@Data
public class ItemAdicionalDTO {
    private Integer itemId;
    private String itemNome;
    private Integer adicionalId;
    private String adicionalNome;
    private Float adicionalValor;
}
```

---

## 🛡️ Tratamento de Exceções

### **GlobalExceptionHandler**
Centraliza o tratamento de exceções da aplicação:

- **ResourceNotFoundException**: Retorna HTTP 404 para recursos não encontrados
- **IllegalArgumentException**: Retorna HTTP 400 para argumentos inválidos
- **Exception**: Retorna HTTP 500 para erros gerais

### **ResourceNotFoundException**
Exceção customizada para recursos não encontrados no banco de dados.

---

## 🌐 Configuração CORS

### **CorsConfig**
Configura o Cross-Origin Resource Sharing para permitir requisições de diferentes origens:

- Permite todos os origins (`*`)
- Métodos permitidos: GET, POST, PUT, DELETE, OPTIONS
- Headers permitidos: todos (`*`)
- Aplicado a todos os endpoints `/api/**`

---

## 🔗 Relacionamentos Entre Entidades

### Diagrama de Relacionamentos:
```
Cliente (1) ←→ (N) Pedido (N) ←→ (1) Item (N) ←→ (N) Adicional
                                        ↕
                                 ItemAdicional
                               (Tabela de Junção)
```

### Detalhamento dos Relacionamentos:

1. **Cliente → Pedido**: Um cliente pode ter vários pedidos
2. **Pedido → Item**: Um pedido contém um item específico
3. **Item ↔ Adicional**: Relacionamento muitos-para-muitos através de ItemAdicional
4. **ItemAdicional**: Entidade intermediária que permite controle granular

---

## 🚀 Fluxo de Operações

### Exemplo: Criar um Pedido
1. **Controller** recebe requisição POST com PedidoDTO
2. **Service** converte DTO para Entity
3. **Service** valida se Cliente e Item existem
4. **Repository** persiste o Pedido no banco
5. **Service** converte Entity de volta para DTO
6. **Controller** retorna DTO como resposta JSON

### Exemplo: Adicionar Adicional a um Item
1. **ItemController** recebe POST `/api/itens/{itemId}/adicionais/{adicionalId}`
2. **ItemService.adicionarAdicional()** busca Item e Adicional
3. Adiciona o Adicional ao Set de adicionais do Item
4. **ItemRepository** persiste as mudanças
5. Retorna sucesso ou erro

---

## 📊 Considerações de Performance

### Estratégias Implementadas:
- **Lazy Loading**: Relacionamentos carregados sob demanda
- **Set vs List**: Uso de Set para evitar duplicatas em relacionamentos ManyToMany
- **DTOs**: Reduzem a transferência de dados desnecessários
- **Cascade Operations**: Operações em cascata para manter consistência

### Pontos de Atenção:
- Relacionamentos ManyToMany podem gerar consultas N+1
- Lazy Loading pode causar LazyInitializationException fora do contexto transacional
- Chaves compostas requerem implementação cuidadosa de equals/hashCode

---

## 🔧 Manutenção e Extensibilidade

### Facilidades para Manutenção:
- **Separação clara de responsabilidades** entre camadas
- **Padrão consistente** em todos os controladores e serviços
- **DTOs** protegem a API de mudanças internas nas entidades
- **Tratamento centralizado de exceções**

### Pontos de Extensão:
- Novos endpoints podem ser facilmente adicionados aos controladores
- Novos campos nas entidades requerem atualização dos DTOs correspondentes
- Relacionamentos adicionais podem ser implementados seguindo os padrões existentes

---

## 📝 Resumo

O sistema de banco de dados da VarandaCafeteria implementa uma arquitetura robusta e bem estruturada, utilizando as melhores práticas do ecossistema Spring Boot. A separação clara entre entidades, repositórios, serviços e controladores, combinada com o uso de DTOs e tratamento centralizado de exceções, resulta em um sistema maintível, extensível e de fácil compreensão.

A estrutura permite operações CRUD completas em todas as entidades, com funcionalidades especiais para gerenciar relacionamentos complexos como a associação entre itens e adicionais. O uso das bibliotecas Jakarta, Spring Framework e Lombok contribui significativamente para a redução de código boilerplate e aumento da produtividade no desenvolvimento.