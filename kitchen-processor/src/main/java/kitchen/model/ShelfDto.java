package kitchen.model;

import java.util.List;

/**
 * Created by prustogi on 8/23/18.
 */
public class ShelfDto {
	private ShelfEnum shelfType;
	private int Capacity;
	private List<ShelfOrder> orders;

	public ShelfEnum getShelfType() {
		return shelfType;
	}

	public void setShelfType(ShelfEnum shelfType) {
		this.shelfType = shelfType;
	}

	public int getCapacity() {
		return Capacity;
	}

	public void setCapacity(int capacity) {
		Capacity = capacity;
	}

	public List<ShelfOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ShelfOrder> orders) {
		this.orders = orders;
	}
}
