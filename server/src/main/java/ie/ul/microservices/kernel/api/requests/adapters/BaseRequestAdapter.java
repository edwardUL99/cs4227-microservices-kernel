package ie.ul.microservices.kernel.api.requests.adapters;

import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.api.requests.APIRequestFactory;
import ie.ul.microservices.kernel.api.requests.URL;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides a base request adapter that implements all methods common to any adapter
 */
public abstract class BaseRequestAdapter implements RequestAdapter {
    /**
     * This is a request object used to delegate to for common functionality that is not dependent on the body,
     * e.g. URL, method and Headers. The adapter adds the body functionality by supplementing different getBody and getJsonBody methods
     */
    protected APIRequest request;

    /**
     * Create a base request adapter from the provided servlet request (need this constructor for APIRequestFactory
     * @param servletRequest the request to create the base from
     */
    protected BaseRequestAdapter(HttpServletRequest servletRequest) {
        this.request = APIRequestFactory.createRequest(servletRequest);
        this.request.getHeaders().set("Content-Type", "application/json"); // the request should be treated as application/json since it's adapted
    }

    /**
     * Get the wrapped request by this facade
     *
     * @return the wrapped request
     */
    @Override
    public HttpServletRequest getWrappedRequest() {
        return request.getWrappedRequest();
    }

    /**
     * Parses the request's URL and returns it
     *
     * @return the URL object representing the request
     */
    @Override
    public URL getRequestURL() {
        return request.getRequestURL();
    }

    /**
     * Get the request method
     *
     * @return the method of the request
     */
    @Override
    public HttpMethod getMethod() {
        return request.getMethod();
    }

    /**
     * Retrieve the headers of the request
     *
     * @return the request headers
     */
    @Override
    public HttpHeaders getHeaders() {
        return request.getHeaders();
    }

    /**
     * Get the first header that matches the given name
     *
     * @param name the header name
     * @return the header value
     */
    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    /**
     * Add the header to the request facade. Note that the request returned from {@link #getWrappedRequest()}
     * will not include this header as the header is only added to the facade. To get all the headers, use the
     * {@link #getHeaders()} method
     *
     * @param name   the name of the header
     * @param header the header value
     */
    @Override
    public void addHeader(String name, String header) {
        request.addHeader(name, header);
    }

    /**
     * Remove the header from the request. Note that if the header exists in the request returned from {@link #getWrappedRequest()},
     * the header will remain in the request. However, the header won't be in the headers returned by {@link #getHeaders()}
     *
     * @param name the name of the header to remove from the facade
     */
    @Override
    public void removeHeader(String name) {
        request.removeHeader(name);
    }
}
