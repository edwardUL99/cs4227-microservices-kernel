package ie.ul.microservices.kernel.server.monitoring;

import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.RegistryImpl;

import java.util.ArrayList;
import java.util.List;

public class ManualMonitoringTest {

    public static void main(String[] args) {
        List<Microservice> microservices = new ArrayList<>();
        microservices.add(new Microservice("localhost", 8080, "microservice1", true));
        microservices.add(new Microservice("localhost", 8080, "microservice2", true));
        microservices.add(new Microservice("localhost", 8080, "microservice3", true));
        microservices.add(new Microservice("localhost", 8080, "microservice4", true));
        microservices.add(new Microservice("localhost", 8080, "microservice5", true));
        microservices.add(new Microservice("localhost", 8080, "microservice6", true));

        RegistryImpl registry = new RegistryImpl();
        MonitorImpl monitor = new MonitorImpl(registry);
        monitor.startMonitoringTest(microservices);
    }
}
