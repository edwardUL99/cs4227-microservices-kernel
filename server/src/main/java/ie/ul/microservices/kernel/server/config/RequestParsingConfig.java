package ie.ul.microservices.kernel.server.config;

import ie.ul.microservices.kernel.api.requests.adapters.AdapterUtils;
import ie.ul.microservices.kernel.api.requests.adapters.XMLRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * This class configures request parsing for the server module. It primarily registers request parser adapters
 */
@Configuration
public class RequestParsingConfig {
    /**
     * Use the given configurer object to configure the request parsing adapters
     * @param configurer the configurer to add adapters to. Ensure a call to {@link AdaptersConfigurer#registerAdapters()}
     *                   is made or else the adapters won't be finally registered
     */
    @Autowired
    public void configureAdapters(AdaptersConfigurer configurer) {
        configurer.registerAdapter("application/xml", AdapterUtils::parseXMLBody,
                        request -> new XMLRequest(request.getWrappedRequest()))
                .registerAdapter("application/x-www-form-urlencoded", AdapterUtils::parseFormBody,
                        request -> request.getWrappedRequest().getParameterMap())
                .registerAdapters(); // call to registerAdapters so the adapters get added to the RequestParserFactory
    }
}
