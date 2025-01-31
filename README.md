# Projeto Delivery Manda Pra Mim 

![Captura de tela 2025-01-30 143212](https://github.com/user-attachments/assets/c4b976a8-d1b9-43b6-a06b-ddab8b3af9e6)

## Descrição

Este projeto é uma API RESTful chamada "Delivery Manda Pra Mim", desenvolvida com o framework Spring Boot. A API permite o gerenciamento de usuários, produtos e categorias, incluindo operações de cadastro, atualização, listagem e exclusão. O projeto utiliza o padrão de arquitetura MVC (Model-View-Controller) e integra-se com um banco de dados MySQL, além de oferecer suporte para testes automatizados.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada para o desenvolvimento da API.
- **Spring Boot**: Framework que simplifica o desenvolvimento de aplicações Java, fornecendo uma configuração automática e uma estrutura robusta.
- **Spring Data JPA**: Para a interação com o banco de dados, facilitando a persistência de dados.
- **Spring Security**: Para a implementação de autenticação e autorização.
- **H2 Database**: Banco de dados em memória utilizado para testes.
- **MySQL**: Banco de dados relacional utilizado para a persistência de dados em produção.
- **JUnit**: Framework de testes utilizado para garantir a qualidade do código.
- **Swagger (Springdoc OpenAPI)**: Para a documentação da API, permitindo que os desenvolvedores visualizem e testem os endpoints de forma interativa.
- **Insomnia**: Ferramenta utilizada para realizar requisições aos endpoints da API durante o desenvolvimento e testes.
- **Render**: Plataforma utilizada para o deploy da API, tornando-a acessível publicamente.

## Estrutura do Projeto

- **Controller**: Contém as classes responsáveis por gerenciar as requisições HTTP e interagir com os serviços.
- **Service**: Contém a lógica de negócio da aplicação.
- **Repository**: Interfaces que estendem `JpaRepository` para a manipulação de dados.
- **Model**: Classes que representam as entidades do sistema (por exemplo, `Usuario`, `Produto`, `Categoria`).

## Endpoints da API

### Usuários
- **POST /usuarios/cadastrar**: Cadastra um novo usuário.
- **POST /usuarios/logar**: Autentica um usuário.
- **GET /usuarios/all**: Lista todos os usuários cadastrados.
- **GET /usuarios/{id}**: Obtém um usuário pelo ID.
- **PUT /usuarios/atualizar**: Atualiza as informações de um usuário existente.

### Produtos
- **POST /produtos**: Cadastra um novo produto.
- **GET /produtos**: Lista todos os produtos cadastrados.
- **GET /produtos/{id}**: Obtém um produto pelo ID.
- **GET /produtos/nome/{nome}**: Busca produtos pelo nome, ignorando maiúsculas e minúsculas.
- **GET /produtos/recomendar-saudaveis**: Recomenda produtos saudáveis.
- **PUT /produtos**: Atualiza as informações de um produto existente.
- **DELETE /produtos/{id}**: Remove um produto pelo ID.

### Categorias
- **POST /categorias**: Cadastra uma nova categoria.
- **GET /categorias**: Lista todas as categorias cadastradas.
- **GET /categorias/{id}**: Obtém uma categoria pelo ID.
- **GET /categorias/nome/{nome}**: Busca categorias pelo nome, ignorando maiúsculas e minúsculas.
- **PUT /categorias**: Atualiza uma categoria existente.
- **DELETE /categorias/{id}**: Remove uma categoria pelo ID.

## Acessar a API

Você pode acessar a API através do seguinte link:

- [Delivery Manda Pra Mim API](https://mandapramim-delivery.onrender.com/swagger-ui/index.html)

E para visualizar a documentação da API, acesse o Swagger UI:

- [Swagger UI](http://localhost:8080/swagger-ui/)

## Configuração do Ambiente

O projeto utiliza o arquivo `application.properties` para gerenciar as configurações de ambiente. Existem duas configurações principais:

### Ambiente de Desenvolvimento

Para executar a aplicação localmente e realizar alterações no código, utilize o arquivo `application-dev.properties`. Este arquivo deve conter as configurações necessárias para o ambiente de desenvolvimento, como a conexão com o banco de dados local.

### Ambiente de Produção

Para acessar a aplicação implantada no Render, utilize o arquivo `application-prod.properties`. Este arquivo deve conter as configurações necessárias para o ambiente de produção, como a conexão com o banco de dados em produção.

### Como Alternar Entre os Ambientes

1. **Desenvolvimento**: Para executar a aplicação em modo de desenvolvimento, certifique-se de que o arquivo `application-dev.properties` está configurado corretamente e que você está executando a aplicação com o perfil de desenvolvimento:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev

2. **Produção**: Para executar a aplicação em modo de produção, utilize o arquivo application-prod.properties e execute a aplicação com o perfil de produção:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=prod

## Conceitos Aprendidos

Durante o desenvolvimento deste projeto, aprendi sobre:

- **Desenvolvimento de APIs RESTful**: Compreendi como estruturar uma API que segue os princípios REST, utilizando métodos HTTP adequados para diferentes operações.
- **Persistência de Dados**: Aprendi a utilizar o Spring Data JPA para interagir com bancos de dados, facilitando a criação, leitura, atualização e exclusão de registros.
- **Segurança em APIs**: Implementei autenticação e autorização utilizando o Spring Security, garantindo que apenas usuários autenticados possam acessar determinados recursos.
- **Testes Automatizados**: Aprendi a escrever testes utilizando JUnit e a importância de garantir a qualidade do código através de testes automatizados.
- **Documentação de APIs**: Utilizei o Swagger para documentar a API, permitindo que outros desenvolvedores entendam e testem os endpoints de forma interativa.
- **Deploy em Render**: Aprendi a realizar o deploy da aplicação na plataforma Render, tornando a API acessível publicamente e facilitando a integração com outras aplicações.

1. Clone o repositório:
  ```bash
   git clone https://github.com/Projeto-Integrador-Grupo7/MandaPraMim_Delivery.git
  ```
2. Navegue até o diretório do projeto:
  ```bash
   cd MandaPraMim_Delivery
  ```
3. Execute o projeto:
  ```bash
   mvn spring-boot:run
  ```

## Contribuições  
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

