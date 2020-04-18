package war;

import javax.ws.rs.core.Application;
import javax.ws.rs.PathParam;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import war.Hello;

public class HelloTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Hello.class);
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        final String responseMsg = target().path("hello/toto").request().get(String.class);

        assertEquals("Hello toto", responseMsg);
    }
}