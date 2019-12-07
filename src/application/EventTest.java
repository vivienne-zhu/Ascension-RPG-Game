package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;

/**
 * This test the methods of the event class
 * 
 * @author JiayuZhu
 *
 */
class EventTest {
	
    JFXPanel jfxPanel = new JFXPanel();
   
    /**
     * This test the event method drop floor
     */
	@Test
	void testDropFloor() {
		Text display = new Text();
		Floor floor = new Floor();
		floor.setFloor(3); 
		
		Event df = new Event();
		df.dropFloor(floor, display);
		assertEquals(2, floor.getFloor());
	}

	/**
	 * this test the event method that boost the heros attack
	 */
	@Test
	void testAttackBoost() {
		GameCharacters hero = new GameCharacters();
		hero.setAttack(100);
		Text display = new Text();
		Event ab = new Event();
		ab.attackBoost(hero, display);
		
		assertEquals(120, hero.getAttack());
	}
	/**
	 * This tests the event method that boost the heros defense
	 */
	@Test
	void testDefenseBoost() {
		GameCharacters hero = new GameCharacters();
		hero.setDefense(100);
		Text display = new Text();
		Event db = new Event();
		db.defenseBoost(hero, display);
		
		assertEquals(120, hero.getDefense());
	}

}
