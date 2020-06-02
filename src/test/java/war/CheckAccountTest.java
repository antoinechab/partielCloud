package war;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class CheckAccountTest extends JerseyTest {
	
	  @Override
	    protected Application configure() {
	        return new ResourceConfig(CheckAccount.class);
	    }
	  
	  @Test
	    public void testGet() {
	    	Response response = target("check/5").request().get();
	    	assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	    	    
	    	Response response2 = target("check/ffe").request().get();
	        assertEquals("Http Response should be 415: ", Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response2.getStatus());
	    }

}
