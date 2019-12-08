package application;

import javafx.scene.image.Image;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class HealerEnemy extends GameCharacters {

	/**
	 * The constructor sets the stats(attack,defense and stamina) of the HealerEnemy
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public HealerEnemy(int floor, int position) {
		setAttack(40 + (4 + (int) (Math.random() * (5 - 4) + 1)) * floor); //40 + (4 to 5 * floor)
		setDefense(20 + (2 + (int) (Math.random() * (4 - 2) + 1)) * floor); //30 + (2 to 4 * floor)
		int startStam = 125 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		setMana(200 + 20 * floor);
		if (position == 0) {
			setX(1050);
		} else if (position == 1) {
			setX(830);
		} else {
			setX(610);
		}
		setY(430);
		setWidth(220);
		setHeight(220);
		setType("Healer");
		setCharacterImage(new Image("healerEnemy.gif", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("healerEnemyRed.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("healEnemyWhite.png",getWidth(), getHeight(), false, false));

	}

}
