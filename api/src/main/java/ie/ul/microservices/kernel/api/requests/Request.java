package ie.ul.microservices.kernel.api.requests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * This class represents a request to send to a REST API
 */
public class Request {
    /**
     * The URL to send the request to
     */
    private String url;
    /**
     * The request method to send the request to
     */
    private HttpMethod method;
    /**
     * The request body
     */
    private Object body;
    /**
     * The headers for the request
     */
    private HttpHeaders headers;

    /**
     * Create a default Request object
     */
    public Request() {
        this(null, null, null, new HttpHeaders());
    }

    /**
     * Construct a Request with all the parameters
     * @param url the url to send the request to
     * @param method the request method to make the request with
     * @param body the body of the request
     * @param headers the headers of the request
     */
    public Request(String url, HttpMethod method, Object body, HttpHeaders headers) {
        this.url = url;
        this.method = method;
        this.body = body;
        this.headers = headers;
    }

    /**
     * Gets the URL to send the request to
     * @return the URL of the request destination
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the request destination
     * @param url the new URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the request method to send the request with
     * @return the request method
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Set the method to send the request with
     * @param method the request method
     */
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    /**
     * Get the body of the request
     * @return the request body
     */
    public Object getBody() {
        return body;
    }

    /**
     * Set the body of the request
     * @param body the request body
     */
    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * Get the headers to send with the request
     * @return the headers to send with the request
     */
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * Set the headers of the request
     * @param headers the headers to set
     */
    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    /**
     * Set the header with the given name and value
     * @param name the name of the header
     * @param value the header value
     */
    public void setHeader(String name, String value) {
        if (this.headers == null) {
            this.headers = new HttpHeaders();
        }

        this.headers.set(name, value);
    }
}
