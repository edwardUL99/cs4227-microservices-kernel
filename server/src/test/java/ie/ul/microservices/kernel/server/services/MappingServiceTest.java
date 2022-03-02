package ie.ul.microservices.kernel.server.services;

import ie.ul.microservices.kernel.api.interception.mapping.DispatcherContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingDispatcher;
import ie.ul.microservices.kernel.server.Constants;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptorChain;
import ie.ul.microservices.kernel.api.interception.mapping.MappingContext;
import ie.ul.microservices.kernel.api.interception.mapping.MappingInterceptor;
import ie.ul.microservices.kernel.server.mapping.MappingResult;
import ie.ul.microservices.kernel.server.models.Microservice;
import ie.ul.microservices.kernel.server.models.URL;
import ie.ul.microservices.kernel.server.registration.Registry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * This context is used for testing interceptors
 */
class InterceptorContext {
    /**
     * Determines if the "before" interception was invoked
     */
    boolean beforeInterceptorHit;

    /**
     * Determines if the "after" interception was invoked
     */
    boolean afterInterceptorHit;

    /**
     * The mapping context object received by the interceptor
     */
    MappingContext mappingContext;
}

/**
 * An interceptor for testing use
 */
class TestInterceptor implements MappingInterceptor {
    /**
     * This test interceptor's context object
     */
    InterceptorContext context;

    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain chain) {
        this.context.beforeInterceptorHit = true;
        this.context.mappingContext = context;
        chain.next(context);
    }

    /**
     * This interception point is called after the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onAfterMapping(MappingContext context, MappingInterceptorChain chain) {
        this.context.afterInterceptorHit = true;
        this.context.mappingContext = context;
        chain.next(context);
    }
}

/**
 * This is a test interceptor that sets the microservice instance to use in the before mapping context
 */
class SetMicroserviceInterceptor implements MappingInterceptor {
    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain chain) {
        context.setMicroservice(MappingServiceTest.MICROSERVICE);
        chain.next(context);
    }

    /**
     * This interception point is called after the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onAfterMapping(MappingContext context, MappingInterceptorChain chain) {
        // no-op, this method isn't needed
    }
}

/**
 * This interceptor is a test interceptor that doesn't call chain.next. It is used to test that mapping terminates
 */
class EarlyEndInterceptor implements MappingInterceptor {
    /**
     * This interception point is called before the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onBeforeMapping(MappingContext context, MappingInterceptorChain chain) {
        context.setResponse(ResponseEntity.notFound().build());
    }

    /**
     * This interception point is called after the mapping takes place
     *
     * @param context the object holding information relating to mapping the request
     * @param chain   the chain interceptor in the chain, this should be called for processing to continue
     */
    @Override
    public void onAfterMapping(MappingContext context, MappingInterceptorChain chain) {
        // no-op, this method isn't needed
    }
}

/**
 * This class is used to test the mapping service
 */
@SpringBootTest
public class MappingServiceTest {
    /**
     * A mock registry for testing
     */
    @MockBean
    private Registry registry;

    /**
     * The mapping service under test
     */
    @Autowired
    private MappingService mappingService;

    /**
     * The mapping dispatcher
     */
    private MappingDispatcher dispatcher;

    /**
     * The interceptor context used for testing
     */
    private InterceptorContext context;

    /**
     * The test interceptor instance
     */
    private TestInterceptor interceptor;

    /**
     * The hostname for testing
     */
    public static final String HOST = "127.0.0.1";

    /**
     * The port for testing
     */
    public static final int PORT = 1234;

    /**
     * The name of the microservice for testing
     */
    public static final String NAME = "micro-service";

    /**
     * The test microservice object
     */
    public static final Microservice MICROSERVICE = new Microservice(HOST, PORT, NAME, true);

    @BeforeEach
    private void init() {
        context = new InterceptorContext();
        interceptor = new TestInterceptor();
        interceptor.context = context;

        dispatcher = DispatcherContext.getDispatcher();

        dispatcher.registerMappingInterceptor(interceptor, MappingDispatcher.RegistrationStrategy.ALL);
    }

    @AfterEach
    private void destroy() {
        dispatcher.clearInterceptors();
        context = null;
        interceptor = null;
    }

    /**
     * Create a mock servlet request for testing
     * @return the mock request
     */
    private HttpServletRequest createMockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setServerName("127.0.0.1");
        request.setLocalPort(8080);
        request.setScheme("http");
        request.setRequestURI(String.format("%s/%s/buy/", Constants.API_GATEWAY, NAME));

        return request;
    }

    /**
     * Tests that a request should be mapped successfully
     */
    @Test
    public void shouldMapRequestSuccessfully() {
        HttpServletRequest request = createMockRequest();
        MappingResult expected = new MappingResult();
        expected.setMicroservice(MICROSERVICE);
        expected.setTerminated(false);
        expected.setUrl(URL.fromParameters("http", "127.0.0.1", 1234, "buy", null));

        given(registry.getMicroservice(NAME))
                .willReturn(MICROSERVICE);

        MappingResult result = mappingService.mapRequest(request);

        assertEquals(expected, result);
        assertTrue(context.beforeInterceptorHit);
        assertTrue(context.afterInterceptorHit);
        assertEquals(context.mappingContext.getMicroservice(), MICROSERVICE);
        assertEquals(context.mappingContext.getURL(), result.getUrl());
        verify(registry).getMicroservice(NAME);
    }

    /**
     * This tests that if the microservice is set in a before mapping interceptor, the set microservice will be used
     */
    @Test
    public void shouldUseInterceptorMicroservice() {
        MappingInterceptor interceptor = new SetMicroserviceInterceptor();
        dispatcher.registerMappingInterceptor(interceptor, MappingDispatcher.RegistrationStrategy.BEFORE);

        HttpServletRequest request = createMockRequest();
        MappingResult expected = new MappingResult();
        expected.setMicroservice(MICROSERVICE);
        expected.setTerminated(false);
        expected.setUrl(URL.fromParameters("http", "127.0.0.1", 1234, "buy", null));

        MappingResult result = mappingService.mapRequest(request);

        assertEquals(expected, result);
        assertEquals(context.mappingContext.getMicroservice(), MICROSERVICE);
        assertEquals(context.mappingContext.getURL(), result.getUrl());
        verify(registry, times(0)).getMicroservice(NAME);
    }

    /**
     * This tests that if an interceptor doesn't call chain.next, mapping will stop/terminate
     */
    @Test
    public void shouldEndMappingIfInterceptorDoesntCallNext() {
        MappingInterceptor interceptor = new EarlyEndInterceptor();
        dispatcher.registerMappingInterceptor(interceptor, MappingDispatcher.RegistrationStrategy.BEFORE);

        HttpServletRequest request = createMockRequest();

        MappingResult result = mappingService.mapRequest(request);

        assertTrue(result.isTerminated());
        assertNotNull(result.getResponse());
        verify(registry, times(0)).getMicroservice(NAME);
    }
}
