package ie.ul.microservices.kernel.api.requests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * This class represents a builder class for building requests
 */
public class RequestBuilder {
    /**
     * The request being built
     */
    private Request request;

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
