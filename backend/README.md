# Backend — Ford Dealership (Spring Boot)

API REST que substitui o antigo `json-server` (mock) do projeto Angular.
Java 17 · Spring Boot 3.3 · Spring Security + JWT · H2 em memória.

## Pré-requisitos
- JDK 17 (o projeto usa o Maven Wrapper, **não** precisa instalar Maven).
- A porta **8080** precisa estar livre (o Docker Desktop costuma ocupá-la — pare-o se necessário).

## Como rodar

```bash
# a partir da pasta backend/
./mvnw spring-boot:run          # Linux/Mac
.\mvnw.cmd spring-boot:run      # Windows (PowerShell)
```

> No Windows, se o caminho do projeto tiver acentos/espaços, prefira rodar o jar:
> ```powershell
> .\mvnw.cmd -DskipTests clean package
> java -jar .\target\dealership-0.0.1-SNAPSHOT.jar
> ```

A aplicação sobe em `http://localhost:8080`.
Console do banco: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:ford`, user `sa`, sem senha).

## Dados iniciais (seed)
No startup, `DataSeeder` carrega `src/main/resources/seed-data.json` (cópia do antigo `db/users.json`)
quando o banco está vazio. As senhas em texto puro são re-hasheadas com BCrypt.

Usuários de teste (senha original mantida):
| E-mail | Senha | Papel |
|---|---|---|
| admin3213@email.com | admin | admin |
| teste@teste.com | teste@123 | usuario |

## Endpoints

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/auth/register` | público |
| POST | `/api/auth/login` → `{ token, user }` | público |
| GET | `/api/cars` · `/api/cars/{id}` · `/api/cars/featured` · `/api/cars/latest` | público |
| POST/PUT/DELETE | `/api/cars` · `/api/cars/{id}` | ADMIN |
| POST | `/api/purchases` | autenticado (usuário) |
| GET | `/api/purchases` | ADMIN |
| PUT | `/api/purchases/{id}/status` | ADMIN |

Rotas protegidas exigem o header `Authorization: Bearer <token>`.

## Configuração
Veja `src/main/resources/application.properties` (porta, H2, segredo/expiração do JWT).
Em produção, mova `app.jwt.secret` para uma variável de ambiente.
