package application;

/**
 * This is the cheap potion class This potion restores 100 points of stamina to
 * the character that uses it
 * 
 * @author JiayuZhu
 *
 */

public class CheapPotion extends Potion {

	/**
	 * The constructor of the cheap potion class sets the restore points and price.
	 */
	public CheapPotion() {
		this.setRestorePoint(100);
		this.setPrice(50);
	}

	/**
	 * This method returns a string, "Cheap Potion".
	 */
	@Override
	public String toString() {
		return "Cheap Potion";
	}

}