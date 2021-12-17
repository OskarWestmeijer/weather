# weather-api

Documentation:

https://www.notion.so/Openweather-API-0c064fb6e37144c38cd1cca9b6ade21d

Docker compose initializes the database on startup.

```docker-compose down

docker-compose down --volumes

docker-compose up

docker exec -it <db> sh

psql -U username1
\l (list databases)
\c carrental 
\d (list tables)
```
