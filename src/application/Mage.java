package application;

import javafx.scene.image.Image;

/**
 * This class is an extension of the GameCharacters class. It represents a Mage
 * game character.
 * 
 * @author Shari Sinclair
 *
 */
public class Mage extends GameCharacters {
	
	/**
	 The constructor sets the stats(attack, defense and stamina etc.) and type 
	 * of the Mage object, and also sets the values needed to display the 
	 * image instance variables in the GUI.
	 */
	public Mage() {
		// Stat values will be changed
		setAttack(100);
		setDefense(60);
		setStamina(400);
		setCurrentStamina(getStamina());
		setMana(400);
		setCurrentMana(getMana());
		setMagicAtk(150);
		setLevel(1);
		setX(80);
		setY(420);
		setMagicx(210);
		setMagicy(520);
		setWidth(230);
		setHeight(230);
		setCharacterImage(new Image("mage.gif", getWidth(), getHeight(), false, false));
		setCharacterImageHurt(new Image("redmage_gif.png",getWidth(), getHeight(), false, false));
		setCharacterImageHeal(new Image("whitemage_gif.png",getWidth(), getHeight(), false, false));
		setMagicAtkImage(new Image("fireblast.png",100, 50, false, false));
		setType("Mage");
	}
}
