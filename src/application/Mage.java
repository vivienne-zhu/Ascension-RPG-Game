package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
/**
 * This class is an extension of the Hero class. It represents a Mage game character.
 * 
 * @author sharisinclair
 *
 */
public class Mage extends GameCharacters {
    private Image magePic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Mage object,
     * and also sets the values needed to display the image instance variable in the GUI.
     */
    public Mage() {
	//Stat values will be changed
        setAttack(50);
        setDefense(40);
        setStamina(300);
        setMana(400);
        setX(50);
        setY(200);
        setWidth(100);
        setHeight(200);
        magePic = new Image("MageTemp.png", true);
    }

    public Image getMagePic() {
        return magePic;
    }

    public void setMagePic(Image mage) {
        this.magePic = mage;
    }

    public static void main(String[] args) {
    }
    
}
