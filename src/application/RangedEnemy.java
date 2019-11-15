package application;

import javafx.scene.image.Image;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class RangedEnemy extends GameCharacters {
	/**
	 * The constructor sets the stats(attack,defense and stamina) of the RangedEnemy
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public RangedEnemy(int floor) {
		// Stat values will be changed
		setAttack(75 + 1 + (int) (Math.random() * ((4) + 1)) * floor);
		setDefense(60 + 1 + (int) (Math.random() * ((2) + 1)) * floor);
		int startStam = 175 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		setX(1230);
		setY(200);
		setWidth(100);
		setHeight(200);
		setType("Ranged");
		//setCharacterImage(new Image("meleeEnemy.png", getWidth(), getHeight(), false, false));
		//setCharacterImageHurt(new Image("redWarrior.png",getWidth(), getHeight(), false, false));

	}

}
