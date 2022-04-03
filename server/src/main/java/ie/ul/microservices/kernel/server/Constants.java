package ie.ul.microservices.kernel.server;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class holds constants and utility functions
 */
public class Constants {
    /**
     * The API gateway URL
     */
    public static final String API_GATEWAY = "/api/gateway";

    /**
     * A header to mark the request as being passed through the kernel
     */
    public static final String KERNEL_HEADER = "X-MICROSERVICE-KERNEL";

    /**
     * Remove the first part of the gateway url (/api/gateway) from the url
     * @param url the url body to remove
     * @return the url parts without gateway url
     */
    public static String removeGatewayURL(String url) {
        int index = url.indexOf(API_GATEWAY);

        if (index != -1) {
            return url.substring(index + API_GATEWAY.length());
        } else {
            return url;
        }
    }

    /**
     * private constructor for static class
     * to prevent instantiation of class.
     */
    private Constants(){
    }

    /**
     * Split the url removing empty parts
     * @param url the url to split
     * @return the split url
     */
    public static String[] splitURL(String url) {
        String[] split = url.split("/");
        List<String> splitList = new ArrayList<>();

        Arrays.stream(split)
                .filter(s -> !s.isEmpty())
                .forEach(splitList::add);

        String[] newSplit = new String[splitList.size()];
        return splitList.toArray(newSplit);
    }

    /**
     * Join the url body to a single string. If the resulting array is empty, it will be replaced with /
     * @param body the body to join.
     * @return the single url body
     */
    public static String joinURL(String[] body) {
        String newBody = String.join("/", body);
        newBody = (newBody.isEmpty()) ? "/":newBody;

        return newBody;
    }

    /**
     * Parse the body if the body is null
     * @return the parsed body
     * @throws IOException if an exception occurs
     */
    public static Object parseBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();

        return (reader.ready()) ? reader.lines().collect(Collectors.joining(System.lineSeparator())):null;
    }
}
