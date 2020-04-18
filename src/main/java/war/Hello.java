package war ;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("hello")
public class Hello {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/{nom}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@PathParam("nom") String nom) {
        return "Hello "+nom;
    }
}
