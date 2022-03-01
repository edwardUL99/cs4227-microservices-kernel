package ie.ul.microservices.kernel.server.interception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This factory provides factories for creating context objects
 */
public abstract class ContextFactory {
    /**
     * Construct the context from the arguments
     * @param contextClass the class object for the context
     * @param args the arguments
     * @return the constructed object
     */
    protected Context constructFromArgs(Class<? extends Context> contextClass, Object[] args) {
        Constructor<? extends Context> constructor;

        try {
            if (args.length == 0) {
                constructor = contextClass.getDeclaredConstructor();
            } else {
                List<Class<?>> parameterList = Arrays.stream(args)
                        .map(Object::getClass)
                        .collect(Collectors.toList());
                Class<?>[] parameters = new Class<?>[parameterList.size()];
                parameters = parameterList.toArray(parameters);

                constructor = contextClass.getDeclaredConstructor(parameters);
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
     * @param args optional args to pass in at construction time
     * @return the context to create
     */
    public abstract Context createContext(Object...args);
}
