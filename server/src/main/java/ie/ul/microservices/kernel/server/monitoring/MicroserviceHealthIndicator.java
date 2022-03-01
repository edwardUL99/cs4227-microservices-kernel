package ie.ul.microservices.kernel.server.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MicroserviceHealthIndicator implements HealthIndicator {

    HttpURLConnection conn;
    Health health;

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @GetMapping("/actuator/health")
    public Health health() {
        int responseCode = 0;
        try {
            URL url = new URL("/actuator/health");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            responseCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(responseCode == 200) {
            health = Health.up().build();
        } else {
            health = Health.down().build();
        }

        System.out.println(responseCode);

        return health;
    }

    @GetMapping("/actuator/shutdown")
    public String shutdown() {
        return "shutdown application";
    }
}
