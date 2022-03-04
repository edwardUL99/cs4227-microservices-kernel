package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.api.requests.Request;
import ie.ul.microservices.kernel.api.requests.RequestBuilder;
import ie.ul.microservices.kernel.api.requests.RequestException;
import ie.ul.microservices.kernel.api.requests.RequestSender;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.registration.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * This class represents the default implementation for the request service
 */
@Service
public class RequestServiceImpl implements RequestService {
    /**
     * The object used for sending requests
     */
    private final RequestSender sender = new RequestSender();
    /**
     * The microservice registry
     */
    private final Registry registry;

    /**
     * Create a request service implementation
     * @param registry the microservice registry
     */
    @Autowired
    public RequestServiceImpl(Registry registry) {
        this.registry = registry;
    }

    /**
     * Sends the request and checks the response for any error conditions
     * @param microservice the microservice the request was sent to
     * @param request to send
     * @return the response
     */
    private ResponseEntity<?> sendAndProcessResponse(Microservice microservice, Request request) {
        // todo may need to do more checking such as the status and exception
        try {
            ResponseEntity<?> response = sender.sendRequest(request);
            int status = response.getStatusCodeValue();

            if (status > 500) {
                registry.unregisterMicroservice(microservice);
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            } else {
                return response;
            }
        } catch (RequestException ex) {
            ex.printStackTrace();

            Throwable cause = ex.getCause();

            if (cause instanceof RestClientException) {
                microservice.setHealthStatus(false);
                registry.unregisterMicroservice(microservice);
            }

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Send the mapped request to the provided microservice
     *
     * @param result       the result of the mapped request
     * @param request      the request to send to the client
     * @return the response of the request
     */
    @Override
    public ResponseEntity<?> sendRequest(MappingResult result, HttpServletRequest request) {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        Request req = new RequestBuilder()
                .withBodyFromRequest(request)
                .withHeadersFromRequest(request)
                .withUrl(result.getUrl().toString())
                .withMethod(method)
                .build();

        return sendAndProcessResponse(result.getMicroservice(), req);
    }
}
