package kitchen.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by prustogi on 8/23/18.
 */
public class OrderListDto implements Serializable{
	private List<OrderDto> orderList;

	public List<OrderDto> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDto> orderList) {
		this.orderList = orderList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OrderListDto)) return false;

		OrderListDto that = (OrderListDto) o;

		return getOrderList() != null ? getOrderList().equals(that.getOrderList()) : that.getOrderList() == null;
	}

	@Override
	public int hashCode() {
		return getOrderList() != null ? getOrderList().hashCode() : 0;
	}
}
