package ie.ul.microservices.kernel.api.requests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * This class represents a builder class for building requests
 */
public class RequestBuilder {
    /**
     * The request being built
     */
    private final Request request;

    /**
     * Construct a builder instance
     */
    public RequestBuilder() {
        this.request = new Request();
    }

    /**
     * Set the URL of the request
     * @param url the server url to send the request to
     * @return an instance of this for chaining
     */
    public RequestBuilder withUrl(String url) {
        request.setUrl(url);
        return this;
    }

    /**
     * Set the body of the request
     * @param body the request body
     * @return an instance of this for chaining
     */
    public RequestBuilder withBody(Object body) {
        request.setBody(body);
        return this;
    }

    /**
     * Parse the body from the given request and set it as the body to use in the eventual built request
     * @param request the request object to take the body from
     * @return an instance of this for chaining
     * @throws RequestException if the parsing of the body fails
     */
    public RequestBuilder withBodyFromRequest(HttpServletRequest request) throws RequestException {
        if (!request.getMethod().equalsIgnoreCase("GET")) {
            return null;
        } else {
            try {
                BufferedReader reader = request.getReader();
                String body = (reader.ready()) ? reader.lines().collect(Collectors.joining(System.lineSeparator())):null;

                return this.withBody(body);
            } catch (IOException ex) {
                throw new RequestException("An error occurred reading the body: " + ex);
            }
        }
    }

    /**
     * Set the method to send the request with
     * @param method the http method
     * @return an instance of this for chaining
     */
    public RequestBuilder withMethod(HttpMethod method) {
        request.setMethod(method);
        return this;
    }

    /**
     * Set the headers of the request
     * @param headers the request headers
     * @return an instance of this for chaining
     */
    public RequestBuilder withHeaders(HttpHeaders headers) {
        request.setHeaders(headers);
        return this;
    }

    /**
     * Set the headers of the request from the given request object
     * @return an instance of this for chaining
     */
    public RequestBuilder withHeadersFromRequest(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames != null && headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.set(header, request.getHeader(header));
        }

        return this.withHeaders(headers);
    }

    /**
     * Set a specific header on the request
     * @param name the name of the header
     * @param value the header value
     * @return an instance of this for chaining
     */
    public RequestBuilder withHeader(String name, String value) {
        request.setHeader(name, value);
        return this;
    }

    /**
     * Returns the built Request
     * @return built Request object
     */
    public Request build() {
        return request;
    }
}
