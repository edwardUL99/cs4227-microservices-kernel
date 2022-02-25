package ie.ul.microservices.kernel.server.controllers;

import ie.ul.microservices.kernel.api.server.RegistrationController;
import ie.ul.microservices.kernel.api.server.RegistrationRequest;
import ie.ul.microservices.kernel.api.server.RegistrationResponse;
import ie.ul.microservices.kernel.api.server.UnregisterRequest;
import ie.ul.microservices.kernel.api.server.UnregisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the implementation of the registration controller
 *
 * TODO add real implementations
 */
@RestController
public class RegistrationControllerImpl implements RegistrationController {
    /**
     * Register the microservice with the kernel through the defined registration request object.
     *
     * @param request the request containing the information of the microservice that wishes to register with the kernel
     * @return the response to the registration request. It can either be successful or unsuccessful
     */
    @Override
    public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
        return null;
    }

    /**
     * This endpoint is used to instruct the kernel to remove the specified microservice from the registry. This usually results
     * in a shutdown request being sent and then removing the microservice from the kernel so that it will no longer
     * be monitored or sent requests. This can be done if something happens the microservice that it can't recover from
     * and the kernel can stop monitoring it
     *
     * @param request the register to remove the microservice from the kernel
     * @return the response after the microservice was unregistered
     */
    @Override
    public ResponseEntity<UnregisterResponse> unregister(UnregisterRequest request) {
        return null;
    }
}
