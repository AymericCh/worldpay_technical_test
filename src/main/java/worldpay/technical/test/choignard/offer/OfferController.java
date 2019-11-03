package worldpay.technical.test.choignard.offer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import worldpay.technical.test.choignard.product.Product;
import worldpay.technical.test.choignard.product.ProductManager;

@Path("/offer")
public class OfferController {
	/**
	 * Get offer using id
	 * @param id
	 * 			Id
	 * @return Offer linked to the id
	 */
	@GET
    public Response get(@QueryParam("id") Integer id) {
        Offer offer = OfferManager.getSingleInstance().getOffers().get(id);
        return Response.ok(offer).build();
    }
 
	/**
	 * Create offer
	 * @param offerToCreate
	 * @return
	 */
    @POST
    public Response createOffer(String offerToCreate) {
    	Offer offer = new Gson().fromJson(offerToCreate, Offer.class);
    	offer.autoExpire();
    	if (offer.isExpired()== null) {
    		offer.setIsExpired(false);
    	}
    	if (offer.isExpired()) {
    		return Response.ok("Offer already expired").build();
    	}
    	Product product = ProductManager.getSingleInstance().getProducts().get(offer.getProductId());
    	if (product != null) {
        	OfferManager.getSingleInstance().getOffers().put(offer.getId(), offer);
        	ProductManager.getSingleInstance().getProducts().get(offer.getId()).getOffers().add(offer);
            return Response.ok(offer).build();
    	}
    	return Response.ok("Product associated to the offer does not exist").build();
    }
    
    /**
     * Cancel the offer linked to the id
     * @param id
     * 			Id of the offer to cancel
     * @return
     */
    @POST
    @Path("/cancel")
    public Response cancelOffer(@QueryParam("id") Integer id) {
        Offer offer = OfferManager.getSingleInstance().getOffers().get(id);
        if (offer == null) {
            return Response.ok("Offer does not exist").build();
        }
        if (!offer.isExpired()) {
            OfferManager.getSingleInstance().getOffers().remove(offer);
            ProductManager.getSingleInstance().getProducts().get(offer.getProductId()).getOffers().remove(offer);
            return Response.ok(offer).build();
        }
        return Response.ok("Offer already expired can not be canceled").build();
    }
 
    /**
     * Get all the offers created in the system
     * @return
     */
    @GET
    @Path("/all")
    public Response get() {
    	List<Offer> offers = new ArrayList<Offer>(OfferManager.getSingleInstance().getOffers().values());
        GenericEntity<List<Offer>> entity = new GenericEntity<List<Offer>>(offers) {};
        return Response.ok(entity).build();
    }
    
}
