package kitchen.model;

/**
 * Created by prustogi on 8/23/18.
 */
public enum ShelfEnum {

	hot(1, "hot"),
	cold(2, "cold"),
	frozen(3, "frozen"),
	overflow(4, "overflow");

	private int id;
	private String name;
	ShelfEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
