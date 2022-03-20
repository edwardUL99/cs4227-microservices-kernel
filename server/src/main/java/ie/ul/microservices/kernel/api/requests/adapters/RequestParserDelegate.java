package ie.ul.microservices.kernel.api.requests.adapters;

import com.google.gson.JsonObject;

import java.util.function.Function;

/**
 * This interface represents the delegate function for the RequestParserAdapter.
 * It is a delegate that takes as a parameter an Object representing the body and then returns the JsonObject
 */
public interface RequestParserDelegate extends Function<Object, JsonObject> { }
