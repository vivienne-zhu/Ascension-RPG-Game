package application;

/**
 * This is the hyper potion class
 * This potion restores 250 points of stamina to the character uses it
 * @author JiayuZhu
 *
 */

public class HyperPotion extends Potion {
	
	/**
	 * The constructor of the hyper potion class sets the restore points and price.
	 */
	public HyperPotion() {
		this.restorePoint = 250;
		this.price = 100;
	}

}
