# OAuth2 Client Credentials com Keycloak

Este projeto demonstra a implementação do fluxo **Client Credentials** do **OAuth2**, utilizando o **Keycloak** como provedor de autenticação.

## 🧾 Sobre

O fluxo **Client Credentials** é ideal para comunicação entre serviços (machine-to-machine), onde uma aplicação backend se autentica diretamente no servidor de autorização (Keycloak) sem interação de usuários finais.

## 🛠️ Tecnologias

- Java / Spring Boot
- OAuth2 (Client Credentials Grant)
- Keycloak (Servidor de Autenticação)
- Docker (para rodar o Keycloak admin console)
- HTTP (para testes)

## ⚙️ Pré-requisitos

- Java 21+
- Keycloak 24+ instalado e em execução
- Um `client-service` configurados no Keycloak com o fluxo de autenticação `Service accounts roles`

## 📥 Instalação

1. Clone o repositório:

```bash
git https://github.com/Emanuel-rawa/Client-Credentials
cd Client-Credentials
```

2. Configure o Keycloak:

- Inicialize o Keycloak:

```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
```

- Crie um **Client** com:
  - `client_id`: por exemplo, `client-service`
  - Habilite o fluxo `Service accounts roles`
  - Copie o `client_secret`

3. Atualize as variáveis de ambiente em [application.yaml](client-server/src/resource/application.yaml):

```yaml
client-id: XXXXX
client-secret: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
```

## ▶️ Como executar

Com o docker do Keycloak rodando, faça

```bash
mvn -f client-server/pom.xml spring-boot:run -DskipTests
mvn -f resource-server/pom.xml spring-boot:run -DskipTests
```

## 🔐 Como funciona

1. A aplicação envia uma requisição `POST` para o endpoint de token do Keycloak:

```
POST /realms/<realm>/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials&
client_id=CLIENT_ID&
client_secret=CLIENT_SECRET
```

2. O Keycloak responde com um token de acesso JWT.

3. O token pode ser usado para autenticar requisições a APIs protegidas.

## 📫 Testando com o Neovim

Necessário ter rest.nvim

1. Acesse o arquivo `sample.http`
2. Faça `:Rest run` em alguma requisição do arquivo

## 📁 Estrutura do Projeto

```
📁 Client-Credentials/
├── 📁 client-server/
│   ├── 📄 pom.xml
│   ├── ▶️ mvnw
│   ├── ▶️ mvnw.cmd
│   └── 📁 src/
│       └── 📁 main/
│           ├── 📁 java/
│           │   └── 📁 br/
│           │       └── 📁 com/
│           │           └── 📁 clientcredentials/
│           │               └── 📁 client_server/
│           │                   ├── 📄 ClientServerApplication.java
│           │                   ├── 📄 HelloController.java
│           │                   ├── 📄 RestClientConfig.java
│           │                   └── 📄 SecurityConfig.java
│           └── 📁 resources/
│               ├── 📄 application.yaml
│               ├── 📁 static/
│               └── 📁 templates/
├── 📁 resource-server/
│   ├── 📄 pom.xml
│   ├── ▶️ mvnw
│   ├── ▶️ mvnw.cmd
│   └── 📁 src/
│       └── 📁 main/
│           ├── 📁 java/
│           │   └── 📁 br/
│           │       └── 📁 com/
│           │           └── 📁 clientcredentials/
│           │               └── 📁 resource_server/
│           │                   └── 📄 ResourceServerApplication.java
│           └── 📁 resources/
│               ├── 📄 application.properties
│               ├── 📁 static/
│               └── 📁 templates/
└── 📄 sample.http

```

## ✅ Licença

Este projeto está sob a licença MIT. Veja o arquivo [MIT](LICENSE) para mais detalhes.
