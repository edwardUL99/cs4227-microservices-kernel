package ie.ul.microservices.sample.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ie.ul.microservices.kernel.api")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
