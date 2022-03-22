package ie.ul.microservices.kernel.api.requests.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ie.ul.microservices.kernel.api.requests.RequestException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * This class provides utility methods for adapters to use
 */
public final class AdapterUtils {
    /**
     * A mapper for mapping XML to JSON
     */
    private static final XmlMapper xmlMapper = new XmlMapper();
    /**
     * A mapper for mapping the JSON to string
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * The GSON mapper for getting the JsonObject value
     */
    private static final Gson gson = new Gson();

    /**
     * A function for parsing an XML request body into JSON
     * @param body the object representing the body, expected to be an instance of XMLRequest
     * @return the object representing the parsed JSON
     */
    public static JsonObject parseXMLBody(Object body) {
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
    public static JsonObject parseFormBody(Object body) {
        if (!Map.class.isAssignableFrom(body.getClass())) {
            throw new RequestException("If request type is application/x-www-form-urlencoded, the request must be transformed to a Map of parameters");
        } else {
            Map<String, String[]> parameters = (Map<String, String[]>) body;
            return gson.fromJson(gson.toJson(parameters), JsonObject.class);
        }
    }
}
