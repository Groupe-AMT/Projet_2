# Projet 2

# Build and run the Applications microservice
## Infrastructure
Pour lancer l’application en elle même, il faut se déplacer dans le dossier **application-impl** puis lancer le script run.sh. Il permettra de lancer la base de donnée mysql ainsi que la page phpmyadmin pour administrer et voir le contenu de la table. Ensuite on lance springboot. 

```sh
cd application-impl
./run.sh
```
## Implémentation
### /PointScaleRewards/{id}
- GET : retourne l'entité PointScaleStats qui contient
  - L'application, pointScaleEntity, l'Id User, l'amout et un timestamp

### /BadgeRewards/{id}
- GET : retourne l'entité BadgeRewards qui contient
  - L'application, pointScaleEntity, l'Id User, l'amout et un timestamp

### /rules
- POST: on peut uniquement POST des règles

### /events
- POST: on peut uniquement POST des events

### /badges
- POST : Permet de créer un badge 
- GET: Donne la liste de badge pour une application donnée 

### /pointscales

- POST : Permet de créer un pointscale 
- GET: Donne la liste de pointscale pour une application donnée 

### /badges/{id}
- GET: retourne un badge avec l'id correspondant

### /applications
- POST:  Permet de créer une application 
- GET: Voir si on garde ça

### /pointscales/{id}
- GET : retourne un pointscales avec l'id correspondant

## Tests
