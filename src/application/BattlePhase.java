package application;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This class represents the actions that occur during the battle phase. This class ensures that
 * proper information is displayed to the user during the course of the battle. It also gives the 
 * user the ability to actually fight the enemy via various effect linked buttons. Finally, this
 * class contains the logic behind battle animations, level up mechanics, and enemy decision making.
 * 
 * @author David Cai
 */
public class BattlePhase extends GameGUI{
	
	private Button attackBtn;
	private Button defendBtn;
	private Button healBtn;
	private HBox hbBtn;
	private Button chooseEnemyBtn;
	private Text dialogue;
	private Text dialogueTwo;
	private Text dialogueThree;
	private Text heroStam;
	private Text enemyStam;
	private Text heroName;
	private Text enemyName;
	private Shop shop;
	private Stage primaryStage;
	private Floor floor;

	public BattlePhase(Stage primaryStage, Shop shop , Floor floor) {
		this.primaryStage = primaryStage;
		this.shop = shop;
		this.floor = floor;
	}


	/**
	 * This method will display relevant combat information like  player/enemy names and health
	 * @param hero The character the player controls
	 * @param allEnemies The ArrayList of all enemies
	 */
	public void dispCombatInfo(GameCharacters hero, ArrayList<GameCharacters> allEnemies) {
		// To display current stamina of hero and enemy (using tester enemy[0]).
		heroName = new Text(hero.getType() + ": " + hero.getName());
		heroName.setStyle(" -fx-font: normal bold 30px 'serif' ");
		heroName.setFill(Color.DODGERBLUE);
		heroStam = new Text("Stamina: " + hero.getCurrentStamina());
		heroStam.setFill(Color.DODGERBLUE);
		heroStam.setStyle(" -fx-font: normal bold 30px 'serif' ");
		enemyName = new Text("Enemy Type: " + allEnemies.get(0).getType());
		enemyName.setStyle(" -fx-font: normal bold 30px 'serif' ");
		enemyName.setFill(Color.DARKRED);
		enemyStam = new Text("Stamina: " + allEnemies.get(0).getCurrentStamina());
		enemyStam.setStyle(" -fx-font: normal bold 30px 'serif' ");
		enemyStam.setFill(Color.DARKRED);
	}
	
	/**
	 * This method will properly create and format the three dialogue boxes used for combat information
	 */
	public void dispDialogue() {
		// To display dialogue and other relevant battle info
		dialogue = new Text("");
		dialogue.setStyle(" -fx-font: normal bold 30px 'serif' ");
		dialogue.setFill(Color.WHITE);
		dialogueTwo = new Text("");
		dialogueTwo.setStyle(" -fx-font: normal bold 30px 'serif' ");
		dialogueTwo.setFill(Color.WHITE);
		dialogueThree = new Text("");
		dialogueThree.setStyle(" -fx-font: normal bold 30px 'serif' ");
		dialogueThree.setFill(Color.WHITE);
	}
	
	/**
	 * This method will create the necessary action buttons during the battle phase
	 */
	public void initButtons() {

		// Creating buttons for player to fight enemies
		this.attackBtn = new Button("Attack");
		attackBtn.setStyle("sdfsdf");
		attackBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		this.defendBtn = new Button("Defend");
		defendBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		this.healBtn = new Button("Heal");
		healBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		this.hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().addAll(attackBtn, defendBtn, healBtn);

		// Button to choose enemy
		this.chooseEnemyBtn = new Button("Attack");
		chooseEnemyBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyBtn.setVisible(false);
	}
	

	/**
	 * This method attaches the proper events to button clicks. Namely it gives action
	 * to the attack, defend, heal, and choose enemy buttons.
	 * @param allEnemies The ArrayList of all enemies
	 * @param hero The character the player controls
	 * @param gc The GraphicsContext used to delete and draw pictures to canvas
	 */
	public void eventButtons(ArrayList<GameCharacters> allEnemies, GameCharacters hero, GraphicsContext gc, Shop shop) {
		//Event handling for when attack button is pressed
		attackBtn.setOnAction(event -> {
			disableButtons(true, attackBtn, healBtn, defendBtn);
			hero.setIsDefending(false);
			chooseEnemyBtn.setVisible(true);	
		});
		
		//Event handling for when defend button is pressed
		defendBtn.setOnAction(event -> {
			Image defendIcon = new Image("defendIcon.png", 80, 80, false, false);
			gc.drawImage(defendIcon, 100, 280); //draw defend icon
			disableButtons(true, attackBtn, healBtn, defendBtn); //disable buttons
			hero.setIsDefending(true);
			enemyTurn(hero, allEnemies, heroStam, dialogue, dialogueTwo, dialogueThree, gc);

			//Enable buttons after 1.5 secs per enemy
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1500 * allEnemies.size()), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn));
			timeline.getKeyFrames().add(frame);
			timeline.play();

			//Delete icon after 1.5 secs per enemy
			Timeline icon = new Timeline(); 
			icon.setCycleCount(1);
			KeyFrame iconDisable = new KeyFrame(Duration.millis(1500 * allEnemies.size()), ae -> 
			gc.clearRect(100, 280, 80, 80));
			icon.getKeyFrames().add(iconDisable);
			icon.play();
		});

		//Event handling for when heal button is pressed
		healBtn.setOnAction(event -> {

		});

		// Actions to take after button to choose enemy is chosen
		chooseEnemyBtn.setOnAction(event -> {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(hero, allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, 1, gc, primaryStage)); // hardcode first minion
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, heroStam, 
					dialogue, dialogueTwo, dialogueThree, gc));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 3 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(3000 * allEnemies.size()), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			chooseEnemyBtn.setVisible(false);
		});
	}

	/**
	 * This method properly formats the GridPane layout used to display most information
	 * like character name, character health, the three dialogue boxes, and various buttons
	 * @return The GridPane itself so it can be used in GameGUI.java
	 */
	public GridPane gridLayout() {
		// Adding all nodes to grid
		GridPane grid = new GridPane();
		
		//Placements for various textboxes and buttons
		grid.add(heroName, 0, 0);
		grid.add(heroStam, 0, 1);
		grid.add(dialogue, 1, 6);
		grid.add(dialogueTwo, 1, 7);
		grid.add(dialogueThree, 1, 8);
		grid.add(enemyName, 2, 0);
		grid.add(enemyStam, 2, 1);
		grid.add(chooseEnemyBtn, 2, 2);
		grid.add(hbBtn, 0, 2);
		
		//Set vertical and horizontal gap spacing
		grid.setVgap(15);
		grid.setHgap(20);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.TOP_CENTER);
		
		//Set location of grid
		grid.setLayoutX(80);
		grid.setLayoutY(60);
		grid.setMinSize(1100, 700);
		
		//Add specific size constraints to lock in formatting
		grid.getColumnConstraints().add(new ColumnConstraints(300));
		grid.getColumnConstraints().add(new ColumnConstraints(350));
		grid.getColumnConstraints().add(new ColumnConstraints(300));
		
		//Center all text within each grid panel
		GridPane.setHalignment(dialogue, HPos.CENTER);
		GridPane.setHalignment(dialogueTwo, HPos.CENTER);
		GridPane.setHalignment(dialogueThree, HPos.CENTER);
		GridPane.setHalignment(heroName, HPos.CENTER);
		GridPane.setHalignment(heroStam, HPos.CENTER);
		GridPane.setHalignment(enemyName, HPos.CENTER);
		GridPane.setHalignment(enemyStam, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyBtn, HPos.CENTER);
		GridPane.setHalignment(hbBtn, HPos.CENTER);
		
		//Make gridlines visible - only for development phase
		grid.setGridLinesVisible(true);
		
		return grid;
	}



	/**
	 * This method creates display text for when it is the heroes turn to attack and updates necessary variables.
	 * 
	 * @param allEnemies The arrayList of enemies the hero is currently fighting.
	 * @param enemyStam The current stamina of the enemy
	 * @param dialogue Text that updates the player on what is currently happening.
	 * @param choice  The enemy character the hero would like to attack (if there are multiple)
	 * @param gc The GraphicalContext needed to display/remove the enemy character image in the GUI.
	 */
	public void heroTurn(GameCharacters hero, ArrayList<GameCharacters> allEnemies, Text enemyStam, Text dialogue, 
			Text dialogueTwo, Text dialogueThree, int choice, GraphicsContext gc, Stage primaryStage) {

		//Move hero forward
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(741);
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true));
		timeline.getKeyFrames().add(frame);

		//Hero hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> hitEnemy(hero, allEnemies, choice, 
				dialogue, dialogueTwo, dialogueThree, enemyStam, gc, primaryStage));
		hit.getKeyFrames().add(frameTwo);

		//Move hero backward
		Timeline timelineTwo = new Timeline();
		timelineTwo.setCycleCount(741);
		KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, false));
		timelineTwo.getKeyFrames().add(frameThree);

		SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo);
		sequence.play();    	
	}

	/**
	 * This method is called when an hero hits an enemy. It is unique
	 * from the hitHero method due to different dialogue that appears.
	 * @param allEnemies The ArrayList of enemies on floor
	 * @param choice The player choice of which enemy to attack
	 * @param dialogue The first textbox used to update battle info
	 * @param dialogueTwo The second textbox used to update battle info
	 * @param dialogueThree The third textbox used to update battle info
	 * @param enemyStam The textbox used to display enemy health
	 * @param gc GraphicsContext to clear character after death
	 */
	public void hitEnemy(GameCharacters hero, ArrayList<GameCharacters> allEnemies, int choice, Text dialogue, Text dialogueTwo, Text dialogueThree,
			Text enemyStam, GraphicsContext gc, Stage primaryStage) {//Shop shop

		//Hero attacks enemy
		GameCharacters enemy = allEnemies.get(choice - 1);
		int attackAmount = hero.attack(enemy);
		enemy.displayCharacter(gc, false, true); //turn enemy red on attack

		//If enemy dies, update information and delete enemy picture
		if (enemy.getCurrentStamina() <= 0) {
			dialogueTwo.setText("You have killed the enemy."); 
			dialogueThree.setText("");//XP stuff and gold stuff will be here
			enemy.displayCharacter(gc, true, false); //deleting picture
			allEnemies.remove(choice - 1);
			
			//Transition to next screen after battle after 5 seconds
			if (this.floor.getFloor() < 10) {
			    Timeline moveOn = new Timeline();
			    moveOn.setCycleCount(1);
			    KeyFrame frame = new KeyFrame(Duration.millis(5000), ae -> transitionScreen(primaryStage));//shop
			    moveOn.getKeyFrames().add(frame);
			    moveOn.play();
			} else if (this.floor.getFloor() == 10){
			    Timeline moveOn = new Timeline();
			    moveOn.setCycleCount(1);
			    KeyFrame frame = new KeyFrame(Duration.millis(5000), ae -> youWinScreen(primaryStage));
			    moveOn.getKeyFrames().add(frame);
			    moveOn.play();
			}
		}

		//After 0.1 seconds revert color only if not dead
		if (allEnemies.contains(enemy)) {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
			enemy.displayCharacter(gc, false, false));
			timeline.getKeyFrames().add(frame);
			timeline.play();
		}

		enemyStam.setText("Stamina: " + enemy.getCurrentStamina());
		dialogue.setText("You dealt " + attackAmount + " damage!");
		//dialogueTwo.setText("");
		dialogueThree.setText("");
	}

	/**
	 * This method allows us to move either the hero character or the enemies forward and
	 * backward for the animation of an attack. It will first clear the current picture
	 * off the canvas, move the X axis of image either forward or backward depending 
	 * on the boolean and repaint in the new location
	 * @param character The character we are moving
	 * @param gc The GraphicsContext used to delete and repaint
	 * @param forward Whether we are moving forward or backward
	 */
	public void move(GameCharacters character, GraphicsContext gc, boolean forward) {

		//Clear current picture
		character.displayCharacter(gc, true, false);

		//Move character accordingly depending on boolean
		if (forward) {
			character.setX(character.getX() + 1);
		} else {
			character.setX(character.getX() - 1);
		}

		//Draw new picture
		character.displayCharacter(gc, false, false);
	}

	/**
	 * This method creates display text for when it is the enemies turn to attack and updates necessary variables.
	 * 
	 * @param allEnemies The arrayList of enemies the hero is currently fighting.
	 * @param heroStam The current stamina of the hero
	 * @param dialogue Text that updates the player on what is currently happening.
	 */
	public void enemyTurn(GameCharacters hero, ArrayList<GameCharacters> allEnemies, Text heroStam, Text dialogue, Text dialogueTwo, 
			Text dialogueThree, GraphicsContext gc) {

		//If enemies are still alive
		if (allEnemies.size() > 0) {
			if (hero.isDefending()) {
				dialogue.setText("It is the enemy's turn.");
				dialogueTwo.setText("");
				dialogueThree.setText("");
			} else {
				dialogueTwo.setText("It is the enemy's turn.");
			}

			//Loop through all enemies so they all attack
			for (int i = 0; i < allEnemies.size(); i++) {
				if (hero.getCurrentStamina() > 0) {
					final Integer innerI = new Integer(i);

					//Move enemy forward
					Timeline timeline = new Timeline(); 
					timeline.setCycleCount(745);
					KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(innerI), gc, false));
					timeline.getKeyFrames().add(frame);

					//Enemy hits hero
					Timeline hit = new Timeline(); 	
					KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> 
					hitHero(hero, allEnemies, dialogueTwo, dialogueThree, heroStam, innerI, gc));
					hit.getKeyFrames().add(frameTwo);

					//Move enemy backward
					Timeline timelineTwo = new Timeline();
					timelineTwo.setCycleCount(745);
					KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(innerI), gc, true));
					timelineTwo.getKeyFrames().add(frameThree);

					SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo);
					sequence.play();
				} else if (hero.getCurrentStamina() < 0){
				    gameOverScreen(primaryStage);
				}
				
			}
		}
	}

	/**
	 * This method is called when an enemy hits the hero. It is unique
	 * from the hitEnemy method due to different dialogue that appears.
	 * @param dialogueTwo The second textbox used to update battle info
	 * @param dialogueThree The third textbox used to update battle info
	 * @param heroStam The textbox used to display hero health
	 * @param i The counter for which enemy attacks
	 * @param gc GraphicsContext to clear character after death
	 */
	public void hitHero(GameCharacters hero, ArrayList<GameCharacters> allEnemies, 
			Text dialogueTwo, Text dialogueThree, Text heroStam, int i, GraphicsContext gc) {
		int attackAmount = allEnemies.get(i).attack(hero);
		hero.displayCharacter(gc, false, true); //turn hero red on attack

		//After 0.1 seconds revert color
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(1);
		KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
		hero.displayCharacter(gc, false, false));
		timeline.getKeyFrames().add(frame);
		timeline.play();

		heroStam.setText("Stamina: " + hero.getCurrentStamina());
		if (hero.isDefending()) {
			dialogueTwo.setText("You took " + attackAmount + " damage!");
		} else {
			dialogueThree.setText("You took " + attackAmount + " damage!");
		}
		if (attackAmount <= 0) {
			dialogueThree.setText("The enemy's attack had no effect on you!");

		} else {
			if (hero.isDefending()) {
				dialogueThree.setText("Your defense blocked " + attackAmount + " damage!");
			}
			if (hero.getCurrentStamina() <= 0) {
				hero.displayCharacter(gc, true, false);
			}
		}
	}

	/** This method disables/enables all user input buttons
	 * @param disable If true, disable all buttons. Enable otherwise.
	 * @param attackBtn Button to attack
	 * @param healBtn Button to use item
	 * @param defendBtn Button to defend
	 */
	public void disableButtons(boolean disable, Button attackBtn, Button healBtn, Button defendBtn) {
		attackBtn.setDisable(disable);
		healBtn.setDisable(disable);
		defendBtn.setDisable(disable);
	}
}
