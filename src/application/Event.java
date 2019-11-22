package application;

import java.io.File;
import java.util.Random;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
	private MediaPlayer mediaPlayer;
	
	/**
	 * The constructor of the event class. 
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
		int a = r.nextInt(5); 
		if (a == 0) {
			this.isEvent = true;			
		}
	}
	
	/**
	 * This method will allow the player to jump 1-2 floors.
	 * 
	 * @param floor the current floor the player is on.
	 */
	public int jumpFloor(Floor floor) {
		Random r = new Random();
		int newFloor = floor.getFloor() + r.nextInt(2) + 1;
		
		if (newFloor >= 10) {
			floor.setFloor(9);
		} else {
			floor.setFloor(newFloor);				
		}
			
		return floor.getFloor();
	}
	
	/**
	 * This method will trigger the drop floor event. 
	 * The player will move down to the lower floor. 
	 * 
	 * @param floor
	 */
	public int dropFloor(Floor floor) {
		floor.decrementFloor();
		
		return floor.getFloor();
	
	}
	
	/**
	 * This method will trigger the get gold event. 
	 * The player will receive a random amount of gold from 1-200.
	 * 
	 * @param hero
	 */
	public Double gainGold(GameCharacters hero, Floor floor) { 
		Random r = new Random();
		double gold = r.nextInt(200) + 1;
		hero.setGold(hero.getGold() + gold);	
		return gold;
	}
	
	/**
	 * This method will trigger the lost gold event. 
	 * The player will lose a random amount of gold from 0-100.
	 * 
	 * @param hero
	 * 
	 */
	public double loseGold(GameCharacters hero,  Floor floor) {
		Random r = new Random();
		double lostGold = r.nextInt(100) + 1;
		
		if (hero.getGold() > lostGold) {
			hero.setGold(hero.getGold() - lostGold);
		} else {
			hero.setGold(0);
			}
		
		return lostGold;
	}
	
	/**
	 * This method runs the event generating function. 
	 * 
	 * @param hero
	 * @param floor
	 * @param display
	 * @param iv
	 */
	public void eventGenerator(GameCharacters hero, Floor floor, Text display, ImageView iv) {
		Random r = new Random();
		int selectedEvent = r.nextInt(4);
		
		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.DARKRED);
		DropShadow ds2 = new DropShadow();
		ds2.setColor(Color.GOLDENROD);
				
		if (selectedEvent == 0) {
			int newFloor2 = this.jumpFloor(floor) + 1;
			display.setText("A map that shows a secret path to avoide the enemies..."
					+ "You get to floor " + newFloor2 + "!");
			iv.setEffect(ds2);
		} else if (selectedEvent == 1) {
			int newFloor = this.dropFloor(floor) + 1;				
			display.setText("A hidden door underneath the box...you droped to floor " + newFloor + " again!");
			iv.setEffect(ds1);
		} else if (selectedEvent == 2) {
			double gold = this.gainGold(hero, floor);	
			display.setText(gold + " gold in the box...You now have " + hero.getGold() + " gold.");
			iv.setEffect(ds2);
		} else if (selectedEvent  == 3) {
			double lostGold;
			lostGold = this.loseGold(hero, floor);		
			display.setText("A Goblin appears and takes " + lostGold + " gold away...You now have " + hero.getGold() + " gold.");
			iv.setEffect(ds1);
		}
	}
	
	/**
	 * This method creates the chest opening sound effect. 
	 */
	
	public void openChestSound() {
		String musicFile = "./src/chestOpening.wav";
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.8);		
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
