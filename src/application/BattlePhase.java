package application;


import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

/**
 * This class represents the actions that occur during the battle phase. This class ensures that
 * proper information is displayed to the user during the course of the battle. It also gives the 
 * user the ability to actually fight the enemy via various effect linked buttons. Finally, this
 * class contains the logic behind battle animations, level up mechanics, and enemy decision making.
 * 
 * @author David Cai, Shari Sinclair and Jiayu Zhu
 */
public class BattlePhase {
	private Stage primaryStage;
	private int floor;
	private HashSet<Integer> dead = new HashSet<Integer>();
	private int totalEnemyHealth;
	private Timeline animateOne;
	private Timeline animateTwo;
	private Timeline animateThree;
	private Timeline animateHero;
	private boolean magic;
	private SoundEffect se;
	private boolean healerTargetAvail;

	public BattlePhase(Stage primaryStage, int floor, int totalEnemyHealth) {
		this.primaryStage = primaryStage;
		this.floor = floor;
		this.totalEnemyHealth = totalEnemyHealth;
		this.se = new SoundEffect();
		setMagic(false);
		
	}

	/**
	 * This method attaches the proper events to button clicks. Namely it gives action
	 * to the attack, defend, heal, and choose enemy buttons.
	 * @param allEnemies The HashMap of all enemies
	 * @param hero The character the player controls
	 * @param gc The GraphicsContext used to delete and draw pictures to canvas
	 * @param transition The scene displayed after player clears the floor
	 * @param youWin The scene displayed after player wins the game
	 * @param reviveScene The scene displayed giving the player the option to revive
	 * @param gameOverScreen The scene displayed when the player loses the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 * @param youWinMusic The music that plays when you win the game
	 * @param display The display elements of needed for battle phase
	 */
	public void eventButtons(HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GameCharacters hero, 
			GraphicsContext gc, Scene transition, Scene youWin, Scene reviveScene, Scene gameOverScreen, 
			MediaPlayer battleMusic, MediaPlayer gameOverMusic , MediaPlayer youWinMusic,BattlePhaseDisplay display) {
		//Event handling for when attack button is pressed
		display.getAttackBtn().setOnAction(event -> {
		    if(totalEnemyHealth != 0) {
			display.getItemBag().setVisible(false);
			display.getError().setVisible(false);
			setMagic(false);
			
			// Restore mana for mage
			 restoreMana(hero, display);

			display.disableButtons(true);
			hero.setIsDefending(false);
			if (dead.contains(1) && dead.contains(2)) {
				display.getChooseEnemyBtn().setVisible(true);
			} else if (dead.contains(0) && dead.contains(2)) {
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			} else if (dead.contains(0) && dead.contains(1)) {
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			} else if (dead.contains(0)) {
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			} else if (dead.contains(1)) {
			    	display.getChooseEnemyBtn().setVisible(true);
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			} else if (dead.contains(2)) {
			    	display.getChooseEnemyBtn().setVisible(true);
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			} else {
			    	display.getChooseEnemyBtn().setVisible(true);
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			}
		    }
		});


		display.getMagicAtkBtn().setOnAction(event ->{
		    if(totalEnemyHealth != 0 && hero.getCurrentMana() >= 50) {
		    display.getItemBag().setVisible(false);
		    display.getError().setVisible(false);
		    setMagic(true);
		    
			display.disableButtons(true);
			hero.setIsDefending(false);
			if (dead.contains(1) && dead.contains(2)) {
				display.getChooseEnemyBtn().setVisible(true);
			} else if (dead.contains(0) && dead.contains(2)) {
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			} else if (dead.contains(0) && dead.contains(1)) {
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			} else if (dead.contains(0)) {
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			} else if (dead.contains(1)) {
			    	display.getChooseEnemyBtn().setVisible(true);
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			} else if (dead.contains(2)) {
			    	display.getChooseEnemyBtn().setVisible(true);
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			} else {
			    	display.getChooseEnemyBtn().setVisible(true);
			    	display.getChooseEnemyTwoBtn().setVisible(true);
			    	display.getChooseEnemyThreeBtn().setVisible(true);
			}
		    } else {
			se.errorSound();
			display.getMagicAtkBtn().setDisable(true);
		    }
		});

		//Event handling for when defend button is pressed
		display.getDefendBtn().setOnAction(event -> {
		    if(totalEnemyHealth != 0) {
		    	display.getItemBag().setVisible(false);
			display.getError().setVisible(false);
			setMagic(false);
			
			Image defendIcon = new Image("defendIcon.png", 80, 80, false, false);
			
			// Restore mana for mage
			restoreMana(hero, display);
			
			gc.drawImage(defendIcon, 140, 280); //draw defend icon
			display.disableButtons(true); //disable buttons
			hero.setIsDefending(true);
			hero.setIsEmpowered(true);
			display.getEmpowered().setVisible(true);
			enemyTurn(hero, allEnemies, gc, floor, reviveScene, gameOverScreen, battleMusic, gameOverMusic, display);
			//Enable buttons after 1.5 secs per enemy
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			display.disableButtons(false));
			timeline.getKeyFrames().add(frame);
			timeline.play();

			//Delete icon after 1.5 secs per enemy
			Timeline icon = new Timeline(); 
			icon.setCycleCount(1);
			KeyFrame iconDisable = new KeyFrame(Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			gc.clearRect(140, 280, 80, 80));
			icon.getKeyFrames().add(iconDisable);
			icon.play();
		    }
		});

		//Event handling for when heal button is pressed
			display.healFunctionDisplay(hero);
		    display.getHealBtn().setOnAction(event -> {
			if (totalEnemyHealth != 0) {
			display.getItemBag().setVisible(true);	
			
			display.getPotionBtn().setOnAction(event1 -> {
				hero.usePotion(hero.getCp(), display.getError());
				display.getHeroStam().setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
				display.resetInfoBar(0, display.getStaminaBar(), 300, hero);
				display.getPotionBtn().setText(hero.itemInfo(hero.getCp()));
				if (display.getError().isVisible() == false) {
				    Timeline timeline = new Timeline(); 
					timeline.setCycleCount(1);
					KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> {
					animateHero.stop();
					hero.displayCharacter(gc, false, false,true);});
					timeline.getKeyFrames().addAll(frame);
					Timeline timeline2 = new Timeline(); 
					timeline2.setCycleCount(1);
					KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> 
					animateHero.play());
					timeline2.getKeyFrames().addAll(frame2);
					SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
					sequence.play(); 
					//hero.displayCharacter(gc, false, false,false));
				}
			});
				
			display.getHyperPotionBtn().setOnAction(event2 -> {
				hero.usePotion(hero.getHp(), display.getError());
				display.getHyperPotionBtn().setText(hero.itemInfo(hero.getHp()));
				display.getHeroStam().setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
				display.resetInfoBar(0, display.getStaminaBar(), 300, hero);
				if (display.getError().isVisible() == false) {
					Timeline timeline = new Timeline(); 
					timeline.setCycleCount(1);
					KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> {
					animateHero.stop();
					hero.displayCharacter(gc, false, false,true);});
					timeline.getKeyFrames().addAll(frame);
					Timeline timeline2 = new Timeline(); 
					timeline2.setCycleCount(1);
					KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> 
					animateHero.play());
					timeline2.getKeyFrames().addAll(frame2);
					SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
					sequence.play(); 
					//hero.displayCharacter(gc, false, false,false));
				}
			
			});
			}
		});
		
		// Actions to take after button to choose enemy is chosen
		display.getChooseEnemyBtn().setOnAction(event -> {
			chooseEnemyBtnEvent(0, hero, allEnemies, transition, youWin, reviveScene, gameOverScreen, gc, battleMusic, gameOverMusic, youWinMusic, display);
		});

		// Actions to take after button to choose enemy is chosen
		display.getChooseEnemyTwoBtn().setOnAction(event -> {
			chooseEnemyBtnEvent(1, hero, allEnemies, transition, youWin, reviveScene, gameOverScreen, gc, battleMusic, gameOverMusic, youWinMusic, display);
		});

		// Actions to take after button to choose enemy is chosen
		display.getChooseEnemyThreeBtn().setOnAction(event -> {
			chooseEnemyBtnEvent(2, hero, allEnemies, transition, youWin, reviveScene, gameOverScreen, gc, battleMusic, gameOverMusic, youWinMusic, display);
		});
	}
	

	/**
	 * This method allows us the animate the enemy characters image
	 * 
	 * @param allEnemies The ArrayList of enemies for that floor
	 * @param gc The GraphicsContext needed to display/remove images
	 */
	public void idleAnimate(HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GraphicsContext gc) {
		if (allEnemies.get(floor).size() > 0) {
			animateOne = new Timeline();
			animateOne.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(0).displayCharacter(gc, false, false, false));
			KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(0).displayCharacter(gc, true, false, false));
			KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(0).displayCharacter(gc, false, false, false));
			animateOne.getKeyFrames().add(frame);
			animateOne.getKeyFrames().add(frameTwo);
			animateOne.getKeyFrames().add(frameThree);
			animateOne.play();
		}

		if (allEnemies.get(floor).size() > 1) {
			animateTwo = new Timeline();
			animateTwo.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false));
			KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(1).displayCharacter(gc, true, false, false));
			KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false));
			animateTwo.getKeyFrames().add(frame);
			animateTwo.getKeyFrames().add(frameTwo);
			animateTwo.getKeyFrames().add(frameThree);
			animateTwo.play();
		}

		if (allEnemies.get(floor).size() > 2) {;
		animateThree = new Timeline();
		animateThree.setCycleCount(Timeline.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
		allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false));
		KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
		allEnemies.get(floor).get(2).displayCharacter(gc, true, false, false));
		KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
		allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false));
		animateThree.getKeyFrames().add(frame);
		animateThree.getKeyFrames().add(frameTwo);
		animateThree.getKeyFrames().add(frameThree);
		animateThree.play();
		}
	}
	
	/**
	 * 
	 * This animates the hero image in the GUI
	 * 
	 * @param hero The chosen game character of the player
	 * @param gc the GraphicsContext need to display/remove images from the GUI
	 */
	public void heroAnimate(GameCharacters hero, GraphicsContext gc) {
			animateHero = new Timeline();
			animateHero.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
			hero.displayCharacter(gc, false, false, false));
			KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
			hero.displayCharacter(gc, true, false, false));
			KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
			hero.displayCharacter(gc, false, false, false));
			animateHero.getKeyFrames().add(frame);
			animateHero.getKeyFrames().add(frameTwo);
			animateHero.getKeyFrames().add(frameThree);
			animateHero.play();
		}

	/**
	 * This method covers the events that occur after pressing a chooseEnemyBtn.
	 * @param enemy The index of the enemy to be attacked
	 * @param hero The chosen game character of the player
	 * @param allEnemies HashMap of all enemy characters
	 * @param transition The scene displayed after player clears the floor
	 * @param youWin The scene displayed after player wins the game
	 * @param reviveScene The scene displayed giving the player the option to revive
	 * @param gameOverScreen The scene displayed when the player loses the game
	 * @param gc the GraphicsContext need to display/remove images from the GUI
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 * @param youWinMusic The music that plays when you win the game
	 * @param display The display elements of needed for battle phase
	 */
	private void chooseEnemyBtnEvent(int enemy, GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies,
			Scene transition, Scene youWin, Scene reviveScene, Scene gameOverScreen, GraphicsContext gc, MediaPlayer battleMusic, 
			MediaPlayer gameOverMusic,MediaPlayer youWinMusic, BattlePhaseDisplay display) {
		display.getEmpowered().setVisible(false);
		if (isMagic() == true) {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> mageTurn(hero, allEnemies, enemy, gc, primaryStage, transition, youWin, battleMusic , youWinMusic, display));
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, gc, floor, reviveScene, gameOverScreen, battleMusic, gameOverMusic, display));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 2 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			display.disableButtons(false));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			display.getChooseEnemyBtn().setVisible(false);
			display.getChooseEnemyTwoBtn().setVisible(false);
			display.getChooseEnemyThreeBtn().setVisible(false);
		} else {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(hero, allEnemies, enemy, gc, primaryStage, transition, youWin, battleMusic, youWinMusic, display));
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, gc, floor, reviveScene, gameOverScreen, battleMusic, gameOverMusic, display));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 2 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			display.disableButtons(false));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			display.getChooseEnemyBtn().setVisible(false);
			display.getChooseEnemyTwoBtn().setVisible(false);
			display.getChooseEnemyThreeBtn().setVisible(false);
		}
	}

	/**
	 * This method creates display text for when it is the heroes turn to attack and updates necessary variables.
	 * 
	 * @param hero The players chosen character
	 * @param allEnemies The HashMap of all enemies
	 * @param choice  The enemy character the hero would like to attack (if there are multiple)
	 * @param gc The GraphicalContext needed to display/remove the enemy character image in the GUI.
	 * @param primaryStage the primary stage/ window of GUI
	 * @param transition the transition screen scene
	 * @param youWin the you win screen scene
	 * @param battleMusic Music that plays in the battle phase
	 * @param youWinMusic Music that plays when you win the game
	 * @param display The display elements of needed for battle phase
	 */
	private void heroTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int choice, GraphicsContext gc, Stage primaryStage, 
		Scene transition, Scene youWin, MediaPlayer battleMusic, MediaPlayer youWinMusic, BattlePhaseDisplay display) {		
		
		//Move hero forward 
		Timeline timeline = new Timeline(); 
		if (choice == 0) {
			timeline.setCycleCount(580);
		} else if (choice == 1) {
			timeline.setCycleCount(380);
		} else {
			timeline.setCycleCount(180);
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true, allEnemies, floor));
		timeline.getKeyFrames().add(frame);

		//Play hit sound clip (needs to play before hero arrives)
		Timeline sound = new Timeline();
		KeyFrame soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
		sound.getKeyFrames().add(soundFrame);
		
		Timeline slash = new Timeline();
		slash.setCycleCount(10);
		KeyFrame slashFrame = new KeyFrame(Duration.millis(1), ae -> hero.displaySlashImage(gc, false, 
				allEnemies.get(floor).get(choice).getX(), allEnemies.get(floor).get(choice).getY()));
		slash.getKeyFrames().add(slashFrame);

		Timeline enemyRed = new Timeline();
		KeyFrame turnRed = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.stop();
			} else if (choice == 1) {
				animateTwo.stop();
			} else {
				animateThree.stop();
			}
			allEnemies.get(floor).get(choice).displayCharacter(gc, false, true, false);
		});
		enemyRed.getKeyFrames().add(turnRed);

		//Finish moving forward
		Timeline finishMove = new Timeline(); 
		finishMove.setCycleCount(130);
		KeyFrame finishFrame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true, allEnemies, floor));
		finishMove.getKeyFrames().add(finishFrame);

		//Hero hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.play();
			} else if (choice == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			hitEnemy(hero, allEnemies, choice, gc, primaryStage, floor, transition, youWin, dead, battleMusic, youWinMusic, display);//dialogue, dialogueTwo, dialogueThree, enemyStam,
		});

		hit.getKeyFrames().add(frameTwo);

		//Move hero backward
		Timeline timelineTwo = new Timeline();
		if (choice == 0) {
			timelineTwo.setCycleCount(710);
		} else if (choice == 1) {
			timelineTwo.setCycleCount(510);
		} else {
			timelineTwo.setCycleCount(310);
		}
		KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, false, allEnemies, floor));
		timelineTwo.getKeyFrames().add(frameThree);

		SequentialTransition sequence = new SequentialTransition(timeline, sound, slash, enemyRed, finishMove, hit, timelineTwo);
		sequence.play();    	
	}

	/**
	 * This method creates animation for when a mage a magic attack on their turn and updates necessary variables.
	 * 
	 * @param hero the hero chosen by the player
	 * @param allEnemies The HashMap of all enemies 
	 * @param choice  The enemy character the hero would like to attack (if there are multiple)
	 * @param gc The GraphicalContext needed to display/remove the enemy character image in the GUI.
	 * @param primaryStage the primary stage/ window of GUI
	 * @param transition The scene after the player clears the floor
	 * @param youWin The screen after the player wins the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param youWinMusic The music that plays when you win the game
	 * @param display The display elements of needed for battle phase
	 */
	private void mageTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int choice, GraphicsContext gc, Stage primaryStage, 
		Scene transition, Scene youWin, MediaPlayer battleMusic, MediaPlayer youWinMusic, BattlePhaseDisplay display) {

		//Move magic blast forward
		Timeline timeline = new Timeline(); 
		if (choice == 0) {
			timeline.setCycleCount(600);
		} else if (choice == 1) {
			timeline.setCycleCount(400);
		} else {
			timeline.setCycleCount(200);
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> moveMagic(hero, hero, gc, allEnemies, floor, true, choice));
		hero.setMagicx(290);
		hero.setMagicy(520);
		timeline.getKeyFrames().add(frame);
		
		//Play hit sound clip 
		Timeline sound = new Timeline();
		KeyFrame soundFrame = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
		sound.getKeyFrames().add(soundFrame);

		Timeline enemyRed = new Timeline();
		KeyFrame turnRed = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.stop();
			} else if (choice == 1) {
				animateTwo.stop();
			} else {
				animateThree.stop();
			}
			allEnemies.get(floor).get(choice).displayCharacter(gc, false, true, false);
		});
		enemyRed.getKeyFrames().add(turnRed);


		//Magic hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(100), ae -> {
			if (choice == 0) {
				animateOne.play();
			} else if (choice == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			hitEnemy(hero, allEnemies, choice, gc, primaryStage, floor, transition, youWin, dead, battleMusic, youWinMusic, display); //dialogue, dialogueTwo, dialogueThree, enemyStam,
		});

		hit.getKeyFrames().add(frameTwo);
		
		Timeline magicClear = new Timeline();
		KeyFrame clear = new KeyFrame(Duration.millis(1), ae ->  
			hero.displayMagicAtkImage(gc, true, hero.getMagicx(),hero.getMagicy()));
		magicClear.getKeyFrames().add(clear);
		
		
		SequentialTransition sequence = new SequentialTransition(timeline, sound, enemyRed, hit, magicClear);
		sequence.play();    	
	}
	
	/**
	 * This method is called when an hero hits an enemy. It is unique
	 * from the hitHero method due to different dialogue that appears.
	 * 
	 * @param hero Player controlled hero GameCharacters
	 * @param allEnemies The HashMap of enemies
	 * @param choice The player choice of which enemy to attack
	 * @param gc GraphicsContext to clear character after death
	 * @param primaryStage Primary stage/window t display GUI
	 * @param floor Current floor number the hero is on
	 * @param transition The scene after the player clears the floor
	 * @param youWin The screen after the player wins the game
	 * @param dead HashSet for dead enemies
	 * @param battleMusic  The music that plays during the battle phase
	 * @param youWinMusic The music that plays when you win the game
	 * @param display The display elements of needed for battle phase
	 */
	private void hitEnemy(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int choice, 
		GraphicsContext gc, Stage primaryStage, int floor, Scene transition, Scene youWin, 
			HashSet<Integer> dead, MediaPlayer battleMusic, MediaPlayer youWinMusic, BattlePhaseDisplay display) { 
		
		int rand = (int) (Math.random() * (2));
		
		GameCharacters enemy = allEnemies.get(floor).get(choice);
		int attackAmount = 0;
		int secondAttack = 0;
		if (isMagic() == true) {
			attackAmount = hero.magicAttack(enemy, hero.isEmpowered());
			setMagic(false);
			if (hero.getCurrentMana() < 0) {
				hero.setCurrentMana(0);
			} else {
				hero.setCurrentMana(hero.getCurrentMana() - 50);
			}
			if (hero.getCurrentMana() < 50) {
				display.getMagicAtkBtn().setDisable(true);
			}
			display.getHeroMana().setText("Mana: " + hero.getCurrentMana() + " / " + hero.getMana());
			display.resetInfoBar(1, display.getManaBar(), 200, hero);
		} else if (hero instanceof Rogue) {
			attackAmount = hero.attack(enemy, false, hero.isEmpowered())[0];
			if (rand == 0 && enemy.getCurrentStamina() != 0) {
				secondAttack = hero.attack(enemy, false, hero.isEmpowered())[0]; //attacking twice
			}	
		} else {
			attackAmount = hero.attack(enemy, false, hero.isEmpowered())[0];
		}
		
		if (choice == 0) {
		    display.resetInfoBar(0, display.getEnemyOneStamBar(), 200, enemy);
		} else if (choice == 1) {
		    display.resetInfoBar(0, display.getEnemyTwoStamBar(), 200, enemy);
		} else {
			display.resetInfoBar(0, display.getEnemyThreeStamBar(), 200, enemy);
		}

		totalEnemyHealth = totalEnemyHealth - attackAmount - secondAttack;
		hero.setIsEmpowered(false);
		enemy.displayCharacter(gc, false, true,false); //turn enemy red on attack	
		
		if (rand == 0 && secondAttack != 0) {
			display.getDialogue().setText("Slice and Dice Triggered!");
			display.getDialogueTwo().setText("You dealt " + attackAmount + " + " + secondAttack + " damage!");
		} else {
			display.getDialogue().setText("You dealt " + attackAmount + " damage!");
			display.getDialogueTwo().setText("");
		}
		display.getDialogueThree().setText("");
		

		//If enemy dies, update information and delete enemy picture
		if (enemy.getCurrentStamina() == 0) {
			// Add death sound effect 		
			se.enemyDeathSound();		
			dead.add(choice);
			if (rand == 0 && secondAttack!= 0) {
				display.getDialogueThree().setText("You have killed the enemy."); 
			} else {
				display.getDialogueTwo().setText("You have killed the enemy."); 
			}
			enemy.displayCharacter(gc, true, false, false); //deleting picture
			if (choice == 0) {
				animateOne.stop();
				display.getEnemyStam().setVisible(false);
				display.getEnemyName().setVisible(false);
				display.getEnemyOneFullStamBar().setVisible(false);
			} else if (choice == 1) {
				animateTwo.stop();
				display.getEnemyTwoStam().setVisible(false);
				display.getEnemyTwoName().setVisible(false);
				display.getEnemyTwoFullStamBar().setVisible(false);
			} else {
				animateThree.stop();
				display.getEnemyThreeStam().setVisible(false);
				display.getEnemyThreeName().setVisible(false);
				display.getEnemyThreeFullStamBar().setVisible(false);
			}
		} else {
			//After 0.1 seconds revert color only if not dead
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
			enemy.displayCharacter(gc, false, false, false));
			timeline.getKeyFrames().add(frame);
			timeline.play();
		}
		//If all enemies dead, move on to next floor
		if (totalEnemyHealth == 0) {
			display.getOutraged().setVisible(false);
			//Transition to next screen after battle after 5 seconds
			if (rand == 0 && secondAttack!= 0) {
				display.getDialogueThree().setText("You have killed all enemies."); 
			} else {
				display.getDialogueTwo().setText("You have killed all enemies."); 
			}
			int xp = 50 * allEnemies.get(floor).size() + floor * 10;
			hero.setXp(hero.getXp() + xp);
			if (hero.getXp() >= (25 + hero.getLevel() * 175)) {
				hero.levelUp();
				hero.setXp(0);
				
			}
			if (floor < 10) {
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(3000), ae ->  primaryStage.setScene(transition));
				moveOn.getKeyFrames().add(frame);
				moveOn.play();
			} else if (floor == 10){
			    	battleMusic.stop(); 
			    	youWinMusic.play();
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(3000), ae -> primaryStage.setScene(youWin));
				moveOn.getKeyFrames().add(frame);
				moveOn.play();
			}
		}

		if (choice == 0 ) {
		    	display.getEnemyStam().setText("Stamina: " + enemy.getCurrentStamina() + " / " + enemy.getStamina());
		} else if (choice == 1) {
		    	display.getEnemyTwoStam().setText("Stamina: " + enemy.getCurrentStamina() + " / " + enemy.getStamina());
		} else {
		    	display.getEnemyThreeStam().setText("Stamina: " + enemy.getCurrentStamina() + " / " + enemy.getStamina());
		}
	}

	/**
	 * This method allows us to move either the hero character or the enemies forward and
	 * backward for the animation of an attack. It will first clear the current picture
	 * off the canvas, move the X axis of image either forward or backward depending 
	 * on the boolean and repaint in the new location
	 * 
	 * @param character The character we are moving
	 * @param gc The GraphicsContext used to delete and repaint
	 * @param forward Whether we are moving forward or backward
	 * @param allEnemies The HashMap of enemies
	 * @param floor The current floor the hero is on
	 */
	private void move(GameCharacters character, GraphicsContext gc, boolean forward, 
			HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor) {

		//Clear current picture
		character.displayCharacter(gc, true, false, false);

		//Move character accordingly depending on boolean
		if (forward) {
			character.setX(character.getX() + 1);
		} else {
			character.setX(character.getX() - 1);
		}

		//Draw new picture
		character.displayCharacter(gc, false, false,false);

		if (allEnemies.get(floor).size() == 3) {
			if (character.getX() + character.getWidth() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}
			if (character.getX() + character.getWidth() >= allEnemies.get(floor).get(2).getX() && !dead.contains(2)) {
				allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false);
			}
		} else if (allEnemies.get(floor).size() == 2) {
			if (character.getX() + character.getWidth() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}				
		}
	}
	
	/**
	 * This method allows us to move the image of the magic attack towards the enemy
	 * 
	 * @param character The character using the magic attack
	 * @param hero The players chosen hero character
	 * @param gc The GraphicsContext used to delete and repaint
	 * @param allEnemies  the HashMap with all the enemies
	 * @param floor the floor number the hero is on
	 * @param isHero Boolean to know whether character using magic is a hero
	 * @param choice the enemy the hero has chosen to attack
	 */
	private void moveMagic(GameCharacters character, GameCharacters hero, GraphicsContext gc, 
			HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor, Boolean isHero, int choice) {

		//Clear current picture
		character.displayMagicAtkImage(gc, true, character.getMagicx(),character.getMagicy());

		//Move magic accordingly 
		if (isHero && character.getMagicx() < allEnemies.get(floor).get(choice).getX()) {
			character.setMagicx(character.getMagicx() + 1);
			character.displayMagicAtkImage(gc, false, character.getMagicx(),character.getMagicy());
		} else if (!isHero) {
			character.setMagicx(character.getMagicx() - 1);	
		}
		
		character.displayMagicAtkImage(gc, false, character.getMagicx(),character.getMagicy());


		if (allEnemies.get(floor).size() == 3) {
			if (character.getMagicx() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}
			if (character.getMagicx() >= allEnemies.get(floor).get(2).getX() && !dead.contains(2)) {
				allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false);
			}
		} else if (allEnemies.get(floor).size() == 2) {
			if (character.getMagicx() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}				
		}
	}

	/**
	 * This method creates display text for when it is the enemies turn to attack and updates necessary variables.
	 * 
	 * @param hero Players chosen hero character
	 * @param allEnemies The HashMap of all enemies 
	 * @param gc GraphicsContext to clear character after death
	 * @param floor Current floor number the hero is on
	 * @param reviveScene The scene giving the player the option to use a revive
	 * @param gameOverScreen The screen after the player loses the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 * @param display The display elements of needed for battle phase
	 */
	private void enemyTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GraphicsContext gc, int floor, 
		Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic, BattlePhaseDisplay display) {

		//If enemies are still alive
		if (totalEnemyHealth > 0) {
			display.getDialogue().setText("It is the enemy's turn.");
			display.getDialogueTwo().setText("");
			display.getDialogueThree().setText("");
			singleEnemyAttacks(hero, allEnemies, gc, reviveScene, gameOverScreen, battleMusic, gameOverMusic, display); 
		}
	}

	/**
	 * This method controls the attack timeline  for each enemies 
	 * 
	 * @param hero@param hero Players chosen hero character
	 * @param allEnemies The HashMap of all enemies 
	 * @param gc GraphicsContext to clear character after death
	 * @param reviveScene The scene giving the player the option to use a revive
	 * @param gameOverScreen The screen after the player loses the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 * @param display The display elements of needed for battle phase
	 */
	private void singleEnemyAttacks(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies,
			GraphicsContext gc, Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic, BattlePhaseDisplay display) {
		if (hero.getCurrentStamina() > 0) {
			//	final Integer innerI = new Integer(i);

			Timeline posOneForward = new Timeline();
			Timeline posOneHit = new Timeline();
			Timeline posOneBackward = new Timeline();
			Timeline posTwoForward = new Timeline();
			Timeline posTwoHit = new Timeline();
			Timeline posTwoBackward = new Timeline();
			Timeline posThreeForward = new Timeline();
			Timeline posThreeHit = new Timeline();
			Timeline posThreeBackward = new Timeline();
			Timeline posOneNoise = new Timeline();
			Timeline posTwoNoise = new Timeline();
			Timeline posThreeNoise = new Timeline();
			

			if (!dead.contains(0)) {
				enemyMoveTimeline(0, allEnemies, gc, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise, hero, reviveScene, 
						gameOverScreen, battleMusic, gameOverMusic, display); 
			}
			if (!dead.contains(1) && (allEnemies.get(floor).size() == 2 || allEnemies.get(floor).size() == 3)) {
				enemyMoveTimeline(1, allEnemies, gc, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise, hero, reviveScene, 
						gameOverScreen, battleMusic, gameOverMusic, display);
			}
			if (!dead.contains(2) && allEnemies.get(floor).size() == 3) {
				enemyMoveTimeline(2, allEnemies, gc, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise, hero, reviveScene, 
						gameOverScreen, battleMusic, gameOverMusic, display);
			}
			if (!dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { //AAA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward, posTwoForward, posTwoNoise, posTwoHit, posTwoBackward, 
						posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && dead.contains(1) && dead.contains(2)) { //DDA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward);
				sequence.play();
			} else if (dead.contains(0) && !dead.contains(1) && dead.contains(2)) { //DAD
				SequentialTransition sequence = new SequentialTransition(posTwoForward, posTwoNoise, posTwoHit, posTwoBackward);
				sequence.play();
			} else if (dead.contains(0) && dead.contains(1) && !dead.contains(2)) { //ADD
				SequentialTransition sequence = new SequentialTransition(posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && !dead.contains(1) && dead.contains(2)) { //DAA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward, posTwoForward, posTwoNoise, posTwoHit, posTwoBackward);
				sequence.play();
			} else if (dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { //AAD
				SequentialTransition sequence = new SequentialTransition(posTwoForward, posTwoNoise, posTwoHit, posTwoBackward, posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && dead.contains(1) && !dead.contains(2)) { //ADA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward, posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			}
		}
	}

	/**
	 * This method takes in the various timelines and adds the appropriate values to them depending on which enemy 
	 * is attacking. This will control how far each enemy should move according to its position.
	 *  
	 * @param position Integer position (0 is right most)
	 * @param allEnemies HashMap of all Enemies
	 * @param gc GraphicsContext to draw and redraw enemies
	 * @param posOneForward Timeline to move 0th enemy to hero
	 * @param posTwoForward Timeline to move 1st enemy to hero
	 * @param posThreeForward Timeline to move 2nd enemy to hero
	 * @param posOneBackward Timeline to move 0th enemy back
	 * @param posTwoBackward Timeline to move 1st enemy back
	 * @param posThreeBackward Timeline to move 2nd enemy back
	 * @param posOneHit Timeline to let 0th enemy hit hero
	 * @param posTwoHit Timeline to let 1st enemy hit hero
	 * @param posThreeHit Timeline to let 2nd enemy hit hero
	 * @param posOneNoise Timeline to play attack sound for 0th enemy 
	 * @param posTwoNoise Timeline to play attack sound for 1st enemy 
	 * @param posThreeNoise Timeline to play attack sound for 2nd enemy
	 * @param hero Player controlled hero GameCharacters
	 * @param reviveScene Scene to show revive option
	 * @param gameOverScreen Scene to show game over
	 * @param battleMusic Music that plays during battle phase
	 * @param gameOverMusic Music that plays when the game is over
	 * @param display The display elements of needed for battle phase
	 */
	private void enemyMoveTimeline(int position, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GraphicsContext gc,
			Timeline posOneForward, Timeline posTwoForward, Timeline posThreeForward, Timeline posOneBackward, Timeline posTwoBackward, 
			Timeline posThreeBackward, Timeline posOneHit, Timeline posTwoHit, Timeline posThreeHit, Timeline posOneNoise, Timeline posTwoNoise, Timeline posThreeNoise,
			GameCharacters hero, Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic, BattlePhaseDisplay display) {
		//Move enemy forward and backwards
		KeyFrame moveForward;
		KeyFrame moveBackward;
		
		KeyFrame heal = null;
		
		if ((allEnemies.get(floor).get(position) instanceof MeleeEnemy ||allEnemies.get(floor).get(position) instanceof BossEnemy)) {
			moveForward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), gc, false,
					allEnemies, floor));
			moveBackward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), gc, true,
					allEnemies, floor));
		} else if (allEnemies.get(floor).get(position) instanceof RangedEnemy){
			moveForward = new KeyFrame(Duration.millis(1), ae -> moveMagic(allEnemies.get(floor).get(position), 
					hero, gc, allEnemies, floor, false, 0));
			moveBackward = new KeyFrame(Duration.millis(1), ae -> {
				allEnemies.get(floor).get(position).displayMagicAtkImage(gc, true, allEnemies.get(floor).get(position).getMagicx(), 
						allEnemies.get(floor).get(position).getMagicy());
				allEnemies.get(floor).get(position).setMagicx(allEnemies.get(floor).get(position).getOldMagicx()); 
			});
		} else { //healer enemy
			int maxMissingHealth = 0;
			GameCharacters mostHurtEnemy = null;
			int enemyPosition = 0;
			for (int i = 0; i < allEnemies.get(floor).size(); i++) { //finding enemy with the most missing health
				int missingHealth = allEnemies.get(floor).get(i).getStamina() - allEnemies.get(floor).get(i).getCurrentStamina();
				if (missingHealth != allEnemies.get(floor).get(i).getStamina()) { //tests for not dead
					if (missingHealth > maxMissingHealth) {
						maxMissingHealth = missingHealth;
						mostHurtEnemy = allEnemies.get(floor).get(i);
						enemyPosition = i;
					}
				}
			}
			GameCharacters outerMostHurtEnemy = mostHurtEnemy;
			if (mostHurtEnemy != null) {
				healerTargetAvail = true;
				int outerPosition = enemyPosition;
				if (position == 0) {
					moveBackward = new KeyFrame(Duration.millis(744), ae -> {
						//do nothing
					});	
				} else if (position == 1) {
					moveBackward = new KeyFrame(Duration.millis(504), ae -> {
						//do nothing
					});	
				} else {
					moveBackward = new KeyFrame(Duration.millis(274), ae -> {
						//do nothing
					});	
				}
				heal = new KeyFrame(Duration.millis(1), ae -> {
					enemyHeal(outerMostHurtEnemy, outerPosition, allEnemies, position, gc, display);
				});	
				moveForward = new KeyFrame(Duration.millis(1), ae -> {
					//do nothing
				});	
			} else {
				healerTargetAvail = false;
				moveForward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), gc, false,
						allEnemies, floor));
				moveBackward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), gc, true,
						allEnemies, floor));
			}
		}
		
		KeyFrame soundFrame;
		KeyFrame soundFrameTwo;
		KeyFrame soundFrameThree;
		
		//Hit noises
		if (allEnemies.get(floor).size() > 0) {
			posOneNoise.setCycleCount(1);
			if (allEnemies.get(floor).get(0) instanceof RangedEnemy) {
				soundFrame = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
			} else if (allEnemies.get(floor).get(0) instanceof MeleeEnemy) {
				soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			} else {
				soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			}
			posOneNoise.getKeyFrames().add(soundFrame);
		}
			
		if (allEnemies.get(floor).size() > 1) {
			posTwoNoise.setCycleCount(1);
			if (allEnemies.get(floor).get(1) instanceof RangedEnemy) {
				soundFrameTwo = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
			} else if (allEnemies.get(floor).get(1) instanceof MeleeEnemy) {
				soundFrameTwo = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			} else {
				soundFrameTwo = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			}
			posTwoNoise.getKeyFrames().add(soundFrameTwo);
		}
		
		if (allEnemies.get(floor).size() > 2) {
			posThreeNoise.setCycleCount(1);
			if (allEnemies.get(floor).get(2) instanceof RangedEnemy) {
				soundFrameThree = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
			} else if (allEnemies.get(floor).get(2) instanceof MeleeEnemy) {
				soundFrameThree = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			} else {
				soundFrameThree = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			}
			posThreeNoise.getKeyFrames().add(soundFrameThree);
		}
		
		//Enemy hits hero	
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> {
			hitHero(hero, allEnemies, position, gc, reviveScene, gameOverScreen, battleMusic, gameOverMusic, false, display);		
		});
		
		//Boss Outrage hits hero	
		KeyFrame bossHit = new KeyFrame(Duration.millis(1), ae -> {
			hitHero(hero, allEnemies,position, gc, reviveScene, gameOverScreen, battleMusic, gameOverMusic, true, display);	
		});
		
		if (position == 0) { //boss can only be this position
			posOneForward.setCycleCount(745);
			posOneForward.getKeyFrames().add(moveForward);
			if (allEnemies.get(floor).get(position) instanceof HealerEnemy && (healerTargetAvail)) {
				posOneBackward.setCycleCount(1);
				posOneBackward.getKeyFrames().add(heal);
			} else { 
				posOneBackward.setCycleCount(745);
			}
			posOneBackward.getKeyFrames().add(moveBackward);
			if (allEnemies.get(floor).get(0) instanceof BossEnemy && 
					((double) allEnemies.get(floor).get(0).getCurrentStamina() / 
							(double) allEnemies.get(floor).get(0).getStamina() < 0.34)) {
				display.getOutraged().setVisible(true);
				posOneHit.getKeyFrames().add(bossHit);
			} else {
				posOneHit.getKeyFrames().add(frameTwo);
			}
		} else if (position == 1) {
			posTwoForward.setCycleCount(505);
			posTwoForward.getKeyFrames().add(moveForward);
			if ((allEnemies.get(floor).get(position) instanceof HealerEnemy  && (healerTargetAvail))) {
				posTwoBackward.setCycleCount(1);
				posTwoBackward.getKeyFrames().add(heal);
			} else {
				posTwoBackward.setCycleCount(505);
			}
			posTwoBackward.getKeyFrames().add(moveBackward);
			posTwoHit.getKeyFrames().add(frameTwo);
		} else { 
			posThreeForward.setCycleCount(275);
			posThreeForward.getKeyFrames().add(moveForward);
			if ((allEnemies.get(floor).get(position) instanceof HealerEnemy && (healerTargetAvail))) {
				posThreeBackward.setCycleCount(1);
				posThreeBackward.getKeyFrames().add(heal);
			} else {
				posThreeBackward.setCycleCount(275);
			}
			posThreeBackward.getKeyFrames().add(moveBackward);
			posThreeHit.getKeyFrames().add(frameTwo);
		}
	}
	
	/**
	 * This method is called when the enemy healer heals itself or its teammates
	 * @param outerMostHurtEnemy The GameCharacters enemy missing the most health
	 * @param outerPosition The int position of enemy missing most health
	 * @param allEnemies HashMap storing all enemies for every floor
	 * @param position The int position of the healer
	 * @param gc GraphicsContext used to draw images
	 * @param display The display elements of needed for battle phase
	 */
	private void enemyHeal(GameCharacters outerMostHurtEnemy, int outerPosition, 
			HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int position, GraphicsContext gc, BattlePhaseDisplay display) {
		
		int healAmt = allEnemies.get(floor).get(position).enemyHeal(outerMostHurtEnemy);
		totalEnemyHealth += healAmt;
		outerMostHurtEnemy.displayCharacter(gc, false, false, true);

		Timeline enemyWhite = new Timeline();
		enemyWhite.setCycleCount(1);
		KeyFrame whiteEnemy = new KeyFrame(Duration.millis(1), ae -> {
			if (outerPosition == 0) {
				animateOne.stop();
				outerMostHurtEnemy.displayCharacter(gc, false, false, true); //turn enemy white on heal
			} else if (outerPosition == 1) {
				animateTwo.stop();
				outerMostHurtEnemy.displayCharacter(gc, false, false, true); //turn enemy white on heal
			} else {
				animateThree.stop();
				outerMostHurtEnemy.displayCharacter(gc, false, false, true); //turn enemy white on heal
			}
		});
		enemyWhite.getKeyFrames().add(whiteEnemy);
		enemyWhite.play();
		
		//After 0.1 seconds revert color
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(1);
		KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> {
			if (outerPosition == 0) {
				animateOne.play();
			} else if (outerPosition == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			});
		timeline.getKeyFrames().add(frame);
		timeline.play();
		
		if (outerPosition == 0) {
			display.getEnemyStam().setText("Stamina: " + outerMostHurtEnemy.getCurrentStamina() + " / " + outerMostHurtEnemy.getStamina());
			display.resetInfoBar(0, display.getEnemyOneStamBar(), 200, outerMostHurtEnemy);
		} else if (outerPosition == 1) {
		    display.getEnemyTwoStam().setText("Stamina: " + outerMostHurtEnemy.getCurrentStamina() + " / " + outerMostHurtEnemy.getStamina());
			display.resetInfoBar(0, display.getEnemyTwoStamBar(), 200, outerMostHurtEnemy);
		} else {
		    display.getEnemyThreeStam().setText("Stamina: " + outerMostHurtEnemy.getCurrentStamina() + " / " + outerMostHurtEnemy.getStamina());
			display.resetInfoBar(0, display.getEnemyThreeStamBar(), 200, outerMostHurtEnemy);
		}
		display.getDialogueTwo().setText("Healer healed " + outerMostHurtEnemy.getType() + " for " + healAmt + " health!");
		display.getDialogueThree().setText("");
	}
	
	
	/**
	 * This method is called when an enemy hits the hero. It is unique
	 * from the hitEnemy method due to different dialogue that appears.
	 * 
	 * @param hero Player controlled hero GameCharacters
	 * @param allEnemies The HashMap of all enemies
	 * @param i The counter for which enemy attacks
	 * @param gc GraphicsContext to clear character after death
	 * @param reviveScene Scene to show revive option
	 * @param gameOverScreen Scene to show game over
	 * @param battleMusic Music that plays during battle phase
	 * @param gameOverMusic Music that plays when the game is over
	 * @param display The display elements of needed for battle phase
	 */
	private void hitHero(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int i, GraphicsContext gc, Scene reviveScene, 
			Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic, Boolean outrage, BattlePhaseDisplay display) { 
		int[] attacks = allEnemies.get(floor).get(i).attack(hero, outrage, false);
		if (!healerTargetAvail || !allEnemies.get(floor).get(i).getType().equals("Healer")) {
		Timeline heroRed = new Timeline();
		heroRed.setCycleCount(1);
		KeyFrame redHero = new KeyFrame(Duration.millis(1), ae -> {
		animateHero.stop();
		hero.displayCharacter(gc, false, true, false);}); //turn hero red on attack
		heroRed.getKeyFrames().add(redHero);
		heroRed.play();
		
		//After 0.1 seconds revert color
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(1); //hero.displayCharacter(gc, false, false,false));
		KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
		animateHero.play());
		timeline.getKeyFrames().add(frame);
		timeline.play();
		}
		display.getHeroStam().setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
		display.resetInfoBar(0, display.getStaminaBar(), 300, hero);
		if (attacks[0] <= 0) {
			display.getDialogueTwo().setText("You took 0 damage!"); // You took 0 damage!
		} else {
		    display.getDialogueTwo().setText(allEnemies.get(floor).get(i).getType() + " dealt " + attacks[0] + " damage to you!");
		}
		if (attacks[0] <= 0) {
			display.getDialogueThree().setText("The enemy's attack had no effect on you!");

		} else {
			if (hero.isDefending()) {
				display.getDialogueThree().setText("Your defense blocked " + attacks[1] + " damage!");
			}
			if (hero.getCurrentStamina() <= 0) {
				hero.displayCharacter(gc, true, false,false);
			}
		}

		// if hero gets killed 
		if (hero.getCurrentStamina() == 0) {
		    se.heroDeathSound();
			if (hero.isHasRevive() == true) {
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame1 = new KeyFrame(Duration.millis(4000), ae ->  primaryStage.setScene(reviveScene));
				moveOn.getKeyFrames().add(frame1);
				moveOn.play();
			} else {
			    	battleMusic.stop();
			    	gameOverMusic.play();
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame1 = new KeyFrame(Duration.millis(4000), ae ->  primaryStage.setScene(gameOverScreen));
				moveOn.getKeyFrames().add(frame1);
				moveOn.play();
			}
		}
	}
	/**
	 * This method enables the mage to restore his mana during battle. 
	 * 
	 * @param hero Player controlled hero GameCharacters
	 * @param display The display elements of needed for battle phase
	 */
    public void restoreMana(GameCharacters hero, BattlePhaseDisplay display) {
		if (hero.getType().equals("Mage")) {
				if (hero.getCurrentMana() + 25 > hero.getMana()) {
					hero.setCurrentMana(hero.getMana());
				} else {
					hero.setCurrentMana(hero.getCurrentMana() + 25);
				}
					display.getHeroMana().setText("Mana: " + hero.getCurrentMana() + " / " + hero.getMana());
					display.resetInfoBar(1, display.getManaBar(), 200, hero);					
				}				
    }


	/**
	 * @return the magic
	 */
	public boolean isMagic() {
		return magic;
	}


	/**
	 * @param magic the magic to set
	 */
	public void setMagic(boolean magic) {
		this.magic = magic;
	}	

}