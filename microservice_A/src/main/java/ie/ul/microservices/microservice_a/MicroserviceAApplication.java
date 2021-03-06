package ie.ul.microservices.microservice_a;

import ie.ul.microservices.microservice_a.controllers.MicroserviceAController;
import org.springframework.boot.SpringApplication;
import ie.ul.microservices.kernel.api.KernelMicroservice;
import org.springframework.context.ApplicationContext;

@KernelMicroservice
public class MicroserviceAApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MicroserviceAApplication.class, args);
        MicroserviceAController microserviceAController = context.getBean(MicroserviceAController.class);

        // console output
        System.out.println(microserviceAController.getMicroserviceName() + " initialized on port " + microserviceAController.getServerPort());
    }
}
