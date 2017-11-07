package cz.kucharo2.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Path("/hello")
public class HelloWorldEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHelloResponse() {
        return "Hello world!";
    }
}
