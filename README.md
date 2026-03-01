# 💳 Mini Banco Digital

Sistema bancário simples com autenticação segura, operações financeiras e histórico de transações.

---

## 🎯 Objetivo

Desenvolver um sistema completo com backend, frontend e microserviço, simulando operações bancárias reais.

---

## 🧠 Arquitetura do Sistema

O sistema é dividido em três partes:

### Backend Principal (Java)
- API REST com Spring Boot
- Regras de negócio bancárias
- Autenticação com JWT
- Persistência em banco de dados (H2 → PostgreSQL)

### Microserviço Auxiliar (Python)
- Serviço independente
- Responsável por: extrato ou antifraude

### Frontend (JavaScript)
- Interface do usuário
- Consumo das APIs
- Dashboard bancário

---

## ⚙️ Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 4.0.3
- Spring Data JPA + Hibernate
- Spring Security
- JWT (jjwt 0.12.6)
- BCrypt (criptografia de senha)
- H2 Database (desenvolvimento) → PostgreSQL (produção)
- Lombok
- Maven

### Microserviço
- Python
- FastAPI ou Flask

### Frontend
- HTML
- CSS
- JavaScript

---

## 🔐 Funcionalidades

### ✅ Implementado
- Cadastro de usuário com senha criptografada (BCrypt)
- Login com geração de token JWT
- Criação automática de conta bancária ao cadastrar
- Depósito
- Saque
- Transferência entre contas
- Extrato de transações ordenado por data

### 🔜 Próximos Passos
- DTOs para controlar resposta da API (remover dados sensíveis)
- Tratamento global de erros padronizado
- Proteção de rotas com JWT (filtro de autenticação)
- Migração do H2 para PostgreSQL

---

## 📏 Regras de Negócio

- Não aceitar valores negativos em depósitos
- Não permitir saque maior que o saldo
- Transferência apenas para contas existentes
- Não permitir transferência para si mesmo
- Senha sempre criptografada — nunca salva em texto puro
- Toda operação financeira é registrada como transação

---

## 🗄️ Modelagem de Dados

### Users
| Campo | Tipo | Descrição |
|---|---|---|
| id | BIGINT | Chave primária |
| name | VARCHAR | Nome do usuário |
| email | VARCHAR UNIQUE | Email de login |
| password | VARCHAR | Senha criptografada (BCrypt) |
| created_at | TIMESTAMP | Data de criação |

### Accounts
| Campo | Tipo | Descrição |
|---|---|---|
| id | BIGINT | Chave primária |
| user_id | FK → users.id | Dono da conta |
| balance | DECIMAL(15,2) | Saldo atual |
| created_at | TIMESTAMP | Data de criação |

### Transactions
| Campo | Tipo | Descrição |
|---|---|---|
| id | BIGINT | Chave primária |
| type | ENUM | DEPOSIT, WITHDRAW, TRANSFER |
| amount | DECIMAL(15,2) | Valor da operação |
| source_account_id | FK → accounts.id | Conta origem (null em depósito) |
| destination_account_id | FK → accounts.id | Conta destino (null em saque) |
| created_at | TIMESTAMP | Data da operação |

---

## 🔌 Endpoints da API

**Base URL:** `http://localhost:8080`

### 🔐 Autenticação
| Método | Rota | Descrição |
|---|---|---|
| POST | /api/auth/register | Cadastrar usuário |
| POST | /api/auth/login | Fazer login e receber JWT |

**Exemplo register:**
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
```

**Exemplo login:**
```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

### 💰 Operações Bancárias
| Método | Rota | Descrição |
|---|---|---|
| GET | /api/transactions/balance/{email} | Ver saldo |
| POST | /api/transactions/deposit/{email} | Depositar |
| POST | /api/transactions/withdraw/{email} | Sacar |
| POST | /api/transactions/transfer/{email} | Transferir |
| GET | /api/transactions/statement/{email} | Ver extrato |

**Exemplo depósito/saque:**
```json
{
  "amount": 500.00
}
```

**Exemplo transferência:**
```json
{
  "destinationEmail": "destino@email.com",
  "amount": 300.00
}
```

---

## 🏗️ Arquitetura do Código

```
com.bancose.digital_bank
 ├── config
 │   ├── SecurityConfig.java       → configuração do Spring Security
 │   ├── SecurityBeansConfig.java  → bean do PasswordEncoder
 │   └── DataLoader.java           → dados iniciais para teste
 ├── controller
 │   ├── AuthController.java       → endpoints de autenticação
 │   ├── AccountController.java    → endpoints de conta
 │   └── TransactionController.java → endpoints bancários
 ├── model
 │   ├── User.java                 → entidade usuário
 │   ├── Account.java              → entidade conta bancária
 │   └── Transaction.java          → entidade transação
 ├── repository
 │   ├── UserRepository.java       → acesso ao banco - usuários
 │   ├── AccountRepository.java    → acesso ao banco - contas
 │   └── TransactionRepository.java → acesso ao banco - transações
 └── service
     ├── UserService.java          → regras de cadastro e login
     ├── JwtService.java           → geração e validação de JWT
     └── TransactionService.java   → regras financeiras
```

---

## ▶️ Como Executar o Projeto

### Pré-requisitos
- Java 17+
- Maven
- IntelliJ IDEA (recomendado)

### Rodando localmente

```bash
# Clonar o repositório
git clone https://github.com/SantyagoAmorim/Digital-Bank.git

# Entrar na pasta do backend
cd digital-bank

# Rodar o projeto
./mvnw spring-boot:run
```

O servidor sobe em: `http://localhost:8080`

O console do banco H2 está disponível em: `http://localhost:8080/h2-console`

```
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vazio)
```

---

## 📂 Estrutura do Repositório

```
Digital-Bank/
 ├── backend-java/     → API REST em Java (Spring Boot)
 ├── microservice-python/ → Microserviço auxiliar (em breve)
 └── frontend/         → Interface web (em breve)
```

---

## 👥 Equipe

| Papel | Nome |
|---|---|
| Backend Java | Yago Santos da Silva |
| Frontend | Antônio Francisco Borges |
| Microserviço Python | A definir |