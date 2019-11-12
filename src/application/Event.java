package application;

/**
 * @author JiayuZhu
 */

import java.util.Random;

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
	 * @param floor the current floor the player is on.
	 */
	public void jumpFloor(Floor floor) {
		Random r = new Random();
		floor.setFloor(floor.getFloor() + r.nextInt(3) + 1);		
	}
	
	/**
	 * This method will trigger the drop floor event. 
	 * The player will move down to the lower floor. 
	 */
	public void dropFloor(Floor floor) {
		floor.setFloor(floor.getFloor() - 1);
	}
	
	/**
	 * This method will trigger the get gold event. 
	 * The player will receive a random amount of gold from 1-200.
	 * @param hero
	 */
	public void gainGold(GameCharacters hero) {
		Random r = new Random();
		hero.setGold(hero.getGold() + r.nextInt(200) + 1);		
	}
	
	/**
	 * This method will trigger the lost gold event. 
	 * The player will lose a random amount of gold from 0-100.
	 * 
	 */
	public void loseGold(GameCharacters hero) {
		Random r = new Random();
		int lostGold = r.nextInt(100) + 1;
		
		if (hero.getGold() > lostGold) {
			hero.setGold(hero.getGold() - lostGold);			
		} else {
			hero.setGold(0);
		}
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
