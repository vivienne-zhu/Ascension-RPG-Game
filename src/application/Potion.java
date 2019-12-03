package application;

/**
 * This is the potion class. Game characters can use potions to restore stamina.
 * 
 * @author JiayuZhu
 *
 */

public class Potion {
	int restorePoint;
	int price;

	/**
	 * The constructor sets the restore points and price of a potion object
	 */
	public Potion() {
		this.restorePoint = 0;
		this.price = 0;
	}

	/**
	 * @return the restorePoint
	 */
	public int getRestorePoint() {
		return restorePoint;
	}

	/**
	 * @param restorePoint the restorePoint to set
	 */
	public void setRestorePoint(int restorePoint) {
		this.restorePoint = restorePoint;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

}