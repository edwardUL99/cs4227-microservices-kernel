package ie.ul.microservices.kernel.api.requests.adapters;

import com.google.gson.JsonObject;
import ie.ul.microservices.kernel.api.requests.APIRequest;
import ie.ul.microservices.kernel.api.requests.RequestBodyParser;
import ie.ul.microservices.kernel.api.requests.RequestBodyParserImpl;
import ie.ul.microservices.kernel.api.requests.RequestException;

/**
 * This class provides the pluggable adapter adapting the RequestBodyParser interface
 */
public class BodyParserAdapter implements RequestBodyParser {
    /**
     * The delegate for the adapter
     */
    private final BodyParserDelegate parserDelegate;
    /**
     * The delegate for transforming the APIRequest into a body suitable for adaptation. For example, if you want to
     * transform the request into an XMLRequest for XML adaptation, you can use request -> new XMLRequest(request.getWrappedRequest())
     */
    private final BodyProducerDelegate producerDelegate;

    /**
     * Construct an adapter with the given delegates which parses the Object body and returns the JsonObject
     * @param parserDelegate the delegate object for parsing
     * @param producerDelegate the delegate object to use for transforming the body
     */
    public BodyParserAdapter(BodyParserDelegate parserDelegate, BodyProducerDelegate producerDelegate) {
        this.parserDelegate = parserDelegate;
        this.producerDelegate = producerDelegate;
    }

    /**
     * Creates an adapter with the default implementation of JSON parsing
     */
    public BodyParserAdapter() {
        this(body -> new RequestBodyParserImpl().parseBody(body), APIRequest::getBody);
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
        return parseBody(producerDelegate.apply(request));
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
