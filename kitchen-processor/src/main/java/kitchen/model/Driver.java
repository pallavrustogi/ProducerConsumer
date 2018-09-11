package kitchen.model;

import java.util.Date;

/**
 * Created by prustogi on 8/24/18.
 */
public class Driver {
	private ShelfOrder shelfOrder;
	private Date pickUpDate;

	public ShelfOrder getShelfOrder() {
		return shelfOrder;
	}

	public void setShelfOrder(ShelfOrder shelfOrder) {
		this.shelfOrder = shelfOrder;
	}

	public Date getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
}
