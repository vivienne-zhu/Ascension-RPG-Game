package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class HealerEnemy extends GameCharacters {
    private Image healerEnemyPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the Archer object,
     * and also sets the values needed to display the image instance variable in the GUI.
     */
    public HealerEnemy() {
	//Stat values will be changed
        setAttack(20);
        setDefense(20);
        setStamina(400);
        setCurrentStamina(400);
        setMana(200);
        setX(1230);
        setY(200);
        setWidth(100);
        setHeight(200);

    }

    
    public Image getEnemyPic() {
        return healerEnemyPic;
    }

    public void setEnemyPic(Image enemy) {
        this.healerEnemyPic = enemy;
    }

    
}
