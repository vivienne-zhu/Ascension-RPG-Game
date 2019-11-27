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
		//Scene start = startScreen(primaryStage);
	    	
	    	Scene test = gameOverScreen(primaryStage);
		//Setting title of primary stage window, adding start scene and showing primary stage
		primaryStage.setTitle("Tower Challenge");
		primaryStage.setScene(test);
		primaryStage.show();
	}

	/**
	 * This method is responsible for displaying the start screen for the game
	 * and plays music for the opening scenes.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
	 * @return startScene The Scene startScene
	 */
	public Scene startScreen(Stage primaryStage) {
		//Creating Pane which will display all the elements/ nodes
		Pane root = new Pane();

//		Creating Start button, adding style and necessary configurations
//		Button btn = new Button("START");
		Image btn = new Image("startButton.png", 250, 80, false, false);		
		ImageView iv = new ImageView(btn);
		Text start = new Text("START");
		start.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 35));
		start.setFill(Color.BLACK);
		
	    StackPane pane = new StackPane();
	    pane.getChildren().addAll(iv, start);
	    pane.setAlignment(Pos.CENTER);
	    pane.setLayoutX(525);
	    pane.setLayoutY(470);
	    pane.setOnMouseClicked(event-> {se.transitionSound(); chooseCharacterScreen(primaryStage);
		});
		
//		btn.setLayoutX(600);
//		btn.setLayoutY(500);
//		btn.setAlignment(Pos.CENTER);
//		btn.setPrefSize(100, 50);
//		btn.setStyle(" -fx-font: normal bold 20px 'serif' ");

		//Event Handling for when Start button is pressed
//		btn.setOnAction(event-> {se.transitionSound(); chooseCharacterScreen(primaryStage);
//		});
		
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
		
		//Mediaplayer for music
		openingMusic.play();
		
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		//Adding background image to Pane
		root.setStyle(" -fx-background-image: url(\"Tower.jpg\");\n" + 
			"    -fx-background-size: cover;");

		//Adding other element/nodes to Pane, then Pane to Scene
		root.getChildren().addAll(title, pane);
		Scene startScene = new Scene(root, 1280, 720);
		return startScene;
	}
	
	/**
	 * This method houses the code needed for the screen that allows the player to
	 * choose their character type/fighter.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
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
		Button rougueBtn = new Button("Rogue");
		rougueBtn.setLayoutX(600);
		rougueBtn.setLayoutY(550);
		rougueBtn.setPrefSize(100, 50);
		rougueBtn.setFont(Font.font(20));
		

		//Event handling for when each button is pressed
		mageBtn.setOnAction(event -> {
		    	se.transitionSound();
			setMage(true);
			nameCharScreen(primaryStage);
		});
		warriorBtn.setOnAction(event -> {
		    	se.transitionSound();
			setWarrior(true);
			nameCharScreen(primaryStage);
		});
		rougueBtn.setOnAction(event -> {
		    	se.transitionSound();
			setRogue(true);
			nameCharScreen(primaryStage);
		});


		//Creating Pane, adding background and then adding above nodes
		Pane display = new Pane();
		display.getChildren().addAll(mageBtn, warriorBtn, rougueBtn, charOption);
		display.setStyle(" -fx-background-image: url(\"Tower.jpg\");\n" + 
			"    -fx-background-size: cover;");
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and then Scene to primary stage and then showing
		Scene chooseChar = new Scene(display, 1280, 720);

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
	public void nameCharScreen(Stage primaryStage) {
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
		
		//EventHandling and error checking for empty textfield
		Text error = new Text();
		getName.add(error, 0, 4);
		
		submitBtn.setOnAction(event -> {
		    if (charNameBox.getText().isEmpty() == true) {
			se.errorSound();
			error.setFill(Color.RED);
			error.setStyle(" -fx-font: normal bold 30px 'serif' ");
			error.setText("Please enter name to continue.");
		    } else {
			openingMusic.stop();
			String name = charNameBox.getText();
			setHeroName(name);
			createHero();
			se.transitionSound();
			battleScreen(primaryStage);
		    }
		});

		//Creating Pane, adding above nodes and background to Pane
		Pane display = new Pane();
		display.getChildren().addAll(getName);
		display.setStyle(" -fx-background-image: url(\"Tower.jpg\");\n" + 
			"    -fx-background-size: cover;");
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

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
		//Below enemy created for testing purposes
		//These will not be hard-coded in the future
//		MeleeEnemy orc = new MeleeEnemy(floor.getFloor(), 0);
//		floorOne.add(orc);
//		MeleeEnemy dummy = new MeleeEnemy(floor.getFloor(), 1);
//		floorOne.add(dummy);
//		MeleeEnemy dummy2 = new MeleeEnemy(floor.getFloor(), 2);
//		floorOne.add(dummy2);
//		allEnemies.put(1, floorOne);
//
//
//
//		ArrayList<GameCharacters> floorTwo = new ArrayList<GameCharacters>();
//		MeleeEnemy orcTwo = new MeleeEnemy(floor.getFloor(), 0);
//		floorTwo.add(orcTwo);
//		//	MeleeEnemy dummyTwo = new MeleeEnemy(floor.getFloor(), 1);
//		//	floorTwo.add(dummyTwo);
//		allEnemies.put(2, floorTwo);
		
	    	//Mediaplayer for music
	    	battleMusic.play();
	    

		//Later on, these will not all be meleeEnemys. They will be randomly generated. Will add when other enemies are balanced
		ArrayList<GameCharacters> floorEnemies = new ArrayList<GameCharacters>();
		if (floor.getFloor() == 1 || floor.getFloor() == 2 || floor.getFloor() == 3) {
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
		} else if (floor.getFloor() == 4 || floor.getFloor() == 5 || floor.getFloor() == 6) {
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 1));
		} else if (floor.getFloor() == 7 || floor.getFloor() == 8 || floor.getFloor() == 9) {
			floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
			floorEnemies.add(new HealerEnemy(floor.getFloor(), 1));
			floorEnemies.add(new RangedEnemy(floor.getFloor(), 2));
		}
		allEnemies.put(floor.getFloor(), floorEnemies);
		totalEnemyHealth = 0;
		for (int i = 0; i < floorEnemies.size(); i++) {
			totalEnemyHealth += floorEnemies.get(i).getCurrentStamina();
		}

		hero.setCurrentMana(hero.getMana());
		
		// Creation of pane -->currently here for GUI testing
		//System.out.println(allEnemies.get(0));
		
		Pane towerLevel = createTowerLevels(primaryStage, allEnemies.get(floor.getFloor()));

		//Code to be added when enemy hashMap completed
		//ArrayList<GameCharacters> tempEnemies = new ArrayList<>();
		//tempEnemies = allEnemies.get(floor.getFloor()); --> get arrayList from enemy hashMap

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
	public Pane createTowerLevels(Stage primaryStage, ArrayList<GameCharacters> floorCopy) {
	    	//Creating pane
		Pane towerLevels = new Pane();

		// To display the background for the floor
		towerLevels.setStyle(" -fx-background-image: url(\"pixelBackLower1.png\");\n" + 
			"    -fx-background-size: cover;");
		
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
		DropShadow ds = new DropShadow();
		ds.setColor(Color.MEDIUMSLATEBLUE);
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
		potionList.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 0.5;");

		// Description for cheap potion
		Text potion1 = new Text("+CHEAP POTION+ \n HP +100 \n PRICE: 50 GOLD");
		potion1.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		potion1.setFill(Color.WHITE);
		potion1.setEffect(d);
		potion1.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 0.5;");

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
		potion2.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 0.5;");

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
		revive.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 0.5;");
		GridPane.setHalignment(revive, HPos.CENTER);
		
		// Description for revive quantity
		Text reviveQuant = new Text("MAX ONE AT A TIME");
		reviveQuant.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		reviveQuant.setFill(Color.WHITE);
		reviveQuant.setEffect(d);
		reviveQuant.setStyle("-fx-stroke: black;\n" + "-fx-stroke-width: 0.5;");
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
		Button continueBtn = new Button("NEXT FLOOR");
		continueBtn.setLayoutX(500);
		continueBtn.setLayoutY(700);
		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");		 
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

		root.add(continueBtn, 3, 9);
		
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
		GridPane.setHalignment(continueBtn, HPos.CENTER);
		
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
		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");		
		continueBtn.setOnAction(event -> {
		    	battleMusic.stop();
		    	se.transitionSound();
			floor.incrementFloor();
			battleScreen(primaryStage);
					});
		// Create 'Open' Button and event handling
		Button openBtn = new Button("OPEN");
		openBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		
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
		grid.setStyle(" -fx-background-image: url(\"pixelBackLower1.png\");\n" + 
			"    -fx-background-size: cover;");
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(500), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		// Create the scene
		Scene eventScene = new Scene(grid, 1280, 720);
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
	    ds1.setColor(Color.PURPLE);
	    revive.setEffect(ds1);
	    
	    
	    //Creating buttons and adding event handling
	    Button reviveBtn = new Button("Use revive");
	    reviveBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
	    reviveBtn.setOnAction(event-> {hero.revive();
	    	battleMusic.stop();
	    	se.transitionSound();
		battleScreen(primaryStage);
		});
	    
	    Button exitBtn = new Button("Don't use revive");
	    exitBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
	    exitBtn.setOnAction(event-> {gameOverScreen(primaryStage);
		});
	    
	    //Creating HBox, adding nodes and style
	    HBox hbBtn = new HBox(10);
	    hbBtn.getChildren().addAll(reviveBtn, exitBtn);
	    hbBtn.setLayoutX(500);
	    hbBtn.setLayoutY(550);
	    hbBtn.setAlignment(Pos.BOTTOM_CENTER);
	    
	    //Creating Pane, adding nodes and style
	    Pane display = new Pane();
	    display.getChildren().addAll(reviveOption, hbBtn, revive);
	
	    // Set background 
	    display.setStyle(" -fx-background-image: url(\"pixelBackLower1.png\");\n" + 
			"    -fx-background-size: cover;");

	    //Fade Transition
	    FadeTransition ft = new FadeTransition(Duration.millis(1000), display);
	    ft.setFromValue(0);
	    ft.setToValue(1);
	    ft.play();
	    
	    //Adding Pane to Scene and Scene to Stage
	    Scene reviveScene = new Scene(display, 1280, 720);
	    reviveScene.setFill(Color.ROYALBLUE);
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
		thankYou.setX(380);
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
		exitBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: cornflowerblue");
		Button playAgainBtn = new Button("Play again");
		playAgainBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: cornflowerblue");
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
		gameWon.setStyle(" -fx-background-image: url(\"youWin1a.jpg\");\n" + 
			"-fx-background-size: cover;");
		
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), gameWon);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and Scene to Stage
		Scene gWon = new Scene(gameWon, 1280, 720);
		gWon.setFill(Color.GOLD);
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
		exitBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: red");
		Button playAgainBtn = new Button("Play again");
		playAgainBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: red");
		HBox hbBtn = new HBox(25);
		hbBtn.getChildren().addAll(exitBtn, playAgainBtn);
		hbBtn.setLayoutX(500);
		hbBtn.setLayoutY(600);
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
		clearedFloor.setX(320);
		clearedFloor.setY(120);
		clearedFloor.setFont(Font.font("impact", FontWeight.BOLD, FontPosture.REGULAR, 75));
		clearedFloor.setStroke(Color.WHITE);
		clearedFloor.setStrokeWidth(1);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLD);
		clearedFloor.setEffect(ds);
		
		//Creating the buttons play for the player to continue on
		Button shopBtn = new Button("Go to the Magic Shop");
		shopBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: darkgoldenrod");
		shopBtn.setDisable(true);
		
		Button next = new Button("Next");
		next.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: darkgoldenrod");
		next.setVisible(false);;

		Button continueBtn = new Button("Continue playing");
		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif';\n" + 
			"-fx-background-color: brown");
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
		goldGained.setFill(Color.WHITE);
		goldGained.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
			
		Text xpGained = new Text();
		int xp= 50 * allEnemies.get(floor.getFloor()).size() + floor.getFloor() * 10;
		xpCount += xp;
		xpGained.setText("You gained " + xp + " xp! Xp = " + xpCount);
		xpGained.setFill(Color.WHITE);
		xpGained.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
		
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
		    goldGained.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 26));
		    xpGained.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 26));
		    userUpdate.setLayoutX(340);
		    userUpdate.setLayoutY(180);
		    levelUp.setText("YOU GAINED A LEVEL! You are now Level " + (hero.getLevel() + 1) 
			    + "! \n\t\t You regained 20% stamina! \n");
		    levelUp.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 27));
		    levelUp.setFill(Color.GOLD);
		    levelUp.setStroke(Color.WHITE);
		    levelUp.setStrokeWidth(0.5);
		    xpCount = 0;
		    next.setVisible(true);
		    next.setOnAction(event->{ next.setVisible(false);
		    continueBtn.setVisible(true);
		    shopBtn.setVisible(true);
			atkUp.setText("Your attack went up by " + hero.getAttackUp() + ". Attack =  " + hero.getAttack());
			atkUp.setFill(Color.WHITE);
			atkUp.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 20));
			stamUp.setText("Your stamina went up by " + hero.getStaminaUp() + ". Stamina =  " + hero.getStamina());
			stamUp.setFill(Color.WHITE);
			stamUp.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 20));
			defUp.setText("Your defense went up by " + hero.getDefenseUp() + ". Defense =  " + hero.getDefense());
			defUp.setFill(Color.WHITE);
			defUp.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 20));
		    
			if(hero.getType().contentEquals("Mage")) {
			    mAtkUp.setText("Your  magic attack went up by " + hero.getMagicAtkUp() + ". Magic Attack =  " + hero.getMagicAtk());
			    mAtkUp.setFill(Color.WHITE);
			    mAtkUp.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 20));
			    manaUp.setText("Your attack went up by " + hero.getManaUp() + ". Mana =  " + hero.getMana());
			    manaUp.setFill(Color.WHITE);
			    manaUp.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 20));
			};});
		   	    
		}
		
		//Adding nodes to VBox
		userUpdate.getChildren().addAll(goldGained, xpGained, levelUp, atkUp, stamUp, defUp, mAtkUp, manaUp);
	
		//Creating Pane and setting background
		Pane display = new Pane();
		display.setStyle(" -fx-background-image: url(\"transition.jpg\");\n" + 
			"    -fx-background-size: cover;");
		

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
		    	se.transitionSound();
			shopScreen(primaryStage);});
		
		//Adding nodes to pane
		display.getChildren().addAll(hbBtn, clearedFloor, userUpdate);
		
		//Fade Transition
		FadeTransition ft = new FadeTransition(Duration.millis(1000), display);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		//Adding Pane to Scene and Scene to Stage
		Scene transition = new Scene(display, 1280, 720);
		//transition.setFill(Color.GREY);
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