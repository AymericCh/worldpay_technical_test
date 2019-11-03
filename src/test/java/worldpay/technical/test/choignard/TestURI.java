package worldpay.technical.test.choignard;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import worldpay.technical.test.choignard.offer.Offer;
import worldpay.technical.test.choignard.product.Product;

public class TestURI {

	private HttpServer server;
    private Client client;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
    @Test
    public void testValidGetURI() {    	 
        // Valid URIs
        assertEquals(200, client.target(Main.BASE_URI+"product/").request().get().getStatus());
        assertEquals(200, client.target(Main.BASE_URI+"offer/").request().get().getStatus());
    }

    @Test
    public void testValidPostURI() {    	 
        // Valid URIs
        assertEquals(200, client.target(Main.BASE_URI+"product/").request().post(Entity.entity(new Gson().toJson(new Product(0, "Terminal", 100)), MediaType.APPLICATION_JSON)).getStatus());
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String startTime = formatter.format(LocalDateTime.now());
        String endTime = formatter.format(LocalDateTime.now().plusDays(1));
        assertEquals(200, client.target(Main.BASE_URI+"offer/").request().post(Entity.entity(new Gson().toJson(new Offer(0, 75, "Black friday", 0, startTime, endTime, "GBP")), MediaType.APPLICATION_JSON)).getStatus());
    }
    
    @Test
    public void testInvalidGetURI() {
    	// Invalid URIs
        assertEquals(404, client.target(Main.BASE_URI+"PRODUCT/").request().get().getStatus());
        assertEquals(404, client.target(Main.BASE_URI+"OFFER").request().get().getStatus());
    }
}
