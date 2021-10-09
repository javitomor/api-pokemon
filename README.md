# api-pokemon
Ce projet permet effectuer un CRUD sur une list de "pockemos" dans un fichier CSV en ligne


#Test Technique Back-End
Petal

#INSTRUCTIONS (Français) :
L'objectif de l’exercice technique est de créer un petit projet qui exposera un API Restful à un
éventuel client. Nous validerons le fonctionnement du projet via l'application Postman.
Il n'y a pas de cadre ou de technologie obligatoire, bien que nous recommandons l’utilisation du
framework Ruby on Rails. Nous vous encourageons également à écrire des tests unitaires, bien que
ce ne soit pas une obligation.
* Vous aurez accès à un fichier en format CSV (lien ci-bas) qui contient une liste de Pokémon. 
* Ce fichier fera office de base de données, et nous vous demandons donc, à partir de cette liste,
d’exposer un API permettant d'effectuer les actions CRUD (Create, Read, Update, Delete). 
* Nous aimerions également obtenir une liste paginée de tous les Pokémon présents dans le fichier.
Une fois le projet terminé vous devez nous partager le lien du projet dans votre GitHub ou autre
solution similaire.
* Voici le lien pour le fichier CSV :
https://gist.github.com/armgilles/194bcff35001e7eb53a2a8b441e8b2c6

#INSTRUCTIONS (English) :
The goal of the technical exercise is to create a small project that will expose a Restful API to a
potential customer. We will validate how the project works via the Postman application.
There is no required framework or technology, although we recommend using the Ruby on Rails
framework. We also encourage you to write unit tests, although this is not a requirement.
You will have access to a CSV file (link below) that contains a list of Pokémon. This file will act
as a database, so we ask you to expose an API from this list to perform CRUD (Create, Read,
Update, Delete) actions. We would also like to get a paginated list of all Pokémon in the file.
Once the project is finished you should share the project link with us in your GitHub or similar
solution.
Here is the link for the CSV file:
https://gist.github.com/armgilles/194bcff35001e7eb53a2a8b441e8b2c6


#EXPLICATION
L'api développée permet de faire un CRUD sur une liste de pokémon.
Il a les méthodes :
* GET [/pokemons/v1.0]: Vous permet d'obtenir une liste paginée des pokémons créés.
* GET [/pokemons/v1.0/{id}]: Vous permet d'obtenir un pokémon par ID.
* POST [/pokemons/v1.0]: Vous permet de charger un pokémon.
* PUT [/pokemons/v1.0/{id}]: Vous permet de mettre à jour les données d'un pokémon.
* DELETE [/pokemons/v1.0/{id}]: Vous permet de supprimer un pokémon de la liste.

Pour obtenir plus d'informations sur la mise en œuvre des services, vous pouvez consulter sa documentation à l'adresse
http://localhost:8080/api-pokemon/swagger-ui.html#/

L'API utilise une base de données H2 et peut être consultée sur
* url: http://localhost:8080/api-pokemon/h2-console/
* user: sa
* pass: 

Le langage de programmation utilisé est Java 11