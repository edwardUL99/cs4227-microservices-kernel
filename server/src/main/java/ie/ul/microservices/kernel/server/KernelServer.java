package ie.ul.microservices.kernel.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation registers a main class as the microservice kernel server
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootApplication
@ComponentScan(basePackages = "ie.ul.microservices.kernel.server")
@EnableJpaRepositories(basePackages = "ie.ul.microservices.kernel.server")
@EntityScan(basePackages = "ie.ul.microservices.kernel.server")
public @interface KernelServer {
}
