package war;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    public Response createAccount(@PathParam("name") String name, @PathParam("amount") float amount) {
    	Response response = null;
    	Connection connection;
    	try {
    		//float f = Float.parseFloat(amount);
    		connection = MyResource.getConnection();
    	    PreparedStatement pstmt = connection.prepareStatement("INSERT INTO compte (name, amount, lastrisk) VALUES( ? , ? , ?)");
    	   // Statement stmt = connection.createStatement();
    	    pstmt.setString(1, name);
    	    pstmt.setFloat(2, amount);
    	    pstmt.setInt(3, 0);
    	    pstmt.executeUpdate();
    	    response = Response.status(Status.CREATED).entity("Le compte "+ name + " avec une somme de "+amount+" a été créé avec succes!").build();
    	    pstmt.close();
    	    connection.close();
    	    return response;
    	}catch(NumberFormatException e) {
    		response = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("Le montant doit-etre un chiffre!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de la creation du compte: " +  e).build();
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
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response oneAccount(@PathParam("id") int id) {
    	Response response = null;
    	try {
    	    Connection connection = MyResource.getConnection();
    	    PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM compte WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
    	    pstmt.setInt(1, id);
    	    ResultSet rs = pstmt.executeQuery();
    	    
    	    int idrs = rs.getInt("id");
	        String name = rs.getString("name");
	        float amount = rs.getFloat("amount");
	        int lastRisk = rs.getInt("lastrisk");
	        Compte compte = new Compte(idrs, name, amount, lastRisk);
    	    response = Response.status(Status.OK).entity(compte.toString()).build();
    	    return response;
    	}catch(NumberFormatException e) {
    		response = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("L'identifiant doit-etre un chiffre!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de la recuperation du compte! " + e).build();
    		System.err.println(e);
    		return response;
    		
    	}
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAccount(@PathParam("id") int id) {
    	Response response = null;
    	try {
    	    Connection connection = MyResource.getConnection();
    	    PreparedStatement pstmt = connection.prepareStatement("DELETE FROM compte WHERE id = ?");
    	   // Statement stmt = connection.createStatement();
    	    pstmt.setInt(1, id);
    	    pstmt.executeUpdate();
    	    response = Response.status(Status.OK).entity("Le compte a été supprimer avec succes!").build();
    	    return response;
    	}catch(NumberFormatException e) {
    		response = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("L'identifiant doit-etre un chiffre!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de la suppression du compte!").build();
    		System.err.println(e);
    		return response;
    		
    	}
    }
    
    @POST
    @Path("/{id}/{name}/{amount}/{lastRisk}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAccount(@PathParam("id") int id, @PathParam("name") String name, @PathParam("amount") float amount, @PathParam("lastRisk") int lastRisk) {
    	Response response = null;
    	try {
    	    Connection connection = MyResource.getConnection();
    	    PreparedStatement pstmt = connection.prepareStatement("UPDATE compte SET name = ? , amount = ? , lastrisk = ?  WHERE id = ? ");
    	    pstmt.setString(1, name);
    	    pstmt.setFloat(2, amount);
    	    pstmt.setInt(3, lastRisk);
    	    pstmt.setInt(4, id);
    	    pstmt.executeUpdate();
    	    Compte compte = new Compte(id, name, amount, lastRisk);
    	    response = Response.status(Status.OK).entity("Le compte a été mis à jour avec succes!").build();
    	    return response;
    	}catch(NumberFormatException e) {
    		response = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("Le montant doit-etre un chiffre!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de la mise à jour du compte!").build();
    		System.err.println(e);
    		return response;
    		
    	}
    }
    
  

    
}