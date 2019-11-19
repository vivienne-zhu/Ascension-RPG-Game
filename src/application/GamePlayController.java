package application;

import java.util.ArrayList;


/**
 * This class is responsible for controlling elements of game play.
 * 
 * @author sharisinclair and David Cai
 *
 */
public class GamePlayController {
	private boolean goToNextLevel;
	private boolean endGamePlay;
	private boolean win;
	

	public GamePlayController() {
		endGamePlay = false;
		win = false;

	}

	/**
	 * This method will determine whether the hero has won the fight and can
	 * continue to the next floor.
	 * 
	 * @param hero       The chosen hero character being used by the player.
	 * @param allEnemies the arraylist of all enemies the hero is fighting
	 * @param floor      The current floor of the hero is on.
	 */
	public void continueGamePlay(GameCharacters hero, ArrayList<GameCharacters> allEnemies, Floor floor) { 
		int heroStam = hero.getCurrentStamina();
		int enemyStam = 0;
		for (int i = 0; i < allEnemies.size(); i++) {
			enemyStam += allEnemies.get(i).getCurrentStamina();
		}
		if (heroStam > 0 || enemyStam == 0 && floor.getFloor() < 10) {
			if (enemyStam <= 0) {
				goToNextLevel = true;
			} else if (heroStam <= 0) {
				endGamePlay = true;
			}
		}
		
	}

	/**
	 * This method will determine whether to end the game or not.
	 * 
	 * @param hero The chosen hero character being used by the player.
	 * @param enemy The enemy(or enemies) the hero is fighting
	 * @param floor The floor the hero is on.
	 */
	public void determineEndOfGame(GameCharacters hero, ArrayList<GameCharacters> allEnemies, Floor floor) { 
	    	int enemyStam = 0;
	    	for (int i = 0; i < allEnemies.size(); i++) {
			enemyStam += allEnemies.get(i).getCurrentStamina();
		}
		if (floor.getFloor() == 10 && enemyStam == 0 && hero.getCurrentStamina() > 0) {
			setWin(true) ;
		} 
	}

	/**
	 * @return the goToNextLevel
	 */
	public boolean isGoToNextLevel() {
		return goToNextLevel;
	}

	/**
	 * @param goToNextLevel the goToNextLevel to set
	 */
	public void setGoToNextLevel(boolean goToNextLevel) {
		this.goToNextLevel = goToNextLevel;
	}

	/**
	 * @return the endGamePlay 
	 */
	public boolean isEndGamePlay() {
		return endGamePlay;
	}

	/**
	 * @param endGamePlay the endGamePlay to set
	 */
	public void setEndGamePlay(boolean endGamePlay) {
		this.endGamePlay = endGamePlay;
	}

	/**
	 * @return the win
	 */
	public boolean isWin() {
	    return win;
	}

	/**
	 * @param win the win to set
	 */
	public void setWin(boolean win) {
	    this.win = win;
	}

}
