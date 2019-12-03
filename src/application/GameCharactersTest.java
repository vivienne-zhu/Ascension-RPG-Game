package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;

/**
 * The class tests several of the methods in the GameCharacters Class.
 * 
 * @author Shari Sinclair
 *
 */
class GameCharactersTest {
    	
	JFXPanel jfxPanel = new JFXPanel();

    	// This tests the attack method
	@Test
	void attackTest() {
		Rogue legolas = new Rogue();
		MeleeEnemy  orc = new MeleeEnemy(2, 0);

		int orcAttack = orc.getAttack();
		int expected = 800 - (orcAttack - 50);
		orc.attack(legolas, false, false);

		assertEquals(expected, legolas.getCurrentStamina());
	}

	// This tests the defend method with and enemy and hero
	@Test
	void defendingTest() {
		Rogue legolas = new Rogue();
		MeleeEnemy  orc = new MeleeEnemy(4, 0);

		legolas.defend();
		orc.attack(legolas, false, false);
		int orcAttack = orc.getAttack();
		int expected = 800 - ((orcAttack - 50)/2);

		assertEquals(expected, legolas.getCurrentStamina());
	}

	// This tests defend method with two heroes
	@Test 
	void magicAtkTest() {
		Mage gandalf = new Mage();
		MeleeEnemy  orc = new MeleeEnemy(8, 0);
		
		int orcDef = orc.getDefense();
		int orcStam = orc.getStamina();
		double expected = (orcStam - ((225 - orcDef) * 1.2));
		gandalf.magicAttack(orc, false);
		assertEquals(expected, 0.2, orc.getCurrentStamina());
	}
	
	

}
