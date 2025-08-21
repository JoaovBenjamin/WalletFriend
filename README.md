# 💰 WalletFriend - Seu Amigo Financeiro Pessoal

## 📖 Descrição

O **WalletFriend** é uma aplicação RESTful completa para gerenciamento financeiro pessoal, desenvolvida em Java com Spring Boot. Este projeto ajuda usuários a organizarem suas finanças através de categorização de gastos, controle de orçamento e relatórios personalizados.

## ✨ Funcionalidades Principais

- **👤 Autenticação Segura** - Sistema de registro e login com JWT
- **📊 Gestão de Categorias** - Organize seus gastos por categorias personalizadas
- **💸 Controle de Transações** - Registre entradas e saídas financeiras
- **📈 Relatórios** - Visualize seus gastos por período e categoria
- **🔒 Segurança** - API protegida com Spring Security e tokens JWT
- **📚 Documentação** - API totalmente documentada com Swagger/OpenAPI

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.0** - Framework principal
- **Spring Security** - Autenticação e autorização
- **JWT** - Tokens de autenticação
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados 
- **Swagger/OpenAPI** - Documentação interativa
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de código boilerplate

## 📦 Como Iniciar o Projeto

### Pré-requisitos

- Java 21 
- Maven 4.0.0
- MySQL 
- Git

### 🔧 Instalação e Configuração

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/walletfriend.git
cd walletfriend
```

2.**Execute a aplicação**

```bash
# Com Maven
mvn spring-boot:run

# Ou compile e execute
mvn clean package
java -jar target/walletfriend-1.0.0.jar
```

3. **Acesse a aplicação**

```text
Servidor rodando em: http://localhost:8080
```

4. **Acesse a documentação**
   ```text
   http://localhost:8080/docs
   ```
