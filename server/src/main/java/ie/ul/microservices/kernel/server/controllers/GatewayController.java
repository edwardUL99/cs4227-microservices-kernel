package ie.ul.microservices.kernel.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller takes all requests from the clients and then does some mapping and forwards the request
 * TODO decide how this will work
 */
@RestController
@RequestMapping("/api/gateway")
public class GatewayController {
    @RequestMapping("/*")
    public ResponseEntity<?> gateway(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
