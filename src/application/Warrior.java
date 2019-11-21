package application;

import javafx.scene.image.Image; 

/**
 * This class is an extension of the GameCharacters class. It represents a
 * Warrior game character.
 * 
 * @author Shari Sinclair
 *
 */
public class Warrior extends GameCharacters {
	
	/**
	 * The constructor sets the stats(attack, defense and stamina etc.) of the Warrior
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public Warrior() {
		// Stat values will be changed
		setAttack(175);
		setDefense(70);
		setStamina(500);
		setCurrentStamina(getStamina());
		setLevel(1);
		setX(10);
		setY(420);
		setWidth(250);
		setHeight(280);
		setCharacterImage(new Image("pixelWarrior.png",getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redWarrior.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("whitewarrior.png",getWidth(), getHeight(), false, false));
		setCharacterImageSlash(new Image("slash.png", 200, 175, false, false));
		setType("Warrior");
		
	}

}
