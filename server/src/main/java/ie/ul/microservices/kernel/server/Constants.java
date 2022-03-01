package ie.ul.microservices.kernel.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class holds constants and utility functions
 */
public class Constants {
    /**
     * The API gateway URL
     */
    public static final String API_GATEWAY = "/api/gateway";

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
}
