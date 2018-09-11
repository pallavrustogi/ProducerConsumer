package kitchen.model;

import java.util.Date;

/**
 * Created by prustogi on 8/23/18.
 */
public class ShelfOrder extends OrderDto {
	private Date createdAt;
	private ShelfEnum shelfAt;


	public ShelfOrder(OrderDto orderDto) {
		super(orderDto.getName(), orderDto.getTemp(), orderDto.getShelfLife(), orderDto.getDecayRate());
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public ShelfEnum getShelfAt() {
		return shelfAt;
	}

	public void setShelfAt(ShelfEnum shelfAt) {
		this.shelfAt = shelfAt;
	}

	@Override
	public float getDecayRate() {
		if (this.shelfAt == ShelfEnum.overflow) {
			return 2 * super.getDecayRate();
		}
		return super.getDecayRate();
	}
}
