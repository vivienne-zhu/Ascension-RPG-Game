package application;

import javafx.scene.image.Image; 

/**
 * This class is an extension of the GameCharacters class. It represents a
 * Rogue game character.
 * 
 * @author Shari Sinclair
 *
 */
public class Rogue extends GameCharacters {
    /**
    The constructor sets the stats(attack, defense and stamina etc.) and type 
	 * of the Rogue object, and also sets the values needed to display the 
	 * image instance variables in the GUI.
     */
    public Rogue() {
		// Stat values will be changed
		setAttack(150);
		setDefense(60);
		setStamina(400);
		setCurrentStamina(getStamina());
		setLevel(1);
		setX(10);
		setY(420);
		setWidth(230);
		setHeight(230);
		setCharacterImage(new Image("rogue.gif", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redrogue_gif.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("whiterogue_gif.png",getWidth(), getHeight(), false, false));
		setType("Rogue");
    }
}
