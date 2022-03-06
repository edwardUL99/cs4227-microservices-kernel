package ie.ul.microservices.kernel.server.monitoring;

import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.RegistryImpl;

import java.util.ArrayList;
import java.util.List;

public class ManualMonitoringTest {

    public static void main(String[] args) {
        //create list of microservices
        List<Microservice> microservices = new ArrayList<>();
        microservices.add(new Microservice(new GenericHealthReporterImpl(false)));
        microservices.add(new Microservice(new GenericHealthReporterImpl(true)));
        microservices.add(new Microservice(new GenericHealthReporterImpl(true)));
        microservices.add(new Microservice(new GenericHealthReporterImpl(false)));
        microservices.add(new Microservice(new GenericHealthReporterImpl(true)));
        microservices.add(new Microservice(new GenericHealthReporterImpl(true)));

        //set microservice attributes
        for(int i = 0; i < microservices.size(); i++) {
            microservices.get(i).setMicroserviceName("microservice" + i);
            microservices.get(i).setMicroserviceID("MS" + i);
        }

        //run startMonitoring() method on list of microservices
        RegistryImpl registry = new RegistryImpl();
        MonitorImpl monitor = new MonitorImpl(registry);
        monitor.startMonitoring();
    }
}
