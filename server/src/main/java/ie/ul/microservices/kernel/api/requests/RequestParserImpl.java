package ie.ul.microservices.kernel.api.requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * This class implements the request body parser
 */
public class RequestParserImpl implements RequestParser {
    /**
     * The GSON object for parsing JSON
     */
    private final Gson gson = new Gson();

    /**
     * Parse the body of the servlet request into JSON
     *
     * @param request the request to parse
     * @return the JsonObject representing the body
     * @throws RequestException if the body fails to be parsed
     */
    @Override
    public JsonObject parseBody(APIRequest request) throws RequestException {
        return parseBody(request.getBody());
    }

    /**
     * Parse the provided body object
     *
     * @param body the object representing the body to parse
     * @return the parsed JsonObject representing the body
     */
    @Override
    public JsonObject parseBody(Object body) {
        return gson.fromJson((String)body, JsonObject.class);
    }
}
