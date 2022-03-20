package ie.ul.microservices.kernel.api.requests;

import com.google.gson.JsonObject;

/**
 * This interface parses the JSON Request Body
 */
public interface RequestBodyParser {
    /**
     * Parse the body of the servlet request into JSON
     * @param request the request to parse
     * @return the JsonObject representing the body
     * @throws RequestException if the body fails to be parsed
     */
    JsonObject parseBody(APIRequest request) throws RequestException;

    /**
     * Parse the provided body object
     * @param body the object representing the body to parse
     * @return the parsed JsonObject representing the body
     */
    JsonObject parseBody(Object body);
}
