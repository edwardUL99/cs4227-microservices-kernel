package ie.ul.microservices.kernel.server;

import ie.ul.microservices.kernel.server.monitoring.Monitor;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public Application context;
    static Registry registry;
    static Monitor monitor;

    @Autowired
    public Application(Registry registry, Monitor monitor){
        Application.registry = registry;
        Application.monitor = monitor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        monitor.startMonitoring();
    }
}
