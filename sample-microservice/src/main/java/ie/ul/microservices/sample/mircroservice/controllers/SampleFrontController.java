package ie.ul.microservices.sample.mircroservice.controllers;

import ie.ul.microservices.kernel.api.client.FrontController;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleFrontController implements FrontController {
    /**
     * This endpoint is called by the kernel to request a health check on the microservice
     *
     * @return the response entity containing the health response
     */
    @Override
    public ResponseEntity<HealthResponse> health() {
        return null;
    }

    /**
     * The kernel can call this endpoint to tell the microservice that it should be shutdown. The response should be sent back
     * to the kernel before shutdown.
     * Sent as a POST mapping since it can be considered to "change" something on the server, i.e. the status of it
     */
    @Override
    public ResponseEntity<?> shutdown() {
        return null;
    }
}
