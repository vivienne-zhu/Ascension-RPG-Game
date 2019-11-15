package application;

import javafx.event.ActionEvent;

import java.util.Random;

/**
 * This class is responsible for controlling elements of game play.
 * 
 * @author sharisinclair and David Cai
 *
 */
public class GamePlayController {
	private boolean goToNextLevel;
	private boolean endGamePlay;

	public GamePlayController() {
		endGamePlay = false;

	}

	/**
	 * This method will determine whether the hero has won the fight and can
	 * continue to the next floor.
	 * 
	 * @param hero       The chosen hero character being used by the player.
	 * @param allEnemies the array of all enemies f
	 * @param floor      The current floor of the hero is on.
	 */
	public void continueGamePlay(GameCharacters hero, GameCharacters allEnemies[], Floor floor) { 
		int heroStam = hero.getCurrentStamina();
		int enemyStam = 0;
		for (int i = 0; i < allEnemies.length; i++) {
			enemyStam += allEnemies[i].getCurrentStamina();
		}

		if (heroStam > 0 || enemyStam == 0 && floor.getFloor() < 10) {
			if (enemyStam <= 0) {
				goToNextLevel = true;
			} else if (heroStam <= 0) {
				floor.setFloor(1);
				endGamePlay = true;
			}
		}
	}

	/**
	 * This method will determine whether to end the game or not.
	 * 
	 * @param hero The chosen hero character being used by the player.
	 * @param enemy The enemy the hero is fighting
	 * @param floor The floor the hero is on.
	 */
	public void determineEndOfGame(GameCharacters hero, GameCharacters enemy, Floor floor) { 
		if (floor.getFloor() == 10 && enemy.getCurrentStamina() == 0 && hero.getCurrentStamina() >0) {
			endGamePlay = true;
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


}
