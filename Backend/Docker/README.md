# Ingsw23-24

1. Assicurarsi di essere nella directory con i file Dockerfile e docker-compose
2. Eseguire il comando `docker-compose up --build`

Per eseguire comandi SQL dentro questo container:
```bash
docker exec -it <container_id> psql -U <username> -d <database> -c "<SQL command>"
