package ie.ul.microservices.kernel.server;

import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.monitoring.MonitorImpl;
import ie.ul.microservices.kernel.server.registration.RegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {
    public Application context;
    static RegistryImpl registry;
    static MonitorImpl monitor;

    @Autowired
    public Application(RegistryImpl registry, MonitorImpl monitor){
        Application.registry = registry;
        Application.monitor = monitor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        monitor.startMonitoring();
    }
}
