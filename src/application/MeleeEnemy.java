package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

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
	public MeleeEnemy(int floor) {
		// Stat values will be changed
		setAttack(85 + 1 + (int) (Math.random() * ((4) + 1)) * floor);
		setDefense(30 + 1 + (int) (Math.random() * ((2) + 1)) * floor);
		int startStam = 250 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		setX(1000);
		setY(330);
		setWidth(240);
		setHeight(260);
		setType("Melee");
		setCharacterImage(new Image("meleeEnemy.png", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redMelee.png",getWidth(), getHeight(), false, false));

	}

}
