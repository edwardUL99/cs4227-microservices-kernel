package ie.ul.microservices.kernel.server.registration;

import java.util.List;

import ie.ul.microservices.kernel.server.models.Microservice;

public class RegistryImpl implements Registry {

    List<Microservice> microservices;
    @Override
    public List<Microservice> getMicroservices() {
        return microservices;
    }

    @Override
    public void registerMicroservice(Microservice microservice) {
        microservices.add(microservice);
    }

    @Override
    public void unregisterMicroservice(Microservice microservice) {
        microservices.remove(microservice);
        
    }
    
}
