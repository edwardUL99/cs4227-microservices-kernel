package ie.ul.microservices.kernel.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ie.ul.microservices.kernel.api.requests.RequestException;
import ie.ul.microservices.kernel.api.requests.RequestParserFactory;
import ie.ul.microservices.kernel.api.requests.adapters.BodyParserAdapter;
import ie.ul.microservices.kernel.api.requests.adapters.XMLRequest;
import org.springframework.context.annotation.Configuration;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * This class configures request parsing for the server module. It primarily registers request parser adapters
 */
@Configuration
public class RequestParsingConfig {
    /**
     * A mapper for mapping XML to JSON
     */
    private final XmlMapper xmlMapper = new XmlMapper();
    /**
     * A mapper for mapping the JSON to string
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * The GSON mapper for getting the JsonObject value
     */
    private final Gson gson = new Gson();

    /**
     * Instantiates the configuration
     */
    public RequestParsingConfig() {
        configureRequestParsingAdapters();
    }

    /**
     * A function for parsing an XML request body into JSON
     * @param body the object representing the body, expected to be an instance of XMLRequest
     * @return the object representing the parsed JSON
     */
    private JsonObject parseXMLBody(Object body) {
        if (!(body instanceof XMLRequest)) {
            throw new RequestException("If request type is application/xml, the body must be transformed to an XMLRequest object");
        } else {
            try {
                Reader reader = new StringReader(((XMLRequest)body).getXML());
                XMLStreamReader stream = XMLInputFactory.newFactory().createXMLStreamReader(reader);

                Object json = xmlMapper.readValue(stream, Object.class);
                String jsonString = objectMapper.writeValueAsString(json);

                return gson.fromJson(jsonString, JsonObject.class);
            } catch (XMLStreamException | IOException e) {
                throw new RequestException("An error occurred retrieving the JSON body", e);
            }
        }
    }

    /**
     * Parse the form body to a JsonObject.
     * @param body the form body, expected to be a map
     * @return the parsed JSON
     */
    @SuppressWarnings("unchecked")
    private JsonObject parseFormBody(Object body) {
        if (!Map.class.isAssignableFrom(body.getClass())) {
            throw new RequestException("If request type is application/x-www-form-urlencoded, the request must be transformed to a Map of parameters");
        } else {
            Map<String, String[]> parameters = (Map<String, String[]>) body;
            return gson.fromJson(gson.toJson(parameters), JsonObject.class);
        }
    }

    /**
     * Configures the adapters for request parsing
     */
    private void configureRequestParsingAdapters() {
        RequestParserFactory.registerAdapter("application/xml",
                new BodyParserAdapter(this::parseXMLBody, request -> new XMLRequest(request.getWrappedRequest())));
        RequestParserFactory.registerAdapter("application/x-www-form-urlencoded",
                new BodyParserAdapter(this::parseFormBody, request -> request.getWrappedRequest().getParameterMap()));
    }
}
