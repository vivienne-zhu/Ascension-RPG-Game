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
public class RangedEnemy extends GameCharacters {
	private Image rangedEnemyPic;

	/**
	 * The constructor sets the stats(attack,defense and stamina) of the RangedEnemy
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public RangedEnemy(int floor) {
		// Stat values will be changed
		setAttack(75 + 1 + (int) (Math.random() * ((4) + 1)) * floor);
		setDefense(60 + 1 + (int) (Math.random() * ((2) + 1)) * floor);
		int startStam = 175 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		setX(1230);
		setY(200);
		setWidth(100);
		setHeight(200);
		setType("Ranged");

	}

	/**
	 * The method allows us to get the image instance variable of the RangedEnemy.
	 * 
	 * @return rangedEnemyPic Image of the RangedEnemy character object.
	 */
	public Image getEnemyPic() {
		return rangedEnemyPic;
	}

	/**
	 * This method sets the image of the RangedEnemy character.
	 * 
	 * @param enemy Image of the RangedEnemy character.
	 */
	public void setEnemyPic(Image enemy) {
		this.rangedEnemyPic = enemy;
	}


}
