# conexao-jdbc-postgresql
Implementação de operações SQL no PostgreSQL com JDBC utilizando Connection, Statement, PreparedStatement e QuerySQL.

## Configuração do Banco de Dados

1. Certifique-se de ter o Docker instalado na sua máquina.
2. Execute o comando abaixo para iniciar um contêiner Docker com o PostgreSQL:

```sh
docker run --name jdbc-postgres -d -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=connection-jdbc -p 5432:5432 postgres

