package ie.ul.microservices.kernel.api.requests;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface provides an APIRequest which is implemented as a facade to the HttpServletRequest
 */
public interface APIRequest {
    /**
     * Get the wrapped request by this facade
     * @return the wrapped request
     */
    HttpServletRequest getWrappedRequest();

    /**
     * Parses the request's URL and returns it
     * @return the URL object representing the request
     */
    URL getRequestURL();

    /**
     * Get the request method
     * @return the method of the request
     */
    HttpMethod getMethod();

    /**
     * Retrieve the headers of the request
     * @return the request headers
     */
    HttpHeaders getHeaders();

    /**
     * Get the first header that matches the given name
     * @param name the header name
     * @return the header value
     */
    String getHeader(String name);

    /**
     * Add the header to the request facade. Note that the request returned from {@link #getWrappedRequest()}
     * will not include this header as the header is only added to the facade. To get all the headers, use the
     * {@link #getHeaders()} method
     * @param name the name of the header
     * @param header the header value
     */
    void addHeader(String name, String header);

    /**
     * Remove the header from the request. Note that if the header exists in the request returned from {@link #getWrappedRequest()},
     * the header will remain in the request. However, the header won't be in the headers returned by {@link #getHeaders()}
     * @param name the name of the header to remove from the facade
     */
    void removeHeader(String name);

    /**
     * Parses the body into a JSON object. This interface assumes that all requests coming to and from the kernel
     * will have JSON payloads, so it simply just parses the body if a body exists.
     * @return the JSON body or null if the request does not make sense to have a body
     */
    JsonObject getJSONBody();

    /**
     * Gets the body of the wrapper request. This is an expensive operation, so parsing of the body should only be carried out
     * on the initial call
     * @return the request body, usually JSON body as a String
     * @throws RequestException if the body fails to be parsed
     */
    Object getBody() throws RequestException;
}
