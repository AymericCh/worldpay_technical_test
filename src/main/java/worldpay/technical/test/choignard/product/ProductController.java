package worldpay.technical.test.choignard.product;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/product")
public class ProductController {
	
	/**
	 * Get product linked to the id
	 * @param id
	 * @return
	 */
	@GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        Product product = ProductManager.getSingleInstance().getProducts().get(id);
        return Response.ok(product).build();
    }
 
	/**
	 * Create product
	 * @param productToCreate
	 * @return
	 */
    @POST
    public Response post(String productToCreate) {
    	Product product = new Gson().fromJson(productToCreate, Product.class);
        ProductManager.getSingleInstance().getProducts().put(product.getId(), product);
        return Response.ok(product).build();
    }
 
    /**
     * Return all the products created in the system
     * @return
     */
    @GET
    public Response get() {
    	List<Product> products = new ArrayList<Product>(ProductManager.getSingleInstance().getProducts().values());
        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.ok(entity).build();
    }
    
}
