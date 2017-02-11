package pl.orangeretail.gofiber.server;

import javax.inject.Named;
import org.glassfish.jersey.server.ResourceConfig;
import pl.orangeretail.gofiber.server.rest.controllers.AuthorizationRequestFilter;
import pl.orangeretail.gofiber.server.rest.controllers.AuthorizationResponseFilter;
import pl.orangeretail.gofiber.server.rest.controllers.CORSResponseFilter;
import pl.orangeretail.gofiber.server.rest.controllers.Controller;

@Named
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(CORSResponseFilter.class);
        register(CustomLoggingFilter.class);
        register(AuthorizationRequestFilter.class);
        register(Controller.class);
        register(AuthorizationResponseFilter.class);
         
    }

}
