package ie.ul.microservices.kernel.api.requests;

import ie.ul.microservices.kernel.api.requests.adapters.RequestParserAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory to build request body parsers
 */
public final class RequestParserFactory {
    /**
     * The map of registered adapters that can be used to adapt the parsing of the content type
     */
    private static final Map<String, RequestParserAdapter> adapters = new HashMap<>();
    /**
     * The default parser for JSON requests
     */
    private static final RequestParser defaultParser = new RequestParserImpl();
    /**
     * Used for logging
     */
    private static final Logger log = LoggerFactory.getLogger(RequestParserFactory.class);

    /**
     * The default content-type able to be parsed
     */
    public static final String DEFAULT_CONTENT = "application/json";

    /**
     * Register the given adapter with the given content type
     * @param contentType the content type to register the adapter with
     * @param adapter the adapter to register
     */
    public static void registerAdapter(String contentType, RequestParserAdapter adapter) {
        adapters.put(contentType, adapter);
    }

    /**
     * Get the parser that can handle the provided servlet request
     * @param request the request to determine what parser to retrieve
     * @return the retrieved parse
     */
    public static RequestParser getParser(HttpServletRequest request) {
        String contentType = request.getContentType();

        if (contentType == null) {
            log.warn("Received a request with no Content-Type specified, defaulting to {}. A Content-Type should be explicitly set where possible", DEFAULT_CONTENT);
            contentType = DEFAULT_CONTENT;
        }

        if (contentType.equals(DEFAULT_CONTENT)) {
            log.debug("Content-Type {} provided, using the default JSON Request Body Parser", DEFAULT_CONTENT);
            return defaultParser;
        } else {
            RequestParser parser = adapters.get(contentType);

            if (parser == null)
                throw new RequestException("The Content-Type " + contentType + " is not directly supported and no adapter has been registered to handle it");

            log.debug("Content-Type {} provided and not directly supported, so using adapted parser {}", contentType, parser);

            return parser;
        }
    }
}
