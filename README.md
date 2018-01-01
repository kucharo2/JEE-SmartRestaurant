# JEE SmartRestaurant
Semestral project for [A4M36JEE](https://developer.jboss.org/wiki/AdvancedJavaEELabFELCVUTPodzim2017) course FEE CTU.

## Team

- Roman Kuchár (team leader)
- Pavel Matyáš
- Jakub Begera
- Pavel Štíbal

## Abstract

Intelligent system for restaurant/pub of a new age is allowing a clear and practical creation of order, without waiting for the arrival of the service and also constant control of actual spending. It brings modern UI with a touch interface and very handy feature for foreigner tourists to prevent the wrong order of accompaniment to a meal. The customer has the option to log into the system and manage only their account. Service can operate the terminal with higher privileges as a customer and help him with his payment or edit the reservation. Restaurant/pub with this system will greatly appreciate the opportunity that the user can evaluate the quality of the food or the entire restaurant or pub. 

### Domain model
![DB diagram](configuration/database/img/Domain%20model.png)

## Task manager

To manage project work distribution we used Smart Restaurant Trello [board](https://trello.com/b/k9kDPU8O/smartrestaurant).

## Local environment

### Environment setup
 Application was implemented in Java EE and deployed on [Wildfly 10](http://wildfly.org/downloads/) and it uses [PostgreSql database](https://www.postgresql.org/download/).
  
  To run application locally follow the [environment setup](configuration).
  
### Deploy
Deploying via Maven (WildFly must be locally running on default port)

```bash
mvn clean package wildfly:deploy
```

