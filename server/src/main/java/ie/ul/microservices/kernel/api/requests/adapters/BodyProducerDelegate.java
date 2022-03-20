package ie.ul.microservices.kernel.api.requests.adapters;

import ie.ul.microservices.kernel.api.requests.APIRequest;

import java.util.function.Function;

/**
 * This delegate function takes an APIRequest and then returns the body of the request in
 * a format that is suitable for adaptation.
 */
public interface BodyProducerDelegate extends Function<APIRequest, Object> { }
