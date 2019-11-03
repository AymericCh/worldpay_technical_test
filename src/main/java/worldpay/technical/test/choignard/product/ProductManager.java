package worldpay.technical.test.choignard.product;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {
	
	private static ProductManager INSTANCE = new ProductManager();
	
	private HashMap<Integer, Product> products = new HashMap();
	
	public static ProductManager getSingleInstance() {
		return INSTANCE;
	}
	
	public Map<Integer, Product> getProducts() {
		return products;
	}
}
