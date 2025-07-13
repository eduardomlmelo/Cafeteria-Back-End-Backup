# CRUD para Entidades do Banco de Dados

Este documento descreve o CRUD completo implementado para as entidades do sistema de cafeteria.

## Estrutura Implementada

### 📁 DTOs (Data Transfer Objects)
- `AdicionalDTO` - Para transferência de dados de adicionais
- `ClienteDTO` - Para transferência de dados de clientes
- `ItemDTO` - Para transferência de dados de itens
- `PedidoDTO` - Para transferência de dados de pedidos
- `ItemAdicionalDTO` - Para transferência de dados da relação item-adicional

### 🎮 Controllers
Todos os controllers implementam operações CRUD completas:

#### AdicionalController (`/api/adicionais`)
- `GET /` - Lista todos os adicionais
- `GET /{id}` - Busca adicional por ID
- `POST /` - Cria novo adicional
- `PUT /{id}` - Atualiza adicional existente
- `DELETE /{id}` - Remove adicional

#### ClienteController (`/api/clientes`)
- `GET /` - Lista todos os clientes
- `GET /{id}` - Busca cliente por ID
- `POST /` - Cria novo cliente
- `PUT /{id}` - Atualiza cliente existente
- `DELETE /{id}` - Remove cliente

#### ItemController (`/api/itens`)
- `GET /` - Lista todos os itens
- `GET /{id}` - Busca item por ID
- `POST /` - Cria novo item
- `PUT /{id}` - Atualiza item existente
- `DELETE /{id}` - Remove item
- `POST /{itemId}/adicionais/{adicionalId}` - Adiciona adicional ao item
- `DELETE /{itemId}/adicionais/{adicionalId}` - Remove adicional do item

#### PedidoController (`/api/pedidos`)
- `GET /` - Lista todos os pedidos
- `GET /{id}` - Busca pedido por ID
- `GET /cliente/{clienteId}` - Lista pedidos de um cliente específico
- `POST /` - Cria novo pedido
- `PUT /{id}` - Atualiza pedido existente
- `DELETE /{id}` - Remove pedido

#### ItemAdicionalController (`/api/item-adicionais`)
- `GET /` - Lista todas as relações item-adicional
- `GET /item/{itemId}` - Lista adicionais de um item específico
- `GET /adicional/{adicionalId}` - Lista itens que usam um adicional específico
- `GET /item/{itemId}/adicional/{adicionalId}` - Busca relação específica
- `POST /` - Cria nova relação item-adicional
- `DELETE /item/{itemId}/adicional/{adicionalId}` - Remove relação

### 🔧 Services
Cada service implementa a lógica de negócio e conversão entre entidades e DTOs:
- `AdicionalService`
- `ClienteService`
- `ItemService`
- `PedidoService`
- `ItemAdicionalService`

### 🛡️ Tratamento de Exceções
- `GlobalExceptionHandler` - Tratamento global de exceções
- `ResourceNotFoundException` - Exceção customizada para recursos não encontrados

### 🌐 Configuração CORS
- `CorsConfig` - Configuração para permitir requisições de diferentes origens

## Exemplos de Uso

### Criar um Cliente
```json
POST /api/clientes
{
    "nome": "João Silva",
    "cpf": 12345678901
}
```

### Criar um Item
```json
POST /api/itens
{
    "nome": "Café Expresso",
    "preco": 5.50
}
```

### Criar um Adicional
```json
POST /api/adicionais
{
    "nome": "Açúcar",
    "valor": 0.00
}
```

### Criar um Pedido
```json
POST /api/pedidos
{
    "clienteId": 1,
    "itemId": 1
}
```

### Adicionar Adicional a um Item
```http
POST /api/itens/1/adicionais/1
```

## Funcionalidades Implementadas

✅ **CRUD Completo** para todas as entidades
✅ **DTOs** para transferência segura de dados
✅ **Tratamento de Exceções** padronizado
✅ **Validação** de dados de entrada
✅ **Relacionamentos** entre entidades
✅ **CORS** configurado para frontend
✅ **Códigos de Status HTTP** apropriados
✅ **Documentação** de endpoints

## Próximos Passos

1. **Validação Avançada**: Implementar validações mais específicas usando Bean Validation
2. **Paginação**: Adicionar paginação para listagens grandes
3. **Filtros**: Implementar filtros de busca
4. **Auditoria**: Adicionar campos de auditoria (criado em, atualizado em)
5. **Testes**: Implementar testes unitários e de integração