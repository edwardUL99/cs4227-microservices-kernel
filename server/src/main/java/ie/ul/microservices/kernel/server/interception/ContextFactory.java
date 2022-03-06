package ie.ul.microservices.kernel.server.interception;

import ie.ul.microservices.kernel.api.interception.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This factory provides factories for creating context objects
 */
public abstract class ContextFactory {
    /**
     * Validate that the arg types match the constructor parameter types
     * @param args the arguments
     * @param constructorTypes the type of the expected constructor parameters
     */
    private void validateArgs(Object[] args, Class<?>[] constructorTypes) {
        if (args.length != constructorTypes.length) {
            throw new RuntimeException("The number of arguments must match the constructor types");
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class<?> type = constructorTypes[i];
            Class<?> argClass = arg.getClass();

            if (!type.isAssignableFrom(argClass)) {
                throw new RuntimeException("Incompatible constructor parameter type. Constructor type " + type + ", argument type " + argClass);
            }
        }
    }

    /**
     * Construct the context from the arguments
     * @param contextClass the class object for the context
     * @param args the arguments
     * @return the constructed object
     */
    protected Context constructFromArgs(Class<? extends Context> contextClass, Object[] args, Class<?>[] constructorTypes) {
        Constructor<? extends Context> constructor;

        try {
            this.validateArgs(args, constructorTypes);

            if (args.length == 0) {
                constructor = contextClass.getDeclaredConstructor();
            } else {
                constructor = contextClass.getDeclaredConstructor(constructorTypes);
            }

            if (args.length == 0) {
                return constructor.newInstance();
            } else {
                return constructor.newInstance(args);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Failed to construct context ", ex);
        }
    }

    /**
     * Create the context
     * @param args arguments to pass into the context by reflection
     * @return the context to create
     */
    public abstract Context createContext(Object... args);
}
