package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class represents the GUI of the game and houses the instance variables
 * needed to capture user input and run the game. Can be run to see preliminary phases of GUI 
 * (Start scene, Character selection and naming, incomplete floor 1 of the tower). 
 * Currently this class is only to begin testing GUI elements and its design will be 
 * revised heavily before final submission (instance variables may be removed/added).
 * 
 * @author Shari Sinclair and David Cai
 *
 */
public class GameGUI extends Application {
	private boolean isMage;
	private boolean isWarrior;
	private boolean isArcher;
	private GameCharacters hero;
	private String heroName;
	private ArrayList<GameCharacters> allEnemies;

	/**
	 * The constructor creates a new character, sets all booleans variables to
	 * false, initialized name as an empty string and enemy array to hold 10
	 * enemies.(Still in testing phase, some variables may be removed/added later)
	 */
	public GameGUI() {
		isMage = false;
		isWarrior = false;
		isArcher = false;
		hero = new GameCharacters();
		allEnemies = new ArrayList<GameCharacters>();

	}

	/**
	 * This is the start method that enables us to run/display our JavaFX
	 * application. It begins by displaying start screen and then allows us to
	 * continue through and play the game.
	 */
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		// Parent root = FXMLLoader.load(getClass().getResource("GameGUI.fxml"));

		//Start Screen Scene creation
		Scene start = startScreen(primaryStage);

		//Setting title of primary stage window, adding start scene and showing primary stage
		primaryStage.setTitle("Tower Challenge");
		primaryStage.setScene(start);
		primaryStage.show();
	}

	/**
	 * This method is responsible for displaying the start screen for the game.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
	 */
	public Scene startScreen(Stage primaryStage) {
		//Creating Pane which will display all the elements/ nodes
		Pane root = new Pane();

		//Creating Start button, adding style and necessary configurations
		Button btn = new Button("START");
		btn.setLayoutX(600);
		btn.setLayoutY(500);
		btn.setAlignment(Pos.CENTER);
		btn.setPrefSize(100, 50);
		btn.setStyle(" -fx-font: normal bold 20px 'serif' ");

		//Event Handling for when Start button is pressed
		EventHandler<MouseEvent> nextScreen = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				chooseCharacterScreen(primaryStage);
			}
		};

		btn.addEventHandler(MouseEvent.MOUSE_CLICKED, nextScreen);

		//Creating Title/ start screen text with game name, adding style and configuration
		Text title = new Text();
		title.setText("Tower Challenge");
		title.setX(250);
		title.setY(400);
		title.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.ITALIC, 100));
		title.setStroke(Color.BLACK);
		title.setStrokeWidth(3);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.FIREBRICK);
		title.setEffect(ds);

		//Adding image fill to Title text
		Image brick = new Image("Brick.jpeg");
		ImagePattern fill = new ImagePattern(brick, 20, 20, 40, 40, false);
		title.setFill(fill);

		//Adding background to Pane
		Image background = new Image("Tower.jpg");
		BackgroundImage background2 = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background startScreen = new Background(background2);
		root.setBackground(startScreen);

		//Adding other element/nodes to Pane, then Pane to Scene
		root.getChildren().addAll(title, btn);
		Scene startScene = new Scene(root, 1280, 720);
		return startScene;
	}

	/**
	 * This method houses the code needed for the screen that allows the player to
	 * choose their character type/fighter.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
	 * @return
	 */
	public void chooseCharacterScreen(Stage primaryStage) {
		//Creating Text, positioning it and adding style and effects
		Text charOption = new Text();
		charOption.setText("Choose your character type");
		charOption.setX(180);
		charOption.setY(350);
		charOption.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 75));
		DropShadow ds = new DropShadow();
		ds.setColor(Color.WHITE);
		charOption.setEffect(ds);

		//Creating buttons for user selection, positioning and adding style
		Button mageBtn = new Button("Mage");
		mageBtn.setLayoutX(600);
		mageBtn.setLayoutY(400);
		mageBtn.setPrefSize(100, 50);
		mageBtn.setFont(Font.font(20));
		Button warriorBtn = new Button("Warrior");
		warriorBtn.setLayoutX(600);
		warriorBtn.setLayoutY(475);
		warriorBtn.setPrefSize(100, 50);
		warriorBtn.setFont(Font.font(20));
		Button archerBtn = new Button("Archer");
		archerBtn.setLayoutX(600);
		archerBtn.setLayoutY(550);
		archerBtn.setPrefSize(100, 50);
		archerBtn.setFont(Font.font(20));

		//Event handling for when each button is pressed
		mageBtn.setOnAction(event -> {
			setMage(true);
			getCharName(primaryStage);
		});
		warriorBtn.setOnAction(event -> {
			setWarrior(true);
			getCharName(primaryStage);
		});
		archerBtn.setOnAction(event -> {
			setArcher(true);
			getCharName(primaryStage);
		});

		//Creating background for Pane
		Image background = new Image("Tower.jpg");
		BackgroundImage background2 = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background startScreen = new Background(background2);

		//Creating Pane, adding background and then adding above nodes
		Pane display = new Pane();
		display.setBackground(startScreen);
		display.getChildren().addAll(mageBtn, warriorBtn, archerBtn, charOption);

		//Adding Pane to Scene and then Scene to primary stage and then showing
		Scene chooseChar = new Scene(display, 1280, 720);

		primaryStage.setScene(chooseChar);
		primaryStage.show();
	}

	/**
	 * This method allows us to take in the character name for the user and uses it
	 * to set the hero name and create the new hero.
	 * 
	 * @return givenName String name entered by the user.
	 */
	public void getCharName(Stage primaryStage) {
		//Creating grid to be used to house text and text field
		GridPane getName = new GridPane();

		//Creating label field and text
		Label charName = new Label("Character Name: ");
		TextField charNameBox = new TextField();

		//Adding label and text field to grid
		getName.add(charName, 0, 0);
		getName.add(charNameBox, 0, 1);

		//Configuring and style to grid and label
		getName.setVgap(10);
		getName.setHgap(10);
		getName.setPadding(new Insets(10, 10, 10, 10));
		getName.setAlignment(Pos.CENTER);
		getName.setLayoutX(300);
		getName.setLayoutY(150);
		getName.setMinSize(600, 400);
		charName.setTextFill(Color.BLACK);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.ANTIQUEWHITE);
		charName.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 50));
		charName.setEffect(ds);

		//Creating button and creating event handling for when the button is pressed
		Button submitBtn = new Button("Enter Tower Floor 1");
		submitBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(submitBtn);
		getName.add(hbBtn, 0, 2);
		submitBtn.setOnAction(event -> {
			String name = charNameBox.getText();
			setHeroName(name);
			createHero();
			fullGame(primaryStage);
		});

		//Adding background to Pane
		Image background = new Image("Tower.jpg");
		BackgroundImage background2 = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background startScreen = new Background(background2);

		//Creating Pane, adding above nodes to Pane, and then Pane to Scene
		Pane display = new Pane();
		display.setBackground(startScreen);
		display.getChildren().addAll(getName);

		//Adding Scene to primary Stage and showing it.
		Scene chooseCharName = new Scene(display, 1280, 720);
		primaryStage.setScene(chooseCharName);
		primaryStage.show();

	}

	/**
	 * This method creates a hero character and sets its name.
	 */
	public void createHero() {
		if (isMage == true) {
			hero = new Mage();
			hero.setName(heroName);
		} else if (isWarrior == true) {
			hero = new Warrior();
			hero.setName(heroName);
		} else if (isArcher == true) {
			hero = new Archer();
			hero.setName(heroName);
		}
	}

	/**
	 * INCOMPLETE METHOD. This method will control the GUI for full game play. For
	 * now it houses the possible logic for bring the game to life.
	 * 
	 * @param primaryStage The primary stage/window of the JavaFX GUI.
	 */
	public void fullGame(Stage primaryStage) {
		GamePlayController gpc = new GamePlayController();

		//Below enemy created for testing purposes
		MeleeEnemy orc = new MeleeEnemy(1);
		allEnemies.add(orc);

		// while(gpc.isEndGamePlay() == false){
		boolean attacking = false;
		boolean defending = false;
		boolean healing = false;

		Pane towerLevel = createTowerLevels();
		/*
		 * Logic: 
		 * while(gpc.isEndGamePlay() == false){
		 * 	-current stamina = stamina , get new enemy in array (let enemy[] index
		 * 	correspond with floor to accommodate events with floor changes) 
		 * 
		 * while(heroStam != 0 || enemyStam !=0) 
		 * 	Player will click buttons, and this will change corresponding booleans set above- attack, defend heal)
		 *  	If (statement to handle which is boolean is true, hero will attack, defend or heal)
		 *  	Enemy react() -(randomly chooses what to do - attach, defend, if heal is an option
		 * 
		 * } 
		 * - Once either hero or enemy stam = 0 and we exit while loop
		 * 	gpc.GameplayContinue(hero,enemy)-- this will check whether hero has won 
		 * 		If continue to next floor = true (i.e. hero has stam, enemy dead)--> increment floor (built into method in gpc), 
		 * 			possibly load shop screen if floor==3,6,9, re-enter original while loop and fight again 
		 * 		Else if Enemy alive ,hero dead --> end game = true and we exit overall while loop
		 *		
		 *  } Exit overall while loop if endgame =true if 
		 *  	(Since endGame = true -->)
		 *  		if floor less than 10 -->Load game over screen (make game over screen)
		 *  		Else --> Congrats you win screen(make congrats screen)
		 * 
		 */


		// Button to shop
		// This will be adujsted when the full game method is completed 
		Button shopBtn = new Button("Go to the Magic Shop");
		shopBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		shopBtn.setAlignment(Pos.BOTTOM_LEFT);

		// Event handling for shop button 
		shopBtn.setOnAction(event -> {
			shop(primaryStage);});

		towerLevel.getChildren().add(shopBtn);

		Scene insideTower = new Scene(towerLevel, 1280, 720);
		primaryStage.setScene(insideTower);
		primaryStage.show();

	}

	/**
	 * This method creates the backdrop, buttons and text needed for fighting inside
	 * the Tower.
	 * 
	 * @return The Pane containing all the graphical elements needed for fights
	 *         inside the Tower.
	 */
	public Pane createTowerLevels() {
		Pane towerLevels = new Pane();

		// To display the background for the floor
		Image tower = new Image("pixelBack.png");
		BackgroundImage background = new BackgroundImage(tower, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background insideTowerBackground = new Background(background);
		Canvas canvas = new Canvas(1280, 720);
		towerLevels.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// To display current stamina of hero and enemy (using tester enemy[0]).
		Text heroName = new Text(hero.getType() + ": " + this.heroName);
		heroName.setStyle(" -fx-font: normal bold 30px 'serif' ");
		heroName.setFill(Color.DODGERBLUE);
		Text heroStam = new Text("Stamina: " + this.hero.getCurrentStamina());
		heroStam.setFill(Color.DODGERBLUE);
		heroStam.setStyle(" -fx-font: normal bold 30px 'serif' ");
		Text enemyName = new Text("Enemy Type: " + this.allEnemies.get(0).getType());
		enemyName.setStyle(" -fx-font: normal bold 30px 'serif' ");
		enemyName.setFill(Color.DARKRED);
		Text enemyStam = new Text("Stamina: " + this.allEnemies.get(0).getCurrentStamina());
		enemyStam.setStyle(" -fx-font: normal bold 30px 'serif' ");
		enemyStam.setFill(Color.DARKRED);

		// To display dialogue and other relevant battle info
		Text dialogue = new Text("");
		dialogue.setStyle(" -fx-font: normal bold 30px 'serif' ");
		dialogue.setFill(Color.WHITE);
		Text dialogueTwo = new Text("");
		dialogueTwo.setStyle(" -fx-font: normal bold 30px 'serif' ");
		dialogueTwo.setFill(Color.WHITE);
		Text dialogueThree = new Text("");
		dialogueThree.setStyle(" -fx-font: normal bold 30px 'serif' ");
		dialogueThree.setFill(Color.WHITE);


		// TEST - Adding hero and boss images
		hero.displayCharacter(gc, false);
		allEnemies.get(0).displayCharacter(gc, false);

		// Creating buttons for player to fight enemies
		Button attackBtn = new Button("Attack");
		attackBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		Button defendBtn = new Button("Defend");
		defendBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		Button healBtn = new Button("Heal");
		healBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().addAll(attackBtn, defendBtn, healBtn);

		// Button to choose enemy
		Button chooseEnemyBtn = new Button("Attack");
		chooseEnemyBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyBtn.setVisible(false);

		//Event handling for when each button is pressed
		attackBtn.setOnAction(event -> {
			hero.setIsDefending(false);
			chooseEnemyBtn.setVisible(true);	
		});
		defendBtn.setOnAction(event -> {
			hero.setIsDefending(true);
			enemyTurn(allEnemies, heroStam, dialogue, dialogueTwo, dialogueThree, gc);
		});
		healBtn.setOnAction(event -> {

		});

		// Actions to take after button to choose enemy is chosen
		chooseEnemyBtn.setOnAction(event -> {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, 1, gc)); // hardcode first minion
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(allEnemies, heroStam, dialogue, dialogueTwo,
					dialogueThree, gc));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();
			chooseEnemyBtn.setVisible(false);
		});


		// Adding all nodes to grid
		GridPane grid = new GridPane();
		grid.add(heroName, 0, 0);
		grid.add(heroStam, 0, 1);
		grid.add(dialogue, 1, 6);
		grid.add(dialogueTwo, 1, 7);
		grid.add(dialogueThree, 1, 8);
		grid.add(enemyName, 2, 0);
		grid.add(enemyStam, 2, 1);
		grid.add(chooseEnemyBtn, 2, 2);
		grid.setVgap(15);
		grid.setHgap(20);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setLayoutX(80);
		grid.setLayoutY(60);
		grid.setMinSize(1100, 700);
		grid.getColumnConstraints().add(new ColumnConstraints(300));
		grid.getColumnConstraints().add(new ColumnConstraints(350));
		grid.getColumnConstraints().add(new ColumnConstraints(300));
		grid.add(hbBtn, 0, 2);
		GridPane.setHalignment(dialogue, HPos.CENTER);
		GridPane.setHalignment(dialogueTwo, HPos.CENTER);
		GridPane.setHalignment(dialogueThree, HPos.CENTER);
		GridPane.setHalignment(heroName, HPos.CENTER);
		GridPane.setHalignment(heroStam, HPos.CENTER);
		GridPane.setHalignment(enemyName, HPos.CENTER);
		GridPane.setHalignment(enemyStam, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyBtn, HPos.CENTER);
		GridPane.setHalignment(hbBtn, HPos.CENTER);
		grid.setGridLinesVisible(true);
		
		// Setting Background for Pane, adding grid to Pane 
		towerLevels.setBackground(insideTowerBackground);
		towerLevels.getChildren().add(grid);

		return towerLevels;
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
	public void heroTurn(ArrayList<GameCharacters> allEnemies, Text enemyStam, Text dialogue, Text dialogueTwo, 
			Text dialogueThree, int choice, GraphicsContext gc) { 
		
		//Move hero forward
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(600);
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true));
		timeline.getKeyFrames().add(frame);
		
		//Hero attacks enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> hitEnemy(allEnemies, choice, 
				dialogue, dialogueTwo, dialogueThree, enemyStam, gc));
		hit.getKeyFrames().add(frameTwo);
		
		//Move hero backward
		Timeline timelineTwo = new Timeline();
		timelineTwo.setCycleCount(600);
		KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, false));
		timelineTwo.getKeyFrames().add(frameThree);
		
		//Play out timelines in order
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
	public void hitEnemy(ArrayList<GameCharacters> allEnemies, int choice, Text dialogue, Text dialogueTwo, Text dialogueThree,
			Text enemyStam, GraphicsContext gc) {
		
		//Hero attacks enemy
		int attackAmount = this.hero.attack(allEnemies.get(choice - 1));
		enemyStam.setText("Stamina: " + this.allEnemies.get(choice - 1).getCurrentStamina());
		dialogue.setText("You dealt " + attackAmount + " damage!");
		dialogueTwo.setText("");
		dialogueThree.setText("");
		
		//If enemy dies, update information and delete enemy picture
		if (allEnemies.get(choice - 1).getCurrentStamina() <= 0) {
			dialogue.setText("You have killed the enemy.");
			dialogueTwo.setText(""); //XP stuff and gold stuff will be here
			dialogueThree.setText("");
			
			//Delete enemy picture off canvas
			allEnemies.get(choice - 1).displayCharacter(gc, true);
			
			//Remove enemy from ArrayList after death
			allEnemies.remove(choice - 1);
		}
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
		character.displayCharacter(gc, true);
		
		//Move character accordingly depending on boolean
		if (forward) {
			character.setX(character.getX() + 1.25);
		} else {
			character.setX(character.getX() - 1.25);
		}
		
		//Draw new picture
		character.displayCharacter(gc, false);
	}

	/**
	 * This method creates display text for when it is the enemies turn to attack and updates necessary variables.
	 * 
	 * @param allEnemies The arrayList of enemies the hero is currently fighting.
	 * @param heroStam The current stamina of the hero
	 * @param dialogue Text that updates the player on what is currently happening.
	 */
	public void enemyTurn(ArrayList<GameCharacters> allEnemies, Text heroStam, Text dialogue, Text dialogueTwo, 
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
					timeline.setCycleCount(600);
					KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(innerI), gc, false));
					timeline.getKeyFrames().add(frame);
					
					//Enemy hits hero
					Timeline hit = new Timeline(); 	
					KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> hitHero( 
							dialogueTwo, dialogueThree, heroStam, innerI, gc));
					hit.getKeyFrames().add(frameTwo);
					
					//Move enemy backward
					Timeline timelineTwo = new Timeline();
					timelineTwo.setCycleCount(600);
					KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(innerI), gc, true));
					timelineTwo.getKeyFrames().add(frameThree);
					
					//Play out timelines in order
					SequentialTransition sequence = new SequentialTransition(timeline, hit, timelineTwo);
					sequence.play();
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
	public void hitHero(Text dialogueTwo, Text dialogueThree, 
			Text heroStam, int i, GraphicsContext gc) {
		int attackAmount = allEnemies.get(i).attack(hero);
		heroStam.setText("Stamina: " + this.hero.getCurrentStamina());
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
				hero.displayCharacter(gc, true);
			}
		}
	}

	/**
	 * This will generate the shop screen, where player is able to buy and sell
	 * items.
	 */
	public void shop(Stage primaryStage) {
		// Create grid pane
		GridPane rootNode = new GridPane();

		// Create the magic shop text
		Text welcome = new Text("Magic Shop");
		welcome.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 70));
		welcome.setFill(Color.GOLDENROD);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLDENROD);
		welcome.setEffect(ds);

		// Error message when money is not enough
		Text errorMsg = new Text("BLABLABLABLA");
		errorMsg.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		errorMsg.setFill(Color.GOLDENROD);
		errorMsg.setVisible(false);

		// Display all items currrently in the hero's bag
		Text potionList = new Text(hero.shopDisplay());
		potionList.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		potionList.setFill(Color.GOLDENROD);

		// Description for cheap potion
		Text potion1 = new Text("+CHEAP POTION+ \n HP +100 \n PRICE: 50 GOLD");
		potion1.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		potion1.setFill(Color.GOLDENROD);

		// Input quantity for cheap potion
		TextField quantity1 = new TextField("Quantity");
		quantity1.setOpacity(0.8);
		quantity1.setMaxWidth(100);

		// Buy and sell buttons for cheap potion
		Button btnBuy1 = new Button("Buy");
		this.buyPotion(btnBuy1, hero.getCp(), quantity1, errorMsg, potionList);
		Button btnSell1 = new Button("Sell");
		this.sellPotion(btnSell1, hero.getCp(), quantity1, errorMsg, potionList);

		// Description for hyper potion
		Text potion2 = new Text("+HYPER POTION+ \n HP +250 \n PRICE: 100 GOLD");
		potion2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		potion2.setFill(Color.GOLDENROD);

		// Input quantity for hyper potion
		TextField quantity2 = new TextField("Quantity");
		quantity2.setMaxWidth(100);
		quantity2.setOpacity(0.8);

		// Buy and sell buttons for hyper potion
		Button btnBuy2 = new Button("Buy");
		this.buyPotion(btnBuy2, hero.getHp(), quantity2, errorMsg, potionList);

		Button btnSell2 = new Button("Sell");
		this.sellPotion(btnSell2, hero.getHp(), quantity2, errorMsg, potionList);

		// Description for revive
		Text revive = new Text("+REVIVE STONE+ \n MAGIC POWER \n BRING THE DEAD BACK TO LIFE \n PRICE: 200 GOLD");
		revive.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		revive.setFill(Color.GOLDENROD);

		// Incomplet: buy and sell buttons for revive, will add eventhandlers
		Button btnBuy3 = new Button("Buy");
		Button btnSell3 = new Button("Sell");

		// Create images for the items at the shop
		Image imgPotion1 = new Image("cheapPotion.png", 200, 200, false, false);
		Image imgPotion2 = new Image("hyperPotion.png", 200, 200, false, false);
		Image imgRevive = new Image("revive.png", 200, 200, false, false);
		ImageView ivPotion1 = new ImageView(imgPotion1);
		ImageView ivPotion2 = new ImageView(imgPotion2);
		ImageView ivRevive = new ImageView(imgRevive);

		// Add nodes to the grid
		rootNode.setHgap(5);
		rootNode.setVgap(5);
		rootNode.add(welcome, 1, 0);
		rootNode.add(ivPotion1, 0, 1);
		rootNode.add(potion1, 0, 2);
		rootNode.add(quantity1, 0, 3);
		rootNode.add(btnBuy1, 0, 4);
		rootNode.add(btnSell1, 0, 5);
		rootNode.add(ivPotion2, 1, 1);
		rootNode.add(potion2, 1, 2);
		rootNode.add(quantity2, 1, 3);
		rootNode.add(btnBuy2, 1, 4);
		rootNode.add(btnSell2, 1, 5);
		rootNode.add(ivRevive, 2, 1);
		rootNode.add(revive, 2, 2);
		rootNode.add(btnBuy3, 2, 3);
		rootNode.add(btnSell3, 2, 4);
		rootNode.add(potionList, 0, 7);
		rootNode.add(errorMsg, 0, 8);
		rootNode.setAlignment(Pos.CENTER);
		rootNode.setPadding(new Insets(5, 5, 5, 5));

		// Set background
		Image shopBg = new Image("magicShop.jpg", 1280, 720, false, false);
		BackgroundImage shopBg1 = new BackgroundImage(shopBg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background shopBg2 = new Background(shopBg1);
		rootNode.setBackground(shopBg2);

		// Create the scene
		Scene shopScene = new Scene(rootNode, 1280, 720);
		primaryStage.setScene(shopScene);
		primaryStage.show();

	}

	/**
	 * This method allows the player to buy the potion from the shop by clicking the
	 * buy button
	 * 
	 * @param btn      buy button
	 * @param potion   the type of the potion the player is buying
	 * @param quantity the quantity of the potion the player is buying
	 * @param errorMsg an error message shows if the player does not have enough
	 *                 money
	 * @param display
	 */
	public void buyPotion(Button btn, Potion potion, TextField quantity, Text errorMsg, Text display) {
		btn.setOnAction(Event -> {
			double cost = potion.getPrice() * Double.parseDouble(quantity.getText());
			if (hero.getGold() >= cost) {
				errorMsg.setVisible(false);
				hero.setGold(hero.getGold() - cost);
				hero.getPotionMap().put(potion,
						hero.getPotionMap().get(potion) + Double.parseDouble(quantity.getText()));
				display.setText(hero.shopDisplay());
			} else {
				errorMsg.setText("YOU DO NOT HAVE ENOUGH MONEY");
				errorMsg.setVisible(true);
			}
		});

	}

	/**
	 * This method allows the player to sell items at the shop by clicking the sell
	 * button
	 * 
	 * @param btn      the sell button
	 * @param potion   the type of potion the player is selling
	 * @param q        the quantity of potion the player is selling
	 * @param errorMsg an error message shows if the player does not have enough
	 *                 items
	 * @param display
	 */
	public void sellPotion(Button btn, Potion potion, TextField q, Text errorMsg, Text display) {
		btn.setOnAction(Event -> {
			double quantity = Double.parseDouble(q.getText());
			if (hero.getPotionMap().get(potion) >= quantity) {
				hero.setGold(hero.getGold() + ((potion.getPrice() / 2) * quantity));
				hero.getPotionMap().put(potion, hero.getPotionMap().get(potion) - quantity);
				display.setText(hero.shopDisplay());
			} else {
				errorMsg.setText("YOU DO NOT HAVE ENOUGH ITEMS");
				errorMsg.setVisible(true);
			}
		});
	}

	/**
	 * @return the isMage
	 */
	public boolean isMage() {
		return isMage;
	}

	/**
	 * @param isMage the isMage to set
	 */
	public void setMage(boolean isMage) {
		this.isMage = isMage;
	}

	/**
	 * @return the isWarrior
	 */
	public boolean isWarrior() {
		return isWarrior;
	}

	/**
	 * @param isWarrior the isWarrior to set
	 */
	public void setWarrior(boolean isWarrior) {
		this.isWarrior = isWarrior;
	}

	/**
	 * @return the isArcher
	 */
	public boolean isArcher() {
		return isArcher;
	}

	/**
	 * @param isArcher the isArcher to set
	 */
	public void setArcher(boolean isArcher) {
		this.isArcher = isArcher;
	}

	/**
	 * @return the hero
	 */
	public GameCharacters getHero() {
		return hero;
	}

	/**
	 * @param hero the hero to set
	 */
	public void setHero(GameCharacters hero) {
		this.hero = hero;
	}

	/**
	 * @return the heroName
	 */
	public String getHeroName() {
		return heroName;
	}

	/**
	 * @param heroName the heroName to set
	 */
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
