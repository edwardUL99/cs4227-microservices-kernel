package ie.ul.microservices.kernel.server.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ie.ul.microservices.kernel.server.models.Microservice;

public class RegistryImpl implements Registry {

    /**
     * A map that use the name of the microservice as the key
     * and the microservice instance with the specfied name as the value
     */
    private Map<String, Microservice> microservices;

    @Override
    public List<Microservice> getMicroservices() {
        return new ArrayList<>(microservices.values());
    }

    /**
     * gets the microservice with the given name
     * @params the name of the microservice instance to retrieve
     * @return microservice with the specified name
     */
    @Override
    public Microservice getMicroservice(String name){
        return microservices.get(name);
    }

    /**
     * registers the given microservice in the registry
     * @params the microservice to be registered
     */
    @Override
    public void registerMicroservice(Microservice microservice) {
        microservices.put(microservice.getName() ,microservice);
    }

    /**
     * unregisters the given microservice from the registry
     * @params the microservice to unregistered
     */
    @Override
    public void unregisterMicroservice(Microservice microservice) {
        microservices.remove(microservice.getName());
        
    }
    
}
