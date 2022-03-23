package ie.ul.microservices.sample.microservice;

import org.springframework.boot.SpringApplication;
import ie.ul.microservices.kernel.api.KernelMicroservice;

@KernelMicroservice
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
