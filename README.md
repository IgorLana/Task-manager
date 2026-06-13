# Task Manager

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-green)
![License](https://img.shields.io/badge/License-%5BAdicionar%20licen%C3%A7a%5D-lightgrey)

API REST para gerenciamento de tarefas e itens de to-do, com autenticação JWT, persistência em MongoDB e geração de tarefas estruturadas com Google GenAI/Gemini.

---

## Índice

- [Sobre o projeto](#sobre-o-projeto)
- [Principais funcionalidades](#principais-funcionalidades)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Arquitetura e visão geral técnica](#arquitetura-e-visão-geral-técnica)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração de variáveis de ambiente](#configuração-de-variáveis-de-ambiente)
- [Como executar localmente](#como-executar-localmente)
- [Como usar](#como-usar)
- [Scripts disponíveis](#scripts-disponíveis)
- [Exemplos de uso](#exemplos-de-uso)
- [Testes](#testes)
- [Build](#build)
- [Deploy](#deploy)
- [Docker](#docker)
- [Documentação da API](#documentação-da-api)
- [Endpoints da API](#endpoints-da-api)
- [Valores aceitos](#valores-aceitos)
- [Validações](#validações)
- [CORS](#cors)
- [Roadmap](#roadmap)
- [Como contribuir](#como-contribuir)
- [Padrões do projeto](#padrões-do-projeto)
- [Troubleshooting](#troubleshooting)
- [FAQ](#faq)
- [Licença](#licença)
- [Autor](#autor)
- [Agradecimentos](#agradecimentos)

---

## Sobre o projeto

O **Task Manager** é uma aplicação backend desenvolvida em Java com Spring Boot para gerenciamento de tarefas.

O projeto possui recursos para:

- cadastro e autenticação de usuários;
- criação, listagem, atualização e remoção de itens de to-do;
- criação, listagem, atualização e remoção de tarefas estruturadas;
- controle de status, prioridade e data de vencimento;
- associação de tarefas ao usuário autenticado;
- proteção dos endpoints via JWT;
- documentação automática da API com SpringDoc OpenAPI;
- geração de tarefas a partir de texto livre usando Google GenAI/Gemini.

> Descrição original do repositório: **Projeto de to-do list**.

---

## Principais funcionalidades

- Autenticação de usuários com JWT.
- Registro de novos usuários.
- Criação de itens de to-do.
- Listagem de to-dos do usuário autenticado.
- Filtro de to-dos por status.
- Filtro de to-dos por prioridade.
- Atualização completa de to-dos.
- Atualização individual de status, prioridade, descrição e data de vencimento.
- Remoção de to-dos.
- Criação de tarefas estruturadas com título, descrição, prioridade, PDCA, checklist e prazo.
- Listagem de tarefas estruturadas do usuário autenticado.
- Atualização e remoção de tarefas estruturadas.
- Geração de tarefas estruturadas com IA a partir de entrada textual.
- Validação de dados com Jakarta Bean Validation.
- Tratamento centralizado de erros de validação.
- Configuração de CORS para integração com frontend local.
- Documentação automática com Swagger UI.

---

## Tecnologias utilizadas

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.4.5 |
| Build | Maven |
| API REST | Spring Web |
| Banco de dados | MongoDB |
| Persistência | Spring Data MongoDB |
| Segurança | Spring Security |
| Autenticação | JWT com `jjwt` |
| Validação | Spring Validation / Hibernate Validator |
| Documentação da API | SpringDoc OpenAPI |
| IA | Google GenAI / Google Cloud AI Platform |
| Serialização | Jackson / Gson |
| Boilerplate | Lombok |
| Testes | Spring Boot Starter Test, JUnit, Maven Surefire |
| Cobertura | JaCoCo |

---

## Arquitetura e visão geral técnica

O projeto segue uma organização em camadas típica de aplicações Spring Boot:

```text
Controller -> Service -> Repository -> MongoDB
```

### Camadas principais

| Camada | Responsabilidade |
|---|---|
| `controller` | Exposição dos endpoints REST |
| `service` | Regras de negócio e validações de acesso do usuário |
| `repository` | Comunicação com MongoDB via Spring Data |
| `entity` | Entidades persistidas no MongoDB |
| `dto` | Objetos de entrada e saída da API |
| `model` | Modelos de domínio, enums e objetos auxiliares |
| `security` | Filtro JWT, geração/validação de tokens e configuração de segurança |
| `config` | CORS, OpenAPI e tratamento global de exceções |
| `cli` | Runner simples executado na inicialização da aplicação |

### Autenticação

Os endpoints de autenticação são públicos:

- `POST /api/auth/login`
- `POST /api/auth/register`

Os demais endpoints sob `/api/**` exigem autenticação via header:

```http
Authorization: Bearer <token>
```

### Persistência

O projeto utiliza MongoDB com coleções identificadas no código.

| Entidade | Coleção |
|---|---|
| `UsuarioEntity` | `users` |
| `ToDoEntity` | `task` |
| `TaskEntity` | `task` |

---

## Estrutura de pastas

```text
Task-manager/
├── .idea/
├── docs/
│   └── tasks.md
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── tarefas/
│                   ├── Application.java
│                   ├── cli/
│                   │   └── CliRunner.java
│                   ├── config/
│                   │   ├── ApiExceptionHandler.java
│                   │   ├── OpenApiConfig.java
│                   │   └── WebConfig.java
│                   ├── controller/
│                   │   ├── AiRestController.java
│                   │   ├── AuthController.java
│                   │   ├── TaskRestController.java
│                   │   └── ToDoRestController.java
│                   ├── dto/
│                   │   ├── JwtResponseDTO.java
│                   │   ├── LoginRequestDTO.java
│                   │   ├── RegistroRequest.java
│                   │   ├── TaskRequestDTO.java
│                   │   ├── TaskResponseDTO.java
│                   │   ├── ToDoRequestDTO.java
│                   │   └── ToDoResponseDTO.java
│                   ├── entity/
│                   │   ├── TaskEntity.java
│                   │   ├── ToDoEntity.java
│                   │   └── UsuarioEntity.java
│                   ├── model/
│                   │   ├── BaseItem.java
│                   │   ├── CardType.java
│                   │   ├── ChecklistItem.java
│                   │   ├── Pdca.java
│                   │   ├── Priority.java
│                   │   ├── Status.java
│                   │   ├── Task.java
│                   │   └── ToDo.java
│                   ├── repository/
│                   │   ├── TaskRepository.java
│                   │   ├── ToDoRepository.java
│                   │   └── UsuarioRepository.java
│                   ├── security/
│                   │   ├── JwtFilter.java
│                   │   ├── JwtService.java
│                   │   └── SecurityConfig.java
│                   └── service/
│                       ├── TaskGenerationService.java
│                       ├── TaskService.java
│                       ├── ToDoService.java
│                       └── UsuarioService.java
├── .gitignore
└── pom.xml
```

> O arquivo `src/main/resources/application.properties` está listado no `.gitignore`, portanto as configurações locais da aplicação não estão versionadas no repositório.

---

## Pré-requisitos

Antes de executar o projeto, instale:

- Java JDK 17
- Maven
- MongoDB local ou uma instância MongoDB remota
- Uma chave de API do Google GenAI, caso utilize o endpoint de geração de tarefas por IA

Verifique as versões instaladas:

```bash
java -version
mvn -version
```

---

## Instalação

Clone o repositório:

```bash
git clone https://github.com/IgorLana/Task-manager.git
```

Acesse a pasta do projeto:

```bash
cd Task-manager
```

Instale as dependências e compile o projeto:

```bash
mvn clean install
```

---

## Configuração de variáveis de ambiente

Crie o arquivo abaixo, caso ainda não exista:

```text
src/main/resources/application.properties
```

Como esse arquivo está no `.gitignore`, ele deve ser criado localmente por cada ambiente.

### Propriedades detectadas no código

| Propriedade | Obrigatória | Descrição | Exemplo |
|---|---:|---|---|
| `jwt.secret` | Sim | Chave secreta Base64 usada para assinar tokens JWT | `[Adicionar chave Base64 segura]` |
| `jwt.expiration` | Sim | Tempo de expiração do token em milissegundos | `86400000` |
| `google.genai.api-key` | Sim, para IA | Chave da API Google GenAI usada pelo serviço de geração de tarefas | `[Adicionar chave da API]` |
| `spring.data.mongodb.uri` | Sim | URI de conexão com MongoDB | `mongodb://localhost:27017/task-manager` |
| `server.port` | Não | Porta HTTP da aplicação | `8080` |

Exemplo de `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/task-manager

jwt.secret=[Adicionar chave Base64 segura]
jwt.expiration=86400000

google.genai.api-key=[Adicionar chave da API]

server.port=8080
```

> A propriedade `jwt.secret` é decodificada com Base64 no código. Use uma chave compatível com o algoritmo HS512.

---

## Como executar localmente

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

A aplicação será iniciada, por padrão, em:

```text
http://localhost:8080
```

Ao iniciar, o runner `CliRunner` imprime:

```text
Running...
```

---

## Como usar

### 1. Registrar usuário

Faça uma requisição para criar um usuário:

```http
POST /api/auth/register
```

Exemplo de payload:

```json
{
  "nome": "Igor Lana",
  "username": "igor@example.com",
  "senha": "123456"
}
```

O endpoint retorna um token JWT.

### 2. Fazer login

```http
POST /api/auth/login
```

Exemplo de payload:

```json
{
  "username": "igor@example.com",
  "senha": "123456"
}
```

Resposta esperada:

```json
{
  "token": "<jwt-token>"
}
```

### 3. Usar endpoints protegidos

Inclua o token no header das próximas requisições:

```http
Authorization: Bearer <jwt-token>
```

---

## Scripts disponíveis

| Comando | Descrição |
|---|---|
| `mvn clean install` | Limpa, compila, executa testes e instala o artefato localmente |
| `mvn spring-boot:run` | Executa a aplicação Spring Boot localmente |
| `mvn test` | Executa os testes configurados no projeto |
| `mvn clean package` | Gera o pacote `.jar` da aplicação |
| `mvn jacoco:report` | Gera relatório de cobertura com JaCoCo |
| `java -jar target/Tarefas-1.0-SNAPSHOT.jar` | Executa o artefato gerado após o build |

---

## Exemplos de uso

### Registrar usuário

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Igor Lana",
    "username": "igor@example.com",
    "senha": "123456"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "igor@example.com",
    "senha": "123456"
  }'
```

### Criar to-do

```bash
curl -X POST http://localhost:8080/api/tarefas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt-token>" \
  -d '{
    "descricao": "Estudar Spring Boot",
    "priority": "MEDIUM",
    "dueDate": "2026-12-31",
    "status": "PENDENTE"
  }'
```

### Listar to-dos

```bash
curl -X GET http://localhost:8080/api/tarefas \
  -H "Authorization: Bearer <jwt-token>"
```

### Filtrar to-dos por status

```bash
curl -X GET http://localhost:8080/api/tarefas/status/PENDENTE \
  -H "Authorization: Bearer <jwt-token>"
```

### Filtrar to-dos por prioridade

```bash
curl -X GET http://localhost:8080/api/tarefas/priority/HIGH \
  -H "Authorization: Bearer <jwt-token>"
```

### Criar tarefa estruturada

```bash
curl -X POST http://localhost:8080/api/task \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt-token>" \
  -d '{
    "descricao": "Planejar entrega do módulo de autenticação",
    "priority": "HIGH",
    "title": "Entrega do módulo de autenticação",
    "pdca": "PLAN",
    "checklist": [
      {
        "label": "Revisar requisitos",
        "checked": false
      },
      {
        "label": "Implementar testes",
        "checked": false
      }
    ],
    "dueDate": "2026-12-31"
  }'
```

### Gerar tarefa com IA

```bash
curl -X POST http://localhost:8080/api/tasks/generate \
  -H "Content-Type: text/plain" \
  -H "Authorization: Bearer <jwt-token>" \
  -d 'Preciso organizar uma reunião com a equipe de vendas para discutir as metas do próximo trimestre.'
```

---

## Testes

O projeto possui dependências e plugins configurados para testes:

- `spring-boot-starter-test`
- `junit`
- `maven-surefire-plugin`
- `jacoco-maven-plugin`

Para executar os testes:

```bash
mvn test
```

Para gerar relatório de cobertura:

```bash
mvn clean test jacoco:report
```

O relatório do JaCoCo normalmente é gerado em:

```text
target/site/jacoco/index.html
```

> Não foram encontrados arquivos de teste versionados no repositório analisado. Adicione testes em `src/test/java`.

---

## Build

Para gerar o artefato da aplicação:

```bash
mvn clean package
```

Artefato esperado:

```text
target/Tarefas-1.0-SNAPSHOT.jar
```

Execute o JAR:

```bash
java -jar target/Tarefas-1.0-SNAPSHOT.jar
```

---

## Deploy

[Adicionar instruções de deploy]

Sugestões de itens a documentar:

- ambiente de execução;
- URL de produção;
- estratégia de configuração segura das variáveis;
- banco MongoDB utilizado;
- pipeline CI/CD;
- processo de release.

---

## Docker

Não foi encontrado `Dockerfile` ou `docker-compose.yml` no repositório.

[Adicionar configuração Docker]

Exemplo de arquivos que podem ser adicionados futuramente:

```text
Dockerfile
docker-compose.yml
```

---

## Documentação da API

A documentação OpenAPI é configurada com o título:

```text
API de Tarefas
```

Após iniciar a aplicação, a documentação Swagger UI deve ficar disponível em:

```text
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI deve ficar disponível em:

```text
http://localhost:8080/v3/api-docs
```

---

## Endpoints da API

### Autenticação

| Método | Endpoint | Autenticação | Descrição |
|---|---|---:|---|
| `POST` | `/api/auth/register` | Não | Registra um novo usuário |
| `POST` | `/api/auth/login` | Não | Autentica usuário e retorna JWT |

#### `POST /api/auth/register`

Payload:

```json
{
  "nome": "Igor Lana",
  "username": "igor@example.com",
  "senha": "123456"
}
```

Resposta:

```json
"<jwt-token>"
```

#### `POST /api/auth/login`

Payload:

```json
{
  "username": "igor@example.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "<jwt-token>"
}
```

---

### To-dos

Base path:

```text
/api/tarefas
```

| Método | Endpoint | Autenticação | Descrição |
|---|---|---:|---|
| `GET` | `/api/tarefas` | Sim | Lista to-dos do usuário autenticado |
| `GET` | `/api/tarefas/status/{status}` | Sim | Lista to-dos por status |
| `GET` | `/api/tarefas/priority/{priority}` | Sim | Lista to-dos por prioridade |
| `POST` | `/api/tarefas` | Sim | Cria um novo to-do |
| `PUT` | `/api/tarefas/{id}` | Sim | Atualiza um to-do |
| `PUT` | `/api/tarefas/{id}/status?status={status}` | Sim | Atualiza o status |
| `PUT` | `/api/tarefas/{id}/priority?priority={priority}` | Sim | Atualiza a prioridade |
| `PUT` | `/api/tarefas/{id}/descricao?descricao={descricao}` | Sim | Atualiza a descrição |
| `PUT` | `/api/tarefas/{id}/duedate?dueDate={yyyy-MM-dd}` | Sim | Atualiza a data de vencimento |
| `DELETE` | `/api/tarefas/{id}` | Sim | Remove um to-do |

#### Payload de criação/atualização

```json
{
  "descricao": "Estudar Java",
  "priority": "MEDIUM",
  "dueDate": "2026-12-31",
  "status": "PENDENTE"
}
```

#### Resposta de listagem

```json
[
  {
    "id": "string",
    "descricao": "Estudar Java",
    "status": "PENDENTE",
    "priority": "MEDIUM",
    "dueDate": "2026-12-31",
    "cardType": "TODO"
  }
]
```

---

### Tarefas estruturadas

Base path:

```text
/api/task
```

| Método | Endpoint | Autenticação | Descrição |
|---|---|---:|---|
| `GET` | `/api/task` | Sim | Lista tarefas estruturadas do usuário autenticado |
| `POST` | `/api/task` | Sim | Cria uma tarefa estruturada |
| `PUT` | `/api/task/{id}` | Sim | Atualiza uma tarefa estruturada |
| `DELETE` | `/api/task/{id}` | Sim | Remove uma tarefa estruturada |

#### Payload de criação/atualização

```json
{
  "descricao": "Planejar entrega do projeto",
  "priority": "HIGH",
  "title": "Entrega do projeto",
  "pdca": "PLAN",
  "checklist": [
    {
      "label": "Definir escopo",
      "checked": false
    },
    {
      "label": "Validar cronograma",
      "checked": false
    }
  ],
  "dueDate": "2026-12-31"
}
```

#### Resposta de listagem

```json
[
  {
    "id": "string",
    "descricao": "Planejar entrega do projeto",
    "priority": "HIGH",
    "title": "Entrega do projeto",
    "pdca": "PLAN",
    "checklist": [
      {
        "label": "Definir escopo",
        "checked": false
      }
    ],
    "dueDate": "2026-12-31",
    "cardType": "TASK"
  }
]
```

---

### Geração de tarefas com IA

Base path:

```text
/api/tasks
```

| Método | Endpoint | Autenticação | Descrição |
|---|---|---:|---|
| `POST` | `/api/tasks/generate` | Sim | Gera uma tarefa estruturada com base em texto livre |

#### Exemplo de entrada

```text
Preciso organizar uma reunião com a equipe de vendas para discutir as metas do próximo trimestre.
```

#### Exemplo de resposta esperada

```json
{
  "priority": "MEDIUM",
  "descricao": "Organizar e conduzir uma reunião com toda a equipe de vendas para discutir e alinhar as metas para o próximo trimestre, garantindo que todos compreendam os objetivos e suas responsabilidades.",
  "dueDate": "2026-12-31",
  "title": "Reunião de Metas",
  "pdca": "PLAN",
  "checklist": [
    {
      "label": "Definir pauta da reunião",
      "checked": false
    },
    {
      "label": "Convidar participantes",
      "checked": false
    }
  ]
}
```

> O serviço utiliza o modelo `gemini-2.5-flash` por meio da biblioteca Google GenAI.

---

## Valores aceitos

### Prioridade

```text
LOW
MEDIUM
HIGH
```

### Status

```text
PENDENTE
CONCLUIDA
ADIADA
EM_ANDAMENTO
```

### PDCA

```text
PLAN
DO
CHECK
ACT
```

### Tipo de card

```text
TODO
TASK
```

---

## Validações

O projeto utiliza validações declarativas nos DTOs.

### ToDoRequestDTO

| Campo | Validação |
|---|---|
| `descricao` | Obrigatório |
| `priority` | Obrigatório |
| `dueDate` | Obrigatório e deve ser uma data futura |
| `status` | Opcional no DTO, usado em atualizações |

### TaskRequestDTO

| Campo | Validação |
|---|---|
| `descricao` | Obrigatório |
| `priority` | Obrigatório |
| `title` | Obrigatório |
| `pdca` | Obrigatório |
| `checklist` | Lista de itens |
| `dueDate` | Obrigatório e deve ser uma data futura |

### Resposta de erro de validação

Quando a validação falha, o projeto retorna uma resposta com o formato:

```json
{
  "erros": {
    "campo": "mensagem de erro"
  }
}
```

---

## CORS

O projeto contém configuração de CORS para ambientes locais.

Origens configuradas nos arquivos do projeto:

```text
http://localhost:4200
http://localhost:8081
```

Métodos permitidos:

```text
GET
POST
PUT
DELETE
OPTIONS
```

---

## Roadmap

O arquivo [`docs/tasks.md`](docs/tasks.md) registra uma lista de melhorias planejadas para o projeto, incluindo:

- melhoria da arquitetura em camadas;
- camada de persistência;
- injeção de dependências;
- sistema de configuração;
- tratamento de exceções;
- logging;
- padronização de formatação;
- prioridades de tarefas;
- datas de vencimento;
- categorias/tags;
- filtros e ordenação;
- edição e exclusão de tarefas;
- autenticação de usuários;
- testes unitários;
- testes de integração;
- cobertura de testes;
- documentação de arquitetura;
- CI/CD;
- processo de release;
- revisão de segurança;
- sanitização de entradas.

---

## Como contribuir

1. Faça um fork do repositório.
2. Crie uma branch para sua alteração:

```bash
git checkout -b feature/minha-alteracao
```

3. Faça as alterações necessárias.
4. Execute os testes:

```bash
mvn test
```

5. Faça commit das mudanças:

```bash
git commit -m "feat: adiciona minha alteração"
```

6. Envie para o repositório remoto:

```bash
git push origin feature/minha-alteracao
```

7. Abra um Pull Request.

---

## Padrões do projeto

[Adicionar guia de estilo ou padrões do projeto]

Sugestões de padronização:

- seguir convenções Java e Spring Boot;
- manter controllers enxutos;
- concentrar regras de negócio em services;
- usar DTOs para entrada e saída da API;
- proteger endpoints sensíveis com autenticação;
- validar payloads com Bean Validation;
- evitar expor entidades diretamente nas respostas;
- adicionar testes para novas funcionalidades.

---

## Troubleshooting

### `application.properties` não encontrado

O arquivo `src/main/resources/application.properties` está ignorado pelo Git. Crie o arquivo localmente e configure as propriedades necessárias.

### Erro ao gerar ou validar JWT

Verifique se `jwt.secret` está configurado em Base64 e possui tamanho adequado para HS512.

### Erro de conexão com MongoDB

Confirme se o MongoDB está em execução e se `spring.data.mongodb.uri` aponta para a instância correta.

### Erro ao chamar endpoint de IA

Verifique se `google.genai.api-key` está configurado corretamente.

### Erro 401 em endpoints `/api/**`

Confirme se o header de autorização está sendo enviado:

```http
Authorization: Bearer <jwt-token>
```

### Erro de CORS no frontend

Confirme se o frontend está rodando em uma das origens configuradas, como:

```text
http://localhost:4200
```

---

## FAQ

### O projeto possui frontend?

Não foi encontrado frontend no repositório analisado. A aplicação disponível é uma API backend em Spring Boot.

### O projeto usa banco de dados relacional?

Não. O projeto usa MongoDB por meio do Spring Data MongoDB.

### O projeto possui Docker?

Não foi encontrado `Dockerfile` ou `docker-compose.yml`.

### O projeto possui documentação Swagger?

Sim. O projeto utiliza SpringDoc OpenAPI.

### Os endpoints exigem autenticação?

Sim. Com exceção de `POST /api/auth/login` e `POST /api/auth/register`, os endpoints `/api/**` exigem JWT.

### O projeto possui testes?

O projeto possui dependências e plugins de teste configurados. Não foram encontrados arquivos de teste versionados no repositório analisado.


---

## Autor

- **IgorLana**
- GitHub: [https://github.com/IgorLana](https://github.com/IgorLana)

---


