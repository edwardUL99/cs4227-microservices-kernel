package ie.ul.microservices.kernel.server.config;

import ie.ul.microservices.kernel.api.requests.RequestParserFactory;
import ie.ul.microservices.kernel.api.requests.adapters.RequestParserAdapter;
import ie.ul.microservices.kernel.api.requests.adapters.RequestParserDelegate;
import ie.ul.microservices.kernel.api.requests.adapters.RequestTransformerDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This component represents a configuration of request parsing adapters
 */
@Component
public class AdaptersConfigurer {
    /**
     * The map of content types to adapters
     */
    private final Map<String, RequestParserAdapter> adapters = new HashMap<>();

    /**
     * Construct and register an adapter with the given content type to take the given parser delegate to perform the adapted parsing
     * and the transformer delegate the transform the APIRequest into an object that can be accepted by the parser delegate
     * @param contentType the content type to register the adapter with
     * @param parserDelegate the delegate that performs the adapted parsing
     * @param transformerDelegate the delegate that transforms the request to the object expected by parserDelegate
     * @return instance of this for chaining
     */
    public AdaptersConfigurer registerAdapter(String contentType, RequestParserDelegate parserDelegate, RequestTransformerDelegate transformerDelegate) {
        return registerAdapter(contentType, new RequestParserAdapter(parserDelegate, transformerDelegate));
    }

    /**
     * Register the adapter with the given content type
     * @param contentType the content type the adapter should be registered for
     * @param adapter the adapter to register
     * @return instance of this for chaining
     */
    public AdaptersConfigurer registerAdapter(String contentType, RequestParserAdapter adapter) {
        this.adapters.put(contentType, adapter);

        return this;
    }

    /**
     * This method takes the configured adapters and registers them with the {@link ie.ul.microservices.kernel.api.requests.RequestParserFactory}
     * so that it knows the adapter to construct for a given content type
     */
    public void registerAdapters() {
        for (Map.Entry<String, RequestParserAdapter> e : adapters.entrySet())
            RequestParserFactory.registerAdapter(e.getKey(), e.getValue());
    }
}
