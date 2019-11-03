package worldpay.technical.test.choignard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

public class OfferTest {
	private HttpServer server;
    private Client client;
    private List<Offer> offers;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        client = ClientBuilder.newClient();

		client.target(Main.BASE_URI+"product/").request().post(Entity.entity(new Gson().toJson(new Product(0, "Terminal", 100)), MediaType.APPLICATION_JSON));
		
		offers = new ArrayList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(LocalDateTime.now());
        String endTime = formatter.format(LocalDateTime.now().plusDays(1));
       
		offers.add(new Offer(0, 75, "Black friday", 0, startTime, endTime, "GBP"));
		offers.add(new Offer(1, 80, "20% off", 0, startTime, endTime, "GBP"));
		for (Offer offer : offers) {
			client.target(Main.BASE_URI+"offer/").request().post(Entity.entity(new Gson().toJson(offer), MediaType.APPLICATION_JSON));
		}
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
    
    @Test
    public void testGetOffer() {    	 
        // Valid URIs
    	String content = (String) client.target(Main.BASE_URI+"offer?id=0").request().get().readEntity(String.class);
    	Offer offer = new Gson().fromJson(content, Offer.class);
        assertEquals(offers.get(0).getId(), offer.getId());
        assertEquals(offers.get(0).getPrice(), offer.getPrice());
        assertEquals(offers.get(0).getProductId(), offer.getProductId());
        assertEquals(offers.get(0).getCurrency(), offer.getCurrency());
        assertEquals(offers.get(0).getDescription(), offer.getDescription());
        assertEquals(offers.get(0).getStartTime(), offer.getStartTime());
        assertEquals(offers.get(0).getEndTime(), offer.getEndTime());
    }
    
    @Test
    public void testInvalidOffer() {    	 
        // Valid URIs

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(LocalDateTime.now().minusDays(2));
        String endTime = formatter.format(LocalDateTime.now().minusDays(1));
       
		Offer offer = new Offer(3, 75, "Already expired offer", 0, startTime, endTime, "GBP");
		String content = client.target(Main.BASE_URI+"offer/").request().post(Entity.entity(new Gson().toJson(offer), MediaType.APPLICATION_JSON)).readEntity(String.class);
		System.out.println(content);
        assertEquals("Offer already expired", content);
        
        startTime = formatter.format(LocalDateTime.now());
        endTime = formatter.format(LocalDateTime.now().plusDays(1));
       
		offer = new Offer(3, 75, "Unknown product", 4, startTime, endTime, "GBP");
		content = client.target(Main.BASE_URI+"offer/").request().post(Entity.entity(new Gson().toJson(offer), MediaType.APPLICATION_JSON)).readEntity(String.class);
		System.out.println(content);
        assertEquals("Product associated to the offer does not exist", content);
    }
    
    @Test
    public void testGetOffers() {    	 
    	String content = (String) client.target(Main.BASE_URI+"offer/all").request().get().readEntity(String.class);
        assertNotNull(new Gson().fromJson(content, Offer[].class));
        assertEquals(offers.size(), new Gson().fromJson(content, Offer[].class).length);
    }
}



