package application;

/**
 * @author jiayuZhu
 */

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public class Shop {
	private Image shopBg;

	private Image cpImage;
	private Image hpImage;
	private Image reviveImage;

	/**
	 * Constructor of the shop class
	 */
	public Shop() {
		this.shopBg = new Image("shop.jpg", 1280, 720, false, false);

		this.cpImage = new Image("cheapPotion.jpg", 150, 150, false, false);
		this.hpImage = new Image("hyperPotion.png", 150, 150, false, false);
		this.reviveImage = new Image("revive.jpg", 150, 150, false, false);
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
			if (text.matches("[0-9]*")) {
				double cost = potion.getPrice() * Double.parseDouble(quantity.getText());
				if (hero.getGold() >= cost) {
					errorMsg.setVisible(false);
					hero.setGold(hero.getGold() - cost);
					hero.getPotionMap().put(potion,
							hero.getPotionMap().get(potion) + Double.parseDouble(quantity.getText()));
					quantity.setText("");
					display.setText(this.shopDisplay(hero));
				} else {
					quantity.setText("");
					errorMsg.setText("YOU DO NOT HAVE ENOUGH GOLD");
					errorMsg.setVisible(true);
			}
			} else {
				quantity.setText("NUMBERS ONLY");
			}
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
			if (text.matches("[0-9]*")) {
				double quantity = Double.parseDouble(q.getText());
				if (hero.getPotionMap().get(potion) >= quantity) {
					hero.setGold(hero.getGold() + ((potion.getPrice() / 2) * quantity));
					hero.getPotionMap().put(potion, hero.getPotionMap().get(potion) - quantity);
					display.setText(this.shopDisplay(hero));
					q.setText("");
				} else {
					errorMsg.setText("YOU DO NOT HAVE ENOUGH ITEMS");
					errorMsg.setVisible(true);
					q.setText("");
				}
			} else {
				q.setText("NUMBERS ONLY");
				
			}
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
				errorMsg.setText("YOU ALDREADY HAVE THAT ITEM");
				errorMsg.setVisible(true);
			} else {
				if (hero.getGold() >= 200) {
					hero.setGold(hero.getGold() - 200);
					hero.setHasRevive(true);
					display.setText(this.shopDisplay(hero));
				} else {
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
					hero.setGold(hero.getGold() + 150);
					hero.setHasRevive(false);
					display.setText(this.shopDisplay(hero));
				} else {
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
			display += "\nRevive \t\t\tx1.0";
		} else {
			display += "\nRevive \t\t\tx0.0";
		}

		return display;
	}

	/**
	 * @return the shopBg
	 */
	public Image getShopBg() {
		return shopBg;
	}

	/**
	 * @param shopBg the shopBg to set
	 */
	public void setShopBg(Image shopBg) {
		this.shopBg = shopBg;
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
