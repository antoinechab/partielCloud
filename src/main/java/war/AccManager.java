package war;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    
    @POST
    @Path("/{name}/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAccount(@PathParam("name") String name, @PathParam("amount") String amount) {
    	Response response = null;
    	try {
    		float f = Float.parseFloat(amount);
    	    Connection connection = getConnection();
    	    PreparedStatement pstmt = connection.prepareStatement("INSERT INTO compte (name, amount, lastrisk) VALUES( ? , ? , ?)");
    	   // Statement stmt = connection.createStatement();
    	    pstmt.setString(1, name);
    	    pstmt.setFloat(2, f);
    	    pstmt.setInt(3, 0);
    	    pstmt.executeUpdate();
    	    response = Response.status(Status.OK).entity("Le compte "+ name + " avec une somme de "+f+" a été créé avec succes!").build();
    	    return response;
    	}catch(NumberFormatException e) {
    		response = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("Le montant doit-etre un chiffre!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de la création du compte").build();
    		System.err.println(e);
    		return response;
    		
    	}
    }
    
    public static Connection getConnection() throws Exception {
   // Class.forName("org.postgresql.Driver");
    URI dbUri = new URI(System.getenv("DATABASE_URL"));

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

    return DriverManager.getConnection(dbUrl, username, password);
  }

    
}