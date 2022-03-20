package ie.ul.microservices.kernel.api.requests;

import com.google.gson.JsonObject;
import ie.ul.microservices.kernel.server.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * This class represents the default implementation of the API request
 */
public class DefaultAPIRequest implements APIRequest {
    /**
     * The wrapped request
     */
    private final HttpServletRequest wrapped;
    /**
     * The HttpHeaders object
     */
    private final HttpHeaders httpHeaders = new HttpHeaders();
    /**
     * The parser for parsing the request body
     */
    private final RequestBodyParser parser;
    /**
     * The parsed body. Cached here after the initial call to {@link #getBody()}
     */
    private Object body;

    /**
     * Create a request object that wraps a servlet request
     * @param wrapped the wrapped servlet request
     */
    public DefaultAPIRequest(HttpServletRequest wrapped) {
        this(wrapped, new RequestBodyParserImpl());
    }

    /**
     * Construct an API Request with the given request and request body parser
     * @param wrapped the wrapped API request
     * @param parser the parsing for parsing the request body
     */
    public DefaultAPIRequest(HttpServletRequest wrapped, RequestBodyParser parser) {
        this.wrapped = wrapped;

        Enumeration<String> headerNames = wrapped.getHeaderNames();

        while (headerNames != null && headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            this.httpHeaders.set(header, wrapped.getHeader(header));
        }

        this.parser = parser;
    }

    /**
     * Get the wrapped request by this facade
     *
     * @return the wrapped request
     */
    @Override
    public HttpServletRequest getWrappedRequest() {
        return wrapped;
    }

    /**
     * Parses the request's URL and returns it
     *
     * @return the URL object representing the request
     */
    @Override
    public URL getRequestURL() {
        return URL.fromServletRequest(wrapped);
    }

    /**
     * Get the request method
     *
     * @return the method of the request
     */
    @Override
    public HttpMethod getMethod() {
        return HttpMethod.valueOf(wrapped.getMethod());
    }

    /**
     * Retrieve the headers of the request
     *
     * @return the request headers
     */
    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    /**
     * Get the first header that matches the given name
     *
     * @param name the header name
     * @return the header value
     */
    @Override
    public String getHeader(String name) {
        return this.httpHeaders.getFirst(name);
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
        this.httpHeaders.add(name, header);
    }

    /**
     * Remove the header from the request. Note that if the header exists in the request returned from {@link #getWrappedRequest()},
     * the header will remain in the request. However, the header won't be in the headers returned by {@link #getHeaders()}
     *
     * @param name the name of the header to remove from the facade
     */
    @Override
    public void removeHeader(String name) {
        this.httpHeaders.remove(name);
    }

    /**
     * Parses the body into a JSON object. This interface assumes that all requests coming to and from the kernel
     * will have JSON payloads, so it simply just parses the body if a body exists.
     *
     * @return the JSON body or null if the request does not make sense to have a body
     */
    @Override
    public JsonObject getJSONBody() {
        return parser.parseBody(this);
    }

    /**
     * Gets the body of the wrapper request
     *
     * @return the request body, usually JSON body as a String
     * @throws RequestException if the body fails to be parsed
     */
    @Override
    public Object getBody() throws RequestException {
        if (getMethod() == HttpMethod.GET) {
            return null;
        } else {
            try {
                if (body == null)
                    body = Constants.parseBody(wrapped);

                return body;
            } catch (IOException ex) {
                throw new RequestException("Failed to parse body", ex);
            }
        }
    }
}
