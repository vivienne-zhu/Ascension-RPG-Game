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
		this.restorePoint = 100;
		this.price = 50;
    }

}
