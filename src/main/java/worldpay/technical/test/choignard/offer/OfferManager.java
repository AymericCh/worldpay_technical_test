package worldpay.technical.test.choignard.offer;

import java.util.HashMap;
import java.util.Map;

public class OfferManager {

	private static OfferManager INSTANCE = new OfferManager();
	
	private HashMap<Integer, Offer> offers = new HashMap();
	
	public static OfferManager getSingleInstance() {
		return INSTANCE;
	}
	
	public Map<Integer, Offer> getOffers() {
		return offers;
	}
}
