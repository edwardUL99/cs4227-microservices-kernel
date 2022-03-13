package ie.ul.microservices.kernel.api.requests.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is an adapter that can parse multipart/form-data to JSON
 */
public class FormRequestAdapter extends BaseRequestAdapter {
    /**
     * The form data body
     */
    private Object body;
    /**
     * The GSON object to use for parsing
     */
    private final Gson gson = new Gson();

    /**
     * Create an adapter from the given request
     * @param request the wrapped request
     */
    public FormRequestAdapter(HttpServletRequest request) {
        super(request);
    }

    /**
     * Parses the body into a JSON object. This interface assumes that all requests coming to and from the kernel
     * will have JSON payloads, so it simply just parses the body if a body exists.
     *
     * @return the JSON body or null if the request does not make sense to have a body
     */
    @Override
    public JsonObject getJSONBody() {
        return gson.fromJson(gson.toJson(getBody()), JsonObject.class);
    }

    /**
     * Gets the body of the wrapper request. This is an expensive operation, so parsing of the body should only be carried out
     * on the initial call
     *
     * @return the request body as a form map
     */
    @Override
    public Object getBody() {
        if (body == null)
            body = request.getWrappedRequest().getParameterMap();

        return body;
    }
}
