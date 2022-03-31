package ie.ul.microservices.kernel.server.monitoring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ie.ul.microservices.kernel.api.server.UnregistrationRequest;
import ie.ul.microservices.kernel.server.controllers.RegistrationControllerImpl;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MonitorControllerTest {

    @InjectMocks
    MonitorController monitorController;

    @Mock
    RestTemplate restTemplate;

    @Mock
    Registry registry;

    @Mock
    RegistrationControllerImpl registrationController;

    @Test
    void testStartMonitoringAllHealthy() {
        when(restTemplate.getForEntity("http://localhost:8900/front/health", String.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(restTemplate.getForEntity("http://localhost:8901/front/health", String.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        List<Microservice> microservices = new ArrayList<>();
        microservices.add(new Microservice("localhost", 8900, "Microservice_A", true));
        microservices.add(new Microservice("localhost", 8901, "Microservice_B", true));

        for (Microservice ms : microservices) {
            String host = ms.getHost();
            int port = ms.getPort();
            String address = "http://" + host + ":" + port + "/front/health";

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
            ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);

            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            System.out.println("Health check:" + response);

        }
    }

    @Test
    void testStartMonitoringAllUnhealthy() {
        when(restTemplate.getForEntity("http://localhost:8900/front/health", String.class)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        when(restTemplate.getForEntity("http://localhost:8901/front/health", String.class)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));

        List<Microservice> microservices = new ArrayList<>();
        microservices.add(new Microservice("localhost", 8900, "Microservice_A", false));
        microservices.add(new Microservice("localhost", 8901, "Microservice_B", false));

        for (Microservice ms : microservices) {
            String microserviceName = ms.getMicroserviceName();
            String microserviceID = ms.getMicroserviceID();
            String host = ms.getHost();
            int port = ms.getPort();
            String address = "http://" + host + ":" + port + "/front/health";
            String shutdownAddress = "http://" + host + ":" + port + "/front/shutdown";

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
            ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);

            assertThat(response.getStatusCodeValue()).isEqualTo(406);
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
        }
    }
}
