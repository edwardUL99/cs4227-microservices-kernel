package ie.ul.microservices.kernel.server.controllers;

import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.api.requests.APIRequestFactory;
import ie.ul.microservices.kernel.server.Constants;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import ie.ul.microservices.kernel.server.services.MappingService;
import ie.ul.microservices.kernel.server.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * This controller takes all requests from the clients and then does some mapping and forwards the request
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
     * The service for sending requests
     */
    private final RequestService requestService;

    /**
     * Create the GatewayController
     * @param mappingService the service used for mapping
     * @param registry the microservice registry
     * @param requestService service for sending requests
     */
    @Autowired
    public GatewayController(MappingService mappingService, Registry registry, RequestService requestService) {
        this.mappingService = mappingService;
        this.registry = registry;
        this.requestService = requestService;
    }

    /**
     * Dispatch the request to the microservice
     * @param request the request to dispatch
     * @return the response body
     */
    private ResponseEntity<?> dispatch(HttpServletRequest request) {
        try {
            APIRequest apiRequest = APIRequestFactory.createRequest(request);
            MappingResult result = this.mappingService.mapRequest(apiRequest);
            ResponseEntity<?> resultResponse = result.getResponse();

            if (result.isTerminated())
                return Objects.requireNonNullElseGet(resultResponse, () -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
            else if (result.getMicroservice() == null)
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();

            return requestService.sendRequest(result, result.getRequest());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * The main entrypoint into the gateway. URL requests should be in the format /kernel-name/url
     * @param request the request being sent to the gateway
     * @return the response body
     */
    @RequestMapping("/**")
    public ResponseEntity<?> gateway(HttpServletRequest request) {
        return dispatch(request);
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
