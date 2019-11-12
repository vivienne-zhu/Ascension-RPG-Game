package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a Mage
 * game character.
 * 
 * @author Shari Sinclair
 *
 */
public class Mage extends GameCharacters {
	
	/**
	 * The constructor sets the stats(attack, defense and stamina etc.) of the Mage
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public Mage() {
		// Stat values will be changed
		setAttack(120);
		setDefense(40);
		setStamina(300);
		setCurrentStamina(getStamina());
		setMana(400);
		setLevel(1);
		setX(130);
		setY(250);
		setWidth(400);
		setHeight(450);
		setCharacterImage(new Image("Mage.png", getWidth(), getHeight(), false, false));
		setType("Mage");
	}
}
