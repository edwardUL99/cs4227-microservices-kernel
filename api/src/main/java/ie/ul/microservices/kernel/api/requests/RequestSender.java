package ie.ul.microservices.kernel.api.requests;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * This interface represents an object that can send a request. It is recommended to implement different senders for
 * different request methods
 */
public class RequestSender {
    /**
     * The template to use for sending requests
     */
    private final RestTemplate template = new RestTemplate();

    /**
     * Send the provided request to the destination and wait for the response
     * @param request the request to send to the server
     * @return the response from the request
     */
    public ResponseEntity<?> sendRequest(Request request) throws RequestException {
        try {
            return template.exchange(new URI(request.getUrl()), request.getMethod(), getRequestEntity(request), String.class);
        } catch (Exception ex) {
            throw new RequestException("An error occurred sending the request: ", ex);
        }
    }

    /**
     * Creates the request entity for the given request
     * @param request the request entity for the given request
     * @return the request entity
     */
    protected RequestEntity<?> getRequestEntity(Request request) {
        return RequestEntity.method(request.getMethod(), request.getUrl())
                .headers(request.getHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(request.getHeaders())
                .accept(MediaType.APPLICATION_JSON)
                .body(request.getBody());
    }
}
