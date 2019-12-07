package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;

class ShopTest {
    
    JFXPanel jfxPanel = new JFXPanel();
    
    
    /**
     * This tests the buyRevive method when the player has no revive and enough gold. 
     * 
     */
	@Test 
	void testEnoughGoldBuyRevive() {
		GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setGold(250);
		hero.setHasRevive(false);
		
		shop.buyRevive(errorMsg, display);
		assertEquals(hero.isHasRevive(), true);
		assertEquals(hero.getGold(), 0);
		
	}
	
    
    /**
     * This tests the buyRevive method when the player has a revive.
     * 
     */	
	@Test 
	void testHasReviveBuyRevive() {
		GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setHasRevive(true);
		shop.buyRevive(errorMsg, display);
		assertEquals("YOU ALDREADY HAVE THAT ITEM", errorMsg.getText()); 
	
	}
	
    /**
     * This tests the buyRevive method when the player does not have enough gold.
     * 
     */	
	@Test 
	void testNoGoldBuyRevive() {
		GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setHasRevive(false);
		hero.setGold(0);
		shop.buyRevive(errorMsg, display);
		assertEquals("YOU DO NOT HAVE ENOUGH GOLD", errorMsg.getText()); 
	
	}
	
	
	/** This tests the sellRevive method when the player has a revive item.
	 * 
	 */
	@Test
	void testHasReviveSellRevive() {
		GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setHasRevive(true);
		hero.setGold(100);
		shop.sellRevive(errorMsg, display);
			
		assertEquals(hero.isHasRevive(), false);
		assertEquals(hero.getGold(), 250);
	}
	
	/** This tests the sellRevive method when the player does not have a revive item.
	 * 
	 */
	@Test
	void testNoReviveSellRevive() {
		GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setHasRevive(false);
		shop.sellRevive(errorMsg, display);
			
		assertEquals("YOU DO NOT HAVE ENOUGH ITEMS", errorMsg.getText()); 
	}

}
