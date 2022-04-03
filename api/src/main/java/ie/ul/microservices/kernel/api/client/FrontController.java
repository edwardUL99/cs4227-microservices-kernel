package ie.ul.microservices.kernel.api.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This interface is the controller interface that all microservices wishing to register with the kernel should implement.
 * It defines the endpoints expected by the kernel and that the kernel can therefore call.
 */
@RequestMapping("/front")
public interface FrontController {
    /**
     * This endpoint is called by the kernel to request a health check on the microservice
     * @return the response entity containing the health response
     */
    @GetMapping("/health")
    ResponseEntity<HealthResponse> health();

    /**
     * The kernel can call this endpoint to tell the microservice that it should be shutdown. The response should be sent back
     * to the kernel before shutdown.
     * Sent as a POST mapping since it can be considered to "change" something on the server, i.e. the status of it
     */
    @PostMapping("/shutdown")
    void shutdown();

}
