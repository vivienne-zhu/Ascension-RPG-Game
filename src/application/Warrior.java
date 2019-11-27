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
	 * The constructor sets the stats(attack, defense and stamina etc.) and type 
	 * of the Warrior object, and also sets the values needed to display the 
	 * image instance variables in the GUI.
	 */
	public Warrior() {
		// Stat values will be changed
		setAttack(175);
		setDefense(70);
		setStamina(500);
		setCurrentStamina(getStamina());
		setLevel(1);
		setX(80);
		setY(410);
		setWidth(230);
		setHeight(240);
		setCharacterImage(new Image("warrior.gif",getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redWarrior_gif.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("whitewarrior_gif.png",getWidth(), getHeight(), false, false));
		setCharacterImageSlash(new Image("slash.png", 200, 175, false, false));
		setType("Warrior");
	}

}
