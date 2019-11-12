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


	@Test
	void attackTest() {
		Archer legolas = new Archer();
		MeleeEnemy  orc = new MeleeEnemy(2);

		int orcAttack = orc.getAttack();
		int expected = 400 - (orcAttack - 60);
		orc.attack(legolas);

		assertEquals(expected, legolas.getCurrentStamina());
	}

	@Test
	void defendingTest() {
		Archer legolas = new Archer();
		MeleeEnemy  orc = new MeleeEnemy(4);

		legolas.defend();
		orc.attack(legolas);
		int orcAttack = orc.getAttack();
		int expected = 400 - ((orcAttack - 60)/2);

		assertEquals(expected, legolas.getCurrentStamina());
	}

	@Test 
	void defendingTestTwo() {
		Warrior aragorn = new Warrior();
		Warrior boromir = new Warrior();

		aragorn.defend();
		boromir.attack(aragorn);
		assertEquals(448, aragorn.getCurrentStamina());
	}

	@Test
	void buyingAndSellingRevivesTest() {
		Warrior link = new Warrior();
		link.setGold(500);
		link.buyRevive();

		assertEquals(300, link.getGold());
		assertEquals(true, link.isHasRevive());

		link.setGold(0);
		link.sellRevive();

		assertEquals(150, link.getGold());
		assertEquals(false, link.isHasRevive());

		link.setGold(0);
		link.buyRevive();

		assertEquals(0, link.getGold());
		assertEquals(false, link.isHasRevive());

	}

	@Test
	void buyingSellingAndUsingPotionTest() {
		Warrior link = new Warrior();
		HyperPotion hp = new HyperPotion();
		link.setGold(300);
		link.buyPotion(hp, 2);

		assertEquals(100, link.getGold());

		link.buyPotion(hp, 1);

		assertEquals(3, link.getPotionMap().get(hp));
		assertEquals(0, link.getGold());

		link.setCurrentStamina(10);
		link.usePotion(hp);

		assertEquals(260, link.getCurrentStamina());

		link.sellPotion(hp, 1);
		assertEquals(1, link.getPotionMap().get(hp));
		assertEquals(50, link.getGold());

	}


}
