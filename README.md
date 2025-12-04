# 📦 Supply Chain Management System (Java & Angular)

![Status](https://img.shields.io/badge/Status-Concluído-success) ![Java](https://img.shields.io/badge/Backend-Java%20Enterprise-red) ![Angular](https://img.shields.io/badge/Frontend-Angular%2017-dd0031)

Sistema completo de gerenciamento de estoque desenvolvido para demonstrar o domínio dos fundamentos da Engenharia de Software. 

Diferente de projetos comuns que dependem excessivamente de frameworks, este sistema utiliza uma arquitetura **"Java Root" (Servlet/JDBC)** no backend para garantir performance máxima e controle total sobre o ciclo de vida das requisições, conectado a um frontend moderno em **Angular**.

---

## 📸 Screenshots

<img width="2554" height="910" alt="image" src="https://github.com/user-attachments/assets/7f90f0fa-fe6c-44e9-b540-8e7595ccc81b" />

<img width="2555" height="907" alt="image" src="https://github.com/user-attachments/assets/b6c679a4-96b9-4d53-8c92-5fa1bfd76200" />



---

## 🚀 Funcionalidades

### 📦 Gestão de Produtos (CRUD Completo)
* **Listagem (Read):** Visualização de dados em tabela interativa (Angular Material) com formatação monetária e feedback de carregamento.
* **Cadastro (Create):** Formulário reativo em Modal (Dialog) com validação de campos obrigatórios e valores negativos.
* **Edição (Update):** Reutilização inteligente do componente de Dialog para editar registros existentes.
* **Exclusão (Delete):** Remoção segura de itens com confirmação visual.

### 📄 Relatórios
* **Geração de PDF:** Endpoint dedicado no Java que gera relatórios de inventário em tempo real utilizando a biblioteca **OpenPDF**, sem depender do frontend para processamento.

---

## 🛠 Tecnologias e Arquitetura

O projeto segue o padrão **Monorepo**, dividindo claramente as responsabilidades:

### ☕ Backend (Java Enterprise)
Focado em alta performance e entendimento profundo do protocolo HTTP e SQL.
* **Java 17 (LTS)**
* **Jakarta EE (Servlets):** Para manipulação de rotas e requisições HTTP.
* **JDBC Nativo:** Conexão direta com o banco para otimização de queries, prevenindo SQL Injection com `PreparedStatement`.
* **Design Patterns:**
    * **DAO (Data Access Object):** Isolamento da camada de dados.
    * **Singleton:** Para gerenciamento de conexões.
    * **Factory:** Para criação de instâncias do banco.
* **OpenPDF:** Geração de arquivos binários.
* **Maven:** Gerenciamento de dependências.

### 🅰️ Frontend (Modern Web)
Interface reativa e componentizada.
* **Angular 17+:** Uso de **Standalone Components** (arquitetura moderna sem NgModules).
* **Angular Material:** UI Kit profissional para tabelas, dialogs, botões e inputs.
* **RxJS:** Manipulação de fluxos assíncronos e Observables.
* **Change Detection Strategy:** Otimização de renderização manual.
* **SCSS:** Estilização encapsulada.

### 🗄 Banco de Dados
* **MySQL 8:** Persistência relacional robusta.

---

## ⚙️ Como executar localmente

### Pré-requisitos
* Java JDK 17+
* Node.js (v18+)
* MySQL Server
* Maven

### 1. Configuração do Backend
1. Navegue até a pasta `/backend`.
2. Configure o banco de dados no arquivo `src/main/resources/db.properties` com seu usuário e senha do MySQL.
3. Execute o script de criação das tabelas (disponível em `src/main/resources/queries.properties` ou documentação anexa).
4. Importe o projeto no Eclipse (ou IDE de preferência) e execute no servidor **Tomcat 10+**.

### 2. Configuração do Frontend
1. Navegue até a pasta `/frontend`.
2. Instale as dependências:
   ```bash
   npm install
