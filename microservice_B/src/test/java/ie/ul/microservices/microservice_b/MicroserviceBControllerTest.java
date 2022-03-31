package ie.ul.microservices.microservice_b;

import static org.assertj.core.api.Assertions.assertThat;

import ie.ul.microservices.kernel.api.client.HealthResponse;
import ie.ul.microservices.microservice_b.controllers.MicroserviceBController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
public class MicroserviceBControllerTest {

    @InjectMocks
    MicroserviceBController microserviceBController;

    @Mock
    ApplicationContext context;

    @Test
    public void testHealth() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<HealthResponse> responseEntity = microserviceBController.health();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testShutdown() {
        assertThat(SpringApplication.exit(context, () -> 0)).isEqualTo(0);
    }




}
