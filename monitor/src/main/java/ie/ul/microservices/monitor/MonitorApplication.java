package ie.ul.microservices.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MonitorApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MonitorApplication.class, args);
        MonitorController monitorController = context.getBean(MonitorController.class);
        System.out.println("Monitoring service initialized");
        monitorController.testStartMonitoring();
        //monitorController.startMonitoring();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
