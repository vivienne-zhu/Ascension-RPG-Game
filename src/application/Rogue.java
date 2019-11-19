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
     * The constructor sets the stats(attack, defense and stamina etc) of the Rogue
     * object, and also sets the values needed to display the image instance
     * variable in the GUI.
     */
    public Rogue() {
		// Stat values will be changed
		setAttack(150);
		setDefense(60);
		setStamina(400);
		setCurrentStamina(getStamina());
		setLevel(1);
		setX(10);
		setY(350);
		setWidth(280);
		setHeight(250);
		setCharacterImage(new Image("rogue.png", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redrogue.png",getWidth(), getHeight(), false, false));
		setType("Rogue");
    }
}
