package war;

import java.sql.Connection;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("AccManager")
public class AccManager {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Ici c'est l'acc Mananger!";
    }
    
    @GET
    @Path("/{nameAccount}/{amount}")
    public Response createAccount(@PathParam("nameAccount") String nom, @PathParam("amount") String amount) {
    	Response response = null;
    	try {
    		float f = Float.parseFloat(amount);
    	      Connection connection = MyResource.getConnection();

    	      Statement stmt = connection.createStatement();
    	      stmt.executeUpdate("INSERT INTO compte (name, amount, lastRisk) VALUES ("+nom+", "+f+", 0)");
    		
    		response = Response.status(Status.OK).entity("Le compte "+ nom + " avec une sommee de "+f+" a été créé avec succes!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de la création du compte").build();
    		return response;
    	}
    }
    
}