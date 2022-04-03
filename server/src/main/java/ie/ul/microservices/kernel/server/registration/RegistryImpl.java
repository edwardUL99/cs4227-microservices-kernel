package ie.ul.microservices.kernel.server.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ie.ul.microservices.kernel.server.models.Microservice;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Implements the registry interface
 * Class maintains a singleton registry
 * of all available microservices
 */
@Service
@Scope("singleton")
public class RegistryImpl implements Registry, ApplicationContextAware {
    private static ApplicationContext context;
    /**
     * A map that use the name of the microservice as the key
     * and the map of microservice IDs pointing to the microservice instance with the specified name as the value
     */
    private final Map<String, Map<String, Microservice>> microservices = new HashMap<>();

    /** 
     * returns the microservices as a list
     * @return list of microservices
    */
    @Override
    public List<Microservice> getMicroservices() {
        List<Microservice> mList = new ArrayList<>();
        for (Map<String, Microservice> idMap : microservices.values()) {
            mList.addAll(idMap.values());
        }
        return mList;
    }

    /**
     * gets the microservice with the given name
     * @params the name of the microservice instance to retrieve
     * @return microservice with the specified name
     */
    @Override
    public Microservice getMicroservice(String name){
        
        if(microservices.containsKey(name)){
            Map<String, Microservice> microserviceMap = microservices.get(name);
            for (Microservice m : microserviceMap.values()) {
                if(m.isHealthy()){
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * registers the given microservice in the registry
     * @params the microservice to be registered
     */
    @Override
    public void registerMicroservice(Microservice microservice) {

        microservice.setMicroserviceID(generateID(microservice));

        if(!microservices.containsKey(microservice.getMicroserviceName())){
            Map<String, Microservice> idMicroservice= new HashMap<>();
            idMicroservice.put(microservice.getMicroserviceID(), microservice);
            microservices.put(microservice.getMicroserviceName(), idMicroservice);
        } else {
            microservices.get(microservice.getMicroserviceName()).put(microservice.getMicroserviceID(), microservice);
        }
    }

    /**
     * registers the microservice with given paramets in the registry
     * @param name - name of microservice
     * @param host - host address of the microservice
     * @param port - port number of the microservice
     * @return string id - registration id of microservice
     */
    @Override
    public String registerMicroservice(String name, String host, int port) {
        Microservice microservice = new Microservice(host, port, name, true);
        registerMicroservice(microservice);
        return microservice.getMicroserviceID();
    }

    /**
     * unregisters the given microservice from the registry
     * @params the microservice to unregistered
     */
    @Override
    public void unregisterMicroservice(Microservice microservice) {
        if(microservices.containsKey(microservice.getMicroserviceName())){
            if(microservices.get(microservice.getMicroserviceName()).containsKey(microservice.getMicroserviceID())) {
                microservices.get(microservice.getMicroserviceName()).remove(microservice.getMicroserviceID());
            }
        }
    }

    /**
     * unregiters the microservice with given parameters from the registry
     * @param microserviceName - name of microservice
     * @param id - registration id of microservice
     * @return boolean that returns true if unregistered successfully
     */
    @Override
    public boolean unregisterMicroservice(String microserviceName, String id) {
        if(microservices.containsKey(microserviceName)){
            if(microservices.get(microserviceName).containsKey(id)) {
                microservices.get(microserviceName).remove(id);
                return true;
            }
        }
        return false;

    }

    /**
     * randomly generates an id for the given microservice
     * @param microservice the microservice that needs a random id
     * @return the randomly generated id of the microservice
     */
    private String generateID(Microservice microservice){
        String id = UUID.randomUUID().toString();
        if (microservices.containsKey(microservice.getMicroserviceName()) && microservices.get(microservice.getMicroserviceName()).containsKey(id)){
            id = generateID(microservice);
        }
        return id;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
