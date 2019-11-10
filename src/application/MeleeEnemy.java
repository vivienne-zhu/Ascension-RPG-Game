package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class MeleeEnemy extends GameCharacters {
    private Image meleeEnemyPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Archer object,
     * and also sets the values needed to display the image instance variable in the GUI.
     */
    public MeleeEnemy() {
	//Stat values will be changed
        setAttack(100);
        setDefense(30);
        setStamina(400);
        setCurrentStamina(400);
        setX(1230);
        setY(200);
        setWidth(100);
        setHeight(200);

    }

    
    public Image getEnemyPic() {
        return meleeEnemyPic;
    }

    public void setEnemyPic(Image enemy) {
        this.meleeEnemyPic = enemy;
    }

    
}
