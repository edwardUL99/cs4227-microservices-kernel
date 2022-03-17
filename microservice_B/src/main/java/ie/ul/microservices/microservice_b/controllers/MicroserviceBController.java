package ie.ul.microservices.microservice_b.controllers;

import ie.ul.microservices.kernel.api.client.FrontController;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MicroserviceBController implements ApplicationContextAware, FrontController {
    private ApplicationContext context;
    @Value("${server.port}")
    private String serverPort;
    @Value("${microservice-name}")
    private String microserviceName;
    @Value("${kernel-url}")
    private String kernelURL;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public String getKernelURL() {
        return kernelURL;
    }

    @Override
    public ResponseEntity<HealthResponse> health() {
        HealthResponse healthResponse = new HealthResponse(getMicroserviceName());
        return new ResponseEntity<>(healthResponse, HttpStatus.OK);
    }

    @Override
    public void shutdown() {
        SpringApplication.exit(context, () -> 0);
        System.out.println("Microservice " + getMicroserviceName() + " shutdown");
    }


}
