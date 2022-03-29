package ie.ul.microservices.kernel.server.monitoring;

import ie.ul.microservices.kernel.api.server.UnregistrationRequest;
import ie.ul.microservices.kernel.server.controllers.RegistrationControllerImpl;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import ie.ul.microservices.kernel.server.registration.RegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@EnableScheduling
@RestController
public class MonitorController extends Thread implements Monitor{
    private final RegistrationControllerImpl registrationController;
    private final Registry registry;

    private final RestTemplate restTemplate;
    private AtomicBoolean isMonitoring = new AtomicBoolean(true);

    @Autowired
    public MonitorController(RegistrationControllerImpl registrationController, Registry registry) {
        this.restTemplate = new RestTemplate();
        this.registrationController = registrationController;
        this.registry = registry;
        this.start();
    }

    @Override
    public void run() {
        startMonitoring();
    }

    @Scheduled(fixedDelay = 5000)
    @Override
    public void startMonitoring() {
        if(isMonitoring.get()) {
            List<Microservice> microservices = registry.getMicroservices();
            for(Microservice ms : microservices){
                String microserviceName = ms.getMicroserviceName();
                String microserviceID = ms.getMicroserviceID();
                String host = ms.getHost();
                int port = ms.getPort();
                String address = "http://" + host + ":" + port + "/front/health";
                String shutdownAddress = "http://" + host + ":" + port + "/front/shutdown";

                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);
                    System.out.println("Health check:" + response);

                    if(!response.getStatusCode().is2xxSuccessful()) {
                        // send UnregistrationRequest
                        registrationController.unregister(new UnregistrationRequest(microserviceName,microserviceID));
                        System.out.println("UnregistrationRequest sent to registrationController");

                        // deregister microservice from service registry
                        registry.unregisterMicroservice(ms);
                        System.out.println("RegistryImpl.unregister called on " + microserviceName);

                        // shutdown microservice
                        ResponseEntity<String> shutdown = restTemplate.getForEntity(shutdownAddress, String.class);
                        System.out.println("MicroserviceController.shutdown() called on " + microserviceName);
                    }

                    boolean healthStatus = response.getStatusCode().is2xxSuccessful();
                    ms.setHealthStatus(healthStatus);

                    System.out.println(ms.getMicroserviceName() + " health status: " + healthStatus + "\n");

                    // if monitoring service cannot connect to the microservice catch exception,
                    //deregister microservice and continue loop
                } catch (ResourceAccessException e) {
                    System.out.println(microserviceName + " unavailable");

                    if(!microservices.isEmpty()) {
                        // send UnregistrationRequest
                        registrationController.unregister(new UnregistrationRequest(microserviceName,microserviceID));
                        System.out.println("UnregistrationRequest sent to registrationController");

                        // deregister microservice from service registry
                        registry.unregisterMicroservice(ms);
                    }
                }
            }
        }
    }

    @Override
    public void stopMonitoring() {
        isMonitoring.set(false);
    }
}
