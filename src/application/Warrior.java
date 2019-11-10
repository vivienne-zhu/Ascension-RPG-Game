package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the Hero class. It represents a Warrior game character.
 * 
 * @author sharisinclair
 *
 */
public class Warrior extends GameCharacters {
    private Image warriorPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Warrior object,
     * and also sets the values needed to display the image instance variable in the GUI.
     */
    public Warrior() {
	//Stat values will be changed
        setAttack(150);
        setDefense(80);
        setStamina(500);
        setX(50);
        setY(200);
        setWidth(100);
        setHeight(200);
        //warriorPic = new Image("Warrior.png", true);
    }

    public Image getWarriorPic() {
        return warriorPic;
    }

    public void setWarriorPic(Image warrior) {
        this.warriorPic = warrior;
    }

    
}
