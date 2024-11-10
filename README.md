# Pilot project to discover microservice architecture

**Run application with arguments**

**Using Maven**

./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8999 --spring.application.instance_id=dhn"

**Using Jar**

java -jar APP.jar --server.port=8999 --spring.application.instance_id=dhn


**Run RabbitMQ on windows**

.\rabbitmq-plugins.bat  enable --plugins-dir "d:\Program Files\RabbitMQ Server\rabbitmq_server-4.0.3\plugins" rabbitmq_management

.\rabbitmq-server.bat start

**URL:** http://localhost:15672/

**User/Password:** guest/guest
