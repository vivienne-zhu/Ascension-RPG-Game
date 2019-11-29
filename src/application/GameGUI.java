package application;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * This class represents the GUI of the game and houses the instance variables
 * needed to capture user input and run the game. Game can be run to see game play up to floor 9. 
 * The design of this class may be revised before final submission 
 * (class will be made more cohesive and DRY).
 * 
 * @author Shari Sinclair, JiayuZhu and David Cai
 *
 */
public class GameGUI extends Application {
	private boolean isMage;
	private boolean isWarrior;
	private boolean isRogue;
	private GameCharacters hero;
	private String heroName;
	private HashMap<Integer, ArrayList<GameCharacters>> allEnemies;
	private int totalEnemyHealth;
	private int xpCount;
	private Floor floor;
	private Shop shop;
	private Event event;
	private MediaPlayer battleMusic; 
	private MediaPlayer openingMusic;
	private MediaPlayer gameOverMusic;
	private MediaPlayer youWinMusic;
	private SoundEffect se;
	

	/**
	 * The sets all booleans variables to false, and initializes the hero,
	 *  allEnemies HashMap, the shop, events,floors, sound effects and
	 *  all necessary media players for music. 
	 * (Still in testing phase, some variables may be removed/added later)
	 */
	public GameGUI() {
		isMage = false;
		isWarrior = false;
		isRogue = false;
		hero = new GameCharacters();
		allEnemies = new HashMap<Integer, ArrayList<GameCharacters>>();
		floor = new Floor();
		shop = new Shop();
		event = new Event();
		se = new SoundEffect();
		openingMusic = se.openingMusic();
		battleMusic = se.backgroundMusic();
		gameOverMusic = se.gameOverMusic();
		youWinMusic = se.youWinMusic();
	}

	/**
	 * This is the start method that enables us to run/display our JavaFX
	 * application. It begins by displaying start screen and then allows us to
	 * continue through and play the game.
	 */
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		//Parent root = FXMLLoader.load(getClass().getResource("GameGUI.fxml"));
		
		//Start Screen Scene creation
		Scene start = startScreen(primaryStage);
	    	//Scene test = gameOverScreen(primaryStage);
		//Setting title of primary stage window, adding start scene and showing primary stage
		primaryStage.setTitle("Tower Challenge");
		primaryStage.setScene(start);
		primaryStage.show();
	}

	/**
	 * This method is responsible for displaying the start screen for the game
	 * and plays music for the opening scenes.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
	 * @return startScene The Scene startScene
	 */
	private Scene startScreen(Stage primaryStage) {
		//Creating Pane which will display all the elements/ nodes
		Pane root = new Pane();

//		Creating Start button, adding style and necessary configurations
		Image btn = new Image("startButton.png", 250, 80, false, false);		
		ImageView iv = new ImageView(btn);
		Text start = new Text("START");
		start.setId("BtnText");
		
		StackPane pane = new StackPane();
		pane.getChildren().addAll(iv,start);
		//pane.setId("startPane");
		pane.setAlignment(Pos.CENTER);
		pane.setLayoutX(525);
		pane.setLayoutY(470);
		pane.setOnMouseClicked(event-> {se.transitionSound(); chooseCharacterScreen(primaryStage);
		});
		

		//Creating Title/ start screen text with game name, adding style and configuration
		Text title = new Text();
		title.setText("Tower Challenge");
		title.setX(250);
		title.setY(400);
		title.setId("startText");

		//Adding image fill to Title text
//		Image brick = new Image("Brick.jpeg");
//		ImagePattern fill = new ImagePattern(brick, 20, 20, 40, 40, false);
//		title.setFill(fill);
		
		//Mediaplayer for music
		openingMusic.play();
		
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		//Adding background image to Pane
		root.setId("startBackground");

		//Adding other element/nodes to Pane, then Pane to Scene
		root.getChildren().addAll(title, pane);
		Scene startScene = new Scene(root, 1280, 720);
		startScene.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		return startScene;
	}
	
	/**
	 * This method houses the code needed for the screen that allows the player to
	 * choose their character type/fighter.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
	 */
	private void chooseCharacterScreen(Stage primaryStage) {
		//Creating Text, positioning it and adding style and effects
		Text charOption = new Text();
		charOption.setText("Choose your character type");
		charOption.setX(180);
		charOption.setY(350);
		charOption.setId("characterOptionText");
		
		//Creating buttons for user selection, positioning and adding style
		Image btnBackGround = new Image("startButton.png", 250, 80, false, false);
		//Mage btn
		ImageView ivMage = new ImageView(btnBackGround);
		Text mage = new Text("Mage");
		mage.setId("BtnText");
		StackPane magePane = new StackPane();
		magePane.getChildren().addAll(ivMage,mage);
		magePane.setAlignment(Pos.CENTER);
		//Warrior btn
		ImageView ivWarrior = new ImageView(btnBackGround);
		Text warrior = new Text("Warrior");
		warrior.setId("BtnText");
		StackPane warriorPane = new StackPane();
		warriorPane.getChildren().addAll(ivWarrior,warrior);
		warriorPane.setAlignment(Pos.CENTER);
		//Rogue btn
		ImageView ivRogue = new ImageView(btnBackGround);
		Text rogue = new Text("Rogue");
		rogue.setId("BtnText");
		StackPane roguePane = new StackPane();
		roguePane.getChildren().addAll(ivRogue,rogue);
		roguePane.setAlignment(Pos.CENTER);
		
		// Clear prior assigned character type
		setMage(false);
		setWarrior(false);
		setRogue(false);
		

		//Event handling for when each button pane is pressed
		magePane.setOnMouseClicked(event -> {
		    	se.transitionSound();
			setMage(true);
			nameCharScreen(primaryStage);
		});
		warriorPane.setOnMouseClicked(event -> {
		    	se.transitionSound();
			setWarrior(true);
			nameCharScreen(primaryStage);
		});
		roguePane.setOnMouseClicked(event -> {
		    	se.transitionSound();
			setRogue(true);
			nameCharScreen(primaryStage);
		});

		//Creating vertical box for buttons 
		VBox btns = new VBox(15);
		btns.setAlignment(Pos.CENTER);
		btns.setLayoutX(500);
		btns.setLayoutY(400);
		btns.getChildren().addAll(magePane, warriorPane, roguePane);

		//Creating Pane, adding background and then adding above nodes
		Pane display = new Pane();
		display.getChildren().addAll(charOption, btns);
		display.setId("startBackground");

		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and then Scene to primary stage and then showing
		Scene chooseChar = new Scene(display, 1280, 720);
		chooseChar.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(chooseChar);
		primaryStage.show();
	}

	/**
	 * This method allows us to take in the character name from the user and use it
	 * to set the hero name and create the new hero.
	 * 
	 * @param primaryStage The primary stage/ window to display the GUI.
	 * @return givenName String name entered by the user.
	 */
	private void nameCharScreen(Stage primaryStage) {
		//Creating grid to be used to house text and text field
		GridPane getName = new GridPane();

		//Creating label field and text
		Label charName = new Label("Character Name: ");
		charName.setId("characterNameText");
		TextField charNameBox = new TextField();

		//Creating submit button 
		Button submitBtn = new Button("Enter Floor 1");
		submitBtn.setId("yellowBtn");
		submitBtn.setLayoutX(1050);
		submitBtn.setLayoutY(600);
		
		
		// Create back button
		Button backBtn = new Button("Back");
		backBtn.setId("yellowBtn");
		backBtn.setLayoutX(100);
		backBtn.setLayoutY(600);
		
		// Event handling to back button 
		backBtn.setOnAction(event -> {se.transitionSound();
			chooseCharacterScreen(primaryStage);
			});
	
		
		//EventHandling and error checking for empty textfield
		Text error = new Text();
		
		submitBtn.setOnMouseClicked(event -> {
		    if (charNameBox.getText().isEmpty() == true) {
			se.errorSound();
			error.setId("charNameErrorText");
			error.setText("Please Enter A Name");
		    } else {
			openingMusic.stop();
			String name = charNameBox.getText();
			setHeroName(name);
			createHero();
			se.transitionSound();
			battleScreen(primaryStage);
		    }
		});
		
		//Adding label and text field to grid
		getName.add(charName, 0, 0);
		getName.add(charNameBox, 0, 1);
		getName.add(error, 0, 2);


		// Fixed height for rows 
		for (int i = 0; i < 3; i++) {
			RowConstraints row = new RowConstraints(40);
			getName.getRowConstraints().add(row);
		}
		
		//Configuring and style to grid and label
		getName.setVgap(10);
		getName.setHgap(10);
		getName.setPadding(new Insets(10, 10, 10, 10));
		getName.setAlignment(Pos.CENTER);
		getName.setLayoutX(300);
		getName.setLayoutY(150);
		getName.setMinSize(600, 400);
		;
		
		//Creating Pane, adding above nodes and background to Pane
		Pane display = new Pane();
		display.getChildren().addAll(getName, backBtn, submitBtn);
		display.setId("startBackground");
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Scene to primary Stage and showing it.
		Scene chooseCharName = new Scene(display, 1280, 720);
		chooseCharName.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(chooseCharName);
		primaryStage.show();

	}

	/**
	 * This method creates a hero character and sets its name.
	 */
	private void createHero() {
		if (isMage == true) {
			hero = new Mage();
			hero.setName(heroName);
		} else if (isWarrior == true) {
			hero = new Warrior();
			hero.setName(heroName);
		} else if (isRogue == true) {
			hero = new Rogue();
			hero.setName(heroName);
		}
	}

	/**
	 * INCOMPLETE METHOD. This method is the central point for full game play.
	 * It creates the Pane for each floor of fighting, restores hero mana, gets 
	 * ArrayList of enemies for the floor from allEnemies HashMap, and plays battle music.
	 * 
	 * @param primaryStage The primary stage/window of the JavaFX GUI.
	 */
	public void battleScreen(Stage primaryStage) {
		
	    //Mediaplayer for music
	   	battleMusic.play();

		//Later on, these will not all be meleeEnemys. They will be randomly generated. Will add when other enemies are balanced
		ArrayList<GameCharacters> floorEnemies = new ArrayList<GameCharacters>();
		if (floor.getFloor() == 1 || floor.getFloor() == 2 || floor.getFloor() == 3) {
		    //floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
			//floorEnemies.add(new BossEnemy(1));
			floorEnemies.add(new HealerEnemy(floor.getFloor(), 0));
			floorEnemies.add(new RangedEnemy(floor.getFloor(), 1));
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 2));
		} else if (floor.getFloor() == 4 || floor.getFloor() == 5 || floor.getFloor() == 6) {
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 1));
		} else if (floor.getFloor() == 7 || floor.getFloor() == 8 || floor.getFloor() == 9) {
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
			floorEnemies.add(new HealerEnemy(floor.getFloor(), 1));
			floorEnemies.add(new RangedEnemy(floor.getFloor(), 2));
		} else if (floor.getFloor() == 10) {
		    	floorEnemies.add(new BossEnemy(10));
		}
		allEnemies.put(floor.getFloor(), floorEnemies);
		totalEnemyHealth = 0;
		for (int i = 0; i < floorEnemies.size(); i++) {
			totalEnemyHealth += floorEnemies.get(i).getCurrentStamina();
		}

		hero.setCurrentMana(hero.getMana());
		
		// Creation of pane -->currently here for GUI testing
		Pane towerLevel = createTowerLevels(primaryStage, allEnemies.get(floor.getFloor()));

		// Pane towerLevel = createTowerLevels(primaryStage, tempEnemies); --> Will replace above code
		Scene insideTower = new Scene(towerLevel, 1280, 720);
		
		primaryStage.setScene(insideTower);
		primaryStage.show();

	}

	/**
	 * This method creates the backdrop, buttons and text needed for fighting inside
	 * the Tower.
	 * 
	 * @param primaryStage The primary Stage/window to display the GUI.
	 * @param floorcopy a copy of the ArrayList of enemies for that floor.
	 * @return towerLevels The Pane containing all the graphical elements 
	 *           needed for each fight inside the Tower.
	 */
	private Pane createTowerLevels(Stage primaryStage, ArrayList<GameCharacters> floorCopy) {
	    	//Creating pane
		Pane towerLevels = new Pane();

		// To display the background for the floor
		towerLevels.setId("insideTower");
		
		Canvas canvas = new Canvas(1280, 720);
		towerLevels.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		//To display floor number
		Text floorNum = new Text();
		floorNum.setText("Floor " + floor.getFloor());
		floorNum.setStyle(" -fx-font: normal bold 40px 'serif' ");
		floorNum.setFill(Color.WHITE);
		floorNum.setX(600);
		floorNum.setY(50);

		// Adding hero and enemy images
		hero.displayCharacter(gc, false, false, false);
		
		for (int i = 0; i < floorCopy.size(); i++) {
			floorCopy.get(i).displayCharacter(gc, false, false, false);
		}

		//Code that controls the battle mechanics on each floor
		BattlePhase battle = new BattlePhase(primaryStage, floor.getFloor(), totalEnemyHealth);
		battle.dispCombatInfo(hero, allEnemies, floor.getFloor());
		battle.idleAnimate(allEnemies, gc);
		battle.heroAnimate(hero, gc);
		battle.dispDialogue();
		battle.initButtons(hero);
		battle.eventButtons(allEnemies, hero, gc, transitionScreen(primaryStage), youWinScreen(primaryStage),  
			reviveScreen(primaryStage),gameOverScreen(primaryStage), battleMusic, gameOverMusic, youWinMusic);
		GridPane grid = battle.gridLayout(allEnemies.get(floor.getFloor()).size(), hero);
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), towerLevels);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		// Setting Background for Pane, adding grid to Pane  
		//towerLevels.setBackground(insideTowerBackground);
		towerLevels.getChildren().addAll(grid, floorNum);
		towerLevels.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		return towerLevels;
	}

	/**
	 * This will generate the shop screen, where player is able to buy and sell
	 * items.
	 * 
	 * @param primaryStage The primary stage/ window to display the GUI.
	 */
	public void shopScreen(Stage primaryStage) {
		// Create grid pane
		GridPane root = new GridPane();
				
		//Create black dropshadow
		DropShadow d = new DropShadow(10, Color.BLACK);

		// Create the magic shop text
		Text welcome = new Text("Magic Shop");
		welcome.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 70));
		welcome.setFill(Color.WHITE);
		DropShadow ds = new DropShadow(10, Color.MEDIUMSLATEBLUE);
		welcome.setEffect(ds);
		GridPane.setHalignment(welcome, HPos.CENTER);

		// Error message
		Text errorMsg = new Text("BLABLABLABLA");
		errorMsg.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		errorMsg.setFill(Color.WHITE);
		errorMsg.setVisible(false);

		// Display all items currently in the hero's bag
		Text potionList = new Text(shop.shopDisplay(hero));
		potionList.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		potionList.setFill(Color.WHITE);
		potionList.setEffect(d);
		potionList.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 1;");

		// Description for cheap potion
		Text potion1 = new Text("+CHEAP POTION+ \n HP +100 \n PRICE: 50 GOLD");
		potion1.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		potion1.setFill(Color.WHITE);
		potion1.setEffect(d);
		potion1.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 1;");

		// Input quantity for cheap potion
		TextField quantity1 = new TextField("Quantity");
		quantity1.setOpacity(0.8);
		quantity1.setMaxWidth(150);

		// Buy and sell buttons for cheap potion
		Button btnBuy1 = new Button("Buy");
		this.shop.buyPotion(this.hero, btnBuy1, hero.getCp(), quantity1, errorMsg, potionList);
		Button btnSell1 = new Button("Sell");
		this.shop.sellPotion(this.hero, btnSell1, hero.getCp(), quantity1, errorMsg, potionList);

		// Description for hyper potion
		Text potion2 = new Text("+HYPER POTION+ \n HP +250 \n PRICE: 100 GOLD");
		potion2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		potion2.setFill(Color.WHITE);
		potion2.setEffect(d);
		potion2.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 1;");

		// Input quantity for hyper potion
		TextField quantity2 = new TextField("Quantity");
		quantity2.setMaxWidth(150);
		quantity2.setOpacity(0.8);

		// Buy and sell buttons for hyper potion
		Button btnBuy2 = new Button("Buy");
		this.shop.buyPotion(this.hero, btnBuy2, hero.getHp(), quantity2, errorMsg, potionList);

		Button btnSell2 = new Button("Sell");
		this.shop.sellPotion(this.hero, btnSell2, hero.getHp(), quantity2, errorMsg, potionList);

		// Description for revive
		Text revive = new Text("+REVIVE POTION+ \n COME BACK TO LIFE \n PRICE: 200 GOLD");
		revive.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		revive.setFill(Color.WHITE);
		revive.setEffect(d);
		revive.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 1;");
		GridPane.setHalignment(revive, HPos.CENTER);
		
		// Description for revive quantity
		Text reviveQuant = new Text("MAX: 1");
		reviveQuant.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		reviveQuant.setFill(Color.WHITE);
		reviveQuant.setEffect(d);
		reviveQuant.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 1;");
		GridPane.setHalignment(reviveQuant, HPos.CENTER);

		// Buy and sell for revive 
		Button btnBuy3 = new Button("Buy");
		this.shop.buyRevive(hero, btnBuy3, errorMsg, potionList);
		Button btnSell3 = new Button("Sell");
		this.shop.sellRevive(hero, btnSell3, errorMsg, potionList);

		// Create imageView for the items at the shop
		ImageView ivPotion1 = new ImageView(this.shop.getCpImage());
		ImageView ivPotion2 = new ImageView(this.shop.getHpImage());
		ImageView ivRevive = new ImageView(this.shop.getReviveImage());
		DropShadow ds2 = new DropShadow();
		ds2.setColor(Color.MEDIUMPURPLE);
		ivPotion1.setEffect(ds2);
		ivPotion2.setEffect(ds2);
		ivRevive.setEffect(ds2);
		HBox hbox = new HBox();
	    HBox hbox1 = new HBox();
		HBox hbox2 = new HBox();
		hbox.getChildren().add(ivPotion1);
		hbox1.getChildren().add(ivPotion2);
		hbox2.getChildren().add(ivRevive);

		// Fixed width for columns
		for (int i = 0; i < 5; i++) {
			ColumnConstraints column = new ColumnConstraints(250);
			root.getColumnConstraints().add(column);
		}

		//Creating continue button and adding event handling
		Button continueBtn = new Button("Next Floor");
		continueBtn.setId("yellowBtn");

//		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
//			"-fx-background-color: darkgoldenrod;\n" + "-fx-text-fill: black ");		 
		this.event.eventHappen();
		if (this.event.isEvent() == true) {
		    continueBtn.setOnAction(event -> {
			se.transitionSound();
			eventScreen(primaryStage);});
		} else {
		    continueBtn.setOnAction(event -> {
			battleMusic.stop();
			se.transitionSound();
			floor.incrementFloor();
			battleScreen(primaryStage);});
		}
		
		// Add nodes to the grid pane
		root.setHgap(60);
		root.setAlignment(Pos.CENTER);
		root.setGridLinesVisible(false);
		root.setVgap(5);
		root.add(welcome, 2, 0);
		root.add(hbox, 1, 1);
		root.add(potion1, 1, 2);
		root.add(quantity1, 1, 3);
		root.add(btnBuy1, 1, 4);
		root.add(btnSell1, 1, 5);
		root.add(hbox1, 2, 1);
		root.add(potion2, 2, 2);
		root.add(quantity2, 2, 3);
		root.add(btnBuy2, 2, 4);
		root.add(btnSell2, 2, 5);
		root.add(hbox2, 3, 1);
		root.add(revive, 3, 2);
		root.add(reviveQuant, 3, 3);
		root.add(btnBuy3, 3, 4);
		root.add(btnSell3, 3, 5);
		root.add(potionList, 2, 8);
		root.add(errorMsg, 2, 9);

		root.add(continueBtn, 4, 9);
		
		welcome.setTextAlignment(TextAlignment.CENTER);
		potion1.setTextAlignment(TextAlignment.CENTER);
		quantity1.setAlignment(Pos.CENTER);
		potion2.setTextAlignment(TextAlignment.CENTER);
		quantity2.setAlignment(Pos.CENTER);
		revive.setTextAlignment(TextAlignment.CENTER);
		reviveQuant.setTextAlignment(TextAlignment.CENTER);
		potionList.setTextAlignment(TextAlignment.CENTER);
		errorMsg.setTextAlignment(TextAlignment.CENTER);
		
		hbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(potion1, HPos.CENTER);
		GridPane.setHalignment(quantity1, HPos.CENTER);
		GridPane.setHalignment(potion2, HPos.CENTER);
		GridPane.setHalignment(quantity2, HPos.CENTER);
		GridPane.setHalignment(revive, HPos.CENTER);
		GridPane.setHalignment(potionList, HPos.CENTER);
		GridPane.setHalignment(errorMsg, HPos.CENTER);
		
		GridPane.setHalignment(btnBuy1, HPos.CENTER);
		GridPane.setHalignment(btnSell1, HPos.CENTER);
		GridPane.setHalignment(btnBuy2, HPos.CENTER);
		GridPane.setHalignment(btnSell2, HPos.CENTER);
		GridPane.setHalignment(btnBuy3, HPos.CENTER);
		GridPane.setHalignment(btnSell3, HPos.CENTER);
		GridPane.setHalignment(continueBtn, HPos.LEFT);
		
		errorMsg.setWrappingWidth(200);

		// Set background		
		root.setStyle(" -fx-background-image: url(\"shop.jpg\");\n" + 
				"    -fx-background-size: cover;");

		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		// Create the scene
		Scene shopScene = new Scene(root, 1280, 720);
		shopScene.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(shopScene);
		primaryStage.show();

	}
	
	/**
	 * This method creates the screen when a special event occurs. 
	 * 
	 * @param primaryStage The primary stage/window to display the GUI.
	 */
	public void eventScreen(Stage primaryStage) {
	    	// Creating the grid
		GridPane grid = new GridPane();
		
		// Text for the event 
		Text txtEvent = new Text("A MYSTERIOUS BOX...");
		txtEvent.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 70));
		txtEvent.setFill(Color.GOLDENROD);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLDENROD);
		txtEvent.setEffect(ds);
		
		// Text for the description of the triggered event 
		Text display = new Text("");
		display.maxWidth(300);
		display.setFill(Color.WHITE);
		display.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		GridPane.setHalignment(display, HPos.CENTER);
		
		// Image for the treasure chest 
		ImageView closedIV = new ImageView(event.getClosedBox());		
		ImageView openIV = new ImageView(event.getOpenBox());
		
		// Creating continue button and adding event handling
		Button continueBtn = new Button("NEXT FLOOR");
		continueBtn.setDisable(true);
		continueBtn.setLayoutX(500);
		continueBtn.setLayoutY(700);
		continueBtn.setId("yellowBtn");
//		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
//			"-fx-background-color: indianred;\n" + "-fx-text-fill: black ");	
		continueBtn.setOnAction(event -> {
		    	battleMusic.stop();
		    	se.transitionSound();
			floor.incrementFloor();
			battleScreen(primaryStage);
					});
		// Create 'Open' Button and event handling
		Button openBtn = new Button("OPEN");
		openBtn.setId("redBtn");
//		openBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
//			"-fx-background-color: gold;\n" + "-fx-text-fill: black ");
		
		openBtn.setOnAction(Event -> {
			
			// Open treasure chest sound effect 
			se.openChestSound();
			
			Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1500), ae -> 
			event.openChest(hero, floor, continueBtn, grid, closedIV, openIV, openBtn, display));
		
			timeline.getKeyFrames().add(frame);
			timeline.play();			
		}); 
				
		// Fixed width for columns
		for (int i = 0; i < 5; i++) {
			ColumnConstraints column = new ColumnConstraints(250);
			grid.getColumnConstraints().add(column);
		}		
			
		// Add nodes to the grid
		grid.setGridLinesVisible(true);

		GridPane.setHalignment(txtEvent, HPos.CENTER);
		GridPane.setHalignment(closedIV, HPos.CENTER);
		GridPane.setHalignment(openBtn,HPos.CENTER);
		grid.setAlignment(Pos.CENTER);

		grid.add(txtEvent, 2, 0);
		grid.add(closedIV, 2, 1);
		grid.add(openBtn, 2, 2);
		grid.add(display, 2, 3);
		grid.add(continueBtn, 4, 6);
		grid.setVgap(20); 
		grid.setHgap(20); 
		
		// Set background 
		grid.setId("insideTower");
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		// Create the scene
		Scene eventScene = new Scene(grid, 1280, 720);
		eventScene.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(eventScene);
		primaryStage.show();
		
	}
	
	/**
	 * This method creates a transition scene after death if the player has a revive
	 * 
	 * @param primaryStage The primary stage or window of the GUI
	 * @return reviveScene The scene giving the player the option to revive
	 */
	public Scene reviveScreen(Stage primaryStage) {
	    //Creating text for the page
	    Text reviveOption = new Text();
	    reviveOption.setText("Would you like to use a revive?");
	    reviveOption.setX(100);
	    reviveOption.setY(200);
	    reviveOption.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 75));
	    reviveOption.setFill(Color.WHITE);
	    DropShadow ds = new DropShadow();
	    ds.setColor(Color.WHITE);
	    reviveOption.setEffect(ds);
	    
	    //Adding image of revive 
	    ImageView revive = new ImageView(shop.getReviveImage());
	    revive.setLayoutX(550);
	    revive.setLayoutY(300);
	    DropShadow ds1 = new DropShadow();
	    ds1.setColor(Color.MEDIUMSPRINGGREEN);
	    revive.setEffect(ds1);
	    
	    
	    //Creating buttons and adding event handling
	    Button reviveBtn = new Button("Use revive");
	    reviveBtn.setId("whiteBtn");
	    reviveBtn.setOnAction(event-> {hero.revive();
	    	battleMusic.stop();
	    	se.transitionSound();
		battleScreen(primaryStage);
		});
	    
	    Button exitBtn = new Button("Don't use revive");
	    exitBtn.setId("whiteBtn");
	    exitBtn.setOnAction(event-> {gameOverScreen(primaryStage);
		});
	    
	    //Creating HBox, adding nodes and style
	    HBox hbBtn = new HBox(40);
	    hbBtn.getChildren().addAll(reviveBtn, exitBtn);
	    hbBtn.setLayoutX(470);
	    hbBtn.setLayoutY(550);
	    hbBtn.setAlignment(Pos.BOTTOM_CENTER);
	    
	    //Creating Pane, adding nodes and style
	    Pane display = new Pane();
	    display.getChildren().addAll(reviveOption, hbBtn, revive);
	
	    // Set background 
	    display.setId("insideTower");

	    //Fade Transition
	    FadeTransition ft = new FadeTransition(Duration.millis(1000), display);
	    ft.setFromValue(0);
	    ft.setToValue(1);
	    ft.play();
	    
	    //Adding Pane to Scene and Scene to Stage
	    Scene reviveScene = new Scene(display, 1280, 720);
	    reviveScene.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
	    primaryStage.setScene(reviveScene);
	    primaryStage.show();
	    return reviveScene;	    
	}

	/**
	 * This method creates screen when the player wins the game
	 * 
	 * @param primaryStage The primary stage/ window for displaying the GUI.
	 * @return gWon The scene that is displayed of the player wins the game.
	 */
	public Scene youWinScreen(Stage primaryStage) {
	    
		//Adding text to Pane
		Text youWin = new Text();
		youWin.setText("Congratulations. YOU WON!");
		youWin.setX(200);
		youWin.setY(130);
		youWin.setFont(Font.font("impact", FontWeight.BOLD, FontPosture.REGULAR, 80));
		youWin.setStroke(Color.WHITE);
		youWin.setStrokeWidth(1);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.WHITE);
		youWin.setEffect(ds);
		Text thankYou = new Text();
		thankYou.setText("Thank you for playing!");
		thankYou.setX(360);
		thankYou.setY(550);
		thankYou.setFont(Font.font("impact", FontWeight.BOLD, FontPosture.REGULAR, 60));
		thankYou.setStroke(Color.WHITE);
		thankYou.setStrokeWidth(1);
		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.CORNFLOWERBLUE);
		thankYou.setEffect(ds1);

		//Creating Pane 
		Pane gameWon = new Pane();

		//Creating the buttons to exit the game or play again
		Button exitBtn = new Button("Exit game");
		exitBtn.setId("whiteBtn");
		Button playAgainBtn = new Button("Play again");
		playAgainBtn.setId("whiteBtn");
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().addAll(exitBtn, playAgainBtn);
		hbBtn.setLayoutX(500);
		hbBtn.setLayoutY(600);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		
		//Adding eventHandling for buttons
		exitBtn.setOnAction(event-> {primaryStage.close();;});
		playAgainBtn.setOnAction(event-> {try {
		    	youWinMusic.stop();
		    	floor.setFloor(1);
		    	se.transitionSound();
			start(primaryStage);
		} catch (FileNotFoundException e) { 
			// Temporary handling of exception, will change what happens once tested.
			primaryStage.close();
		}});

		//Adding nodes to pane and set pane background
		gameWon.getChildren().addAll(hbBtn, youWin, thankYou);
		gameWon.setId("youWinBackground");
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), gameWon);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and Scene to Stage
		Scene gWon = new Scene(gameWon, 1280, 720);
		gWon.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(gWon);
		primaryStage.show();
		return gWon;

	}
	

	/**
	 * This method creates game over screen when the player loses to the enemy
	 * 
	 * @param primaryStage The primary stage/ window for displaying the GUI.
	 * @return gOver The scene displayed when the player loses the game.
	 */
	public Scene gameOverScreen(Stage primaryStage) {
		//Creating the game over image text for the Pane and adding effects
		Image gameOverText = new Image("gameover.png");
		ImageView gameOverText2 = new ImageView(gameOverText);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.DARKRED);
		gameOverText2.setEffect(ds);
		gameOverText2.setLayoutX(180);
		gameOverText2.setLayoutY(10);

		//Creating the buttons to exit the game or play again
		Button exitBtn = new Button("Exit game");
		exitBtn.setId("gameOverBtn");
		Button playAgainBtn = new Button("Play again");
		playAgainBtn.setId("gameOverBtn");
		HBox hbBtn = new HBox(25);
		hbBtn.getChildren().addAll(exitBtn, playAgainBtn);
		hbBtn.setLayoutX(500);
		hbBtn.setLayoutY(630);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		
			  
		//Adding eventHandlint for buttons
		exitBtn.setOnAction(event-> {primaryStage.close();});
		playAgainBtn.setOnAction(event-> {try {
		    	gameOverMusic.stop();
		    	se.transitionSound();
		    	floor.setFloor(1);
			start(primaryStage);
		} catch (FileNotFoundException e) { 
			// Temporary handling of exception, will change what happens once tested.
			primaryStage.close();
		}});

		//Creating Pane and adding nodes
		Pane gameOver = new Pane();
		gameOver.getChildren().addAll(gameOverText2, hbBtn);
		gameOver.setStyle(" -fx-background-color: black");

		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), gameOver);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and Scene to Stage
		Scene gOver = new Scene(gameOver, 1280, 720);
		gOver.setFill(Color.BLACK);
		gOver.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(gOver);
		primaryStage.show();
		return gOver;

	}
	/**
	 * This method creates the transition page after the user has cleared the floor.
	 * 
	 * @param primaryStage The primary stage/window of the GUI.
	 * @return transition The scene displayed after a battle to update the user 
	 */
	public Scene transitionScreen(Stage primaryStage) {
	    	//Creating text for the page
		Text clearedFloor = new Text();
		clearedFloor.setText("You cleared floor " + floor.getFloor() + "!");
		clearedFloor.setId("clearedFloorText");
		clearedFloor.setX(320);
		clearedFloor.setY(120);
		
		//Creating the buttons play for the player to continue on
		Button shopBtn = new Button("Magic Shop");
		shopBtn.setId("yellowBtn");
		shopBtn.setDisable(true);
		
		Button next = new Button("Next");
		next.setId("yellowBtn");
		next.setVisible(false);;

		Button continueBtn = new Button("Next Floor");
		continueBtn.setId("redBtn");
		HBox hbBtn = new HBox(15);
		hbBtn.getChildren().addAll(shopBtn, continueBtn, next);
		hbBtn.setLayoutX(430);
		hbBtn.setLayoutY(600);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		
		//Creating VBox and setting alignment
		VBox userUpdate = new VBox(20);
		userUpdate.setLayoutX(380);
		userUpdate.setLayoutY(250);
		userUpdate.setAlignment(Pos.CENTER);
			
		//Creating text for gold and xp gained
		Text goldGained = new Text();
		int gold = 10 + (int)(Math.random() * ((4) + 1) * floor.getFloor());
		hero.setGold(hero.getGold() +  gold);
		goldGained.setText("You gained " + (int)gold + " gold! Gold = " + hero.getGold());
		goldGained.setId("xpAndGoldText");
		
		Text xpGained = new Text();
		int xp= 50 * allEnemies.get(floor.getFloor()).size() + floor.getFloor() * 10;
		xpCount += xp;
		xpGained.setText("You gained " + xp + " xp! Xp = " + xpCount);
		xpGained.setId("xpAndGoldText");
		
		//Creating text for level up and the conditions to display it
		Text levelUp = new Text();
		Text atkUp = new Text();
		Text stamUp = new Text();
		Text defUp = new Text();
		Text mAtkUp = new Text();
		Text manaUp = new Text();
		
		if (xpCount >= (50 + hero.getLevel() * 80)) {
		    continueBtn.setVisible(false);
		    shopBtn.setVisible(false);
		    goldGained.setId("xpAndGoldTwoText");
		    xpGained.setId("xpAndGoldTwoText");
		    userUpdate.setLayoutX(340);
		    userUpdate.setLayoutY(180);
		    levelUp.setText("YOU GAINED A LEVEL! You are now Level " + (hero.getLevel() + 1) 
			    + "! \n\t\t You regained 20% stamina! \n");
		    levelUp.setId("levelUpText");
		    xpCount = 0;
		    next.setVisible(true);
		    next.setOnAction(event->{ next.setVisible(false);
		    continueBtn.setVisible(true);
		    shopBtn.setVisible(true);
			atkUp.setText("Your attack went up by " + hero.getAttackUp() + ". Attack =  " + hero.getAttack());
			atkUp.setId("staminaLevelUpdates");
			stamUp.setText("Your stamina went up by " + hero.getStaminaUp() + ". Stamina =  " + hero.getStamina());
			stamUp.setId("staminaLevelUpdates");
			defUp.setText("Your defense went up by " + hero.getDefenseUp() + ". Defense =  " + hero.getDefense());
			defUp.setId("staminaLevelUpdates");
		    
			if(hero.getType().contentEquals("Mage")) {
			    mAtkUp.setText("Your  magic attack went up by " + hero.getMagicAtkUp() + ". Magic Attack =  " + hero.getMagicAtk());
			    mAtkUp.setId("staminaLevelUpdates");
			    manaUp.setText("Your attack went up by " + hero.getManaUp() + ". Mana =  " + hero.getMana());
			    manaUp.setId("staminaLevelUpdates");
			};});
		   	    
		}
		
		//Adding nodes to VBox
		userUpdate.getChildren().addAll(goldGained, xpGained, levelUp, atkUp, stamUp, defUp, mAtkUp, manaUp);
	
		//Creating Pane and setting background
		Pane display = new Pane();
		display.setId("transitionBackground");	

		// Event handling for if there is a special event 
		this.event.eventHappen();
		if (this.event.isEvent() == true) {
			continueBtn.setOnAction(event -> {
			    se.transitionSound();
			    eventScreen(primaryStage);});
		} else {
			continueBtn.setOnAction(event -> {
			    battleMusic.stop();
			    se.transitionSound();
			    floor.incrementFloor();
			    battleScreen(primaryStage);});
		}
		
		// Event handling for shop, only available on 3rd, 6th and 9th floor 
		if (floor.getFloor() == 1 ||  floor.getFloor() == 6 || floor.getFloor() == 9) {
			shopBtn.setDisable(false);
		} 		
		shopBtn.setOnAction(event -> {
			se.shopSound();
			shopScreen(primaryStage);
			});
		
		//Adding nodes to pane
		display.getChildren().addAll(hbBtn, clearedFloor, userUpdate);
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and Scene to Stage
		Scene transition = new Scene(display, 1280, 720);
		transition.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		return transition;
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
	 * @return the isRogue
	 */
	public boolean isRogue() {
		return isRogue;
	}

	/**
	 * @param isRogue the isRogue to set
	 */
	public void setRogue(boolean isRogue) {
		this.isRogue = isRogue;
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

	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * @param shop the shop to set
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}

	/**
	 * @return the floor
	 */
	public Floor getFloor() {
		return floor;
	}

	/**
	 * @param floor the floor to set
	 */
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	

	/**
	 * @return the battleMusic
	 */
	public MediaPlayer getBattleMusic() {
	    return battleMusic;
	}

	/**
	 * @param battleMusic the battleMusic to set
	 */
	public void setBattleMusic(MediaPlayer battleMusic) {
	    this.battleMusic = battleMusic;
	}

	public static void main(String[] args) {
		launch(args);
	}
}