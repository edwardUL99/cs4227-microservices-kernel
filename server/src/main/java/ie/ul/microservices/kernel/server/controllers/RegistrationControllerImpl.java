package ie.ul.microservices.kernel.server.controllers;

import ie.ul.microservices.kernel.api.server.RegistrationController;
import ie.ul.microservices.kernel.api.server.RegistrationRequest;
import ie.ul.microservices.kernel.api.server.RegistrationResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import ie.ul.microservices.kernel.api.server.UnregistrationRequest;
import ie.ul.microservices.kernel.api.server.UnregistrationResponse;
import ie.ul.microservices.kernel.server.Constants;
import ie.ul.microservices.kernel.server.registration.Registry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * This is the implementation of the registration controller
 *
 * TODO add real implementations
 */
@RestController
@RequestMapping(Constants.API_GATEWAY)
public class RegistrationControllerImpl implements RegistrationController, ApplicationContextAware {

    private static ApplicationContext context;
    private final Registry registry;

    public RegistrationControllerImpl(Registry registry){
        this.registry = registry;
    }

    /**
     * Register the microservice with the kernel through the defined registration request object.
     *
     * @param request the request containing the information of the microservice that wishes to register with the kernel
     * @return the response to the registration request. It can either be successful or unsuccessful
     */
    @Override
    @PostMapping("/connect")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        HttpStatus httpStatus = HttpStatus.OK;
        String id = registry.registerMicroservice(request.getName(), request.getHost(), request.getPort());
        if(id == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        }
        
        RegistrationResponse registrationResponse = new RegistrationResponse(id);
        return new ResponseEntity<>(registrationResponse, httpStatus);
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
    @PostMapping("/unregister")
    public ResponseEntity<UnregistrationResponse> unregister(UnregistrationRequest request) {
        HttpStatus httpStatus = HttpStatus.OK;

        boolean unregistered = registry.unregisterMicroservice(request.GetName(), request.GetID());
        if(!unregistered) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        }

        UnregistrationResponse unregistrationResponse = new UnregistrationResponse(unregistered);
        return new ResponseEntity<>(unregistrationResponse, httpStatus);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
