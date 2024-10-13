# Pilot project to discover microservice architecture

**Run application with arguments**

**Using Maven**

./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8999 --spring.application.instance_id=dhn"

**Using Jar**

java -jar APP.jar --server.port=8999 --spring.application.instance_id=dhn
