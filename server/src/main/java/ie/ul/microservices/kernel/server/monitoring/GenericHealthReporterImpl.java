package ie.ul.microservices.kernel.server.monitoring;

/**
 * Generic HealthReporter implementation. In practice, each microservice
 * should use a different implementation with a custom isHealthy() method,
 * e.g. the isHealthy() method for a database microservice might try to perform
 * a query in order to determine if the database is healthy whereas other
 * microservices would have isHealthy() implementations more appropriate to
 * their functions.
 */
public class GenericHealthReporterImpl implements  HealthReporter {

    private boolean healthState;

    public GenericHealthReporterImpl(boolean b) {
        this.healthState = b;
    }

    /**
     * There should be a custom implementation of this method
     * for each type of microservice.
     */
    @Override
    public boolean isHealthy() {
        return healthState;
    }
}
