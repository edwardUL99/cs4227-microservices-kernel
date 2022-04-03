package ie.ul.microservices.kernel.api.client;

import java.net.InetAddress;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import ie.ul.microservices.kernel.api.requests.Request;
import ie.ul.microservices.kernel.api.requests.RequestBuilder;
import ie.ul.microservices.kernel.api.requests.RequestSender;
import ie.ul.microservices.kernel.api.server.RegistrationRequest;

/**
 * Implements CommandLineRunner
 * Registration runner implements the run method which
 * is called by microservices at run time so that they 
 * automatically register with the kernel.
 */
@Configuration
@Component
public class RegistrationRunner implements CommandLineRunner {

    /**
     * 
     */
    @Autowired
    Environment environment;

    /**
     * register - retrives boolean value to determine if registration should occur
     * kernalURL - retrieves value of kernel url from property file
     * microserviceName - retrieves value of microservice name from property file
     * port - retrieves value of server port from property file
     */
    @Value("${kernel-register:true}")
    private boolean register;
    @Value("${kernel-url:}")
    private String kernelURL;
    @Value("${microservice-name:}")
    private String microserviceName;
    @Value("${server.port:8080}")
    private int port;

    /**
     * Determines if registration should occur.
     * Fetches host address and port values for the microservice.
     * Creates url for registration request to be sent to.
     * Constructs the registration request.
     * Request is then built and sent with the registration request..
     */
    @Override
    public void run(String... args) throws Exception {
        if (register) {
            environment.getProperty("server.port");
            String host = InetAddress.getLocalHost().getHostAddress();

            String url = kernelURL + "/api/gateway/connect/";

            RegistrationRequest registrationRequest = new RegistrationRequest(microserviceName, host, port);
            Request req = new RequestBuilder()
                    .withBody(registrationRequest)
                    .withHeader("Content-Type", "application/json")
                    .withUrl(url)
                    .withMethod(HttpMethod.POST)
                    .build();

            RequestSender aRequestSender = new RequestSender();
            aRequestSender.sendRequest(req);
        }
    }
}
