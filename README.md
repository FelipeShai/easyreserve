# API EasyReserve

## Introdução

A API EasyReserve fornece uma interface RESTful para gerenciar reservas, avaliações e detalhes de restaurantes. Este
projeto demonstra princípios de arquitetura limpa e utiliza tecnologias modernas para garantir escalabilidade e
facilidade de manutenção.

## Funcionalidades

### Gerenciamento de Restaurantes

- Criar novos restaurantes.
- Listar restaurantes com filtros opcionais por localização e tipo de cozinha.

### Sistema de Reservas

- Criar reservas.
- Visualizar reservas de um restaurante específico.
- Cancelar reservas.

### Avaliações

- Enviar avaliações para restaurantes, incluindo notas e comentários.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada no backend.
- **Spring Boot 3.x**: Framework para construção da API e lógica de negócios.
- **Gradle**: Ferramenta de build e gerenciador de dependências.
- **JPA** com Banco de Dados H2: Para gerenciar interações com o banco de dados.
- **Swagger/OpenAPI**: Para documentação e testes da API.

## Instruções de Configuração

### Pré-requisitos

- JDK 20 ou superior
- Gradle
- Ambiente de Desenvolvimento Integrado (IDE), como o IntelliJ IDEA

### Passos para Configuração

1. Clone o repositório:
   ```bash
   https://github.com/FelipeShai/easyreserve.git
   cd easyreserve
   ```
2. Construa e execute a aplicação usando Gradle:
   ```bash
   ./gradlew bootRun
   ```
3. Acesse a documentação da API em `http://localhost:8080/swagger-ui.html`.

## Endpoints da API

### Reservas

#### 1. Criar Reserva

- **POST** `/reservations`
    - **Descrição**: Cria uma nova reserva.
    - **Corpo da Requisição**:
   ```json
  {
    "restaurantId": 1,
    "customerName": "John Doe",
    "customerContact": "john.doe@example.com",
    "reservationDateTime": "2023-12-25T19:00:00",
    "numberOfGuests": 2,
    "status": "CONFIRMED"
  }
   ```
    - **Resposta de Sucesso**: `201 Created`

#### 2. Visualizar Reservas por Restaurante

- **GET** `/reservations/restaurant/{restaurantId}`
    - **Descrição**: Recupera todas as reservas de um restaurante específico.
    - **Parâmetro de Caminho**:
        - `restaurantId`: ID do restaurante
    - **Resposta de Sucesso**: `200 OK`

#### 3. Cancelar Reserva

- **DELETE** `/reservations/{reservationId}`
    - **Descrição**: Cancela uma reserva específica.
    - **Parâmetro de Caminho**:
        - `reservationId`: ID da reserva
    - **Resposta de Sucesso**: `204 No Content`

### Restaurantes

#### 4. Listar Restaurantes

- **GET** `/restaurants`
    - **Descrição**: Retorna uma lista de restaurantes. Suporta filtros opcionais.
    - **Parâmetros de Consulta**:
        - `location` (opcional): Filtrar pela localização do restaurante.
        - `cuisineType` (opcional): Filtrar pelo tipo de cozinha.
    - **Resposta de Sucesso**: `200 OK`

#### 5. Criar Restaurante

- **POST** `/restaurants`

    - **Descrição**: Adiciona um novo restaurante ao sistema.
    - **Corpo da Requisição**:

      ```json
      {
        "name": "Restaurante Exemplo",
        "location": "Endereço Exemplo",
        "cuisineType": "Italiana",
        "openingHours": "12:00",
        "capacity": 50
      }
      ```

    - **Resposta de Sucesso**: `201 Created`

### Avaliações

#### 6. Enviar Avaliação

- **POST** `/reviews`
    - **Descrição**: Permite que os usuários enviem uma avaliação para um restaurante.
    - **Corpo da Requisição**:
      ```json
      {
        "restaurantId": 1,
        "userId": 123,
        "rating": 4,
        "comment": "Ótima comida e atendimento."
      }
      ```
    - **Resposta de Sucesso**: `201 Created`

## Tratamento de Erros

Cada endpoint implementa um tratamento robusto de erros para gerenciar problemas comuns, como:

- `400 Bad Request`: Dados de entrada inválidos.
- `404 Not Found`: Recurso solicitado não encontrado.
- `500 Internal Server Error`: Problemas inesperados no servidor.

## Conclusão

A API EasyReserve é uma solução abrangente para o gerenciamento de reservas de restaurantes, oferecendo endpoints
intuitivos e recursos robustos. A arquitetura limpa e o uso de tecnologias modernas garantem que o sistema seja
escalável e fácil de manter.
