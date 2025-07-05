# ☕ VarandaCafeteria

Um sistema de pedidos online para uma cafeteria, desenvolvido com foco em boas práticas e padrões de projeto.  
Este backend foi implementado em **Java + Spring Boot** e utiliza **MySQL** como banco de dados.

---

##  Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Maven
- WebSockets (para notificações)
- Padrões de projeto (Strategy, Factory, Decorator, Observer, State, Command, DAO, BO)

---

##  Estrutura do Projeto

src/
├── controller         # Endpoints REST (Pedidos, Produtos, Clientes)
├── dto               # DTOs para entrada e saída de dados
├── model
│   └── entity         # Entidades JPA
│   └── enums          # Enums (Estado do Pedido, Tipo de Pagamento)
├── repository         # Interfaces JPA
├── service
│   └── bo             # Regras de negócio (BO)
│   └── factory        # Factory Method das bebidas
│   └── decorator      # Adicionais aplicados às bebidas
│   └── strategy       # Estratégia de desconto (pagamento)
│   └── observer       # Observadores (cozinha e cliente)
│   └── state          # Estado do pedido
│   └── command        # Comandos para ações nos pedidos
├── dao                # DAO com acesso ao banco de dados
└── config             # WebSocket e inicialização de dados


 Como executar o projeto localmente
Clone o repositório:

git clone https://github.com/seu-usuario/VarandaCafeteria.git
cd VarandaCafeteria


Configure o banco de dados:

Crie um banco MySQL chamado varanda_cafe e ajuste o arquivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/varanda_cafe
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update


Execute a aplicação:

./mvnw spring-boot:run

Ou, no IntelliJ/VSCode, rode a classe VarandaCafeteriaApplication.java.
