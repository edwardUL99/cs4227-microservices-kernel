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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public String registerMicroservice(String name, String host, int port) {
        Microservice microservice = new Microservice(host, port, name, false);
        String id = generateID(microservice);
        microservice.setMicroserviceID(id);

        registerMicroservice(microservice);
        return id;
    }

    /**
     * unregisters the given microservice from the registry
     * @params the microservice to unregistered
     */
    @Override
    public void unregisterMicroservice(Microservice microservice) {
        microservices.get(microservice.getMicroserviceName()).remove(microservice.getMicroserviceID());

    }

    /**
     * randomly generates an id for the given microservice
     * @param microservice the microservice that needs a random id
     * @return the randomly generated id of the microservice
     */
    private String generateID(Microservice microservice){
        String id = UUID.randomUUID().toString();
        if (microservices.get(microservice.getMicroserviceName()).containsKey(id)){
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
