package ie.ul.microservices.kernel.server.plugIn;

import java.util.Properties;

import ie.ul.microservices.kernel.server.models.Microservice;

class PlugInFactory{

    //uses a properties file to list valid microservices
    private static Properties props = new Properties(); 

    static { 
        try { 
            props.load(PlugInFactory.class.getResourceAsStream("/plugins.properties")); 
        } catch (Exception ex) { 
            throw new ExceptionInInitializerError(ex); 
        } 
    }

    /**
     * 
     * @param key - recevies a key to be compared against implementation names.
     * @return
     */
    public static Object getMicroService(String key){
        String implementationName = props.getProperty(key);
        if(implementationName == null) {
            throw new RuntimeException("unspecifed implementation");
        }
        try{
            //add code to retrieve correct microservice from registry
            return new Microservice();
        } catch(Exception ex) {
            throw new RuntimeException("factory was unable to constuct");
        }
    }
}