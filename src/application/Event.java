package application;


import java.util.Random;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class represents and controls event that can happen randomly in the game.
 * 
 * @author JiayuZhu
 *
 */
public class Event {
	private boolean isEvent;
	private Image closedBox;
	private Image openBox;
	
	/**
	 * The constructor of the event class sets the necessary image instance variable
	 * and initializes isEvent to false.
	 */
	public Event() {
		this.isEvent = false;
		closedBox = new Image("closed_treasure.png", 300, 300, false, false);
		openBox = new Image("open_treasure.png", 300, 300, false, false);
		
	}
	
	/**
	 * This method determines whether the special event occurs.
	 * The chance of the occurrence of a special event is 20%.
	 */
	public void eventHappen() {
		Random r = new Random();
		int event = r.nextInt(1); // 10% chance to get an event  
		if (event == 0) {
			this.isEvent = true;			
		}
	}
	
	/**
	 * This method will allow the player to jump 1-2 floors.
	 * 
	 * @param floor The current floor the player is on.
	 * @param hero The player hero 
	 * @param dispaly The text display of the event
	 */
	public void jumpFloor(GameCharacters hero, Floor floor, Text display) {
		Random r = new Random();
		int newFloor = floor.getFloor() + r.nextInt(2) + 1;
		floor.setFloor(newFloor);
		
		newFloor++;
		
		display.setText("A map that shows a secret path to avoid the enemies..."
				+ "You get to floor " + newFloor + "!");
	}
	
	/**
	 * This method will trigger the drop floor event. 
	 * The player will move down to the lower floor. 
	 * 
	 * @param floor The current floor the player is on
	 * @param display Text display of the event
	 */
	public void dropFloor(Floor floor, Text display) {
		floor.decrementFloor();
		int newFloor = floor.getFloor() + 1;
		display.setText("A hidden door underneath the box...you dropped to floor " + newFloor + " again!");
	
	}
	
	/**
	 * This method will trigger the get gold event. 
	 * The player will receive a random amount of gold from 1-200.
	 * 
	 * @param hero The player hero
	 * @param text The text display of the event
	 */
	public void gainGold(GameCharacters hero, Text display) { 
		Random r = new Random();
		int gold = r.nextInt(200) + 1;
		hero.setGold(hero.getGold() + gold);	
		
		display.setText(gold + " gold in the box...You now have " + hero.getGold() + " gold.");
	}
	
	/**
	 * This method will trigger the lost gold event. 
	 * The player will lose a random amount of gold from 0-100.
	 * 
	 * @param hero The player hero
	 * @param floor The current floor the hero is on
	 * @param display The text display of the event
	 */
	public void loseGold(GameCharacters hero, Floor floor, Text display) {
		Random r = new Random();
		int lostGold = r.nextInt(100) + 1;
		
		if (hero.getGold() > lostGold) {
			hero.setGold(hero.getGold() - lostGold);
		} else {
			lostGold = hero.getGold();
			hero.setGold(0);
			}
		
		display.setText("A Goblin appears and takes " + lostGold + " gold away...You now have " + hero.getGold() + " gold.");
		
	}
	
	/**
	 * This method will trigger the attack boost even.
	 * The player will get a 20% boost on his attack pts.
	 * @param hero
	 * @param display
	 */
	public void attackBoost(GameCharacters hero, Text display) {
		hero.setAttack((int)(hero.getAttack()* 1.2));
		display.setText("A hidden elf gives you her blessing...Your attacks become more powerful.\nNew attack: " + hero.getAttack());
		display.setTextAlignment(TextAlignment.CENTER);
	}
	
	
//	public void defenseBoost(GameCharacters hero, Text display) {
//		hero.setDefense((int)(hero.getDefense() * 1.2));
//		display.setText(""));
//	}
	
	
	
	/**
	 * This method runs the event generating function. 
	 * 
	 * @param hero The players chosen hero
	 * @param floor The current floor the hero is on
	 * @param display The text to let the user know the what event occurred
	 * @param iv The image / image view to which effects will be added.
	 */
	public void eventGenerator(GameCharacters hero, Floor floor, Text display, ImageView iv) {
		Random r = new Random();
//		int selectedEvent = r.nextInt(4);
		int selectedEvent = 4;
		
		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.DARKRED);
		DropShadow ds2 = new DropShadow();
		ds2.setColor(Color.GOLDENROD);
				
		if (selectedEvent == 0) {
			jumpFloor(hero, floor, display);
			iv.setEffect(ds2);
		} else if (selectedEvent == 1) {
			dropFloor(floor, display);
			iv.setEffect(ds1);
		} else if (selectedEvent == 2) {
			gainGold(hero, display);
			iv.setEffect(ds2);
		} else if (selectedEvent  == 3) {
			loseGold(hero, floor, display);
			iv.setEffect(ds1);
		} else if (selectedEvent == 4) {
			attackBoost(hero, display);
			iv.setEffect(ds2);
		}
	}
	
		
	/**
	 * This method is responsible for running the event phase. 
	 * 
	 * @param hero The player chosen hero
	 * @param floor The current floor the player is on
	 * @param continueBtn The button to continue playing
	 * @param grid The grid currently housing the various nodes
	 * @param closedIV The image view of the closed chest
	 * @param openIV the image view of the open chest
	 * @param openBtn The button to open the chest
	 * @param display Text to update the user on the event that occurred
	 */
	public void openChest(GameCharacters hero, Floor floor, Button continueBtn, GridPane grid, ImageView closedIV, ImageView openIV, Button openBtn, Text display) {
		continueBtn.setDisable(false);
		grid.getChildren().remove(closedIV);
		grid.add(openIV, 2, 1);		
		GridPane.setHalignment(openIV, HPos.CENTER);
		openBtn.setVisible(false);		
		eventGenerator(hero, floor, display, openIV);
	}
	
	/**
	 * @return the isEvent
	 */
	public boolean isEvent() {
		return isEvent;
	}

	/**
	 * @param isEvent the isEvent to set
	 */
	public void setEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	/**
	 * @return the closedBox
	 */
	public Image getClosedBox() {
		return closedBox;
	}

	/**
	 * @param closedBox the closedBox to set
	 */
	public void setClosedBox(Image closedBox) {
		this.closedBox = closedBox;
	}

	/**
	 * @return the openBox
	 */
	public Image getOpenBox() {
		return openBox;
	}

	/**
	 * @param openBox the openBox to set
	 */
	public void setOpenBox(Image openBox) {
		this.openBox = openBox;
	}
	
	
	
}
