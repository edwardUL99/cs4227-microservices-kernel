package ie.ul.microservices.kernel.api.requests;

import javax.servlet.http.HttpServletRequest;

/**
 * A factory to build API Requests
 */
public class APIRequestFactory {
    /**
     * Takes the request and uses the specified content type to determine the type to determine the APIRequest to use.
     * @param request the request to create the APIRequest from
     * @return the created request
     */
    public static APIRequest createRequest(HttpServletRequest request) {
        return new DefaultAPIRequest(request, RequestParserFactory.getParser(request));
    }
}
