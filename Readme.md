# Microservicio Consumidor - RabbitMQ (Actividad Formativa 5)

Microservicio encargado de la escucha de mensajes desde un broker RabbitMQ y su posterior persistencia en una base de datos Oracle Cloud. Desarrollado como parte del proyecto de la asignatura Desarrollo Cloud Native I.

## Arquitectura
El servicio implementa un patrón de consumidor asíncrono utilizando:
- **Spring Boot AMQP**: Para la gestión de la cola.
- **Spring Data JPA**: Para la persistencia de datos.
- **Oracle JDBC con Wallet**: Conectividad segura hacia Oracle Cloud.

## Tecnologías
* **Java 21**
* **Spring Boot 3.5.14**
* **RabbitMQ**
* **Oracle Database**
* **Lombok**
* **JUnit 5 / Mockito** (Pruebas unitarias e integración)

## Configuración
El archivo `application.properties` requiere las siguientes variables de entorno o configuración local:

```properties
# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672

# Oracle Cloud (Requiere carpeta de Wallet)
spring.datasource.url=jdbc:oracle:thin:@db_name_high?TNS_ADMIN=C:/Oci/wallets
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}