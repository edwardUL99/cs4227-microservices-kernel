package ie.ul.microservices.kernel.api.client;

import java.net.InetAddress;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.subst.Token.Type;
import ie.ul.microservices.kernel.api.requests.Request;
import ie.ul.microservices.kernel.api.requests.RequestBuilder;
import ie.ul.microservices.kernel.api.requests.RequestSender;
import ie.ul.microservices.kernel.api.server.RegistrationRequest;

@Configuration
@Component
public class RegistrationRunner implements CommandLineRunner {

    @Autowired
    Environment environment;

    @Value("${kernel-url}")
    private String kernelURL;
    @Value("${microservice-name}")
    private String microserviceName;
    @Value("${server.port}")
    private int port;

    @Override
    public void run(String... args) throws Exception {
        environment.getProperty("server.port");
        String host = InetAddress.getLocalHost().getHostName();
        //String host = "localhost";

        String url = kernelURL + "/api/gateway/connect/";

        //DEBUG
        System.out.println("RegistrationRunner");
        System.out.println("microservicename: " + microserviceName);
        System.out.println("host: " + host);
        System.out.println("port: " + port);
        System.out.println("server.port: "+ environment.getProperty("server.port"));
        System.out.println("url: " + url);

        RegistrationRequest registrationRequest = new RegistrationRequest(microserviceName, host, port);
        Request req = new RequestBuilder()
                    .withBody(registrationRequest)
                    .withHeader("Content-Type","application/json")
                    .withUrl(url)
                    .withMethod(HttpMethod.POST)
                    .build();
                    
        RequestSender aRequestSender = new RequestSender();
        aRequestSender.sendRequest(req);
    }
    
}
