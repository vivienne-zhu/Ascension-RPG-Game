package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class HealerEnemy extends GameCharacters {
    private Image healerEnemyPic;

    /**
     * The constructor sets the stats(attack,defense and stamina) of the HealerEnemy
     * object, and also sets the values needed to display the image instance
     * variable in the GUI.
     */
    public HealerEnemy(int floor) {
	// Stat values will be changed
	setAttack(20 + (int) (Math.random() * ((5) + 1)) * floor);
	setDefense(20 + (int) (Math.random() * ((3) + 1)) * floor);
	int startStam = 400 + (int) (Math.random() * ((10) + 1)) * floor;
	setStamina(startStam);
	setCurrentStamina(startStam);
	setMana(200 + (int) (Math.random() * ((10) + 1)) * floor);
	setX(1230);
	setY(200);
	setWidth(100);
	setHeight(200);

    }

    /**
     * The method allows us to get the image instance variable of the HealerEnemy.
     * 
     * @return healerEnemyPic Image of the HealerEnemy character object.
     */
    public Image getEnemyPic() {
	return healerEnemyPic;
    }

    /**
     * This method sets the image of the HealerEnemy character.
     * 
     * @param enemy Image of the HealerEnemy character.
     */
    public void setEnemyPic(Image enemy) {
	this.healerEnemyPic = enemy;
    }

}
