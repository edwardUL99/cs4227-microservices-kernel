package ie.ul.microservices.kernel.api.requests.adapters;

import com.google.gson.JsonObject;
import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.api.requests.RequestParser;
import ie.ul.microservices.kernel.api.requests.RequestParserImpl;
import ie.ul.microservices.kernel.api.requests.RequestException;

/**
 * This class provides the pluggable adapter adapting the RequestParser interface
 */
public class RequestParserAdapter implements RequestParser {
    /**
     * The delegate for parsing of the request body
     */
    private final RequestParserDelegate parserDelegate;
    /**
     * The delegate for transforming the APIRequest into a body suitable for adaptation. For example, if you want to
     * transform the request into an XMLRequest for XML adaptation, you can use request -> new XMLRequest(request.getWrappedRequest())
     */
    private final RequestTransformerDelegate transformerDelegate;

    /**
     * Construct an adapter with the given delegates which parses the Object body and returns the JsonObject
     * @param parserDelegate the delegate object for parsing
     * @param transformerDelegate the delegate object to use for transforming the body
     */
    public RequestParserAdapter(RequestParserDelegate parserDelegate, RequestTransformerDelegate transformerDelegate) {
        this.parserDelegate = parserDelegate;
        this.transformerDelegate = transformerDelegate;
    }

    /**
     * Construct an adapter with the given parsing delegate which uses the default {@link APIRequest#getBody()} as
     * the RequestTransformerDelegate
     * @param parserDelegate the delegate object for parsing
     */
    public RequestParserAdapter(RequestParserDelegate parserDelegate) {
        this(parserDelegate, APIRequest::getBody);
    }

    /**
     * Creates an adapter with the default implementation of JSON parsing
     */
    public RequestParserAdapter() {
        this(body -> new RequestParserImpl().parseBody(body));
    }

    /**
     * Parse the body of the servlet request into JSON
     *
     * @param request the request to parse
     * @return the JsonObject representing the body
     * @throws RequestException if the body fails to be parsed
     */
    @Override
    public JsonObject parseBody(APIRequest request) throws RequestException {
        return parseBody(transformerDelegate.apply(request));
    }

    /**
     * Parse the provided body object
     *
     * @param body the object representing the body to parse
     * @return the parsed JsonObject representing the body
     */
    @Override
    public JsonObject parseBody(Object body) {
        return parserDelegate.apply(body);
    }
}
