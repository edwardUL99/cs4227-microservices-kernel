package ie.ul.microservices.kernel.server.services;

import com.google.gson.Gson;
import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.api.requests.Request;
import ie.ul.microservices.kernel.api.requests.RequestBuilder;
import ie.ul.microservices.kernel.api.requests.RequestException;
import ie.ul.microservices.kernel.api.requests.RequestSender;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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
     * Parsing JSON
     */
    private final Gson gson = new Gson();

    /**
     * Create a request service implementation
     */
    @Autowired
    public RequestServiceImpl() {
    }

    /**
     * Handle the given request exception and send the error response
     * @param ex the exception to process
     * @return the response body
     */
    private ResponseEntity<?> handleException(RequestException ex) {
        ex.printStackTrace();

        Throwable cause = ex.getCause();

        if (cause instanceof HttpClientErrorException) {
            HttpClientErrorException clientError = (HttpClientErrorException)cause;

            return ResponseEntity.status(clientError.getRawStatusCode()).body(clientError.getResponseBodyAsString());
        } else if (cause instanceof HttpServerErrorException) {
            HttpServerErrorException clientError = (HttpServerErrorException)cause;

            return ResponseEntity.status(clientError.getRawStatusCode()).body(clientError.getResponseBodyAsString());
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Sends the request and checks the response for any error conditions
     * @param request to send
     * @return the response
     */
    private ResponseEntity<?> sendAndProcessResponse(Request request) {
        try {
            ResponseEntity<?> response = sender.sendRequest(request);
            int status = response.getStatusCodeValue();

            if (status > 500) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            } else {
                return response;
            }
        } catch (RequestException ex) {
            return handleException(ex);
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
    public ResponseEntity<?> sendRequest(MappingResult result, APIRequest request) {
        String json = gson.toJson(request.getJSONBody());

        Request req = new RequestBuilder()
                .withBody(json)
                .withHeaders(request.getHeaders())
                .withUrl(result.getUrl().toString())
                .withMethod(request.getMethod())
                .build();

        return sendAndProcessResponse(req);
    }
}
