package ie.ul.microservices.kernel.api.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * This class represents the response to a health check from the kernel
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HealthResponse {
    private String microserviceName;
    private String microserviceID;
    private HttpStatus statusCode;
    private String status;

}
