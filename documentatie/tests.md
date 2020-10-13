De tests worden uitgevoerd met github actions.

Als er een nieuwe commit is naar master EN als er een pull request naar master is/is geupdate, wordt dit uitgevoerd:

* Github test met Maven of de Java-tests slagen. status: ![Java CI with Maven](https://github.com/huict/prbed-2021-v2b-v/workflows/Java%20CI%20with%20Maven/badge.svg)
* terwijl de tests nog bezig zijn, wordt ook gelijk op heroku deployed. status: (status check niet beschikbaar voor heroku)
* Als heroku klaar is, gaat postman aan de slag. dit gebeurt ook met github. status: ![postman](https://github.com/huict/prbed-2021-v2b-v/workflows/postman%20+%20Java%20CI%20with%20Maven/badge.svg)

Als de java tests niet slagen, is de kans klein dat postman/heroku wel werkt.

**Als 1 van alle tests niet successvol is, de pull-request niet pushen!**
