# cs4227-microservices-kernel
This project is a framework that is used to define a microservice kernel. A microservice kernel allows microservices to register
themselves with the microservice under a specified name. Registration occurs every time the microservice starts up so that the
hostname and port of the microservice is updated automatically as long as the microservice can communicate with the kernel. 

## Overview
The aim of this is to allow for automated service discovery and automated microservice configuration and orchestration.
Rather than the kernel having to be manually configured with a list of services, as long as the microservices follow the kernel API,
they can automatically register themselves. The kernel also provides a monitoring service to monitor the health of each microservice 
instance registered within the kernel.

The kernel then provides a gateway to the services to clients. Rather than each client having to know the hostname 
and IP address of each microservice they wish to send a request to, they only need to know the details of the kernel server.
They then send their request to the kernel with information search as the service name and the request body/parameters. 

The gateway then maps the request to an available microservice and returns the response as soon as the microservice that
can handle the request sends the response to the kernel. With the gateway, you can have any number of microservices running without 
increasing the number of different URLs the clients have to use. Changes to these URLs are also done transparently to the clients.  

## Requirements
- To use the project, you need JDK 11
- Maven 3.6.3

## Kernel Server
The kernel server is the gateway which maps incoming requests to registered microservices. It provides the following features:
- Mapping of incoming requests to microservices that can handle them. The URL should be of the form:
http://gateway-url:gateway-port/api/gateway/microservice-name/rest/of/request
- Authentication of incoming requests using JWT tokens through the:
  - /api/gateway/register/ and /api/gateway/authenticate/ POST endpoints
- Registration of client microservices
- Monitoring of registered microservices

### Creating a kernel server
To create a kernel server, add the following dependency to your pom.xml file:
```xml
<dependency>
    <groupId>ie.ul.microservices.kernel</groupId>
    <artifactId>server</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
This dependency includes the APIs and files necessary for the server to function. Then create a main application like so:
```java
import ie.ul.microservices.kernel.server.KernelServer;
import org.springframework.boot.SpringApplication;

@KernelServer
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```
The KernelServer annotation is provided by the server artifact and sets up the server so that it will scan for all the Spring
components it requires and load up all API endpoints.

In the application.properties file add the following line:
```
# don't register the kernel with itself
kernel-register=false
```
This is so that the server does not attempt to register with itself. You can also change the port by adding the line:
```
server.port=8090
```

## Creating a microservice
The process of creating a microservice that will be registered to the kernel is as follows
1. Add the following dependency to your pom.xml:
```xml
<dependency>
    <groupId>ie.ul.microservices.kernel</groupId>
    <artifactId>api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
This artifact provides the APIs necessary for a microservice to be able to be registered with the kernel
2. Create a controller class that implements the `ie.ul.microservices.kernel.api.client.FrontController.java` interface like so:
```java
package ie.ul.microservices.sample.microservice.controllers;

import ie.ul.microservices.kernel.api.client.FrontController;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleFrontController implements FrontController {
    /**
     * This endpoint is called by the kernel to request a health check on the microservice
     *
     * @return the response entity containing the health response
     */
    @Override
    public ResponseEntity<HealthResponse> health() {
        return null;
    }

    /**
     * The kernel can call this endpoint to tell the microservice that it should be shutdown. The response should be sent back
     * to the kernel before shutdown.
     * Sent as a POST mapping since it can be considered to "change" something on the server, i.e. the status of it
     */
    @Override
    public void shutdown() {
    }
}
```
You need to define what parameters of the HealthResponse should be returned from the health endpoint. The shutdown endpoint
doesn't return anything, just tells the microservice that it should shut itself down.
3. Create a main application like so:
```java
import ie.ul.microservices.kernel.api.KernelMicroservice;
import org.springframework.boot.SpringApplication;

@KernelMicroservice
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```
This annotation imports all the components necessary to start-up the microservice and then register itself with the
kernel
4. In the application.properties file, add the following properties:
```
server.port=8900
microservice-name=microservice-name
kernel-url=http://localhost:8080
```
This defines the port the microservice will run on, the name of the microservice and the URL the kernel is running on. The
IP address of the microservice is collected automatically