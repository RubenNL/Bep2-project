Voer dit uit in je postgres(bijv. met pgadmin):

```postgresql
CREATE USER "vliegDbUser" WITH CREATEDB PASSWORD 'vliegDbPass';
CREATE DATABASE "vliegmaatschappij" OWNER "vliegDbUser";
```
