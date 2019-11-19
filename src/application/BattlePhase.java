package application;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents the actions that occur during the battle phase. This class ensures that
 * proper information is displayed to the user during the course of the battle. It also gives the 
 * user the ability to actually fight the enemy via various effect linked buttons. Finally, this
 * class contains the logic behind battle animations, level up mechanics, and enemy decision making.
 * 
 * @author David Cai
 */
public class BattlePhase {
	
	private Button attackBtn;
	private Button defendBtn;
	private Button healBtn;
	private VBox itemBag;
	private HBox hbBtn;
	private Button chooseEnemyBtn;
	private Button chooseEnemyTwoBtn;
	private Button chooseEnemyThreeBtn;
	private Text error;
	private Text dialogue;
	private Text dialogueTwo;
	private Text dialogueThree;
	private Text heroStam;
	private Text enemyStam;
	private Text enemyTwoStam;
	private Text enemyThreeStam;
	private Text heroName;
	private Text enemyName;
	private Text enemyTwoName;
	private Text enemyThreeName;
	private Stage primaryStage;
	private int floor;
	private HashSet<Integer> dead = new HashSet<Integer>();
	private int totalEnemyHealth;
	
	public BattlePhase(Stage primaryStage, int floor, int totalEnemyHealth) {
		this.primaryStage = primaryStage;
		this.floor = floor;
		this.totalEnemyHealth = totalEnemyHealth;
	}


	/**
	 * This method will display relevant combat information like  player/enemy names and health
	 * @param hero The character the player controls
	 * @param allEnemies The ArrayList of all enemies
	 */
	public void dispCombatInfo(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor) {
		// To display current stamina of hero and enemy (using tester enemy[0]).
		heroName = new Text(hero.getType() + ": " + hero.getName());
		heroName.setStyle(" -fx-font: normal bold 24px 'serif' ");
		heroName.setFill(Color.DODGERBLUE);
		heroStam = new Text("Stamina: " + hero.getCurrentStamina());
		heroStam.setFill(Color.DODGERBLUE);
		heroStam.setStyle(" -fx-font: normal bold 24px 'serif' ");
		enemyName = new Text("Enemy Type: " + allEnemies.get(floor).get(0).getType());
		enemyName.setStyle(" -fx-font: normal bold 24px 'serif' ");
		enemyName.setFill(Color.DARKRED);
		enemyStam = new Text("Stamina: " + allEnemies.get(floor).get(0).getCurrentStamina());
		enemyStam.setStyle(" -fx-font: normal bold 24px 'serif' ");
		enemyStam.setFill(Color.DARKRED);
		
		if (allEnemies.get(floor).size() > 1) {
			enemyTwoName = new Text("Enemy Type: " + allEnemies.get(floor).get(1).getType());
			enemyTwoName.setStyle(" -fx-font: normal bold 24px 'serif' ");
			enemyTwoName.setFill(Color.DARKRED);
			enemyTwoStam = new Text("Stamina: " + allEnemies.get(floor).get(1).getCurrentStamina());
			enemyTwoStam.setStyle(" -fx-font: normal bold 24px 'serif' ");
			enemyTwoStam.setFill(Color.DARKRED);
		}
		if (allEnemies.get(floor).size() > 2) {
			enemyThreeName = new Text("Enemy Type: " + allEnemies.get(floor).get(2).getType());
			enemyThreeName.setStyle(" -fx-font: normal bold 24px 'serif' ");
			enemyThreeName.setFill(Color.DARKRED);
			enemyThreeStam = new Text("Stamina: " + allEnemies.get(floor).get(2).getCurrentStamina());
			enemyThreeStam.setStyle(" -fx-font: normal bold 24px 'serif' ");
			enemyThreeStam.setFill(Color.DARKRED);
		}
	}
	
	/**
	 * This method will properly create and format the three dialogue boxes used for combat information
	 */
	public void dispDialogue() {
		// To display dialogue and other relevant battle info
		dialogue = new Text("");
		dialogue.setStyle(" -fx-font: normal bold 24px 'serif' ");
		dialogue.setFill(Color.WHITE);
		dialogueTwo = new Text("");
		dialogueTwo.setStyle(" -fx-font: normal bold 24px 'serif' ");
		dialogueTwo.setFill(Color.WHITE);
		dialogueThree = new Text("");
		dialogueThree.setStyle(" -fx-font: normal bold 24px 'serif' ");
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
		this.chooseEnemyTwoBtn = new Button("Attack");
		chooseEnemyTwoBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyTwoBtn.setVisible(false);
		this.chooseEnemyThreeBtn = new Button("Attack");
		chooseEnemyThreeBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyThreeBtn.setVisible(false);
	}
	
	
	/**
	 * This method handles the heal function in the battle phase. 
	 * 
	 * @param hero
	 */
	public void healFunction(GameCharacters hero, GraphicsContext gc) {
		this.itemBag = new VBox();		
		itemBag.setMaxWidth(200);
				
		// Error message 
		this.error = new Text("you can't see me");
		this.error.setStyle(" -fx-font: normal bold 18px 'serif';  ");
		this.error.setVisible(false);
		this.error.setFill(Color.WHITE);
		
		// cheap potion button
		String btnInfo1 = hero.itemInfo(hero.getCp());	
		Button potionBtn = new Button(btnInfo1);
		potionBtn.setStyle(" -fx-font: normal bold 18px 'serif' ");

		potionBtn.setMaxWidth(200);
		potionBtn.setOnAction(event -> {
			hero.usePotion(hero.getCp(), this.error);
			heroStam.setText("Stamina: " + hero.getCurrentStamina());
			potionBtn.setText(hero.itemInfo(hero.getCp()));
			if (this.error.isVisible() == false) {
			    Timeline timeline = new Timeline(); 
			    timeline.setCycleCount(1);
			    KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
			    hero.displayCharacter(gc, false, false,true));
			    timeline.getKeyFrames().addAll(frame);
			    Timeline timeline2 = new Timeline(); 
			    timeline2.setCycleCount(1);
			    KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> 
			    hero.displayCharacter(gc, false, false,false));
			    timeline2.getKeyFrames().addAll(frame2);
			    SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
			    sequence.play(); 
			}
			
		});
		
		// hyper potion button 
		String btnInfo2 = hero.itemInfo(hero.getHp());		
		Button hyperPotionBtn = new Button(btnInfo2);
		hyperPotionBtn.setStyle(" -fx-font: normal bold 18px 'serif' ");
		hyperPotionBtn.setMaxWidth(200);
		hyperPotionBtn.setOnAction(event -> {
			hero.usePotion(hero.getHp(), this.error);
			hyperPotionBtn.setText(hero.itemInfo(hero.getHp()));
			heroStam.setText("Stamina: " + hero.getCurrentStamina());
			if (this.error.isVisible() == false) {
			    Timeline timeline = new Timeline(); 
			    timeline.setCycleCount(1);
			    KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
			    hero.displayCharacter(gc, false, false,true));
			    timeline.getKeyFrames().addAll(frame);
			    Timeline timeline2 = new Timeline(); 
			    timeline2.setCycleCount(1);
			    KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> 
			    hero.displayCharacter(gc, false, false,false));
			    timeline2.getKeyFrames().addAll(frame2);
			    SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
			    sequence.play(); 
			}
		});
		
		// revive button 
//		String btnInfo3 = "Revive:\t";
//		if (hero.isHasRevive() == true) {
//			btnInfo3 += "1.0";
//		} else {
//			btnInfo3 += "0.0";
//		}
//		Button reviveBtn = new Button(btnInfo3);	
//		reviveBtn.setStyle(" -fx-font: normal bold 18px 'serif' ");
//		reviveBtn.setMaxWidth(200);
//		reviveBtn.setDisable(true);
		
		// set background
		itemBag.setStyle("-fx-background-color: gainsboro");
		itemBag.getChildren().addAll(potionBtn, hyperPotionBtn);//reviveBtn
		itemBag.setVisible(false);
		
	}

	/**
	 * This method attaches the proper events to button clicks. Namely it gives action
	 * to the attack, defend, heal, and choose enemy buttons.
	 * @param allEnemies The ArrayList of all enemies
	 * @param hero The character the player controls
	 * @param gc The GraphicsContext used to delete and draw pictures to canvas
	 */
	public void eventButtons(HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GameCharacters hero, 
			GraphicsContext gc, Scene transition, Scene youWin, Scene reviveScene, Scene gameOverScreen) {
		//Event handling for when attack button is pressed
		attackBtn.setOnAction(event -> {
			itemBag.setVisible(false);
			error.setVisible(false);
			
			disableButtons(true, attackBtn, healBtn, defendBtn);
			hero.setIsDefending(false);
			if (dead.contains(1) && dead.contains(2)) {
				chooseEnemyBtn.setVisible(true);
			} else if (dead.contains(0) && dead.contains(2)) {
				chooseEnemyTwoBtn.setVisible(true);
			} else if (dead.contains(0) && dead.contains(1)) {
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(0)) {
				chooseEnemyTwoBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(1)) {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(2)) {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyTwoBtn.setVisible(true);
			} else {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyTwoBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			}
			
		});
		
		//Event handling for when defend button is pressed
		defendBtn.setOnAction(event -> {
			itemBag.setVisible(false);
			error.setVisible(false);
			
			Image defendIcon = new Image("defendIcon.png", 80, 80, false, false);
			gc.drawImage(defendIcon, 100, 280); //draw defend icon
			disableButtons(true, attackBtn, healBtn, defendBtn); //disable buttons
			hero.setIsDefending(true);
			enemyTurn(hero, allEnemies, heroStam, dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen);
			//Enable buttons after 1.5 secs per enemy
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1500 * allEnemies.get(floor).size()), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn));
			timeline.getKeyFrames().add(frame);
			timeline.play();

			//Delete icon after 1.5 secs per enemy
			Timeline icon = new Timeline(); 
			icon.setCycleCount(1);
			KeyFrame iconDisable = new KeyFrame(Duration.millis(1500 * allEnemies.get(floor).size()), ae -> 
			gc.clearRect(100, 280, 80, 80));
			icon.getKeyFrames().add(iconDisable);
			icon.play();
		});

		//Event handling for when heal button is pressed
		this.healFunction(hero, gc);
		healBtn.setOnAction(event -> {
			this.itemBag.setVisible(true);			
		});

		// Actions to take after button to choose enemy is chosen
		chooseEnemyBtn.setOnAction(event -> {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(hero, allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, 0, gc, primaryStage, transition, youWin)); // hardcode first minion
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, heroStam, 
					dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 3 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(2000 * allEnemies.get(floor).size()), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			chooseEnemyBtn.setVisible(false);
			chooseEnemyTwoBtn.setVisible(false);
			chooseEnemyThreeBtn.setVisible(false);
		});
		
		// Actions to take after button to choose enemy is chosen
		chooseEnemyTwoBtn.setOnAction(event -> {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(hero, allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, 1, gc, primaryStage, transition, youWin)); // hardcode second minion
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, heroStam, 
					dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 3 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(2000 * allEnemies.get(floor).size()), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			chooseEnemyBtn.setVisible(false);
			chooseEnemyTwoBtn.setVisible(false);
			chooseEnemyThreeBtn.setVisible(false);
		});
		
		// Actions to take after button to choose enemy is chosen
		chooseEnemyThreeBtn.setOnAction(event -> {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(hero, allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, 2, gc, primaryStage, transition, youWin)); // hardcode second minion
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, heroStam, 
					dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 3 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(2000 * allEnemies.get(floor).size()), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			chooseEnemyBtn.setVisible(false);
			chooseEnemyTwoBtn.setVisible(false);
			chooseEnemyThreeBtn.setVisible(false);
		});
	}

	/**
	 * This method properly formats the GridPane layout used to display most information
	 * like character name, character health, the three dialogue boxes, and various buttons
	 * @return The GridPane itself so it can be used in GameGUI.java
	 */
	public GridPane gridLayout(int enemyCount) {
		// Adding all nodes to grid
		GridPane grid = new GridPane();
		
		//Placements for various textboxes and buttons
		grid.add(heroName, 0, 0);
		grid.add(heroStam, 0, 1);
		grid.add(error, 1, 1);
		grid.add(itemBag, 1, 2);
		if (enemyCount == 1) {
			grid.add(dialogue, 1, 5);
			grid.add(dialogueTwo, 1, 6);
			grid.add(dialogueThree, 1, 7);
			grid.add(enemyName, 2, 0);
			grid.add(enemyStam, 2, 1);
			grid.add(chooseEnemyBtn, 2, 2);
		} else if (enemyCount == 2) {
			grid.add(dialogue, 1, 5);
			grid.add(dialogueTwo, 1, 6);
			grid.add(dialogueThree, 1, 7);
			grid.add(enemyName, 3, 0);
			grid.add(enemyStam, 3, 1);
			grid.add(chooseEnemyBtn, 3, 2);
			grid.add(enemyTwoName, 2, 0);
			grid.add(enemyTwoStam, 2, 1);
			grid.add(chooseEnemyTwoBtn, 2, 2);
		} else {
			grid.add(dialogue, 2, 3);
			grid.add(dialogueTwo, 2, 4);
			grid.add(dialogueThree, 2, 5);
			grid.add(enemyName, 4, 0);
			grid.add(enemyStam, 4, 1);
			grid.add(chooseEnemyBtn, 4, 2);
			grid.add(enemyTwoName, 3, 0);
			grid.add(enemyTwoStam, 3, 1);
			grid.add(chooseEnemyTwoBtn, 3, 2);
			grid.add(enemyThreeName, 2, 0);
			grid.add(enemyThreeStam, 2, 1);
			grid.add(chooseEnemyThreeBtn, 2, 2);
		}
		grid.add(hbBtn, 0, 2);
		
		//Set vertical and horizontal gap spacing
		grid.setVgap(10);
		grid.setHgap(20);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.TOP_CENTER);
		
		//Set location of grid
		grid.setLayoutX(20);
		grid.setLayoutY(60);
		grid.setMinSize(1200, 700);
		
		//Add specific size constraints to lock in formatting
		if (enemyCount == 1) {
			grid.getColumnConstraints().add(new ColumnConstraints(300));
			grid.getColumnConstraints().add(new ColumnConstraints(550));
			grid.getColumnConstraints().add(new ColumnConstraints(300));
		} else if (enemyCount == 2) {
			grid.getColumnConstraints().add(new ColumnConstraints(300));
			grid.getColumnConstraints().add(new ColumnConstraints(350));
			grid.getColumnConstraints().add(new ColumnConstraints(200));
			grid.getColumnConstraints().add(new ColumnConstraints(200));
		} else {
			grid.getColumnConstraints().add(new ColumnConstraints(300));
			grid.getColumnConstraints().add(new ColumnConstraints(150));
			grid.getColumnConstraints().add(new ColumnConstraints(200));
			grid.getColumnConstraints().add(new ColumnConstraints(200));
			grid.getColumnConstraints().add(new ColumnConstraints(200));
		}
		
		//Center all text within each grid panel
		GridPane.setHalignment(dialogue, HPos.CENTER);
		GridPane.setHalignment(dialogueTwo, HPos.CENTER);
		GridPane.setHalignment(dialogueThree, HPos.CENTER);
		GridPane.setHalignment(heroName, HPos.CENTER);
		GridPane.setHalignment(heroStam, HPos.CENTER);
		GridPane.setHalignment(enemyName, HPos.CENTER);
		GridPane.setHalignment(enemyStam, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyBtn, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyTwoBtn, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyThreeBtn, HPos.CENTER);
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
	public void heroTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, Text enemyStam, Text dialogue, 
			Text dialogueTwo, Text dialogueThree, int choice, GraphicsContext gc, Stage primaryStage, Scene transition, Scene youWin) {

		//Move hero forward
		Timeline timeline = new Timeline(); 
		if (choice == 0) {
			timeline.setCycleCount(741);
		} else if (choice == 1) {
			timeline.setCycleCount(459);
		} else {
			timeline.setCycleCount(200);
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true, allEnemies, floor));
		timeline.getKeyFrames().add(frame);

		//Hero hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> hitEnemy(hero, allEnemies, choice, 
				dialogue, dialogueTwo, dialogueThree, enemyStam, gc, primaryStage, floor, transition, youWin, dead));
		hit.getKeyFrames().add(frameTwo);

		//Move hero backward
		Timeline timelineTwo = new Timeline();
		if (choice == 0) {
			timelineTwo.setCycleCount(741);
		} else if (choice == 1) {
			timelineTwo.setCycleCount(459);
		} else {
			timelineTwo.setCycleCount(200);
		}
		KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, false, allEnemies, floor));
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
	public void hitEnemy(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int choice, Text dialogue, Text dialogueTwo, Text dialogueThree,
			Text enemyStam, GraphicsContext gc, Stage primaryStage, int floor, Scene transition, Scene youWin, HashSet<Integer> dead) {
		
		GameCharacters enemy = allEnemies.get(floor).get(choice);
		int attackAmount = hero.attack(enemy);
		enemy.displayCharacter(gc, false, true,false); //turn enemy red on attack

		//If enemy dies, update information and delete enemy picture
		if (enemy.getCurrentStamina() <= 0) {
			dead.add(choice);
			dialogueTwo.setText("You have killed the enemy."); 
			dialogueThree.setText("");//XP stuff and gold stuff will be here
			enemy.displayCharacter(gc, true, false, false); //deleting picture
		//	allEnemies.get(floor).remove(choice);	
		} else {
			//After 0.1 seconds revert color only if not dead
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
			enemy.displayCharacter(gc, false, false, false));
			timeline.getKeyFrames().add(frame);
			timeline.play();
		}
		System.out.println(totalEnemyHealth);
		//If all enemies dead, move on to next floor
		if (totalEnemyHealth == 0) {
			//Transition to next screen after battle after 5 seconds
			if (floor < 10) {
			    Timeline moveOn = new Timeline();
			    moveOn.setCycleCount(1);
			    KeyFrame frame = new KeyFrame(Duration.millis(3000), ae ->  primaryStage.setScene(transition));
			    moveOn.getKeyFrames().add(frame);
			    moveOn.play();
			} else if (floor == 10){
			    Timeline moveOn = new Timeline();
			    moveOn.setCycleCount(1);
			    KeyFrame frame = new KeyFrame(Duration.millis(3000), ae -> primaryStage.setScene(youWin));
			    moveOn.getKeyFrames().add(frame);
			    moveOn.play();
			}
		}
		
		if (choice == 0 ) {
			enemyStam.setText("Stamina: " + enemy.getCurrentStamina());
		} else if (choice == 1) {
			enemyTwoStam.setText("Stamina: " + enemy.getCurrentStamina());
		} else {
			enemyThreeStam.setText("Stamina: " + enemy.getCurrentStamina());
		}
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
	public void move(GameCharacters character, GraphicsContext gc, boolean forward, 
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
		
		//Draw in overlapped enemies
//		if (allEnemies.get(floor).size() > 1 && character == allEnemies.get(floor).get(0)) {
//			allEnemies.get(floor).get(1).displayCharacter(gc, false, false);
//		} else if (allEnemies.get(floor).size() > 1 && character == allEnemies.get(floor).get(1)) {
//			allEnemies.get(floor).get(0).displayCharacter(gc, false, false);
//		} else if (allEnemies.get(floor).size() > 1 && character.getX() + character.getWidth() >= 
//				allEnemies.get(floor).get(1).getX()){
//			allEnemies.get(floor).get(1).displayCharacter(gc, false, false);
//		} else if (allEnemies.get(floor).size() == 1 && dead.contains(0)) {
//			if (character.getX() + character.getWidth() >=  allEnemies.get(floor).get(0).getX()) {
//				allEnemies.get(floor).get(0).displayCharacter(gc, false, false);
//			}
//		}
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
	 * This method creates display text for when it is the enemies turn to attack and updates necessary variables.
	 * 
	 * @param allEnemies The arrayList of enemies the hero is currently fighting.
	 * @param heroStam The current stamina of the hero
	 * @param dialogue Text that updates the player on what is currently happening.
	 */
	public void enemyTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, Text heroStam, Text dialogue, Text dialogueTwo, 
			Text dialogueThree, GraphicsContext gc, int floor, Scene reviveScene, Scene gameOverScreen) {

		//If enemies are still alive
		if (totalEnemyHealth > 0) {
			if (hero.isDefending()) {
				dialogue.setText("It is the enemy's turn.");
				dialogueTwo.setText("");
				dialogueThree.setText("");
			} else {
				dialogueTwo.setText("It is the enemy's turn.");
			}
			singleEnemyAttacks(hero, allEnemies, gc, reviveScene, gameOverScreen);
		}
	}
	
	public void singleEnemyAttacks(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies,
			 GraphicsContext gc, Scene reviveScene, Scene gameOverScreen) {
			if (hero.getCurrentStamina() > 0) {
			//	final Integer innerI = new Integer(i);

				Timeline timeline = new Timeline();
				Timeline hit = new Timeline();
				Timeline timelineTwo = new Timeline();
				Timeline timeline2 = new Timeline();
				Timeline hit2 = new Timeline();
				Timeline timelineTwo2 = new Timeline();
				Timeline timeline3 = new Timeline();
				Timeline hit3 = new Timeline();
				Timeline timelineTwo3 = new Timeline();
				
				if (!dead.contains(0)) {
					//Move enemy forward
					timeline = new Timeline(); 
					timeline.setCycleCount(745);
					KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(0), gc, false,
							allEnemies, floor));
					timeline.getKeyFrames().add(frame);
	
					//Enemy hits hero
					hit = new Timeline(); 	
					KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> 
					hitHero(hero, allEnemies, dialogueTwo, dialogueThree, heroStam, 0, gc, reviveScene, gameOverScreen));
					hit.getKeyFrames().add(frameTwo);
	
					//Move enemy backward
					timelineTwo = new Timeline();
					timelineTwo.setCycleCount(745);
					KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(0), gc, true,
							allEnemies, floor));
					timelineTwo.getKeyFrames().add(frameThree);
					if (dead.contains(2) && dead.contains(1)) {
						SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo);
						sequence.play();				
					}
				}
				
				if (!dead.contains(1) && allEnemies.get(floor).size() == 2) {
					timeline2 = new Timeline(); 
					timeline2.setCycleCount(500);
					KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(1), gc, false,
							allEnemies, floor));
					timeline2.getKeyFrames().add(frame);
	
					//Enemy hits hero
					hit2 = new Timeline(); 	
					KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> 
					hitHero(hero, allEnemies, dialogueTwo, dialogueThree, heroStam, 0, gc, reviveScene, gameOverScreen));
					hit.getKeyFrames().add(frameTwo);
	
					//Move enemy backward
					timelineTwo2 = new Timeline();
					timelineTwo2.setCycleCount(500);
					KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(1), gc, true,
							allEnemies, floor));
					timelineTwo2.getKeyFrames().add(frameThree);
					if (dead.contains(2)) {
						SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo, timeline2, hit2, timelineTwo2);
						sequence.play();				
					}
				}
				
				if (!dead.contains(2) && allEnemies.get(floor).size() == 3) {
					//Move enemy forward
					timeline3 = new Timeline(); 
					timeline3.setCycleCount(240);
					KeyFrame frame3 = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(2), gc, false,
							allEnemies, floor));
					timeline3.getKeyFrames().add(frame3);
	
					//Enemy hits hero
					hit3 = new Timeline(); 	
					KeyFrame frameTwo3 = new KeyFrame(Duration.millis(1), ae -> 
					hitHero(hero, allEnemies, dialogueTwo, dialogueThree, heroStam, 1, gc, reviveScene, gameOverScreen));
					hit3.getKeyFrames().add(frameTwo3);
	
					//Move enemy backward
					timelineTwo3 = new Timeline();
					timelineTwo3.setCycleCount(240);
					KeyFrame frameThree3 = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(2), gc, true,
							allEnemies, floor));
					timelineTwo3.getKeyFrames().add(frameThree3);
				}
				
				if (!dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { //AAA
					SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo, timeline2, hit2, timelineTwo2, timeline3, hit3, timelineTwo3);
					sequence.play();
				} else if (!dead.contains(0) && dead.contains(1) && dead.contains(2)) { //DDA
					SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo);
					sequence.play();
				} else if (dead.contains(0) && !dead.contains(1) && dead.contains(2)) { //DAD
					SequentialTransition sequence = new SequentialTransition(timeline2, hit2, timelineTwo2);
					sequence.play();
				} else if (dead.contains(0) && dead.contains(1) && !dead.contains(2)) { //ADD
					SequentialTransition sequence = new SequentialTransition(timeline3, hit3, timelineTwo3);
					sequence.play();
				} else if (!dead.contains(0) && !dead.contains(1) && dead.contains(2)) { //DAA
					SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo, timeline2, hit2, timelineTwo2);
					sequence.play();
				} else if (dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { //AAD
					SequentialTransition sequence = new SequentialTransition(timeline2, hit2, timelineTwo2, timeline3, hit3, timelineTwo3);
					sequence.play();
				} else if (!dead.contains(0) && dead.contains(1) && !dead.contains(2)) { //ADA
					SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo, timeline3, hit3, timelineTwo3);
					sequence.play();
				}
			}
	}

	/**
	 * This method is called when an enemy hits the hero. It is unique
	 * from the hitEnemy method due to different dialogue that appears.
	 * 
	 * @param dialogueTwo The second textbox used to update battle info
	 * @param dialogueThree The third textbox used to update battle info
	 * @param heroStam The textbox used to display hero health
	 * @param i The counter for which enemy attacks
	 * @param gc GraphicsContext to clear character after death
	 */
	public void hitHero(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, 
			Text dialogueTwo, Text dialogueThree, Text heroStam, int i, GraphicsContext gc, Scene reviveScene, Scene gameOverScreen) {
		int attackAmount = allEnemies.get(floor).get(i).attack(hero);
		hero.displayCharacter(gc, false, true,false); //turn hero red on attack

		//After 0.1 seconds revert color
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(1);
		KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
		hero.displayCharacter(gc, false, false,false));
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
				hero.displayCharacter(gc, true, false,false);
			}
		}
		
		// if hero gets killed 
		if (hero.getCurrentStamina() == 0) {
		    //Line below used to test reviveScene, will be removed later
		    hero.setHasRevive(true);
			if (hero.isHasRevive() == true) {
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame1 = new KeyFrame(Duration.millis(4000), ae ->  primaryStage.setScene(reviveScene));
				moveOn.getKeyFrames().add(frame1);
				moveOn.play();
			} else {
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame1 = new KeyFrame(Duration.millis(4000), ae ->  primaryStage.setScene(gameOverScreen));
				moveOn.getKeyFrames().add(frame1);
				moveOn.play();
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