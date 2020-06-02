package war;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class AccManagerTest extends JerseyTest {
	

    @Override
    protected Application configure() {
        return new ResourceConfig(AccManager.class);
    }

    @Test
    public void testGet() {
    	Response response = target("AccManager").request().get();
    	    assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testPost() {
    	Response response = target("AccManager/jeanMichel/255").request().post(Entity.text(MediaType.TEXT_PLAIN));
    	assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
    	
    	Response response2 = target("AccManager/jeanMichel/zqz").request().post(Entity.text(MediaType.TEXT_PLAIN));
    	assertEquals("Http Response should be 415: ", Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response2.getStatus());
    }
    
    @Test
    public void testDelete() {
    	Response response = target("AccManager/3").request().delete();
    	assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
    	
    	Response response2 = target("AccManager/zqz").request().delete();
    	assertEquals("Http Response should be 415: ", Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response2.getStatus());
    }
    
    @Test
    public void testUdpate() {
    	Response response = target("AccManager/5/jeanMichel/255/0").request().post(Entity.text(MediaType.TEXT_PLAIN));
    	assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
    	
    	Response response2 = target("AccManager/5/jeanMichel/255/dzd").request().post(Entity.text(MediaType.TEXT_PLAIN));
    	assertEquals("Http Response should be 415: ", Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response2.getStatus());
    }
    
    
}
