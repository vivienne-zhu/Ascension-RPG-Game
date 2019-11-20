package application;

import javafx.scene.image.Image;

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
		setAttack(100);
		setDefense(60);
		setStamina(400);
		setCurrentStamina(getStamina());
		setMana(400);
		setCurrentMana(getMana());
		setMagicAtk(150);
		setLevel(1);
		setX(50);
		setY(470);
		setWidth(160);
		setHeight(180);
		setCharacterImage(new Image("mage1.png", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redmage1.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("whitemage.png",getWidth(), getHeight(), false, false));
		setType("Mage");
	}
}
