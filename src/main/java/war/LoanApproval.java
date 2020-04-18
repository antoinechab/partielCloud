package war;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

@Path("LoanApproval")
public class LoanApproval {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Pour qu'une demande de crédit soit traiter elle doit respecter ce format: /nomducompte/somme";
    }
    
    @GET
    @Path("/{nomducompte}/{somme}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt(@PathParam("nomducompte") String nom, @PathParam("somme") float somme) {
    	try {
    		return Response.status(Status.OK).entity(nom + "demande une somme de " + (float)somme).build();
    	}catch(ClassCastException e) {
    		
    		return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("Le paterne ne respect pas le format et le type de donnée acceptée").build();
    	}
    	
    }

}