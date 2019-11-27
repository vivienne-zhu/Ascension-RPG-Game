package application;

import javafx.scene.image.Image; 

/**
 * This class is an extension of the GameCharacters class. It represents a
 * typical Enemy game character.
 * 
 * @author David Cai
 *
 */
public class BossEnemy extends GameCharacters {
    /**
     * The constructor sets the stats(attack,defense and stamina) of the BossEnemy
     * object, and also sets the values needed to display the image instance
     * variable in the GUI. 
     */
    public BossEnemy(int floor) {
		// Stat values will be changed
		setAttack(150 + 1 + (int) (Math.random() * ((4) + 1)) * floor);
		setDefense(60 + 1 + (int) (Math.random() * ((2) + 1)) * floor);
		int startStam = 500 + 20 * floor;
		setStamina(startStam);
		setCurrentStamina(startStam);
		setX(950);
		setY(350);
		setMagicx(930);
		setOldMagicx(930);
		setWidth(250);
		setHeight(300);
		setType("Boss");
		setCharacterImage(new Image("bossEnemy.gif", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("bossEnemyRed.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("bossEnemyWhite.png",getWidth(), getHeight(), false, false));
    }

}
