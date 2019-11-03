package worldpay.technical.test.choignard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import worldpay.technical.test.choignard.product.Product;

public class ProductTest {
	private HttpServer server;
    private Client client;
    private List<Product> products;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        client = ClientBuilder.newClient();
		
		products = new ArrayList();
		products.add(new Product(0, "Terminal", 100));
		products.add(new Product(1, "Credit card", 20));
		products.add(new Product(2, "Online payment", 1));
		for (Product product : products) {
			client.target(Main.BASE_URI+"product/").request().post(Entity.entity(new Gson().toJson(product), MediaType.APPLICATION_JSON));
		}
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
    
    @Test
    public void testGetProduct() {    	 
        // Valid URIs
    	String content = (String) client.target(Main.BASE_URI+"product/0").request().get().readEntity(String.class);
        assertEquals(products.get(0), new Gson().fromJson(content, Product.class));
    }
    

    
    @Test
    public void testGetProducts() {    	 
        // Valid URIs
    	String content = (String) client.target(Main.BASE_URI+"product/0").request().get().readEntity(String.class);
    	assertNotNull(new Gson().fromJson(content, Product[].class));
        assertEquals(products.size(), new Gson().fromJson(content, Product[].class).length);
    }
}



