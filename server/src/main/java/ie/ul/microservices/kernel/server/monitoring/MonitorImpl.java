package ie.ul.microservices.kernel.server.monitoring;

import com.google.gson.Gson;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@EnableScheduling
@Service
public class MonitorImpl implements Monitor {
    private final Registry registry;
    private AtomicBoolean isMonitoring = new AtomicBoolean(true);

    @Autowired
    public MonitorImpl(Registry registry) {
        this.registry = registry;
    }

    /**
     * Test method
     */
    public void startMonitoringTest(List<Microservice> microservices) {
        List<Microservice> unhealthyMicroservices = new ArrayList<>();
        Gson gson = new Gson();

        if(isMonitoring.get()) {
            for(Microservice ms : microservices){
                ResponseEntity<HealthResponse> response = ms.health();
                boolean healthStatus = response.getStatusCode().is2xxSuccessful();
                ms.setHealthStatus(healthStatus);

                //console output
                String responseJson = gson.toJson(response);
                System.out.println(responseJson);
                System.out.println("healthStatus of " + ms.getMicroserviceName() + " set to " + healthStatus);

                //if the microservice is unhealthy add it to the list unhealthyMicroservices
                if(!healthStatus) {
                    unhealthyMicroservices.add(ms);
                    System.out.println(ms.getMicroserviceName() + " added to unhealthyMicroservices\n");
                }

                System.out.println();
            }

            //pass unhealthyMicroservices to registry to be deregistered and shutdown
            registry.handleUnhealthyMicroservices(unhealthyMicroservices);
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Autowired
    @Override
    public void startMonitoring() {
        if(isMonitoring.get()) {
            List<Microservice> microservices = registry.getMicroservices();
            List<Microservice> unhealthyMicroservices = new ArrayList<>();
            Gson gson = new Gson();

            System.out.println(microservices.size() + " active microservices\n");

            for(Microservice ms : microservices){
                ResponseEntity<HealthResponse> response = ms.health();
                boolean healthStatus = response.getStatusCode().is2xxSuccessful();
                ms.setHealthStatus(healthStatus);

                //console output
                String responseJson = gson.toJson(response);
                System.out.println(responseJson + "\n");
                System.out.println("healthStatus of " + ms.getMicroserviceName() + " set to " + healthStatus);

                //if the microservice is unhealthy add it to the list unhealthyMicroservices
                if(!healthStatus) {
                    unhealthyMicroservices.add(ms);
                    System.out.println(ms.getMicroserviceName() + " added to unhealthyMicroservices");
                }
            }

            //pass unhealthyMicroservices to registry to be deregistered and shutdown
            registry.handleUnhealthyMicroservices(unhealthyMicroservices);
        }
    }

    @Override
    public void stopMonitoring() {
        isMonitoring.set(false);
    }
}
