package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a Mage
 * game character.
 * 
 * @author sharisinclair
 *
 */
public class Mage extends GameCharacters {
    private Image magePic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Mage
     * object, and also sets the values needed to display the image instance
     * variable in the GUI.
     */
    public Mage() {
	// Stat values will be changed
	setAttack(80);
	setDefense(40);
	setStamina(300);
	setCurrentStamina(300);
	setMana(400);
	setX(50);
	setY(200);
	setWidth(100);
	setHeight(200);
	// magePic = new Image("Mage.png", 100, 200, false, false);
    }

    /**
     * The method allows us to get the image instance variable of the Mage.
     * 
     * @return magePic Image of the Mage character object.
     */
    public Image getMagePic() {
	return magePic;
    }

    /**
     * This method sets the image of the Mage character.
     * 
     * @param mage Image of the Mage character.
     */
    public void setMagePic(Image mage) {
	this.magePic = mage;
    }
}
