package application;

import javafx.event.ActionEvent;

import java.util.Random;

/**
 * Thsi class is responsible for controlling game play.
 * 
 * @author sharisinclair and David Cai
 *
 */
public class GamePlayController {
    private boolean continueFighting;
    private boolean goToNextLevel;
    private boolean endGamePlay;
    
    
    public GamePlayController() {
	continueFighting = true;
	endGamePlay = false; 
	
    }
    
    /**
     * This method will determine whether the hero has won the fight and can continue to the next floor.
     * 
     * @param hero The chosen hero character being used by the player.
     * @param enemy The enemy character currently being fought by the hero.
     * @param floor The current floor of the hero is on.
     */
   public void continueGamePlay(GameCharacters hero, GameCharacters allEnemies[]) { // Floor floor
       int heroStam = hero.getCurrentStamina();
       int enemyStam = 0;
       for (int i = 0; i < allEnemies.length; i++) {
    	   enemyStam += allEnemies[i].getCurrentStamina();
       }
       //int floor  = floor.getFloorNumber();
       if ( heroStam == 0 || enemyStam == 0)// && floor < 10 {
	   continueFighting = false;
	   if (enemyStam <= 0) {
	       goToNextLevel = true;
	   } else if (heroStam <= 0) {
	       //floor.setFloorNumber(0)
	   }
       }
   
   
   public void determineEndOfGame(GameCharacters hero, GameCharacters enemy) { // Floor floor
       //Method - if floor number = 10 and enemy stamina <= 0, hero > 0, set endGamePlay to true.
   }
   
}
