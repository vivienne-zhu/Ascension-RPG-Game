package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the Hero class. It represents a Archer game character.
 * 
 * @author sharisinclair
 *
 */
public class Archer extends GameCharacters {
    private Image archerPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Archer object,
     * and also sets the values needed to display the image instance variable in the GUI.
     */
    public Archer() {
	//Stat values will be changed
        setAttack(100);
        setDefense(60);
        setStamina(400);
        setCurrentStamina(400);
        setX(50);
        setY(200);
        setWidth(100);
        setHeight(200);
        //archerPic = new Image("Archer.png", true);
    }

    
    public Image getArcherPic() {
        return archerPic;
    }

    public void setArcherPic(Image archer) {
        this.archerPic = archer;
    }

    
}
