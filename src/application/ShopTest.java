package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

class ShopTest {
    
    JFXPanel jfxPanel = new JFXPanel();
    
    /**
     * This tests the buy potion method when no quantity or 0 is provided as an input.
     * 
     */
    @Test 
    void testEmptyTextBuyPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		TextField quantity = new TextField();
		Text errorMsg = new Text();
		Text display = new Text();
		
		quantity.setText("");
		shop.buyPotion(hero.getCp(), quantity, errorMsg, display);
		assertEquals("INVALID QUANTITY", quantity.getText());
		
		quantity.setText("0");
		shop.buyPotion(hero.getCp(), quantity, errorMsg, display);
		assertEquals("INVALID QUANTITY", quantity.getText());   	
    }
    
    /**
     * This tests the buy potion method when the player has enough gold to buy the potion. 
     * 
     */
    @Test 
    void testHasGoldBuyPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		TextField quantity = new TextField();
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setGold(100);
		quantity.setText("2");
		shop.buyPotion(hero.getCp(), quantity, errorMsg, display);
        
		assertEquals(0, hero.getGold());
		assertEquals(2, hero.getPotionMap().get(hero.getCp()));  	 
    }
    
    /**
     * This tests the buy potion method when the player does not have enough gold to buy the potion. 
     * 
     */ 
    @Test 
    void testNoGoldBuyPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		TextField quantity = new TextField();
		Text errorMsg = new Text();
		Text display = new Text();
		
		hero.setGold(1);
		quantity.setText("100");
		shop.buyPotion(hero.getHp(), quantity, errorMsg, display);
		
		assertEquals("YOU DO NOT HAVE ENOUGH GOLD", errorMsg.getText());
 
    }
    
    /**
     * This tests the buy potion method when invalid input is provided as an quantity. 
     * 
     */
    @Test 
    void testInvalidBuyPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
		TextField quantity = new TextField();
		Text errorMsg = new Text();
		Text display = new Text();
		
		quantity.setText("kunborghini");
		shop.buyPotion(hero.getHp(), quantity, errorMsg, display);
		
		assertEquals("NUMBERS ONLY", quantity.getText());    	
    }
    
    /**
     * This tests the sell potion method when no quantity is provided. 
     * 
     */
    
    @Test 
    void testEmptyTextSellPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
    	
    }
    
    /**
     * This tests the sell potion method when the player has the quantity of items to sell. 
     *  
     */
    @Test 
    void testHasItemSellPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
    	
    }
    
    /** 
     * This tests the sell potion method when the player does not have enough items to sell.
     * 
     */
    @Test 
    void testNoItemSellPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
 
    }
    
    /**
     * This tests the sell potion method when invalid input is provided by the player as quantity. 
     * 
     */
    @Test 
    void testInvalidSellPotion() {
    	GameCharacters hero = new GameCharacters();
		Shop shop = new Shop(hero);
    	
    }
    
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
