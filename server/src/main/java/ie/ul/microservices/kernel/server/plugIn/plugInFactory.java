package ie.ul.microservices.kernel.server.plugIn;

import java.util.Properties;

import ie.ul.microservices.kernel.server.models.Microservice;

class PlugInFactory{
    private static Properties props = new Properties(); 

    static { 
        try { 
            props.load(PlugInFactory.class.getResourceAsStream("/plugins.properties")); 
        } catch (Exception ex) { 
            throw new ExceptionInInitializerError(ex); 
        } 
    }
    public static Object getMicroService(String key){
        String implementationName = props.getProperty(key);
        if(implementationName == null) {
            throw new RuntimeException("unspecifed implementation");
        }
        try{
            //code to return correct microservice
            return new Microservice();
        } catch(Exception ex) {
            throw new RuntimeException("factory was unable to constuct");
        }
    }
}