package ie.ul.microservices.kernel.server.models;

import javax.servlet.http.HttpServletRequest;

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
     * Get the request body split by the path delimiter /
     * @return the split URL body
     */
    public String[] getBodyParts() {
        return this.body.split("/");
    }

    /**
     * Create a URL object from the servlet request
     * @param request the request to create the url from
     * @return the created URL
     */
    public static URL fromServletRequest(HttpServletRequest request) {
        return fromParameters(request.getScheme(), request.getLocalName(), request.getLocalPort(), request.getRequestURI(), request.getQueryString());
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
