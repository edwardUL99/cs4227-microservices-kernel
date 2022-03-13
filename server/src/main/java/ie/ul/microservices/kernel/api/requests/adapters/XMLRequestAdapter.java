package ie.ul.microservices.kernel.api.requests.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * This class is an adapter that can parse XML to JSON
 */
public class XMLRequestAdapter extends BaseRequestAdapter {
    /**
     * The XML request being adapted to the
     */
    private final XMLRequest xmlRequest;
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
     * The body parsed from the request. Initialised on first getBody call
     */
    private String body;

    /**
     * Create an XMLRequestAdapter
     * @param request the request being adapted
     */
    public XMLRequestAdapter(XMLRequest request) {
        super(request.getRequest());
        this.xmlRequest = request;
    }

    /**
     * A utility constructor function that creates an XMLRequest from the request and then adapts it
     * @param request the request to adapt
     */
    public XMLRequestAdapter(HttpServletRequest request) {
        this(new XMLRequest(request));
    }

    /**
     * Parses the body into a JSON object. This interface assumes that all requests coming to and from the kernel
     * will have JSON payloads, so it simply just parses the body if a body exists.
     *
     * @return the JSON body or null if the request does not make sense to have a body
     * @throws IOException if the body fails to be parsed
     */
    @Override
    public JsonObject getJSONBody() throws IOException {
        try {
            Reader reader = new StringReader(getBody());
            XMLStreamReader stream = XMLInputFactory.newFactory().createXMLStreamReader(reader);

            Object json = xmlMapper.readValue(stream, Object.class);
            String jsonString = objectMapper.writeValueAsString(json);

            return gson.fromJson(jsonString, JsonObject.class);
        } catch (XMLStreamException e) {
            throw new IOException("An error occurred retrieving the JSON body", e);
        }
    }

    /**
     * Gets the body of the wrapper request. This is an expensive operation, so parsing of the body should only be carried out
     * on the initial call
     *
     * @return the request body as JSON string
     * @throws IOException if the body fails to be parsed
     */
    @Override
    public String getBody() throws IOException {
        if (body == null)
            body = xmlRequest.getXML();

        return body;
    }
}
