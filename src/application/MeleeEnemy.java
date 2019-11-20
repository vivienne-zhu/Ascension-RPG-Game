package application;

import javafx.scene.image.Image;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class MeleeEnemy extends GameCharacters {

	/**
	 * The constructor sets the stats(attack,defense and stamina) of the MeleeEnemy
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public MeleeEnemy(int floor, int position) {
		// Stat values will be changed
		setAttack(85 + 4 + (int) (Math.random() * (6 - 4) + 1) * floor); //85 + (4 to 6 * floor)
		setDefense(30 + 3 + (int) (Math.random() * (5 - 3) + 1) * floor); //30 + (3 to 5 * floor)
		int startStam = 250 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		if (position == 0) {
			setX(950);
		} else if (position == 1) {
			//setX(730);
			setX(730);
		} else {
			//setX(470);
			setX(510);
		}
		setY(430);
		setWidth(220);
		setHeight(220);
		setType("Melee");
//		setCharacterImage(new Image("meleeEnemy.png", getWidth(), getHeight(), false, false));
		setCharacterImage(new Image("meleeEnemy.gif", getWidth(), getHeight(), false, false));

		setCharacterImageHurt(new Image("redMelee.png",getWidth(), getHeight(), false, false));

	}

}
