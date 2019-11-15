package application;

import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This class represents and controls event that can happen randomly in the game.
 * 
 * @author JiayuZhu
 *
 */
public class Event {
	private boolean isEvent;
	
	/**
	 * The constructor of the event class. 
	 */
	public Event() {
		this.isEvent = false;
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
	 * This method will allow the player to jump 1-3 floors.
	 * 
	 * @param floor the current floor the player is on.
	 */
	public void jumpFloor(Floor floor) {
		Random r = new Random();
		floor.setFloor(floor.getFloor() + r.nextInt(3) + 1);		
	}
	
	/**
	 * This method will trigger the drop floor event. 
	 * The player will move down to the lower floor. 
	 * 
	 * @param floor
	 */
	public void dropFloor(Floor floor) {
		floor.setFloor(floor.getFloor() - 1);
	
	}
	
	/**
	 * This method will trigger the get gold event. 
	 * The player will receive a random amount of gold from 1-200.
	 * 
	 * @param hero
	 */
	public void gainGold(GameCharacters hero) {
		Random r = new Random();
		hero.setGold(hero.getGold() + r.nextInt(200) + 1);	
		//incrementFloor();
	}
	
	/**
	 * This method will trigger the lost gold event. 
	 * The player will lose a random amount of gold from 0-100.
	 * 
	 * @param hero
	 * 
	 */
	public void loseGold(GameCharacters hero) {
		Random r = new Random();
		double lostGold = r.nextInt(100) + 1;
		
		if (hero.getGold() > lostGold) {
			hero.setGold(hero.getGold() - lostGold);
			//incrementFloor()
		} else {
			hero.setGold(0);
			//incrementFloor()
		}
	}
	
	/**
	 * This method creates a display to let the player know if  and what event has happened
	 * 
	 * @param primaryStage The primary stage/window needed to display the GUI.
	 * @param g The GameGUI class 
	 */
	public void eventScene(Stage primaryStage, GameGUI g) {
	    //Temporary values layout and text
	    this.eventHappen();
	    Text eventText = new Text();
	    eventText.setLayoutX(600);
	    eventText.setLayoutY(300);
	    if (this.isEvent() == true) {
		eventText.setText("An event has happened!");
	    } else {
		eventText.setText("No event has happened!");
	    }
	    
	    //Creating continue button and setting eventHandling
	    Button continueBtn = new Button("Continue to next floor");
	    continueBtn.setOnAction(event->{g.fullGame(primaryStage);});
	    continueBtn.setLayoutX(600);
	    continueBtn.setLayoutY(700);
	    
	    //Creating pane, adding children 
	    Pane display = new Pane();
	    display.getChildren().addAll(continueBtn, eventText);
	    
	    //Creating scene, adding it to stage and showing stage
	    Scene eventScene = new Scene(display, 1280, 720);
	    primaryStage.setScene(eventScene);
	    primaryStage.show();
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
	
}
