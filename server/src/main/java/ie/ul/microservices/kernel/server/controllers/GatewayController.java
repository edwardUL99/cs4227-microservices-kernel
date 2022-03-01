package ie.ul.microservices.kernel.server.controllers;

import ie.ul.microservices.kernel.server.Constants;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.services.MappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller takes all requests from the clients and then does some mapping and forwards the request
 * TODO decide how this will work
 */
@RestController
@RequestMapping(Constants.API_GATEWAY)
public class GatewayController {
    /**
     * The service used for mapping
     */
    private final MappingService mappingService;

    /**
     * Create the GatewayController
     * @param mappingService the service used for mapping
     */
    public GatewayController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @RequestMapping("/**")
    public ResponseEntity<?> gateway(HttpServletRequest request, HttpServletResponse response) {
        MappingResult result = this.mappingService.mapRequest(request);
        return ResponseEntity.ok(result);
    }
}
