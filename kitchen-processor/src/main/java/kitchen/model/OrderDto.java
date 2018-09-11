package kitchen.model;

import java.io.Serializable;

/**
 * Created by prustogi on 8/23/18.
 */
public class OrderDto implements Serializable {
	private String name;
	private TemperatureEnum temp;
	private int shelfLife;
	private float decayRate;

	public OrderDto(String name, TemperatureEnum temp, int shelfLife, float decayRate) {
		this.name = name;
		this.temp = temp;
		this.shelfLife = shelfLife;
		this.decayRate = decayRate;
	}

	public OrderDto() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TemperatureEnum getTemp() {
		return temp;
	}

	public void setTemp(TemperatureEnum temp) {
		this.temp = temp;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public float getDecayRate() {
		return decayRate;
	}

	public void setDecayRate(float decayRate) {
		this.decayRate = decayRate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OrderDto)) return false;

		OrderDto orderDto = (OrderDto) o;

		if (getShelfLife() != orderDto.getShelfLife()) return false;
		if (Float.compare(orderDto.getDecayRate(), getDecayRate()) != 0) return false;
		if (getName() != null ? !getName().equals(orderDto.getName()) : orderDto.getName() != null) return false;
		return getTemp() == orderDto.getTemp();
	}

	@Override
	public int hashCode() {
		int result = getName() != null ? getName().hashCode() : 0;
		result = 31 * result + (getTemp() != null ? getTemp().hashCode() : 0);
		result = 31 * result + getShelfLife();
		result = 31 * result + (getDecayRate() != +0.0f ? Float.floatToIntBits(getDecayRate()) : 0);
		return result;
	}
}
