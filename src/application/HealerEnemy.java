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
		// Stat values will be changed
		setAttack(20 + 1 + (int) (Math.random() * ((4) + 1)) * floor);
		setDefense(20 + 1 + (int) (Math.random() * ((2) + 1)) * floor);
		int startStam = 125 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		setMana(200 + 20 * floor);
		if (position == 0) {
			setX(950);
		} else if (position == 1) {
			setX(730);
		} else {
			setX(510);
		}
		setY(430);
		setWidth(220);
		setHeight(220);
		setType("Healer");
		setCharacterImage(new Image("healerEnemy.gif", getWidth(), getHeight(), false, false));
		//setCharacterImageHurt(new Image("redWarrior.png",getWidth(), getHeight(), false, false));

	}

}
