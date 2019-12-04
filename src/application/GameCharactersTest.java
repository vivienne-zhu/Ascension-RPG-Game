package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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

    // This tests magic attack method with two hero and melee enemy
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
 // This tests the attack method
    @Test
    void empoweredAttackTest() {
	Warrior aragorn = new Warrior();
	MeleeEnemy  orc = new MeleeEnemy(8, 0);
	
	aragorn.setIsEmpowered(true);
	int orcDef = orc.getDefense();
	int orcStam = orc.getStamina();
	double expected = (orcStam - ((150 - orcDef) * 2));
	aragorn.attack(orc, false, true);

	assertEquals(expected, orc.getCurrentStamina());
    }

    //This tests the level up method on a mage to see 5 stat increases
    @Test 
    void levelUpTest() {
	Mage gandalf = new Mage();

	gandalf.levelUp();

	assertEquals(135,2, gandalf.getAttack());
	assertEquals(48,2, gandalf.getDefense());
	assertEquals(850,10, gandalf.getStamina());
	assertEquals(125, gandalf.getMana());
	assertEquals(262,3, gandalf.getMagicAtk());
    }

    //This tests the use of a potion and its removal from the potionMap
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

    //This tests the revive method
    @Test 
    void reviveTest() {
	Warrior link = new Warrior();
	link.setHasRevive(true);
	link.setCurrentStamina(0);
	link.revive();

	assertEquals(900, link.getCurrentStamina());
	assertEquals(false, link.isHasRevive());
    }

//    @Test
//    void charCreationTest() {
//	GameGUI game = new GameGUI();
//	Stage primaryStage = new Stage();
//	try {
//	    game.start(primaryStage);
//	    
//	    assertEquals("Mage", game.getHero().getType());
//	    assertEquals(1, game.getFloor());
//	    assertEquals(1, game.getHero().getName());
//	    assertEquals(800, game.getHero().getStamina());
//	} catch (FileNotFoundException e) {
//	    e.printStackTrace();
//	}
//    }

}
