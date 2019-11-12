package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * Warrior game character.
 * 
 * @author sharisinclair
 *
 */
public class Warrior extends GameCharacters {
    private Image warriorPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Warrior
     * object, and also sets the values needed to display the image instance
     * variable in the GUI.
     */
    public Warrior() {
	// Stat values will be changed
	setAttack(175);
	setDefense(80);
	setStamina(500);
	setCurrentStamina(500);
	setX(50);
	setY(200);
	setWidth(100);
	setHeight(200);
	warriorPic = new Image("Warrior.png",100, 200, false, false);
    }

    /**
     * The method allows us to get the image instance variable of the Warrior.
     * 
     * @return warriorPic Image of the Warrior character object.
     */
    public Image getWarriorPic() {
	return warriorPic;
    }

    /**
     * This method sets the image of the Warrior character.
     * 
     * @param warrior Image of the Warrior character.
     */
    public void setWarriorPic(Image warrior) {
	this.warriorPic = warrior;
    }

}
