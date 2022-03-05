package ie.ul.microservices.kernel.server.monitoring;

import ie.ul.microservices.kernel.server.models.Microservice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class MonitorImpl implements Monitor {
    List<Microservice> microservices;

    @Override
    public void startMonitoring() {
        //get active microservices list from ApplicationContext?
        for(Microservice ms : microservices){
            ms.health();
        }
    }

    @Override
    public void stopMonitoring() {

    }
}
