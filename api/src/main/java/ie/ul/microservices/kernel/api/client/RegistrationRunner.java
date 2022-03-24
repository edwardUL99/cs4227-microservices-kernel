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

@Configuration
@Component
public class RegistrationRunner implements CommandLineRunner {

    @Autowired
    Environment environment;

    @Value("${kernel-register:true}")
    private boolean register;
    @Value("${kernel-url:}")
    private String kernelURL;
    @Value("${microservice-name:}")
    private String microserviceName;
    @Value("${server.port:8080}")
    private int port;

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
