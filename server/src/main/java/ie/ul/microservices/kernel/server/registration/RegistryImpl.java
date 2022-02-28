package ie.ul.microservices.kernel.server.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ie.ul.microservices.kernel.server.models.Microservice;

public class RegistryImpl implements Registry {

    Map<String, Microservice> microservices;

    @Override
    public List<Microservice> getMicroservices() {
        return new ArrayList<>(microservices.values());
    }

    public Microservice getMicroservice(String name){
        return microservices.get(name);
    }

    @Override
    public void registerMicroservice(Microservice microservice) {
        microservices.put(microservice.getName() ,microservice);
    }

    @Override
    public void unregisterMicroservice(Microservice microservice) {
        microservices.remove(microservice.getName());
        
    }
    
}
