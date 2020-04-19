package war;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import model.Compte;

@Path("AccManager")
public class AccManager {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
  
    @POST
    @Path("/{name}/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAccount(@PathParam("name") String name, @PathParam("amount") String amount) {
    	Response response = null;
    	try {
    		float f = Float.parseFloat(amount);
    	    Connection connection = MyResource.getConnection();
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
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response accountList() {
    	Response response = null;
    	try {
    		Connection connection = MyResource.getConnection();
    		Statement st = connection.createStatement();
    		ResultSet rs = st.executeQuery("SELECT * FROM compte");
    		ArrayList<Compte> arrayComptes = new ArrayList<Compte>();
    		while (rs.next())
    	      {
    	        int id = rs.getInt("id");
    	        String name = rs.getString("name");
    	        float amount = rs.getFloat("amount");
    	        int lastRisk = rs.getInt("lastrisk");
    	        Compte compte = new Compte(id, name, amount, lastRisk);
    	        arrayComptes.add(compte);
    	        // print the results
    	      }
    	      st.close();
    	    response = Response.status(Status.OK).entity(arrayComptes.toString()).build();
    	    return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Une erreur s'est produite: Impossible de recuperer la liste des comptes").build();
    		System.err.println(e);
    		return response;
    		
    	}
    }
    
  

    
}