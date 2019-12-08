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
 * This class represents the actions that occur during the battle phase. This
 * class ensures that proper information is displayed to the user during the
 * course of the battle. It also gives the user the ability to actually fight
 * the enemy via various effect linked buttons. Finally, this class contains the
 * logic behind battle animations, level up mechanics, and enemy decision
 * making.
 * 
 * @author David Cai, Shari Sinclair and Jiayu Zhu
 */
public class BattlePhase {
	private Stage primaryStage;
	private int floor;
	private HashSet<Integer> dead = new HashSet<Integer>();
	private HashMap<Integer, ArrayList<GameCharacters>> allEnemies = new HashMap<Integer, ArrayList<GameCharacters>>();
	private int totalEnemyHealth;
	private GameCharacters hero;
	private GraphicsContext gc;
	private Timeline animateOne;
	private Timeline animateTwo;
	private Timeline animateThree;
	private Timeline animateHero;
	private boolean magic;
	private SoundEffect se;
	private boolean healerTargetAvail;
	private Scene transition;
	private Scene youWin;
	private Scene reviveScene;
	private Scene gameOverScreen;
	private MediaPlayer battleMusic;
	private MediaPlayer gameOverMusic;
	private MediaPlayer youWinMusic;
	private BattlePhaseDisplay display;
	private int defendCount;

	public BattlePhase(Stage primaryStage, int floor, int totalEnemyHealth,
			HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GameCharacters hero, GraphicsContext gc,
			Scene transition, Scene youWin, Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic,
			MediaPlayer gameOverMusic, MediaPlayer youWinMusic, BattlePhaseDisplay display) {
		this.primaryStage = primaryStage;
		this.floor = floor;
		this.allEnemies = allEnemies;
		this.totalEnemyHealth = totalEnemyHealth;
		this.hero = hero;
		this.se = new SoundEffect();
		this.gc = gc;
		this.transition = transition;
		this.youWin = youWin;
		this.reviveScene = reviveScene;
		this.gameOverScreen = gameOverScreen;
		this.battleMusic = battleMusic;
		this.gameOverMusic = gameOverMusic;
		this.youWinMusic = youWinMusic;
		this.display = display;
		setMagic(false);
		defendCount = 0;

	}

	/**
	 * This method shows enemy attack buttons based on which enemies are still alive
	 */
	private void showEnemyBtns() {
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

	/**
	 * This method attaches the proper events to button clicks. Namely it gives
	 * action to the attack, defend, heal, and choose enemy buttons.
	 */
	public void eventButtons() {
		// Event handling for when attack button is pressed
		display.getAttackBtn().setOnAction(event -> {
			if (totalEnemyHealth != 0) {
				display.getItemBag().setVisible(false);
				display.getError().setVisible(false);
				setMagic(false);
				display.getDefendText().setVisible(false);
				restoreMana(); // Restore mana for mage
				display.disableButtons(true);
				hero.setIsDefending(false);
				showEnemyBtns();
			}
		});

		display.getMagicAtkBtn().setOnAction(event -> {
			if (totalEnemyHealth != 0 && hero.getCurrentMana() >= 50) {
				display.getItemBag().setVisible(false);
				display.getError().setVisible(false);
				setMagic(true);
				display.disableButtons(true);
				hero.setIsDefending(false);
				showEnemyBtns();
			} else {
				se.errorSound();
				display.getMagicAtkBtn().setDisable(true);
			}
		});

		// Event handling for when defend button is pressed
		display.getDefendBtn().setOnAction(event -> {
			if (totalEnemyHealth != 0) {
				display.getItemBag().setVisible(false);
				display.getError().setVisible(false);
				setMagic(false);

				// Increments defend count for warrior stacked attacked advantage
				defendCount++;
				if (defendCount == 2 && hero.getType().equals("Warrior")) {
					display.getDefendText().setVisible(true);
					display.getDefendText().setText("Next attack x 1.75");
				} else if (defendCount == 3 && hero.getType().equals("Warrior")) {
					display.getDefendText().setVisible(true);
					display.getDefendText().setText("Next attack x 2");
				}
				restoreMana(); // Restore mana for mage
				Image defendIcon = new Image("defendIcon.png", 80, 80, false, false);
				gc.drawImage(defendIcon, 140, 280); // draw defend icon
				display.disableButtons(true); // disable buttons
				hero.setIsDefending(true);
				hero.setIsEmpowered(true);
				display.getEmpowered().setVisible(true);
				enemyTurn();

				// Enable buttons after 1.5 secs per enemy
				Timeline timeline = new Timeline();
				timeline.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())),
						ae -> display.disableButtons(false));
				timeline.getKeyFrames().add(frame);
				timeline.play();

				// Delete icon after 1.5 secs per enemy
				Timeline icon = new Timeline();
				icon.setCycleCount(1);
				KeyFrame iconDisable = new KeyFrame(
						Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())),
						ae -> gc.clearRect(140, 280, 80, 80));
				icon.getKeyFrames().add(iconDisable);
				icon.play();
			}
		});

		// Event handling for when heal button is pressed
		display.healFunctionDisplay(hero);
		display.getHealBtn().setOnAction(event -> {
			if (totalEnemyHealth != 0) {
				display.getItemBag().setVisible(true);
				Potion cp = hero.getCp();
				Potion hp = hero.getHp();
				display.getPotionBtn().setOnAction(event1 -> {
					healHero(cp);
				});
				display.getHyperPotionBtn().setOnAction(event2 -> {
					healHero(hp);
				});
			}
		});

		// Actions to take after button to choose enemy is chosen
		display.getChooseEnemyBtn().setOnAction(event -> {
			chooseEnemyBtnEvent(0);
		});

		// Actions to take after button to choose enemy is chosen
		display.getChooseEnemyTwoBtn().setOnAction(event -> {
			chooseEnemyBtnEvent(1);
		});

		// Actions to take after button to choose enemy is chosen
		display.getChooseEnemyThreeBtn().setOnAction(event -> {
			chooseEnemyBtnEvent(2);
		});
	}

	/**
	 * This method heals the hero and updates item based on potion btn chosen
	 * 
	 * @param p The type of potion used by the hero
	 */
	private void healHero(Potion p) {
		hero.usePotion(p, display.getError());
		display.getHeroStam().setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
		display.resetInfoBar(0, display.getStaminaBar(), 300, hero);

		if (p == hero.getCp()) {
			display.getPotionBtn().setText(hero.itemInfo(p));
		} else if (p == hero.getHp()) {
			display.getHyperPotionBtn().setText(hero.itemInfo(p));
		}

		if (display.getError().isVisible() == false) {
			Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> {
				animateHero.stop();
				hero.displayCharacter(gc, false, false, true);
			});
			timeline.getKeyFrames().addAll(frame);
			Timeline timeline2 = new Timeline();
			timeline2.setCycleCount(1);
			KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> animateHero.play());
			timeline2.getKeyFrames().addAll(frame2);
			SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
			sequence.play();
		}
	}

	/**
	 * This method allows us to animate the enemy characters image
	 * 
	 * @param num     The enemy index in the allEnemies array we to animate
	 * @param animate The Time line we will use to animate the enemy
	 */
	private void enemyAnimate(int num, Timeline animate) {
		animate.setCycleCount(Timeline.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(5),
				ae -> allEnemies.get(floor).get(num).displayCharacter(gc, false, false, false));
		KeyFrame frameTwo = new KeyFrame(Duration.millis(5),
				ae -> allEnemies.get(floor).get(num).displayCharacter(gc, true, false, false));
		KeyFrame frameThree = new KeyFrame(Duration.millis(5),
				ae -> allEnemies.get(floor).get(num).displayCharacter(gc, false, false, false));
		animate.getKeyFrames().add(frame);
		animate.getKeyFrames().add(frameTwo);
		animate.getKeyFrames().add(frameThree);
		animate.play();
	}

	/**
	 * This method allows us the animate multiple enemy characters image
	 */
	public void idleAnimate() {
		if (allEnemies.get(floor).size() > 0) {
			animateOne = new Timeline();
			enemyAnimate(0, animateOne);
		}
		if (allEnemies.get(floor).size() > 1) {
			animateTwo = new Timeline();
			enemyAnimate(1, animateTwo);
		}
		if (allEnemies.get(floor).size() > 2) {
			;
			animateThree = new Timeline();
			enemyAnimate(2, animateThree);
		}
	}

	/**
	 * This animates the hero image in the GUI
	 */
	public void heroAnimate() {
		animateHero = new Timeline();
		animateHero.setCycleCount(Timeline.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> // paint picture
		hero.displayCharacter(gc, false, false, false));
		KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> // delete picture
		hero.displayCharacter(gc, true, false, false));
		KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> // paint picture again
		hero.displayCharacter(gc, false, false, false));
		animateHero.getKeyFrames().add(frame);
		animateHero.getKeyFrames().add(frameTwo);
		animateHero.getKeyFrames().add(frameThree);
		animateHero.play();
	}

	/**
	 * This method allows for a turn of battle one hero and one enemy turn
	 * 
	 * @param frames The Key frame for the corresponding hero attack type
	 */
	private void attackOrder(KeyFrame frames) {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		KeyFrame frame = frames;
		timeline.getKeyFrames().add(frame);
		Timeline timelineTwo = new Timeline();
		timelineTwo.setCycleCount(1);
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn()); // enemy turn takes 1.4s
		timelineTwo.getKeyFrames().add(frameTwo);
		SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
		sequence.play();

		// Enable buttons after 2 seconds per enemy
		Timeline enable = new Timeline();
		enable.setCycleCount(1);
		KeyFrame frameEnable = new KeyFrame(Duration.millis(1400 * (allEnemies.get(floor).size() - dead.size())),
				ae -> display.disableButtons(false));
		enable.getKeyFrames().add(frameEnable);
		enable.play();

		// Disable all choose enemy buttons
		display.getChooseEnemyBtn().setVisible(false);
		display.getChooseEnemyTwoBtn().setVisible(false);
		display.getChooseEnemyThreeBtn().setVisible(false);
	}

	/**
	 * This method covers the events that occur after pressing a chooseEnemyBtn.
	 * 
	 * @param enemy The index of the enemy to be attacked
	 */
	private void chooseEnemyBtnEvent(int enemy) {
		display.getEmpowered().setVisible(false);
		KeyFrame frameMagic = new KeyFrame(Duration.millis(1), ae -> mageTurn(enemy));
		KeyFrame frameAttack = new KeyFrame(Duration.millis(1), ae -> heroTurn(enemy));
		if (isMagic() == true) {
			attackOrder(frameMagic);
		} else {
			attackOrder(frameAttack);
		}
	}

	/**
	 * This method creates display text for when it is the heroes turn to attack and
	 * updates necessary variables.
	 * 
	 * @param choice The enemy character the hero would like to attack (if there are
	 *               multiple)
	 */
	private void heroTurn(int choice) {

		// Move hero forward
		Timeline timeline = new Timeline();
		if (choice == 0) {
			timeline.setCycleCount(580); // hit rightmost enemy
		} else if (choice == 1) {
			timeline.setCycleCount(380); // hit middle enemy
		} else {
			timeline.setCycleCount(180); // hit leftmost enemy
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(hero, true));
		timeline.getKeyFrames().add(frame);

		// Play hit sound clip (needs to play before hero arrives)
		Timeline sound = new Timeline();
		KeyFrame soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
		sound.getKeyFrames().add(soundFrame);

		// Add slash animation upon attack
		Timeline slash = new Timeline();
		slash.setCycleCount(10);
		KeyFrame slashFrame = new KeyFrame(Duration.millis(1), ae -> hero.displaySlashImage(gc, false,
				allEnemies.get(floor).get(choice).getX(), allEnemies.get(floor).get(choice).getY()));
		slash.getKeyFrames().add(slashFrame);

		// Turn enemy red when hurt
		Timeline enemyRed = new Timeline();
		KeyFrame turnRed = new KeyFrame(Duration.millis(1), ae -> { // stopping animation so red is not overwritten
			if (choice == 0) {
				animateOne.stop();
			} else if (choice == 1) {
				animateTwo.stop();
			} else {
				animateThree.stop();
			}
			allEnemies.get(floor).get(choice).displayCharacter(gc, false, true, false); // paint red picture
		});
		enemyRed.getKeyFrames().add(turnRed);

		// Finish moving forward
		Timeline finishMove = new Timeline();
		finishMove.setCycleCount(130);
		KeyFrame finishFrame = new KeyFrame(Duration.millis(1), ae -> move(hero, true));
		finishMove.getKeyFrames().add(finishFrame);

		// Hero hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.play();
			} else if (choice == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			hitEnemy(choice);
		});

		hit.getKeyFrames().add(frameTwo);

		// Move hero backward
		Timeline timelineTwo = new Timeline();
		if (choice == 0) {
			timelineTwo.setCycleCount(710);
		} else if (choice == 1) {
			timelineTwo.setCycleCount(510);
		} else {
			timelineTwo.setCycleCount(310);
		}
		KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(hero, false));
		timelineTwo.getKeyFrames().add(frameThree);

		SequentialTransition sequence = new SequentialTransition(timeline, sound, slash, enemyRed, finishMove, hit,
				timelineTwo);
		sequence.play();
	}

	/**
	 * This method creates animation for when a mage a magic attack on their turn
	 * and updates necessary variables.
	 * 
	 * @param choice The enemy character the hero would like to attack (if there are
	 *               multiple)
	 */
	private void mageTurn(int choice) {

		// Move magic blast forward
		Timeline timeline = new Timeline();
		if (choice == 0) {
			timeline.setCycleCount(600);
		} else if (choice == 1) {
			timeline.setCycleCount(400);
		} else {
			timeline.setCycleCount(200);
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> moveMagic(hero, true, choice));
		hero.setMagicx(290);
		hero.setMagicy(520);
		timeline.getKeyFrames().add(frame);

		// Play hit sound clip
		Timeline sound = new Timeline();
		KeyFrame soundFrame = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
		sound.getKeyFrames().add(soundFrame);

		// Turn enemy red on hit
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

		// Magic hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(100), ae -> {
			if (choice == 0) {
				animateOne.play();
			} else if (choice == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			hitEnemy(choice);
		});

		hit.getKeyFrames().add(frameTwo);

		Timeline magicClear = new Timeline();
		KeyFrame clear = new KeyFrame(Duration.millis(1),
				ae -> hero.displayMagicAtkImage(gc, true, hero.getMagicx(), hero.getMagicy()));
		magicClear.getKeyFrames().add(clear);

		SequentialTransition sequence = new SequentialTransition(timeline, sound, enemyRed, hit, magicClear);
		sequence.play();
	}

	/**
	 * This method is called when an hero hits an enemy. It is unique from the
	 * hitHero method due to different dialogue that appears.
	 * 
	 * @param choice The player choice of which enemy to attack
	 */
	private void hitEnemy(int choice) {
		int rand = (int) (Math.random() * (2)); // for Rogue's double attack ability
		GameCharacters enemy = allEnemies.get(floor).get(choice);
		int attackAmount = 0;
		int secondAttack = 0; // for Rogue only
		if (isMagic() == true) { // if using mage magic attack
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
		} else if (hero instanceof Rogue) { // calculating rogue attack and rogue special attack
			attackAmount = hero.attack(enemy, false, hero.isEmpowered(), defendCount)[0];
			if (rand == 0 && enemy.getCurrentStamina() != 0) {
				secondAttack = hero.attack(enemy, false, hero.isEmpowered(), defendCount)[0]; // attacking twice
			}
		} else {
			attackAmount = hero.attack(enemy, false, hero.isEmpowered(), defendCount)[0];
		}

		if (choice == 0) { // reseting info bars for enemies after attack
			display.resetInfoBar(0, display.getEnemyOneStamBar(), 200, enemy);
		} else if (choice == 1) {
			display.resetInfoBar(0, display.getEnemyTwoStamBar(), 200, enemy);
		} else {
			display.resetInfoBar(0, display.getEnemyThreeStamBar(), 200, enemy);
		}
		defendCount = 0; // keeps track for warrior progressive defending
		totalEnemyHealth = totalEnemyHealth - attackAmount - secondAttack;
		hero.setIsEmpowered(false);
		enemy.displayCharacter(gc, false, true, false); // turn enemy red on attack

		if (rand == 0 && secondAttack != 0) { // Rogue's ability triggered
			display.getDialogue().setText("Slice and Dice Triggered!");
			display.getDialogueTwo().setText("You dealt " + attackAmount + " + " + secondAttack + " damage!");
		} else {
			display.getDialogue().setText("You dealt " + attackAmount + " damage!");
			display.getDialogueTwo().setText("");
		}
		display.getDialogueThree().setText("");

		// If enemy dies, update information and delete enemy picture
		if (enemy.getCurrentStamina() == 0) {
			// Add death sound effect
			se.enemyDeathSound();
			dead.add(choice);
			if (rand == 0 && secondAttack != 0) {
				display.getDialogueThree().setText("You have killed the enemy.");
			} else {
				display.getDialogueTwo().setText("You have killed the enemy.");
			}
			enemy.displayCharacter(gc, true, false, false); // deleting picture

			// Stopping appropriate animations and deleting appropriate bars upon death
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

			// After 0.1 seconds revert color only if not dead
			Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> enemy.displayCharacter(gc, false, false, false));
			timeline.getKeyFrames().add(frame);
			timeline.play();
		}

		// If all enemies dead, move on to next floor
		if (totalEnemyHealth == 0) {
			display.getOutraged().setVisible(false);

			// Transition to next screen after battle after 5 seconds
			if (rand == 0 && secondAttack != 0) {
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
			if (floor < 10) { // advance floors if not boss floor
				moveOn(transition);
			} else if (floor == 10) { // otherwise finish game
				battleMusic.stop();
				youWinMusic.play();
				moveOn(youWin);
			}
		}
		if (choice == 0) {
			display.getEnemyStam().setText("Stamina: " + enemy.getCurrentStamina() + " / " + enemy.getStamina());
		} else if (choice == 1) {
			display.getEnemyTwoStam().setText("Stamina: " + enemy.getCurrentStamina() + " / " + enemy.getStamina());
		} else {
			display.getEnemyThreeStam().setText("Stamina: " + enemy.getCurrentStamina() + " / " + enemy.getStamina());
		}
	}

	/**
	 * This method creates a time line to transition to a new scene
	 * 
	 * @param s The scene to transition to
	 */
	private void moveOn(Scene s) {
		Timeline moveOn = new Timeline();
		moveOn.setCycleCount(1);
		KeyFrame frame = new KeyFrame(Duration.millis(1500), ae -> primaryStage.setScene(s));
		moveOn.getKeyFrames().add(frame);
		moveOn.play();
	}

	/**
	 * This method allows us to move either the hero character or the enemies
	 * forward and backward for the animation of an attack. It will first clear the
	 * current picture off the canvas, move the X axis of image either forward or
	 * backward depending on the boolean and repaint in the new location
	 * 
	 * @param character The character we are moving
	 * @param forward   Whether we are moving forward or backward
	 */
	private void move(GameCharacters character, boolean forward) {

		// Clear current picture
		character.displayCharacter(gc, true, false, false);

		// Move character accordingly depending on boolean
		if (forward) {
			character.setX(character.getX() + 1);
		} else {
			character.setX(character.getX() - 1);
		}

		// Draw new picture
		character.displayCharacter(gc, false, false, false);

		// Draws proper enemy pictures to prevent overlap during animations
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
	 * @param isHero    Boolean to know whether character using magic is a hero
	 * @param choice    the enemy the hero has chosen to attack
	 */
	private void moveMagic(GameCharacters character, Boolean isHero, int choice) {

		// Clear current picture
		character.displayMagicAtkImage(gc, true, character.getMagicx(), character.getMagicy());

		// Move magic accordingly
		if (isHero && character.getMagicx() < allEnemies.get(floor).get(choice).getX()) {
			character.setMagicx(character.getMagicx() + 1);
			character.displayMagicAtkImage(gc, false, character.getMagicx(), character.getMagicy());
		} else if (!isHero) {
			character.setMagicx(character.getMagicx() - 1);
		}

		character.displayMagicAtkImage(gc, false, character.getMagicx(), character.getMagicy());

		// Draws proper pictures to prevent overlap as fireball moves over enemies
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
	 * This method creates display text for when it is the enemies turn to attack
	 * and updates necessary variables.
	 */
	private void enemyTurn() {

		// If enemies are still alive
		if (totalEnemyHealth > 0 && hero.getCurrentStamina() > 0) {
			display.getDialogue().setText("It is the enemy's turn.");
			display.getDialogueTwo().setText("");
			display.getDialogueThree().setText("");
			singleEnemyAttacks();
		}
	}

	/**
	 * This method controls the attack timeline for each enemies
	 */
	private void singleEnemyAttacks() {

		// For rightmost enemy
		Timeline posOneForward = new Timeline();
		Timeline posOneHit = new Timeline();
		Timeline posOneBackward = new Timeline();

		// For middle enemy
		Timeline posTwoForward = new Timeline();
		Timeline posTwoHit = new Timeline();
		Timeline posTwoBackward = new Timeline();

		// For leftmost enemy
		Timeline posThreeForward = new Timeline();
		Timeline posThreeHit = new Timeline();
		Timeline posThreeBackward = new Timeline();

		// Hit noises
		Timeline posOneNoise = new Timeline();
		Timeline posTwoNoise = new Timeline();
		Timeline posThreeNoise = new Timeline();

		if (hero.getCurrentStamina() > 0) {

			// Calculates the necessary movement for each enemy depending on position
			if (!dead.contains(0)) {
				enemyMoveTimeline(0, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise);
			}
			if (!dead.contains(1) && (allEnemies.get(floor).size() == 2 || allEnemies.get(floor).size() == 3)) {
				enemyMoveTimeline(1, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise);
			}
			if (!dead.contains(2) && allEnemies.get(floor).size() == 3) {
				enemyMoveTimeline(2, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise);
			}

			// Determines what sequence of movements and attacks and sounds is appropriate
			// depending on who is alive/dead

			// AAA (alive, alive, alive going from left to right)
			if (!dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { 
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit,
						posOneBackward, posTwoForward, posTwoNoise, posTwoHit, posTwoBackward, posThreeForward,
						posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && dead.contains(1) && dead.contains(2)) { // DDA (dead, dead, alive)
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit,
						posOneBackward);
				sequence.play();
			} else if (dead.contains(0) && !dead.contains(1) && dead.contains(2)) { // DAD
				SequentialTransition sequence = new SequentialTransition(posTwoForward, posTwoNoise, posTwoHit,
						posTwoBackward);
				sequence.play();
			} else if (dead.contains(0) && dead.contains(1) && !dead.contains(2)) { // ADD
				SequentialTransition sequence = new SequentialTransition(posThreeForward, posThreeNoise, posThreeHit,
						posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && !dead.contains(1) && dead.contains(2)) { // DAA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit,
						posOneBackward, posTwoForward, posTwoNoise, posTwoHit, posTwoBackward);
				sequence.play();
			} else if (dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { // AAD
				SequentialTransition sequence = new SequentialTransition(posTwoForward, posTwoNoise, posTwoHit,
						posTwoBackward, posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && dead.contains(1) && !dead.contains(2)) { // ADA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit,
						posOneBackward, posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			}
		}
	}

	/**
	 * This method takes in the various timelines and adds the appropriate values to
	 * them depending on which enemy is attacking. This will control how far each
	 * enemy should move according to its position.
	 * 
	 * @param position         Integer position (0 is right most)
	 * @param posOneForward    Timeline to move 0th enemy to hero
	 * @param posTwoForward    Timeline to move 1st enemy to hero
	 * @param posThreeForward  Timeline to move 2nd enemy to hero
	 * @param posOneBackward   Timeline to move 0th enemy back
	 * @param posTwoBackward   Timeline to move 1st enemy back
	 * @param posThreeBackward Timeline to move 2nd enemy back
	 * @param posOneHit        Timeline to let 0th enemy hit hero
	 * @param posTwoHit        Timeline to let 1st enemy hit hero
	 * @param posThreeHit      Timeline to let 2nd enemy hit hero
	 * @param posOneNoise      Timeline to play attack sound for 0th enemy
	 * @param posTwoNoise      Timeline to play attack sound for 1st enemy
	 * @param posThreeNoise    Timeline to play attack sound for 2nd enemy
	 */
	private void enemyMoveTimeline(int position, Timeline posOneForward, Timeline posTwoForward,
			Timeline posThreeForward, Timeline posOneBackward, Timeline posTwoBackward, Timeline posThreeBackward,
			Timeline posOneHit, Timeline posTwoHit, Timeline posThreeHit, Timeline posOneNoise, Timeline posTwoNoise,
			Timeline posThreeNoise) {

		// Move enemy forward and backwards
		KeyFrame moveForward;
		KeyFrame moveBackward;

		KeyFrame heal = null;

		// If MeleeEnemy or BossEnemy, we have them move forward and backward as normal
		if ((allEnemies.get(floor).get(position) instanceof MeleeEnemy
				|| allEnemies.get(floor).get(position) instanceof BossEnemy)) {
			moveForward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), false));
			moveBackward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), true));

			// RangedEnemy doesn't move so they have a unique moveForward and moveBackward
			// keyframe
		} else if (allEnemies.get(floor).get(position) instanceof RangedEnemy) {
			moveForward = new KeyFrame(Duration.millis(1),
					ae -> moveMagic(allEnemies.get(floor).get(position), false, 0));
			moveBackward = new KeyFrame(Duration.millis(1), ae -> {
				allEnemies.get(floor).get(position).displayMagicAtkImage(gc, true,
						allEnemies.get(floor).get(position).getMagicx(),
						allEnemies.get(floor).get(position).getMagicy());
				allEnemies.get(floor).get(position).setMagicx(allEnemies.get(floor).get(position).getOldMagicx());
			});

			// HealerEnemy also does not move if it can heal but will move if no valid
			// healing targets
		} else {
			int maxMissingHealth = 0;
			GameCharacters mostHurtEnemy = null;
			int enemyPosition = 0;
			for (int i = 0; i < allEnemies.get(floor).size(); i++) { // finding enemy with the most missing health
				int missingHealth = allEnemies.get(floor).get(i).getStamina()
						- allEnemies.get(floor).get(i).getCurrentStamina();
				if (missingHealth != allEnemies.get(floor).get(i).getStamina()) { // tests for not dead
					if (missingHealth > maxMissingHealth) {
						maxMissingHealth = missingHealth;
						mostHurtEnemy = allEnemies.get(floor).get(i);
						enemyPosition = i;
					}
				}
			}
			GameCharacters outerMostHurtEnemy = mostHurtEnemy;

			// Healing activated, no movement necessary
			if (mostHurtEnemy != null) {
				healerTargetAvail = true;
				int outerPosition = enemyPosition;
				if (position == 0) {
					moveBackward = new KeyFrame(Duration.millis(744), ae -> {
						// do nothing
					});
				} else if (position == 1) {
					moveBackward = new KeyFrame(Duration.millis(504), ae -> {
						// do nothing
					});
				} else {
					moveBackward = new KeyFrame(Duration.millis(274), ae -> {
						// do nothing
					});
				}
				heal = new KeyFrame(Duration.millis(1), ae -> {
					enemyHeal(outerMostHurtEnemy, outerPosition, position);
				});
				moveForward = new KeyFrame(Duration.millis(1), ae -> {
					// do nothing
				});

				// All enemies at full health, healer will attack by moving
			} else {
				healerTargetAvail = false;
				moveForward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), false));
				moveBackward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), true));
			}
		}

		KeyFrame soundFrame;
		KeyFrame soundFrameTwo;
		KeyFrame soundFrameThree;

		// Hit noises
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

		// Enemy hits hero
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> {
			hitHero(position, false);
		});

		// Boss Outrage hits hero
		KeyFrame bossHit = new KeyFrame(Duration.millis(1), ae -> {
			hitHero(position, true);
		});

		// Right most enemy, needs to move the most
		if (position == 0) { // boss can only be this position
			posOneForward.setCycleCount(745);
			posOneForward.getKeyFrames().add(moveForward);

			// Healer with valid heal does not need to move
			if (allEnemies.get(floor).get(position) instanceof HealerEnemy && (healerTargetAvail)) {
				posOneBackward.setCycleCount(1);
				posOneBackward.getKeyFrames().add(heal);
			} else {
				posOneBackward.setCycleCount(745);
			}
			posOneBackward.getKeyFrames().add(moveBackward);

			// Boss is outraged, add appropriate dialogues
			if (allEnemies.get(floor).get(0) instanceof BossEnemy
					&& ((double) allEnemies.get(floor).get(0).getCurrentStamina()
							/ (double) allEnemies.get(floor).get(0).getStamina() < 0.34)) {
				display.getOutraged().setVisible(true);
				posOneHit.getKeyFrames().add(bossHit);
			} else {
				posOneHit.getKeyFrames().add(frameTwo);
			}

			// Center enemy
		} else if (position == 1) {
			posTwoForward.setCycleCount(505);
			posTwoForward.getKeyFrames().add(moveForward);

			// Healer with valid heal does not need to move
			if ((allEnemies.get(floor).get(position) instanceof HealerEnemy && (healerTargetAvail))) {
				posTwoBackward.setCycleCount(1);
				posTwoBackward.getKeyFrames().add(heal);
			} else {
				posTwoBackward.setCycleCount(505);
			}
			posTwoBackward.getKeyFrames().add(moveBackward);
			posTwoHit.getKeyFrames().add(frameTwo);

			// Leftmost enemy
		} else {
			posThreeForward.setCycleCount(275);
			posThreeForward.getKeyFrames().add(moveForward);

			// Healer with valid heal does not need to move
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
	 * This method is called when the enemy healer heals itself or its team mates
	 * 
	 * @param outerMostHurtEnemy The GameCharacters enemy missing the most health
	 * @param outerPosition      The int position of enemy missing most health
	 * @param position           The int position of the healer
	 */
	private void enemyHeal(GameCharacters outerMostHurtEnemy, int outerPosition, int position) {

		int healAmt = allEnemies.get(floor).get(position).enemyHeal(outerMostHurtEnemy);
		totalEnemyHealth += healAmt;
		outerMostHurtEnemy.displayCharacter(gc, false, false, true);

		// Turn enemy white upon heal
		Timeline enemyWhite = new Timeline();
		enemyWhite.setCycleCount(1);
		KeyFrame whiteEnemy = new KeyFrame(Duration.millis(1), ae -> {
			if (outerPosition == 0) {
				animateOne.stop();
				outerMostHurtEnemy.displayCharacter(gc, false, false, true); // turn rightmost enemy white on heal
			} else if (outerPosition == 1) {
				animateTwo.stop();
				outerMostHurtEnemy.displayCharacter(gc, false, false, true); // turn center enemy white on heal
			} else {
				animateThree.stop();
				outerMostHurtEnemy.displayCharacter(gc, false, false, true); // turn leftmostenemy white on heal
			}
		});
		enemyWhite.getKeyFrames().add(whiteEnemy);
		enemyWhite.play();

		// After 0.1 seconds revert color
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

		// Update all info bars
		if (outerPosition == 0) {
			display.getEnemyStam().setText(
					"Stamina: " + outerMostHurtEnemy.getCurrentStamina() + " / " + outerMostHurtEnemy.getStamina());
			display.resetInfoBar(0, display.getEnemyOneStamBar(), 200, outerMostHurtEnemy);
		} else if (outerPosition == 1) {
			display.getEnemyTwoStam().setText(
					"Stamina: " + outerMostHurtEnemy.getCurrentStamina() + " / " + outerMostHurtEnemy.getStamina());
			display.resetInfoBar(0, display.getEnemyTwoStamBar(), 200, outerMostHurtEnemy);
		} else {
			display.getEnemyThreeStam().setText(
					"Stamina: " + outerMostHurtEnemy.getCurrentStamina() + " / " + outerMostHurtEnemy.getStamina());
			display.resetInfoBar(0, display.getEnemyThreeStamBar(), 200, outerMostHurtEnemy);
		}
		display.getDialogueTwo()
				.setText("Healer healed " + outerMostHurtEnemy.getType() + " for " + healAmt + " health!");
		display.getDialogueThree().setText("");
	}

	/**
	 * This method is called when an enemy hits the hero. It is unique from the
	 * hitEnemy method due to different dialogue that appears.
	 * 
	 * @param i The counter for which enemy attacks
	 */
	private void hitHero(int i, Boolean outrage) {
		int[] attacks = allEnemies.get(floor).get(i).attack(hero, outrage, false, defendCount);

		// Triggers when a heal is not available or no healer enemies
		if (!healerTargetAvail || !allEnemies.get(floor).get(i).getType().equals("Healer")) {
			Timeline heroRed = new Timeline(); // turning hero red timeline
			heroRed.setCycleCount(1);
			KeyFrame redHero = new KeyFrame(Duration.millis(1), ae -> {
				animateHero.stop();
				hero.displayCharacter(gc, false, true, false);
			}); // turn hero red on attack
			heroRed.getKeyFrames().add(redHero);
			heroRed.play();

			// After 0.1 seconds revert color
			Timeline timeline = new Timeline();
			timeline.setCycleCount(1); // hero.displayCharacter(gc, false, false,false));
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> animateHero.play());
			timeline.getKeyFrames().add(frame);
			timeline.play();
		}

		// Updating appropriate dialogues
		display.getHeroStam().setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
		display.resetInfoBar(0, display.getStaminaBar(), 300, hero);
		if (attacks[0] <= 0) {
			display.getDialogueTwo().setText("You took 0 damage!"); // You took 0 damage!
		} else {
			display.getDialogueTwo()
					.setText(allEnemies.get(floor).get(i).getType() + " dealt " + attacks[0] + " damage to you!");
		}
		if (attacks[0] <= 0) {
			display.getDialogueThree().setText("The enemy's attack had no effect on you!");
		} else {
			if (hero.isDefending()) {
				display.getDialogueThree().setText("Your defense blocked " + attacks[1] + " damage!");
			}
			if (hero.getCurrentStamina() <= 0) {
				hero.displayCharacter(gc, true, false, false);
			}
		}

		// if hero gets killed
		if (hero.getCurrentStamina() == 0) {
			se.heroDeathSound();
			if (hero.isHasRevive() == true) {
				moveOn(reviveScene);
			} else {
				battleMusic.stop();
				gameOverMusic.play();
				moveOn(gameOverScreen);
			}
			
		}
	}

	/**
	 * This method enables the mage to restore his mana during battle.
	 * 
	 * @param hero Player controlled hero GameCharacters
	 */
	public void restoreMana() {
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