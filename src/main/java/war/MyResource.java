package war;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.net.URISyntaxException;
import java.net.URI;
import java.sql.SQLException;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hello, Heroku!";
    }

    @Path("db")    
    @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String getItbd() {
            return showDatabase();
        }
    


    public static Connection getConnection() throws Exception {
   // Class.forName("org.postgresql.Driver");
    URI dbUri = new URI(System.getenv("DATABASE_URL"));

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

    return DriverManager.getConnection(dbUrl, username, password);
  }

  private String showDatabase()
       {
    try {
      Connection connection = getConnection();

      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      String out = "Hello!\n";
      while (rs.next()) {
          out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
      }

      return out;
    } catch (SQLException e) {
      return "There was an error: " + e.getMessage();
    }
    catch (Exception ex){
        return "ERROR"+ex.getMessage();
    }

  }
  @Path("createTable")    
  @GET
      @Produces(MediaType.TEXT_PLAIN)
      public String createTable() throws Exception {
  		Connection connection = getConnection();

  		Statement stmt = connection.createStatement();
  	 	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS compte (name VARCHAR(100), amount float, lastRisk int)");
          return "Ok";
      }



}
