package ie.ul.microservices.kernel.api.requests;

import ie.ul.microservices.kernel.api.requests.adapters.FormRequestAdapter;
import ie.ul.microservices.kernel.api.requests.adapters.RequestAdapter;
import ie.ul.microservices.kernel.api.requests.adapters.XMLRequestAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * A factory to build API Requests
 */
public class APIRequestFactory {
    /**
     * A map to allow the registration of content type to a function that takes a servlet request and returns an APIRequest for that request
     */
    private static final HashMap<String, Class<? extends RequestAdapter>> adapters = new HashMap<>();

    /**
     * The default content-type able to be parsed
     */
    public static final String DEFAULT_CONTENT = "application/json";

    static {
        adapters.put("application/xml", XMLRequestAdapter.class);
        adapters.put("application/x-www-form-urlencoded", FormRequestAdapter.class);
    }

    /**
     * Create the APIRequest wrapping the provided request (default request)
     * @param request the request wrapping the request
     * @return the APIRequest object
     */
    public static APIRequest createRequest(HttpServletRequest request) {
        return createRequestForContentType(request, DEFAULT_CONTENT);
    }

    /**
     * Takes the request and uses its content type to determine the type to determine the APIRequest to use. If the content
     * type if not application/json, an appropriate request adapter implementation will be attempted to be found
     * @param request the request to create the APIRequest from
     * @return the created request
     */
    public static APIRequest createRequestForContentType(HttpServletRequest request) {
        return createRequestForContentType(request, request.getContentType());
    }

    /**
     * Construct the adapter class
     * @param request the request to pass into the adapter
     * @param adapter the adapter class object
     * @return the constructed adapter
     */
    private static RequestAdapter construct(HttpServletRequest request, Class<? extends RequestAdapter> adapter) {
        try {
            return adapter.getDeclaredConstructor(HttpServletRequest.class).newInstance(request);
        } catch (NoSuchMethodException ex) {
            throw new RequestException("Cannot construct a RequestAdapter without a constructor that takes a HttpServletRequest object");
        } catch (ReflectiveOperationException ex) {
            throw new RequestException("Failed to construct the RequestAdapter: " + adapter);
        }
    }

    /**
     * Takes the request and uses the specified content type to determine the type to determine the APIRequest to use.
     * @param request the request to create the APIRequest from
     * @param contentType the content type to create the request for. If not application/json, an appropriate adapter will
     * be found
     * @return the created request
     */
    public static APIRequest createRequestForContentType(HttpServletRequest request, String contentType) {
        if (contentType.equals(DEFAULT_CONTENT)) {
            return new DefaultAPIRequest(request);
        } else {
            Class<? extends RequestAdapter> adapter = adapters.get(contentType);

            if (adapter == null)
                throw new RequestException("No RequestAdapter found for Content-Type: " + contentType);

            return construct(request, adapter);
        }
    }
}
