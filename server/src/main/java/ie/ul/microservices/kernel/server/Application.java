package ie.ul.microservices.kernel.server;

import ie.ul.microservices.kernel.server.monitoring.MonitorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@KernelServer
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        MonitorController monitorController = new MonitorController();
        monitorController.start();
    }
}
