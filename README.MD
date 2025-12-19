# 🧪 Automação de Testes de API com Rest Assured

Projeto de automação de testes de API da aplicação ServeRest, desenvolvido com foco em boas práticas de QA, organização de código e execução automatizada via CI.

Os testes são executados automaticamente via **GitHub Actions** a cada push ou pull request.

---

## 🔧 Tecnologias Utilizadas

- Java 17  
- Maven  
- Rest Assured  
- JUnit 5 
- Postman 
- GitHub Actions (CI)  
- API pública ServeRest  

---

## 🧪 Testes Manuais com Postman

* Criação de collection
* Uso de environments e variáveis
* Testes de login e captura de token
* Validação de status code e respostas
* Execução manual dos fluxos principais da API

---

## 🌐 API Testada

- **ServeRest**
- Documentação: https://serverest.dev

---

## 📂 Estrutura do Projeto

src
└── test
    └── java
        └── api
            ├── auth        # Testes de autenticação (login)
            ├── users       # Testes de usuários
            └── product     # Testes de produtos

---

## ✅ Cenários Testados

### 🔐 Autenticação
- Login com credenciais válidas
- Validação do token retornado

### 👤 Usuários
- Criação de usuário com dados válidos
- Listagem de usuários
- Listagem autenticada com token

### 📦 Produtos
- Criação de produto sem token (401)
- Criação de produto com token (201)
- Atualização de produto
- Exclusão de produto

---

## ▶️ Como Executar os Testes Localmente

### Pré-requisitos:
- Java 17+
- Maven

### Executar:
```bash
mvn test

---

## 🔄 Integração Contínua (CI)

O projeto conta com **GitHub Actions** para execução automática dos testes a cada:

* Push
* Pull Request

O workflow:

* Faz checkout do código
* Configura o ambiente Java
* Executa os testes via Maven

---

## 📌 Observações

Os testes utilizam dados dinâmicos (ex.: email e nome de produto) para evitar conflitos em execuções repetidas

O foco do projeto é qualidade de código, clareza e estabilidade em CI, não volume excessivo de testes

Projeto desenvolvido com objetivo de aprendizado prático

---