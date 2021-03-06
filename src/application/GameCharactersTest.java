package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;


/**
 * The class tests several of the methods in the GameCharacters Class.
 * 
 * @author Shari Sinclair
 *
 */
class GameCharactersTest {

	JFXPanel jfxPanel = new JFXPanel();

	/**
	 * This tests the attack method
	 */
	@Test
	void attackTest() {
		Rogue legolas = new Rogue();
		MeleeEnemy  orc = new MeleeEnemy(2, 0);

		int orcAttack = orc.getAttack();
		int expected = 800 - (orcAttack - 45);
		orc.attack(legolas, false, false, 0);

		assertEquals(expected, legolas.getCurrentStamina());
	}

	/**
	 * This tests the defend method with an enemy and a hero
	 */
	@Test
	void defendingTest() {
		Rogue legolas = new Rogue();
		MeleeEnemy  orc = new MeleeEnemy(4, 0);

		legolas.defend();
		orc.attack(legolas, false, false, 0);
		int orcAttack = orc.getAttack();
		int expected = (int) (800 - Math.ceil(orcAttack - 45) * 0.25);

		assertEquals(expected, legolas.getCurrentStamina());
	}

	/**
	 * This tests magic attack method with a mage and melee enemy
	 */
	@Test 
	void magicAtkTest() {
		Mage gandalf = new Mage();
		MeleeEnemy  orc = new MeleeEnemy(8, 0);

		int orcDef = orc.getDefense();
		int orcStam = orc.getStamina();
		double expected = (orcStam - ((250 - orcDef) * 1.2));
		gandalf.magicAttack(orc, false);
		assertEquals(expected, 0.2, orc.getCurrentStamina());
	}

	/**
	 * This tests the attack method when the character is empowered
	 */
	@Test
	void empoweredAttackTest() {
		Warrior aragorn = new Warrior();
		MeleeEnemy  orc = new MeleeEnemy(8, 0);

		//aragorn.setIsEmpowered(true);
		int orcDef = orc.getDefense();
		int orcStam = orc.getStamina();
		int expected =  (orcStam - (int) ((185 - orcDef) * 1.5));
		aragorn.attack(orc, false, true, 0);

		assertEquals(expected, orc.getCurrentStamina());
	}

	/**
	 * This tests the attack method when the warrior has defended 3 times in a row
	 */
	@Test
	void warriorDefendTest() {
		Warrior aragorn = new Warrior();
		BossEnemy urukhai = new BossEnemy(10);

		int bossDef = urukhai.getDefense(); 
		int bossStam = urukhai.getCurrentStamina();
		int expected = (bossStam - (int) ((185 - bossDef) * 2));
		aragorn.attack(urukhai, false, true, 3);

		assertEquals(expected, urukhai.getCurrentStamina());
	}

	/**
	 * This tests the attack method when the Boss is outraged
	 */
	@Test
	void outragedAttackTest() {
		Warrior aragorn = new Warrior();
		BossEnemy urukhai = new BossEnemy(10);

		int bossAtk = urukhai.getAttack(); 
		int expected = 1000 - ((bossAtk - 60) * 3);
		urukhai.attack(aragorn, true, false, 0);

		assertEquals(expected, aragorn.getCurrentStamina());
	}

	/**
	 * This tests the attack method when the Boss is outraged and 
	 * attacks the hero while he is defending
	 */
	@Test
	void outragedAttackDefendTest() {
		Warrior aragorn = new Warrior();
		BossEnemy urukhai = new BossEnemy(10);

		aragorn.setIsDefending(true);
		int bossAtk = urukhai.getAttack(); 
		int expected = (int)(1000 - Math.ceil(((bossAtk - 60) * 3) * 0.25));
		urukhai.attack(aragorn, true, false, 0);

		assertEquals(expected, aragorn.getCurrentStamina());
	}


	/**
	 * This tests the level up method on a mage to see 5 stat increases
	 */
	@Test 
	void levelUpTest() {
		Mage gandalf = new Mage();
		gandalf.levelUp();

		assertEquals(135,2, gandalf.getAttack());
		assertEquals(48,2, gandalf.getDefense());
		assertEquals(850,10, gandalf.getStamina());
		assertEquals(75, gandalf.getMana());
		assertEquals(262,3, gandalf.getMagicAtk());
	}

	/**
	 * This tests the use of a potion and its removal from the potionMap
	 */
	@Test 
	void usePotionTest() {
		Warrior link = new Warrior();
		CheapPotion cp = new CheapPotion();
		Text error = new Text();
		link.setCurrentStamina(100);
		link.getPotionMap().put(cp, 3);
		link.usePotion(cp,error);

		assertEquals(200, link.getCurrentStamina());
		assertEquals(2, link.getPotionMap().get(cp));
	}


	/**
	 * This tests the use of a potion when the potionMap/item bag is empty
	 */
	@Test 
	void usePotionEmptyMapTest() {
		Warrior link = new Warrior();
		CheapPotion cp = new CheapPotion();
		Text error = new Text("");
		link.getPotionMap().put(cp, 0);
		link.usePotion(cp,error);

		assertEquals("YOU DO NOT HAVE ENOUGH ITEMS",error.getText());
	}

	/**
	 * This tests the use of a potion when stamina is full
	 */
	@Test 
	void usePotionFullStaminaTest() {
		Warrior link = new Warrior();
		HyperPotion hp = new HyperPotion();
		Text error = new Text();
		link.setCurrentStamina(1000);
		link.getPotionMap().put(hp, 1);
		link.usePotion(hp,error);

		assertEquals(1000, link.getCurrentStamina());
		assertEquals("YOU HAVE REACHED THE MAX STAMINA",error.getText());
	}


	/**
	 * This tests the revive method
	 */
	@Test 
	void reviveTest() {
		Warrior link = new Warrior();
		link.setHasRevive(true);
		link.setCurrentStamina(0);
		link.revive();

		assertEquals(1000, link.getCurrentStamina());
		assertEquals(false, link.isHasRevive());
	}

	/**
	 * This test the enemy heal method
	 */
	@Test 
	void enemyHealTest() {
		HealerEnemy annoy = new HealerEnemy(3,0);
		MeleeEnemy hurt = new MeleeEnemy(3,1);
		int newStam = annoy.getAttack() + hurt.getCurrentStamina();
		int expected = Math.min(newStam, hurt.getStamina());
		annoy.enemyHeal(hurt);
		assertEquals(expected, hurt.getCurrentStamina());
	}

}
