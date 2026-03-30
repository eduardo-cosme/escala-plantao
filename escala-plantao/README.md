# Teste Pratico - Gestao de Escala de Plantoes

API REST para cadastro de profissionais e controle de escala de plantoes.

## Tecnologias

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- H2 Database (em memoria)
- Maven
- JUnit 5 + Mockito

## Pre-requisitos

- JDK 17 instalado
- Git (opcional)

## Como rodar o projeto

> Importante: execute os comandos dentro da pasta que contem o `pom.xml`.

### Windows (PowerShell)

```powershell
cd C:\particular\desafio\escala-plantao
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```
A aplicacao sobe, por padrao, na porta `8080`.

Se a porta `8080` estiver ocupada, rode com outra porta (exemplo `8081`):

## Banco H2

Console H2 habilitado em:

- `http://localhost:8080/h2-console`

Parametros de conexao:

- JDBC URL: `jdbc:h2:mem:escala-db`
- User Name: `sa`
- Password: (em branco)

## Carga inicial automatica

Ao subir a aplicacao, a classe `DataInitializer` sincroniza dados base:

### Categorias

- `1 - Medico`
- `2 - Enfermeiro`
- `3 - Tecnico de Enfermagem`

### Turnos

- `1 - Manha (6h)`
- `2 - Tarde (6h)`
- `3 - Noite (12h)`

## Regras de negocio implementadas em plantao

- Nao permite plantao duplicado para o mesmo profissional no mesmo dia/turno.
- Nao permite exceder a carga horaria semanal do profissional.

## Endpoints principais

### Profissionais

- `POST /api/profissionais`
- `GET /api/profissionais`
- `GET /api/profissionais/categoria/{categoriaId}`

Exemplo (`POST /api/profissionais`):

```json
{
  "nome": "Maria Silva",
  "registro": "COREN-12345",
  "categoriaId": 2,
  "cargaHorariaSemanal": 36
}
```

### Plantoes

- `POST /api/plantoes`
- `DELETE /api/plantoes/{id}`
- `GET /api/plantoes/semana?data=2026-03-30`

Exemplo (`POST /api/plantoes`):

```json
{
  "profissionalId": 1,
  "turnoId": 3,
  "data": "2026-03-30"
}
```

## Testes

Rodar todos os testes:

### Windows (PowerShell)

```powershell
cd C:\particular\desafio\escala-plantao
.\mvnw.cmd test
```

Rodar apenas os testes unitarios de regra de negocio do `PlantaoService`:

### Windows (PowerShell)

```powershell
cd C:\particular\desafio\escala-plantao
.\mvnw.cmd -Dtest=PlantaoServiceTest test
```

## Estrutura (resumo)

- `src/main/java/com/escala/plantao/controller` - endpoints REST
- `src/main/java/com/escala/plantao/service` - regras de negocio
- `src/main/java/com/escala/plantao/repository` - acesso a dados
- `src/main/java/com/escala/plantao/config/DataInitializer.java` - carga inicial
- `src/test/java/com/escala/plantao/service/PlantaoServiceTest.java` - testes unitarios das regras

