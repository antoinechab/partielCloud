package war;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import model.Compte;

@Path("check")
public class CheckAccount {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */  
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response check(@PathParam("id") int idAccount) {
    	Response response = null;
    	Connection connection;
    	try {
    		//float f = Float.parseFloat(amount);
    		connection = MyResource.getConnection();
    	    PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM compte WHERE id = ?");
    	   // Statement stmt = connection.createStatement();
    	    pstmt.setInt(1, idAccount);
    	    ResultSet resultSet = pstmt.executeQuery();
    	    //response = Response.status(Status.OK).entity("Le compte "+ name + " avec une somme de "+amount+" a été créé avec succes!").build();
    	    
    	    while (resultSet.next()) {
    	    	
    	    	
    	    	int id = resultSet.getInt("id");
    	    	String name = resultSet.getString("name");
    	    	float amount = resultSet.getFloat("amount");
    	    	Compte compte = new Compte(id, name, amount, 0);
    	    	String riskString = "non-risque !";
    	    	if(amount < 2000.0) {
    	    		int risk = 1;
    	    		compte.setLastrisk(risk);
    	    		riskString = "risque !";
    	    		pstmt = connection.prepareStatement("UPDATE compte SET lastrisk = ? WHERE id = ? ");
    	    		pstmt.setInt(1, risk);
    	    		pstmt.setInt(2, id);
    	    		pstmt.executeUpdate();
    	    	}
    	    	response = Response.status(Status.OK).entity("Le compte "+ compte.toString() + " est un compte " + riskString).build();

            }
    	    pstmt.close();
    	    connection.close();
    	    return response;
    	}catch(NumberFormatException e) {
    		response = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity("L'id doit-etre un chiffre!").build();
    		return response;
    	}catch(Exception e) {
    		response = Response.status(Status.BAD_REQUEST).entity("Erreur lors de récupération du compte: " +  e).build();
    		System.err.println(e);
    		return response;
    		
    	}
    }
}