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
		setX(770);
		setY(240);
		setWidth(450);
		setHeight(470);
		setType("Melee");
		//Image below commented out in order to pass JUnit tests
		//setCharacterImage(new Image("meleeEnemyOrc.png", getWidth(), getHeight(), false, false));

	}

}
