# 🎮 Pokémon Team Builder - Full Stack

Bem-vindo ao **Pokémon Team Builder**, uma aplicação moderna e robusta para criar e gerenciar sua equipe dos sonhos de Pokémon da 1ª Geração. Este projeto demonstra a integração entre um backend **Node.js** e um frontend de ponta utilizando **Kotlin Compose Multiplatform (Wasm)**.

---

## 🚀 Tecnologias Utilizadas

### **Frontend**
- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **Framework UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Target: **WebAssembly / Wasm**)
- **Arquitetura:** MVI (**Model-View-Intent**)
- **Rede:** [Ktor Client](https://ktor.io/) com JSON Serialization
- **Imagens:** [Kamel](https://github.com/Kamel-Media/Kamel) para carregamento assíncrono de sprites da PokeAPI.

### **Backend**
- **Ambiente:** [Node.js](https://nodejs.org/)
- **Framework:** [Express](https://expressjs.com/)
- **Banco de Dados:** [MySQL](https://www.mysql.com/)
- **Middleware:** CORS habilitado para comunicação segura entre origens.

---

## 📋 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
- [Git](https://git-scm.com/)
- [Node.js](https://nodejs.org/) (v16+)
- [JDK](https://adoptium.net/) (v17+ para rodar o Gradle/Kotlin)
- Um servidor **MySQL** ativo.

---

## 🛠️ Instalação e Configuração

### 1. Clonar o Repositório
```bash
git clone <url-do-repositorio>
cd PokemonTeamBuilderNodeComposeWeb
```

### 2. Configurar o Banco de Dados
- Certifique-se de que seu MySQL está rodando.
- Importe o arquivo `db.sql` localizado na pasta `NodeProgWeb2`:
```bash
# Exemplo via linha de comando
mysql -u seu_usuario -p < NodeProgWeb2/db.sql
```

### 3. Rodar o Backend
Navegue até a pasta do backend, instale as dependências e inicie o servidor:
```bash
cd NodeProgWeb2
npm install
npm run dev
```
O servidor estará rodando em `http://localhost:3000`.

### 4. Rodar o Frontend
Em um novo terminal, vá para a pasta do frontend e execute o comando de execução no navegador:
```bash
cd PokemonTeamBuilderFront
./gradlew wasmJsBrowserRun
```
*Aguarde a compilação inicial (pode demorar um pouco na primeira vez). O navegador abrirá automaticamente em `http://localhost:8080`.*

---

## 🌟 Funcionalidades

- **Listagem Completa:** Visualize todos os Pokémon da 1ª Geração.
- **Meu Time:** Gerencie uma equipe de até 6 Pokémon.
- **Personalização:** Adicione apelidos personalizados ao recrutar um Pokémon.
- **Sprites Oficiais:** Veja as imagens oficiais de cada espécie.
- **Interface Localizada:** UI totalmente em **Português (Brasil)**.
- **Design Responsivo:** Interface construída com Material Design para uma experiência premium.

---

## 🏗️ Estrutura do Projeto

```
.
├── NodeProgWeb2/              # Backend Express (Node.js)
│   ├── src/                   # Arquitetura por camadas (Controllers, Repositories)
│   └── db.sql                 # Script de criação do banco de dados
├── PokemonTeamBuilderFront/    # Frontend Compose Multiplatform (Wasm)
│   ├── src/wasmJsMain/kotlin/ # Código fonte Kotlin (MVI)
│   └── build.gradle.kts       # Configurações de dependências Gradle
└── .gitignore                 # Exclusões padrão para Node e Kotlin
```
