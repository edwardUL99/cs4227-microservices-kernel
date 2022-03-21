package ie.ul.microservices.microservice_b;

import ie.ul.microservices.microservice_b.controllers.MicroserviceBController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "ie.ul.microservices.kernel.api")
@SpringBootApplication
public class MicroserviceBApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MicroserviceBApplication.class, args);
        MicroserviceBController microserviceBController = context.getBean(MicroserviceBController.class);

        // console output
        System.out.println(microserviceBController.getMicroserviceName() + " initialized on port " + microserviceBController.getServerPort());
    }

}
