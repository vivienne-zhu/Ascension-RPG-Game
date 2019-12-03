package application;

/**
 * This is the hyper potion class This potion restores 250 points of stamina to
 * the character that uses it.
 * 
 * @author JiayuZhu
 *
 */

public class HyperPotion extends Potion {

	/**
	 * The constructor of the hyper potion class sets the restore points and price.
	 */
	public HyperPotion() {
		this.setRestorePoint(250);
		this.setPrice(150);
	}

	/**
	 * This method returns a string, "Hyper Potion"
	 */
	@Override
	public String toString() {
		return "Hyper Potion";
	}

}
