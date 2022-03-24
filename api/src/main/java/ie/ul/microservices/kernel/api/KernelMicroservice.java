package ie.ul.microservices.kernel.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a microservice to be registered with the kernel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootApplication
@ComponentScan(basePackages = "ie.ul.microservices.kernel.api")
@ComponentScan
@EnableJpaRepositories
@EntityScan
public @interface KernelMicroservice {
}
