package ie.ul.microservices.kernel.api.requests.adapters;

import ie.ul.microservices.kernel.server.Constants;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class is used to parse XML from a request
 */
public class XMLRequest {
    /**
     * This request represents an XMLRequest
     */
    private final HttpServletRequest request;

    /**
     * Construct  an XML Request
     * @param request the request to wrap
     */
    public XMLRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Get the wrapped request
     * @return the wrapped request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Parse the request body into an XML string
     * @return the xml string
     * @throws IOException if the XML fails to be parsed
     */
    public String getXML() throws IOException {
       return (String)Constants.parseBody(request);
    }
}
