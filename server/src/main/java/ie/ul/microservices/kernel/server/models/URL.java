package ie.ul.microservices.kernel.server.models;

import ie.ul.microservices.kernel.server.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a URL received into the gateway
 */
public class URL {
    /**
     * The scheme of the url, http/https
     */
    private String scheme;
    /**
     * The URL hostname
     */
    private String hostname;

    /**
     * The port in the URL
     */
    private int port;

    /**
     * The rest of the URL after the http://[host]:[port]/ bit
     */
    private String body;

    /**
     * Query parameters passed with the URL
     */
    private String queryParams;

    /**
     * Only allow creation from factory methods
     */
    private URL() {}

    /**
     * Get the URL scheme
     * @return url scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Set the url scheme
     * @param scheme url scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * The hostname in the URL
     * @return URL host
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Set the URL hostname
     * @param hostname the new hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Get the URL port
     * @return url port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the url port
     * @param port the new port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the rest of the URL path
     * @return rest os path
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the rest of the URL path
     * @param body the rest of the path
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Get the query parameters passed with the request
     * @return the url query parameters
     */
    public String getQueryParams() {
        return queryParams;
    }

    /**
     * Set the query parameters passed with the request
     * @param queryParams the request query parameters
     */
    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    /**
     * Returns the URL as string
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String result = this.scheme + "://" + this.hostname;

        if (this.port != 0) {
            result += ":" + this.port;
        }

        if (this.body != null && !this.body.startsWith("/")) {
            result += "/" + this.body;
        } else {
            result += this.body;
        }

        boolean endsWithSlash = result.endsWith("/");
        if (this.queryParams != null && !this.queryParams.isEmpty()) {
            if (endsWithSlash)
                result = result.substring(0, result.length() - 1);

            result += "?" + this.queryParams;
        } else if (!endsWithSlash) {
            result += "/";
        }

        return result;
    }

    /**
     * Get the request body split by the path delimiter /
     * @return the split URL body
     */
    public String[] getBodyParts() {
        return Constants.splitURL(this.body);
    }

    /**
     * Check if this url is equal to the provided url
     * @param o the other object to compare to
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return port == url.port && Objects.equals(scheme, url.scheme) && Objects.equals(hostname, url.hostname) && Objects.equals(body, url.body) && Objects.equals(queryParams, url.queryParams);
    }

    /**
     * Generate the hashcode for this object
     * @return the generated hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(scheme, hostname, port, body, queryParams);
    }

    /**
     * Create a URL object from the servlet request
     * @param request the request to create the url from
     * @return the created URL
     */
    public static URL fromServletRequest(HttpServletRequest request) {
        return fromParameters(request.getScheme(), request.getLocalName(), request.getLocalPort(),
                Constants.removeGatewayURL(request.getRequestURI()), request.getQueryString());
    }

    public static URL fromParameters(String scheme, String hostname, int port, String body, String queryParams) {
        URL url = new URL();

        url.setScheme(scheme);
        url.setHostname(hostname);
        url.setPort(port);
        url.setBody(body);
        url.setQueryParams(queryParams);

        return url;
    }
}
