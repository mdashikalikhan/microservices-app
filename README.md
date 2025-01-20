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

**Spring Cloud Bus in Spring Cloud Config Server - Automatc Configuration update to all Microservice from private Git Repository:**

<ul>
  <li>Config Server Push configuration to Rabbit MQ by sending busrefresh request</li>
  <li>URL: <b>POST http://localhost:9292/actuator/busrefresh</b></li>
  <li>All microservices are notified the new properties values</li>
  <li>properties configuration <b>GET http://localhost:9292/user-service/default</b></li>
</ul>

**API Gateway actuator:**
<ul>
  <li>GET http://localhost:7080/actuator</li>
</ul>

**User microservice call Album microservice through API Gateway:**

<ul>
  <li>GET http://localhost:7080/query/{userId}</li>
</ul>

**Resilience4j circuit brekaer implemented. Check circuit breaker events:**

<ul>
  <li>GET http://localhost:7080/user/actuator/circuitbreakerevents</li>
</ul>
