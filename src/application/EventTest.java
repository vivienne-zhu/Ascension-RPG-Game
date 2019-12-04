package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;

class EventTest {
	
    JFXPanel jfxPanel = new JFXPanel();
   

	@Test
	void testDropFloor() {
		Text display = new Text();
		Floor floor = new Floor();
		floor.setFloor(3);
		
		Event df = new Event();
		df.dropFloor(floor, display);
		assertEquals(floor.getFloor(), 2);
	}

	@Test
	void testAttackBoost() {
		GameCharacters hero = new GameCharacters();
		hero.setAttack(100);
		Text display = new Text();
		Event ab = new Event();
		ab.attackBoost(hero, display);
		
		assertEquals(hero.getAttack(), 120);
	}

	@Test
	void testDefenseBoost() {
		GameCharacters hero = new GameCharacters();
		hero.setDefense(100);
		Text display = new Text();
		Event db = new Event();
		db.defenseBoost(hero, display);
		
		assertEquals(hero.getDefense(), 120);
	}

}
