package ie.ul.microservices.monitor;

import ie.ul.microservices.kernel.api.server.UnregistrationRequest;
import ie.ul.microservices.kernel.server.controllers.RegistrationControllerImpl;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.monitoring.Monitor;
import ie.ul.microservices.kernel.server.registration.RegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@EnableScheduling
@RestController
public class MonitorController implements Monitor {
    // TODO test once microservice registration is operational
//    private final RegistrationControllerImpl registrationController = RegistrationControllerImpl.getContext().getBean(RegistrationControllerImpl.class);
//    private final RegistryImpl registry = RegistryImpl.getContext().getBean(RegistryImpl.class);

    private final RestTemplate restTemplate;
    private AtomicBoolean isMonitoring = new AtomicBoolean(true);
    private List<MicroserviceTestClass> microservices = new ArrayList<>();

    @Autowired
    public MonitorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // test method
    @Scheduled(fixedDelay = 5000)
    public void testStartMonitoring() {
        microservices.add(new MicroserviceTestClass("localhost", 8900, "microservice-A", false));
        microservices.add(new MicroserviceTestClass("localhost", 8901, "microservice-B", false));
        if(isMonitoring.get()) {
            for(MicroserviceTestClass ms : microservices) {
                String microserviceName = ms.getMicroserviceName();
                String host = ms.getHost();
                int port = ms.getPort();
                String address = "http://" + host + ":" + port + "/front/health";
                String shutdownAddress = "http://" + host + ":" + port + "/front/shutdown";
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);
                    System.out.println("Health check:" + response);

                    if(!response.getStatusCode().is2xxSuccessful()) {
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
                }
            }
        }
    }

    // this method can be tested once microservice registration is operational
    @Scheduled(fixedDelay = 5000)
    @Override
    public void startMonitoring() {
//        if(isMonitoring.get()) {
//            List<Microservice> microservices = registry.getMicroservices();
//            for(Microservice ms : microservices){
//                String microserviceName = ms.getMicroserviceName();
//                String microserviceID = ms.getMicroserviceID();
//                String host = ms.getHost();
//                int port = ms.getPort();
//                String address = "http://" + host + ":" + port + "/front/health";
//                String shutdownAddress = "http://" + host + ":" + port + "/front/shutdown";
//
//                try {
//                    ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);
//                    System.out.println("Health check:" + response);
//
//                    if(!response.getStatusCode().is2xxSuccessful()) {
//                        // send UnregistrationRequest
//                        registrationController.unregister(new UnregistrationRequest(microserviceName,microserviceID));
//                        System.out.println("UnregistrationRequest sent to registrationController");
//
//                        // deregister microservice from service registry
//                        registry.unregisterMicroservice(ms);
//                        System.out.println("RegistryImpl.unregister called on " + microserviceName);
//
//                        // shutdown microservice
//                        ResponseEntity<String> shutdown = restTemplate.getForEntity(shutdownAddress, String.class);
//                        System.out.println("MicroserviceController.shutdown() called on " + microserviceName);
//                    }
//
//                    boolean healthStatus = response.getStatusCode().is2xxSuccessful();
//                    ms.setHealthStatus(healthStatus);
//
//                    System.out.println(ms.getMicroserviceName() + " health status: " + healthStatus + "\n");
//
//
//                    // if monitoring service cannot connect to the microservice catch exception,
//                    //deregister microservice and continue loop
//                } catch (ResourceAccessException e) {
//                    System.out.println(microserviceName + " unavailable");
//
//                    // send UnregistrationRequest
//                    registrationController.unregister(new UnregistrationRequest(microserviceName,microserviceID));
//                    System.out.println("UnregistrationRequest sent to registrationController");
//
//                    // deregister microservice from service registry
//                    registry.unregisterMicroservice(ms);
//
//                }
//            }
//        }
    }

    @Override
    public void stopMonitoring() {
        isMonitoring.set(false);
    }
}
