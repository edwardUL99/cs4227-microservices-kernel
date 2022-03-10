package ie.ul.microservices.kernel.api.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * This interface represents the kernel's controller for registering microservices. It is in the API module simply for visibility
 * to the clients of the available registration methods. But, it should only be implemented by a microservice kernel server
 * TODO think about other methods
 */
@RequestMapping("/kernel/registration")
public interface RegistrationController {
    /**
     * Register the microservice with the kernel through the defined registration request object.
     * @param request the request containing the information of the microservice that wishes to register with the kernel
     * @return the response to the registration request. It can either be successful or unsuccessful
     */
    @PostMapping("/register")
    ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest request);

    /**
     * This endpoint is used to instruct the kernel to remove the specified microservice from the registry. This usually results
     * in a shutdown request being sent and then removing the microservice from the kernel so that it will no longer
     * be monitored or sent requests. This can be done if something happens the microservice that it can't recover from
     * and the kernel can stop monitoring it
     * @param request the register to remove the microservice from the kernel
     * @return the response after the microservice was unregistered
     */
    ResponseEntity<UnregistrationResponse> unregister(@RequestBody @Valid UnregistrationRequest request);
}
