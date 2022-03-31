package ie.ul.microservices.kernel.server;

import org.springframework.boot.SpringApplication;

@KernelServer
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
