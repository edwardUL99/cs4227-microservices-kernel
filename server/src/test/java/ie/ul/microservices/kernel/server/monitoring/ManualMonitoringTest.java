package ie.ul.microservices.kernel.server.monitoring;

import ie.ul.microservices.kernel.server.registration.RegistryImpl;

public class ManualMonitoringTest {

    public static void main(String[] args) {
        RegistryImpl registry = new RegistryImpl();
        MonitorImpl monitor = new MonitorImpl(registry);
        monitor.startMonitoring();
    }
}
