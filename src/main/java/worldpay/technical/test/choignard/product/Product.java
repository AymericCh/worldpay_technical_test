package worldpay.technical.test.choignard.product;

import java.util.ArrayList;
import java.util.List;

import worldpay.technical.test.choignard.offer.Offer;

public class Product {

    private final Integer id;
    private String name;
    private int price;
    private List<Offer> offers;
    
    /**
     * Product constructor
     * @param id
     * 			Id
     * @param name
     * 			Name of the product
     * @param price
     * 			Price of the product
     */
	public Product(Integer id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.offers = new ArrayList();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Integer getId() {
		return id;
	}
	public List<Offer> getOffers(){
		if (offers == null) {
			this.offers = new ArrayList();
		}
		return offers;
	}
	
}
