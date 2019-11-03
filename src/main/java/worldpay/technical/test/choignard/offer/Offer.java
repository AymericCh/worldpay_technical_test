package worldpay.technical.test.choignard.offer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Offer {
	
	private final Integer id;
	private final int price;
	private final String description;
	private final Integer productId;
	private final String endTime;
	private final String startTime;
	private Boolean isExpired = false;
	private final String currency;
	
	/**
	 * Offer constructor
	 * @param id
	 * 			Id
	 * @param price
	 * 			Price
	 * @param description
	 * 			Description
	 * @param productId
	 * 			Id product
	 * @param startTime
	 * 			Start time of the offer
	 * @param endTime
	 * 			End time of the offer
	 * @param currency
	 * 			Currency
	 */
	public Offer(Integer id, int price, String description, Integer productId, String startTime, String endTime, String currency) {
		this.id = id;
		this.price = price;
		this.description = description;
		this.productId = productId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.currency = currency;
		
	}
	
	public void autoExpire() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date endTimeFormatted = df.parse(endTime);
			if (endTimeFormatted.before(new Date())) {
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						isExpired = true;	
					}
					
				}, endTimeFormatted);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			isExpired = true;
		}
	}
	
	public Integer getId() {
		return id;
	}
	
	public int getPrice() {
		return price;
	}
	public String getDescription() {
		return description;
	}
	public Integer getProductId() {
		return productId;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public Boolean isExpired() {
		return isExpired;
	}
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}
	public String getCurrency() {
		return currency;
	}
}
