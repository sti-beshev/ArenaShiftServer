# Arena Shift Server

This is a web app for maintaining workers shifts schedule for a workplace that i used to work. 
Also it serves as a backend to a client app - [Arena Shift](https://play.google.com/store/apps/details?id=com.beshev.arenashift&hl=en).

## The Frontend

The frontend is written in HTML5 with [AngularJS](https://angularjs.org/).

## The Backend

* The backend is written in Java 7 and it uses Java EE features.
* It is completely ready to be deployed to [Google App Engine](https://cloud.google.com/appengine/).
* For database uses [Google Cloud Datastore](https://cloud.google.com/appengine/docs/standard/java/datastore/). A high scalability NoSQL database with high performance.
* Have a fully functional REST API using [Jersey](https://jersey.github.io/).
* Tested with unit and integration tests using [JUnit 4](https://junit.org/junit4/).

## Others

The project is build, run and deployed with [Gradle](https://gradle.org/).