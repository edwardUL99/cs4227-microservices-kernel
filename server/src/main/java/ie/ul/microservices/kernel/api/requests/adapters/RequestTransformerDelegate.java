package ie.ul.microservices.kernel.api.requests.adapters;

import ie.ul.microservices.kernel.api.requests.APIRequest;

import java.util.function.Function;

/**
 * This delegate function takes an APIRequest and then transforms the body of the request in
 * a format that is suitable for adaptation.
 *
 * This delegate is used by the {@link RequestParserAdapter#parseBody(APIRequest)} method and then calls
 * {@link RequestParserAdapter#parseBody(Object)} with the return value
 */
public interface RequestTransformerDelegate extends Function<APIRequest, Object> { }
