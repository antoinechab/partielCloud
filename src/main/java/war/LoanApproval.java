package war;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public String getIt(@PathParam("nomducompte") String nom, @PathParam("somme") float somme) {
        return "Le nom du compte: "+nom + " demande la somme de " + somme;
    }

}