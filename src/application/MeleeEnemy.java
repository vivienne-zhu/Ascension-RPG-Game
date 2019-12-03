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
		setAttack(60 + (4 + (int) (Math.random() * (5 - 4) + 1)) * floor); //60 + (4 to 5 * floor)
		setDefense(45 + (2 + (int) (Math.random() * (4 - 2) + 1)) * floor); //45 + (2 to 4 * floor)
		int startStam = 250 + 25 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		if (position == 0) {
			setX(1030);
		} else if (position == 1) {
			setX(810);
		} else {
			setX(590);
		}
		setY(430);
		setWidth(220);
		setHeight(220);
		setType("Melee");
//		setCharacterImage(new Image("meleeEnemy.png", getWidth(), getHeight(), false, false));
		setCharacterImage(new Image("meleeEnemy.gif", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("meleeEnemyRed.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("meleeEnemyWhite.png",getWidth(), getHeight(), false, false));

	}

}
