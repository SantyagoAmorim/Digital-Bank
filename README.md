# 💳 Mini Banco Digital

Sistema bancário simples com autenticação segura, operações financeiras e histórico de transações.

## 🎯 Objetivo
Desenvolver um sistema completo com backend, frontend e microserviço, simulando operações bancárias reais.

---

## 🧠 Arquitetura do Sistema

O sistema é dividido em três partes:

### Backend Principal (Java)
- API REST
- Regras de negócio bancárias
- Autenticação com JWT
- Persistência em banco de dados

### Microserviço Auxiliar (Python)
- Serviço independente
- Responsável por: (extrato ou antifraude)

### Frontend (JavaScript)
- Interface do usuário
- Consumo das APIs
- Dashboard bancário

---

## ⚙️ Tecnologias Utilizadas

### Backend
- Java
- Spring Boot
- Banco de dados relacional

### Microserviço
- Python
- FastAPI ou Flask

### Frontend
- HTML
- CSS
- JavaScript

---

## 🔐 Funcionalidades

### Autenticação
- Cadastro de usuário
- Login
- Senha criptografada
- Proteção com JWT

### Operações Bancárias
- Depósito
- Saque
- Transferência entre contas

### Extrato
- Histórico de transações
- Ordenação por data
- Tipo de operação

---

## 📏 Regras de Negócio

- Não aceitar valores negativos em depósitos
- Não permitir saque maior que o saldo
- Transferência apenas para contas existentes
- Não permitir transferência para si mesmo

---

## 🗄️ Modelagem de Dados

### Usuário
- id
- nome
- email
- senha

### Conta
- id
- saldo
- usuario_id

### Transação
- id
- tipo (deposito, saque, transferencia)
- valor
- data
- conta_origem
- conta_destino

---

## 🔌 Endpoints da API (Planejado)

### Autenticação
POST /auth/register  
POST /auth/login

### Conta
GET /account/balance

### Operações
POST /deposit  
POST /withdraw  
POST /transfer

### Extrato
GET /transactions

---

## ▶️ Como Executar o Projeto

Instruções serão adicionadas após implementação.

---

## 📂 Estrutura do Repositório

/backend-java  
/microservice-python  
/frontend

---

## 👥 Equipe

Backend Java: Yago Santos da Silva  
Frontend: Antônio Francisco Borges  
Microserviço Python:  
