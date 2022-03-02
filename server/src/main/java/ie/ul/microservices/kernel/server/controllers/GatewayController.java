package ie.ul.microservices.kernel.server.controllers;

import ie.ul.microservices.kernel.server.Constants;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import ie.ul.microservices.kernel.server.services.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * The microservice registry
     */
    private final Registry registry;

    /**
     * Create the GatewayController
     * @param mappingService the service used for mapping
     * @param registry the microservice registry
     */
    @Autowired
    public GatewayController(MappingService mappingService, Registry registry) {
        this.mappingService = mappingService;
        this.registry = registry;
    }

    /**
     * The main entrypoint into the gateway. URL requests should be in the format /kernel-name/url
     * @param request the request being sent to the gateway
     * @param response the response being sent back to the client
     * @return the response body
     */
    @RequestMapping("/**")
    public ResponseEntity<?> gateway(HttpServletRequest request, HttpServletResponse response) {
        MappingResult result = this.mappingService.mapRequest(request);
        return ResponseEntity.ok(result);
    }

    /**
     * This entrypoint allows for the lookup of a microservice
     * @param name the microservice name
     * @return the found microservice
     */
    @GetMapping("/lookup")
    public ResponseEntity<?> lookup(@RequestParam String name) {
        Microservice microservice = registry.getMicroservice(name);

        if (microservice == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(microservice);
        }
    }
}
