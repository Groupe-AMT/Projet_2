# Projet 2

# Build and run the Applications microservice
## Infrastructure
Pour lancer l’application en elle même, il faut se déplacer dans le dossier **application-impl** puis lancer le script run.sh. Il permettra de lancer la base de donnée mysql ainsi que la page phpmyadmin pour administrer et voir le contenu de la table. Ensuite on lance springboot. 

```sh
cd Docker 
docker-compose up --build 
cd ../application-impl
mvn clean install && mvn sping-boot:run
```
## Implémentation
### /PointScaleRewards/{id}
- GET : retourne l'entité PointScaleStats qui contient
  - L'application, pointScaleEntity, l'Id User, l'amout et un timestamp

```json

```

### /BadgeRewards/{id}
- GET : retourne l'entité BadgeRewards qui contient
  - L'application, pointScaleEntity, l'Id User, l'amout et un timestamp

```json
  
```

### /rules
- POST: on peut uniquement POST des règles

```json
{
  "if": {
    "action": "string",
    "attribute": "string"
  },
  "name": "string",
  "then": {
    "badge": "string",
    "points": {
      "amount": 0,
      "pointscale": "string"
    }
  }
}  
```
### /events
- POST: on peut uniquement POST des events


```json
{
  "IDUser": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "action": "string",
  "attribute": "string",
  "timestamp": "2020-12-26T14:28:34.144Z",
  "userName": "string"
}  
```
### /badges
- POST : Permet de créer un badge 

```json
{
  "image": "string",
  "name": "string"
}
```
- GET: Donne la liste de badge pour l'application en cours d'utilisation. 

```json
[
  {
    "name": "Badge925",
    "image": "image"
  },
  {
    "name": "Badge826",
    "image": "image"
  }
]  
```
### /pointscales

- POST : Permet de créer un pointscale 

```json
{
  "image": "string",
  "name": "string"
}  
```
- GET: Donne la liste de pointscale pour une application donnée 

```json
[
  {
    "name": "PS1",
    "scale": 201
  },
  {
    "name": "string",
    "scale": 34
  }
]
```

### /badges/{id}

- GET: retourne un badge avec l'id correspondant pour l'application en cours

```json
{
  "name": "badg1",
  "image": "test1"
}
```
Pour un badge qui n'existe pas, ou du moins pour cette application voici le type de réponse
```json
{
  "timestamp": "2020-12-26T14:26:14.400+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "",
  "path": "/badges/10"
}
```



### /applications

- POST:  Permet de créer une application 

```json
{
  "contact": "string",
  "description": "string",
  "name": "string"
}
```
- GET: Voir si on garde ça

```json
{
    "name": "app3",
    "description": "test3",
    "contact": "Jerome Arn",
    "XApiKey": "45ecbaf9-f789-4b21-adfc-30435e916ff3"
}
```
### /pointscales/{id}
- GET : retourne un pointscales avec l'id correspondant pour une application donnée 

```json
{
  "name": "PS1",
  "scale": 201
}
```
Pour une Point Scale qui ne figure pas dans l'application en cours d'utilisation

```json
{
  "timestamp": "2020-12-26T14:31:05.795+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "",
  "path": "/pointscales/10"
}
```
### /overallrankings

- GET : 

```json
  
```

### /rankings

- GET effectue un classement par utilisateur pour les badges et les point scale toutes classe confondues 

```json
[
  {
    "name": "Badge Rewards Ranking",
    "results": []
  },
  {
    "name": "PointScale Rewards Ranking",
    "results": [
      {
        "iduser": "affeab9b-8171-4853-83be-299bb35eba8f",
        "nbItems": 2
      }
    ]
  }
]
```



## Tests