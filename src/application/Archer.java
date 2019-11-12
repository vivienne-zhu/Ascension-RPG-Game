package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * Archer game character.
 * 
 * @author Shari Sinclair
 *
 */
public class Archer extends GameCharacters {
    private Image archerPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Archer
     * object, and also sets the values needed to display the image instance
     * variable in the GUI.
     */
    public Archer() {
	// Stat values will be changed
	setAttack(150);
	setDefense(60);
	setStamina(400);
	setCurrentStamina(400);
	setX(50);
	setY(200);
	setWidth(100);
	setHeight(200);
	archerPic = new Image("Archer.png", 100, 200, false, false);
    }

    /**
     * The method allows us to get the image instance variable of the Archer.
     * 
     * @return acherPic Image of the Archer character object.
     */
    public Image getArcherPic() {
	return archerPic;
    }

    /**
     * This method sets the image of the Archer character.
     * 
     * @param archer Image of the Archer character.
     */
    public void setArcherPic(Image archer) {
	this.archerPic = archer;
    }

}
