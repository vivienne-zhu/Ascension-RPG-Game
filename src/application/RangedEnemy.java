package application;

import javafx.scene.image.Image;

/**
 * This class is an extension of the GameCharacters class. It represents a
 * typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class RangedEnemy extends GameCharacters {
	/**
	 * The constructor sets the stats(attack,defense and stamina) of the RangedEnemy
	 * object, and also sets the values needed to display the image instance
	 * variable in the GUI.
	 */
	public RangedEnemy(int floor, int position) {
		// Stat values will be changed
		setAttack(75 + 1 + (int) (Math.random() * ((4) + 1)) * floor);
		setDefense(60 + 1 + (int) (Math.random() * ((2) + 1)) * floor);
		int startStam = 175 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		if (position == 0) {
			setX(950);
			setMagicx(930);
			setOldMagicx(930);
		} else if (position == 1) {
			setX(730);
			setMagicx(710);
			setOldMagicx(710);
		} else {
			setX(510);
			setMagicx(490);
			setOldMagicx(490);
		}
		setY(430);
		setWidth(220);
		setHeight(220);
		setType("Ranged");
		
		setMagicy(520);
		setCharacterImage(new Image("rangedEnemy.gif", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("rangedEnemyRed.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("rangedEnemyWhite.png",getWidth(), getHeight(), false, false));
		setMagicAtkImage(new Image("fireblastRev.png",100, 50, false, false));

	}

}
