package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

/**
 * This class represent the magic shop available to the player on floor 3,6 & 9.
 * 
 * @author JiayuZhu
 */

public class Shop {
	private Image cpImage;
	private Image hpImage;
	private Image reviveImage;
	private SoundEffect se;

	/**
	 * Constructor of the shop class
	 */
	public Shop() {
		this.cpImage = new Image("cheapPotion.png", 150, 150, false, false);
		this.hpImage = new Image("hyperPotion.png", 150, 150, false, false);
		this.reviveImage = new Image("revive.png", 150, 150, false, false);
		this.se = new SoundEffect();
	}

	/**
	 * This method allows the player to buy the potion from the shop by clicking the
	 * buy button
	 * 
	 * @param hero     the hero
	 * @param btn      buy button
	 * @param potion   the type of the potion the player is buying
	 * @param quantity the quantity of the potion the player is buying
	 * @param errorMsg an error message shows if the player does not have enough
	 *                 money
	 * @param display  a text showing gold and the items in the player's bag
	 */
	public void buyPotion(GameCharacters hero, Button btn, Potion potion, TextField quantity, Text errorMsg,
			Text display) {
		btn.setOnAction(Event -> {
			String text = quantity.getText();
			if (text.isEmpty() || text.matches("0")) {
				se.errorSound();
				quantity.setText("INVALID QUANTITY");
				quantity.setOnMouseClicked(event -> quantity.clear());
			} else {			
			if (text.matches("[0-9]*")) {
				int cost =  potion.getPrice() * Integer.parseInt(quantity.getText());
				if (hero.getGold() >= cost) {
					se.moneySound();
					errorMsg.setVisible(false);
					hero.setGold(hero.getGold() - cost);
					hero.getPotionMap().put(potion,
							hero.getPotionMap().get(potion) + Integer.parseInt(quantity.getText()));
					quantity.clear();
					display.setText(this.shopDisplay(hero));
				} else {
					se.errorSound();
					quantity.clear();
					errorMsg.setText("YOU DO NOT HAVE ENOUGH GOLD");
					errorMsg.setVisible(true);
			}
			} else {
				se.errorSound();
				quantity.setText("NUMBERS ONLY");
				quantity.setOnMouseClicked(event -> quantity.clear());
			}
		};
		});

	}

	/**
	 * This method allows the player to sell items at the shop by clicking the sell
	 * button
	 * 
	 * @param hero     the hero
	 * @param btn      the sell button
	 * @param potion   the type of potion the player is selling
	 * @param q        the quantity of potion the player is selling
	 * @param errorMsg an error message shows if the player does not have enough
	 *                 items
	 * @param display  a text showing gold and the items in the player's bag
	 */
	public void sellPotion(GameCharacters hero, Button btn, Potion potion, TextField q, Text errorMsg, Text display) {
		btn.setOnAction(Event -> {
			String text = q.getText();
			if (text.isEmpty() || text.matches("0")) {
				se.errorSound();
				q.setText("INVALID QUANTITY");
				q.setOnMouseClicked(event -> q.clear());
			} else {		
			if (text.matches("[0-9]*")) {
				int quantity = Integer.parseInt(q.getText());
				if (hero.getPotionMap().get(potion) >= quantity) {
					se.moneySound();
					hero.setGold(hero.getGold() + ((potion.getPrice() / 2) * quantity));
					hero.getPotionMap().put(potion, hero.getPotionMap().get(potion) - quantity);
					display.setText(this.shopDisplay(hero));
					q.clear();
				} else {
					se.errorSound();
					errorMsg.setText("YOU DO NOT HAVE ENOUGH ITEMS");
					errorMsg.setVisible(true);
					q.clear();
				}
			} else {
				se.errorSound();
				q.setText("NUMBERS ONLY");
				q.setOnMouseClicked(event -> q.clear());				
			}
		};
		});
	}

	/**
	 * This method allows the player to buy the revive item at the shop.
	 * 
	 * @param hero     the hero
	 * @param btn      the buy button
	 * @param errorMsg an error message that shows the player fails to buy the item
	 * @param display  a text showing gold and the items in the player's bag
	 */
	public void buyRevive(GameCharacters hero, Button btn, Text errorMsg, Text display) {
		btn.setOnAction(Event -> {
			if (hero.isHasRevive() == true) {
				se.errorSound();
				errorMsg.setText("YOU ALDREADY HAVE THAT ITEM");
				errorMsg.setVisible(true);
			} else {
				if (hero.getGold() >= 200) {
					se.moneySound();
					hero.setGold(hero.getGold() - 200);
					hero.setHasRevive(true);
					display.setText(this.shopDisplay(hero));
				} else {
					se.errorSound();
					errorMsg.setText("YOU DO NOT HAVE ENOUGH GOLD");
					errorMsg.setVisible(true);
				}
			}
		});
	}

	/**
	 * This method allows the player to sell a revive item at the shop.
	 * 
	 * @param hero     the hero
	 * @param btn      the sell button
	 * @param errorMsg an error messsage taht shows the player fails to sell the
	 *                 item
	 * @param display  a text showing gold and the items in the player's bag
	 */
	public void sellRevive(GameCharacters hero, Button btn, Text errorMsg, Text display) {
		btn.setOnAction(Event -> {
				if (hero.isHasRevive() == true) {
					se.moneySound();
					hero.setGold(hero.getGold() + 150);
					hero.setHasRevive(false);
					display.setText(this.shopDisplay(hero));
				} else {
					se.errorSound();
					errorMsg.setText("YOU DO NOT HAVE ENOUGH ITEMS");
					errorMsg.setVisible(true);
				}
		});
	}
    
	/**
	 * This method returns a string that include player's gold and what is in his
	 * bag.
	 * 
	 * @param hero the hero
	 * @return display a string with amount of gold and what is in the player's bag
	 */
	public String shopDisplay(GameCharacters hero) {
		String display = "You have: " + hero.getGold() + " GOLD \n\nITEM BAG: ";
		for (Potion p : hero.getPotionMap().keySet()) {

			display += "\n" + p + "\t\tx" + hero.getPotionMap().get(p);

		}

		if (hero.isHasRevive() == true) {
			display += "\nRevive \t\t\tx1";
		} else {
			display += "\nRevive \t\t\tx0";
		}

		return display;
	}

	/**
	 * @return the cpImage
	 */
	public Image getCpImage() {
		return cpImage;
	}

	/**
	 * @param cpImage the cpImage to set
	 */
	public void setCpImage(Image cpImage) {
		this.cpImage = cpImage;
	}

	/**
	 * @return the hpImage
	 */
	public Image getHpImage() {
		return hpImage;
	}

	/**
	 * @param hpImage the hpImage to set
	 */
	public void setHpImage(Image hpImage) {
		this.hpImage = hpImage;
	}

	/**
	 * @return the reviveImage
	 */
	public Image getReviveImage() {
		return reviveImage;
	}

	/**
	 * @param reviveImage the reviveImage to set
	 */
	public void setReviveImage(Image reviveImage) {
		this.reviveImage = reviveImage;
	}

}
