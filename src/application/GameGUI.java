package application;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.util.*;



/**
 * This class represents the GUI of the game and houses the instance variables
 * ,screens and GUI elements needed to run the game.
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
	private Event event;
	private MediaPlayer battleMusic; 
	private MediaPlayer openingMusic;
	private MediaPlayer gameOverMusic;
	private MediaPlayer youWinMusic;
	private SoundEffect se;
	private VBox heroInfo;
	private VBox heroInfoTwo;
	

	/**
	 * The constructor sets all booleans variables to false, 
	 * and initializes all necessary instance variables.
	 */
	public GameGUI() {
		isMage = false;
		isWarrior = false;
		isRogue = false;
		hero = new GameCharacters();
		allEnemies = new HashMap<Integer, ArrayList<GameCharacters>>();
		floor = new Floor();
		event = new Event();
		se = new SoundEffect();
		openingMusic = se.openingMusic();
		battleMusic = se.backgroundMusic();
		gameOverMusic = se.gameOverMusic();
		youWinMusic = se.youWinMusic();
		heroInfo = new VBox(15);
		heroInfoTwo = new VBox(15);

	}
	
	/**
	 * This is the start method that enables us to run/display our JavaFX
	 * application. It begins by displaying start screen and then allows us to
	 * continue through and play the game.
	 */
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		//Start Screen Scene creation
		Scene start = startScreen(primaryStage);
	    	
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
		
		//Create logo and text for start screen
		Image logo = new Image("ascension.png");
		ImageView logo1 = new ImageView(logo);
		logo1.setLayoutX(350);
		logo1.setLayoutY(80);
		logo1.setFitWidth(580);
		logo1.setFitHeight(520);
		logo1.setId("logo");
		DropShadow ds = new DropShadow(20, Color.WHITE);
		logo1.setEffect(ds);
		Text start = new Text("Click to START!");
		
		//Event handling for hovering over and clicking the logo
		logo1.setOnMouseEntered(event->{
		    	logo1.setLayoutX(350);
			logo1.setLayoutY(80);
			logo1.setFitWidth(600);
			logo1.setFitHeight(540);
			start.setId("whiteBtnText");
			start.setLayoutX(535);
			start.setLayoutY(640);
			DropShadow ds1 = new DropShadow(20, Color.RED);
			start.setVisible(true);
			start.setEffect(ds1);
			ds.setColor(Color.RED);
		});
		logo1.setOnMouseExited(event->{
		    	logo1.setLayoutX(350);
			logo1.setLayoutY(80);
			logo1.setFitWidth(580);
			logo1.setFitHeight(520);
			start.setVisible(false);
			ds.setColor(Color.WHITE);
		});
		
		logo1.setOnMouseClicked(event-> {se.transitionSound(); chooseCharacterScreen(primaryStage);
		});

		//Media player for music
		openingMusic.play();
		
		//Fade Transition
		screenFade(root);
		
		//Adding background image to Pane
		root.setId("startBackground");
		
		//Adding other element/nodes to Pane, then Pane to Scene
		root.getChildren().addAll(logo1, start);
		Scene startScene = new Scene(root, 1280, 720);
		startScene.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		return startScene;
	}
	
	/**
	 * This method allows us to create the wooden buttons/pane
	 * 
	 * @param type The text to be place on the button/pane
	 * @return pane The StackPane that will serve as a button
	 */
	public StackPane createWoodenButtons(String type) {
	    //Create image view and text,  add effects
	    Image btn = new Image("startButton.png", 250, 80, false, false);		
	    ImageView iv = new ImageView(btn);
	    DropShadow ds = new DropShadow(10, Color.ANTIQUEWHITE);
	    iv.setEffect(ds);
	    Text btnText = new Text();

	    //Determine Text the btn will display
	    if (type.equals("Mage")) {
		btnText.setText(type);
	    } else if (type.equals("Warrior")) {
		btnText.setText(type);
	    } else if (type.equals("Rogue")) {
		btnText.setText(type);
	    } 
	    
	    //create pane and add nodes, and style
	    StackPane pane = new StackPane();
	    pane.getChildren().addAll(iv,btnText);
	    pane.setAlignment(Pos.CENTER);
	    pane.setId("btnText");
	   
	    return pane;
	}
	
	/**
	 * This method houses the code needed for the screen that allows the player to
	 * choose their character type/hero.
	 * 
	 * @param primaryStage The primary Stage object of the JavaFX application GUI.
	 */
	private void chooseCharacterScreen(Stage primaryStage) {
		//Creating Text, positioning it and adding style and effects
		Text charOption = new Text();
		charOption.setText("Choose your character type");
		charOption.setX(140);
		charOption.setY(220);
		charOption.setId("characterOptionText");
		
		//Creating buttons for user selection
		StackPane magePane = createWoodenButtons("Mage");
		StackPane warriorPane = createWoodenButtons("Warrior");
		StackPane roguePane = createWoodenButtons("Rogue");
		
		// Clear prior assigned character type
		setMage(false);
		setWarrior(false);
		setRogue(false);
		
		//Event handling for when each button pane is pressed/hovered over
		eventHandleCharBtns(magePane, "Mage", primaryStage);
		eventHandleCharBtns(warriorPane, "Warrior", primaryStage);
		eventHandleCharBtns(roguePane, "Rogue", primaryStage);
		
		//Creating vertical box for buttons 
		VBox btns = new VBox(30);
		btns.setAlignment(Pos.CENTER);
		btns.setLayoutX(525);
		btns.setLayoutY(300);
		btns.getChildren().addAll(magePane, warriorPane, roguePane);

		//Creating Pane, adding background and then adding above nodes
		Pane display = new Pane();
		display.getChildren().addAll(charOption, btns, heroInfo, heroInfoTwo) ;
		display.setId("startTwoBackground");

		//Fade Transition
		screenFade(display);
		
		//Adding Pane to Scene and then Scene to primary stage and then showing
		Scene chooseChar = new Scene(display, 1280, 720);
		chooseChar.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(chooseChar);
		primaryStage.show();
	}
	/**
	 * This method handles the event for when character buttons are pressed or
	 * hovered over. 
	 * 
	 * @param pane The StackPane used to create the button
	 * @param text The text on the pane
	 * @param type The type of hero/character
	 * @param primaryStage The primary GUI window
	 */
	private void eventHandleCharBtns(StackPane pane, String type, Stage primaryStage) {
	    //Event handling for hen clicked, it creates corresponding hero
	    pane.setOnMouseClicked(event -> {
		se.transitionSound();
		if (type.equals("Mage")) {
		    setMage(true);
		} else if (type.equals("Warrior")) {
		    setWarrior(true);
		} else if (type.equals("Rogue")) {
		    setRogue(true);
		}
		nameCharScreen(primaryStage);
	    });
	    
	    //When entered, text becomes white and two info panes are shown
	    pane.setOnMouseEntered(event->{
		pane.getChildren().remove(1);
		Text btnText = new Text(type);
		btnText.setId("whiteBtnText");
		pane.getChildren().add(btnText);
		heroInfoBox(type);
		heroInfo.setVisible(true);
		heroInfoTwo.setVisible(true);
	    });
	    
	    //When exited, text is black and panes disappear
	    pane.setOnMouseExited(event->{
		pane.getChildren().remove(1);
		Text btnText = new Text(type);
		btnText.setId("btnText");
		pane.getChildren().add(btnText);
		heroInfo.setVisible(false);
		heroInfoTwo.setVisible(false);

	    });
	}

	
	/**
	 * This method creates the VBox that appears when you hover over each character type
	 * 
	 * @param type String containing the type of hero
	 * @return VBox containing basic info about each hero
	 */
	private VBox heroInfoBox(String type) {
	    //Setting style id for VBoxes
	    heroInfo.setId("heroInfoBox");
	    heroInfoTwo.setId("heroInfoBox");
	    
	    if (type.equals("Mage")) {
		heroInfo.getChildren().clear();
		heroInfo.setLayoutX(110);
		heroInfo.setLayoutY(270);
		heroInfoTwo.getChildren().clear();
		heroInfoTwo.setLayoutX(860);
		heroInfoTwo.setLayoutY(270);
		
		//Changes text based on hero button you hover over
		//Mage text
		Text info = new Text();
		GameCharacters mage = new Mage();
		info.setText("Mages have a stronger magical attack"+ "\n" 
			+ "but are limited by mana usage. This class"
			+"\n"+ "is recommended for strategic players." + "\n" +
			"\n" + " Attack: " + mage.getAttack() + "   Defense: " + mage.getDefense() + "   Stamina: " + mage.getStamina() 
			+  "\n" + "          Magic Atk: " + mage.getMagicAtk() + "	Mana: " + mage.getMana());
		heroInfo.getChildren().add(info);
		
		Text infoTwo = new Text();
		infoTwo.setText("		Special Attribute: Mana" + "\n" + 
		"                Special Ability: Fireball" + "\n" +
		"Fireball is a special attack that is stronger" + "\n" + 
		"than a normal attack. It also deals 20% more" + "\n" + 
		"damage to Melee enemies. It costs 50 mana " + "\n" + 
		"to use. Use other moves to restore 25 mana.");
		heroInfoTwo.getChildren().add(infoTwo);
		
	    } else if (type.equals("Warrior")) {
		heroInfo.getChildren().clear();
		heroInfo.setLayoutX(110);
		heroInfo.setLayoutY(380);
		heroInfoTwo.getChildren().clear();
		heroInfoTwo.setLayoutX(860);
		heroInfoTwo.setLayoutY(380);
		//Warrior text
		Text info = new Text();
		GameCharacters warrior = new Warrior();
		info.setText("Warriors have high stamina and defense."
			+"\n" + "This class is recommended for beginners." + "\n" +
			 "\n" +"			Attack: " + warrior.getAttack() + "\n" + 
			"			Defense: " + warrior.getDefense() + "\n" + 
			"			Stamina: " + warrior.getStamina());
		heroInfo.getChildren().add(info);
		
		Text infoTwo = new Text();
		infoTwo.setText("		Special Attribute: Defense" + "\n" + 
		"                   Special Ability: Sturdy" + "\n" +
		"The warrior specializes in health and defense." + "\n" + 
		"It gains more defense stats during level up" +
		"\n" + "and its Sturdy ability gives it 200 more" + "\n" + 
		"starting health than the other two classes.");
		heroInfoTwo.getChildren().add(infoTwo);
		
	    } else if (type.equals("Rogue")) {
		heroInfo.getChildren().clear();
		heroInfo.setLayoutX(110);
		heroInfo.setLayoutY(495);
		heroInfoTwo.getChildren().clear();
		heroInfoTwo.setLayoutX(860);
		heroInfoTwo.setLayoutY(495);
		//Rogue text
		Text info = new Text();
		GameCharacters rogue = new Rogue();
		info.setText("Rogues have a chance to attack twice."
			+"\n" + "This class is recommended for everyone."+ "\n" +
			"\n" + "			Attack: " + rogue.getAttack() + "\n" + 
			"			Defense: " + rogue.getDefense() + "\n" + 
			"			Stamina: " + rogue.getStamina());
		heroInfo.getChildren().add(info);
		
		Text infoTwo = new Text();
		infoTwo.setText("		  Special Attribute: Attack" + "\n" + "              Special Ability: Slice and Dice" + "\n" +
		"The rogue specializes in burst damage and raw" + "\n" + "attack power. It gains additional attack stats" +
		"\n" + "on level up. Its ability gives the rogue a 33%" + "\n" + 
		"to chance to hit an enemy twice in per attack.");
		heroInfoTwo.getChildren().add(infoTwo);
		
	    } 
	   
	    return heroInfo;
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

		//Creating label field and text, adding style
		Label charName = new Label("Character Name: ");
		charName.setId("characterNameText");
		TextField charNameBox = new TextField();

		//Creating submit button, adding style
		Button submitBtn = new Button("Enter Floor 1");
		submitBtn.setId("yellowBtn");
		submitBtn.setLayoutX(1050);
		submitBtn.setLayoutY(600);
		
		
		// Create back button, adding style
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
			error.setId("errorText");
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
		GridPane.setHalignment(error, HPos.CENTER);

		// Fixed height for rows 
		for (int i = 0; i < 3; i++) {
			RowConstraints row = new RowConstraints(40);
			getName.getRowConstraints().add(row);
		}
		
		//Configuring and style to grid and label
		getName.setId("getNameGrid");
		getName.setLayoutX(430);
		getName.setLayoutY(250);
		;
		
		//Creating Pane, adding above nodes and background to Pane
		Pane display = new Pane();
		display.getChildren().addAll(getName, backBtn, submitBtn);
		display.setId("startTwoBackground");
		
		//Fade Transition
		screenFade(display);

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
	 * This method creates the battleScreen, which is the central point for all game play.
	 * It creates the Pane for each floor of fighting, restores hero mana, 
	 * creates ArrayList of floorEnemies, and plays battle music.
	 * 
	 * @param primaryStage The primary stage/window of the JavaFX GUI.
	 */
	public void battleScreen(Stage primaryStage) {
		
	    //Mediaplayer for music
	   	battleMusic.play();

	   	//Create arrylist enemies for the floor
	   	ArrayList<GameCharacters> floorEnemies = new ArrayList<GameCharacters>();
		
		int healerCount = 0; //keeps track of enemy healers
		int nonHealerCount = 0; //keeps track of other enemies
		if (floor.getFloor() == 1 || floor.getFloor() == 2 || floor.getFloor() == 3) {
			int randEnemy = (int) (Math.random() * (2));
			if (randEnemy == 0) {
				floorEnemies.add(new MeleeEnemy(floor.getFloor(), 0));
			} else {
				floorEnemies.add(new RangedEnemy(floor.getFloor(), 0));
			}
		} else if (floor.getFloor() == 4 || floor.getFloor() == 5 || floor.getFloor() == 6) {
			for (int i = 0; i < 2; i++) {
				int randEnemy = (int) (Math.random() * (3));
				while (randEnemy == 2 && (healerCount == 1 || nonHealerCount == 0)) { //max one healer per battle and must have at least one non-healer
					randEnemy = (int) (Math.random() * (3));
				}
				if (randEnemy == 0) {
					floorEnemies.add(new MeleeEnemy(floor.getFloor(), i));
					nonHealerCount++;
				} else if (randEnemy == 1){
					floorEnemies.add(new RangedEnemy(floor.getFloor(), i));
					nonHealerCount++;
				} else if (randEnemy == 2){
					floorEnemies.add(new HealerEnemy(floor.getFloor(), i));
				}
			}
		} else if (floor.getFloor() == 7 || floor.getFloor() == 8 || floor.getFloor() == 9) {
			floorEnemies.add(new HealerEnemy(floor.getFloor(), 0)); //guaranteed one healer on these floors
			for (int i = 1; i < 3; i++) {
				int randEnemy = (int) (Math.random() * (2));
				if (randEnemy == 0) {
					floorEnemies.add(new MeleeEnemy(floor.getFloor(), i));
				} else {
					floorEnemies.add(new RangedEnemy(floor.getFloor(), i));
				}
			}
		} else if (floor.getFloor() == 10) {
		    	floorEnemies.add(new BossEnemy(10));
		}
		allEnemies.put(floor.getFloor(), floorEnemies);
		totalEnemyHealth = 0;
		for (int i = 0; i < floorEnemies.size(); i++) {
			totalEnemyHealth += floorEnemies.get(i).getCurrentStamina();
		}
		//Restore hero mana each new floor
		hero.setCurrentMana(hero.getMana());
		
		// Creation of pane, and scene. Adding pane to scene, and scene to stage
		Pane towerLevel = createTowerLevels(primaryStage, allEnemies.get(floor.getFloor()));
		Scene insideTower = new Scene(towerLevel, 1280, 720);
		primaryStage.setScene(insideTower);
		primaryStage.show();

	}

	/**
	 * This method creates the GUI elements needed for fighting inside
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
		
		//Caching pane for performance
		towerLevels.setCache(true);
		towerLevels.setCacheShape(true);
		towerLevels.setCacheHint(CacheHint.SPEED);

		// To display the background for the floor
		towerLevels.setId("insideTower");
		
		Canvas canvas = new Canvas(1280, 720);
		towerLevels.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		//To display floor number
		Text floorNum = new Text();
		floorNum.setText("Floor " + floor.getFloor());
		floorNum.setId("floorNumberText");
		floorNum.setX(600);
		floorNum.setY(50);

		// Adding hero and enemy images
		hero.displayCharacter(gc, false, false, false);
		
		for (int i = 0; i < floorCopy.size(); i++) {
			floorCopy.get(i).displayCharacter(gc, false, false, false);
		}

		//Code that controls the battle mechanics on each floor
		BattlePhaseDisplay display = new BattlePhaseDisplay();
		BattlePhase battle = new BattlePhase(primaryStage, floor.getFloor(), totalEnemyHealth, allEnemies, hero, gc,
				transitionScreen(primaryStage), youWinScreen(primaryStage), reviveScreen(primaryStage),
				gameOverScreen(primaryStage), battleMusic, gameOverMusic, youWinMusic, display);

		VBox heroStats = display.heroStatsDisplay(hero);
		display.dispCombatInfo(hero, allEnemies, floor.getFloor());
		display.dispDialogue();
		display.initButtons(hero);
		battle.idleAnimate();
		battle.heroAnimate();
		battle.eventButtons();
		GridPane grid = display.gridLayout(allEnemies.get(floor.getFloor()).size(), hero);
		
		//Fade Transition
		screenFade(towerLevels);

		// Adding grid to Pane , adding stylesheet to pane
		towerLevels.getChildren().addAll(heroStats, grid, floorNum);
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
		Shop shop = new Shop(hero);

		// Create the magic shop text
		Text welcome = new Text("Magic Shop");
		welcome.setId("shopTitle");
		GridPane.setHalignment(welcome, HPos.CENTER);

		// Error message
		Text errorMsg = new Text("BLABLABLABLA");
		errorMsg.setId("shopErrorText");
		errorMsg.setVisible(false);

		// Display all items currently in the hero's bag
		Text potionList = new Text(shop.shopDisplay());
		potionList.setId("shopText");

		// Description for cheap potion
		Text potion1 = new Text("+CHEAP POTION+ \n HP +100 \n PRICE: 50 GOLD");
		potion1.setId("shopText");

		// Input quantity for cheap potion
		TextField quantity1 = new TextField("Quantity");
		quantity1.setOnMouseClicked(event -> quantity1.clear());
		quantity1.setOpacity(0.8);
		quantity1.setMaxWidth(150);

		// Buy and sell buttons for cheap potion
		Button btnBuy1 = new Button("Buy");
		btnBuy1.setOnAction(event -> shop.buyPotion(hero.getCp(), quantity1, errorMsg, potionList));
		Button btnSell1 = new Button("Sell");
		btnSell1.setOnAction(event -> shop.sellPotion(hero.getCp(), quantity1, errorMsg, potionList));
		

		// Description for hyper potion
		Text potion2 = new Text("+HYPER POTION+ \n HP +350 \n PRICE: 150 GOLD");
		potion2.setId("shopText");


		// Input quantity for hyper potion
		TextField quantity2 = new TextField("Quantity");
		quantity2.setOnMouseClicked(event -> quantity2.clear());
		quantity2.setMaxWidth(150);
		quantity2.setOpacity(0.8);

		// Buy and sell buttons for hyper potion
		Button btnBuy2 = new Button("Buy");
		btnBuy2.setOnAction(event -> shop.buyPotion(hero.getHp(), quantity2, errorMsg, potionList));
		Button btnSell2 = new Button("Sell");
		btnSell2.setOnAction(event -> shop.sellPotion(hero.getHp(), quantity2, errorMsg, potionList));
		
		// Description for revive
		Text revive = new Text("+REVIVE POTION+ \n COME BACK TO LIFE \n PRICE: 250 GOLD");
		revive.setId("shopText");
		GridPane.setHalignment(revive, HPos.CENTER);
		
		// Description for revive quantity
		Text reviveQuant = new Text("MAX: 1");
		reviveQuant.setId("shopText");
		GridPane.setHalignment(reviveQuant, HPos.CENTER);

		// Buy and sell for revive 
		Button btnBuy3 = new Button("Buy");
		btnBuy3.setOnAction(event -> shop.buyRevive(errorMsg, potionList));
		Button btnSell3 = new Button("Sell");
		btnSell3.setOnAction(event -> shop.sellRevive(errorMsg, potionList));
		
		//Setting style for shop buttons
		btnBuy1.setId("whiteBtn");
		btnSell1.setId("whiteBtn");
		btnBuy2.setId("whiteBtn");
		btnSell2.setId("whiteBtn");
		btnBuy3.setId("whiteBtn");
		btnSell3.setId("whiteBtn");
		
		// Create imageView for the items at the shop
		ImageView ivPotion1 = new ImageView(shop.getCpImage());
		ImageView ivPotion2 = new ImageView(shop.getHpImage());
		ImageView ivRevive = new ImageView(shop.getReviveImage());
		DropShadow ds2 = new DropShadow();
		ds2.setColor(Color.MEDIUMPURPLE);
		ds2.setWidth(0.8);
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
		root.add(errorMsg, 1, 8);

		root.add(continueBtn, 4, 9);
		
		welcome.setTextAlignment(TextAlignment.CENTER);
		potion1.setTextAlignment(TextAlignment.CENTER);
		quantity1.setAlignment(Pos.CENTER);
		potion2.setTextAlignment(TextAlignment.CENTER);
		quantity2.setAlignment(Pos.CENTER);
		revive.setTextAlignment(TextAlignment.CENTER);
		reviveQuant.setTextAlignment(TextAlignment.CENTER);
		potionList.setTextAlignment(TextAlignment.CENTER);
		errorMsg.setTextAlignment(TextAlignment.LEFT);
		
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
		root.setId("shopBackground");

		//Fade Transition
		screenFade(root);
		
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
		txtEvent.setId("eventTitle");
		
		// Text for the description of the triggered event 
		Text display = new Text("");
		display.maxWidth(300);
		display.setId("eventText");
		GridPane.setHalignment(display, HPos.CENTER);
		
		// Image for the treasure chest 
		ImageView closedIV = new ImageView(event.getClosedBox());		
		ImageView openIV = new ImageView(event.getOpenBox());
		
		// Creating continue button and adding event handling
		Button continueBtn = new Button("NEXT FLOOR");
		continueBtn.setDisable(true);
		continueBtn.setLayoutX(500);
		continueBtn.setLayoutY(700);
		continueBtn.setId("redBtn");	
		continueBtn.setOnAction(event -> {
		    	battleMusic.stop();
		    	se.transitionSound();
			floor.incrementFloor();
			battleScreen(primaryStage);
					});
		// Create 'Open' Button and event handling
		Button openBtn = new Button("OPEN");
		openBtn.setId("yellowBtn");		
		openBtn.setOnAction(Event -> {
			
			// Open treasure chest sound effect 
			se.openChestSound();
			
			Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1500), ae -> 
			event.openChest(hero, floor, continueBtn, grid, closedIV, openIV, openBtn, display));
		    event.setEvent(false);
			
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
		screenFade(grid);
		
		// Create the scene
		Scene eventScene = new Scene(grid, 1280, 720);
		eventScene.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(eventScene);
		primaryStage.show();
		
	}
	
	/**
	 * This method adds a fade transition to the scene
	 * 
	 * @param p The Pane that will be added to the scene
	 */
	private void screenFade(Pane p) {
	    FadeTransition ft = new FadeTransition(Duration.millis(1000), p);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
	}
	
	/**
	 * This method creates a scene after death if the player has a revive
	 * 
	 * @param primaryStage The primary stage or window of the GUI
	 * @return reviveScene The scene giving the player the option to revive
	 */
	public Scene reviveScreen(Stage primaryStage) {
	    //Creating text for the page
	    Text reviveOption = new Text();
	    reviveOption.setText("Would you like to use a revive?");
	    reviveOption.setId("revive");
	    reviveOption.setX(100);
	    reviveOption.setY(200);
	    
	    //Adding image of revive 
		Shop shop = new Shop(hero);
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
	    screenFade(display);
	    
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
		youWin.setId("congrats");
		Text thankYou = new Text();
		thankYou.setText("Thank you for playing!");
		thankYou.setX(360);
		thankYou.setY(550);
		thankYou.setId("thankYou");

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
			primaryStage.close();
		}});

		//Adding nodes to pane and set pane background
		gameWon.getChildren().addAll(hbBtn, youWin, thankYou);
		gameWon.setId("youWinBackground");
		
		//Fade Transition
		screenFade(gameWon);

		//Adding Pane to Scene and Scene to Stage
		Scene gWon = new Scene(gameWon, 1280, 720);
		gWon.getStylesheets().add(getClass().getResource("GameGUI.css").toExternalForm());
		primaryStage.setScene(gWon);
		primaryStage.show();
		return gWon;

	}
	

	/**
	 * This method creates game over screen when the player dies (no revives)
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
			primaryStage.close();
		}});

		//Creating Pane and adding nodes
		Pane gameOver = new Pane();
		gameOver.getChildren().addAll(gameOverText2, hbBtn);
		gameOver.setStyle(" -fx-background-color: black");

		//Fade Transition
		screenFade(gameOver);

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
	 * It displays the things they have gained and stat details if they level up.
	 * 
	 * @param primaryStage The primary stage/window of the GUI.
	 * @return transition The scene displayed after a battle to update the user 
	 */
	public Scene transitionScreen(Stage primaryStage) {
	    	//Creating text for the page
		Text clearedFloor = new Text();
		clearedFloor.setText("You cleared floor " + floor.getFloor() + "!");
		clearedFloor.setId("clearedFloorText");
		clearedFloor.setX(350);
		clearedFloor.setY(120);
		
		//Creating the buttons play for the player to continue on
		Button shopBtn = new Button("Magic Shop");
		shopBtn.setId("yellowBtn");
		shopBtn.setDisable(true);
		
		Button next = new Button("Next");
		next.setId("yellowBtn");
		next.setVisible(false);;
		next.setLayoutX(600);
		next.setLayoutY(580);

		Button continueBtn = new Button("Next Floor");
		continueBtn.setId("redBtn");
		HBox hbBtn = new HBox(15);
		hbBtn.getChildren().addAll(shopBtn, continueBtn);
		hbBtn.setLayoutX(500);
		hbBtn.setLayoutY(650);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		
		//Creating VBox and setting alignment
		VBox userUpdate = new VBox(20);
		userUpdate.setLayoutX(330);
		userUpdate.setLayoutY(250);
		userUpdate.setAlignment(Pos.CENTER);
			
		//Creating text for gold and xp gained
		Text goldGained = new Text();
		int gold = (5 * floor.getFloor()) + (allEnemies.get(floor.getFloor()).size() * 10);
		hero.setGold(hero.getGold() +  gold);
		goldGained.setText("You gained " + (int)gold + " gold! Gold = " + hero.getGold());
		goldGained.setId("xpAndGoldText");
		
		Text xpGained = new Text();
		int xp= 50 * allEnemies.get(floor.getFloor()).size() + floor.getFloor() * 10;
		xpCount += xp;
		xpGained.setText("You gained " + xp + " experience points! EXP = " + xpCount);
		xpGained.setId("xpAndGoldText");
		
		//Creating text for level up and the conditions to display it
		Text levelUp = new Text();
		Text atkUp = new Text();
		Text stamUp = new Text();
		Text defUp = new Text();
		Text mAtkUp = new Text();
		Text manaUp = new Text();
		
		if (xpCount >= (25 + hero.getLevel() * 175)) {
		    continueBtn.setVisible(false);
		    shopBtn.setVisible(false);
		    goldGained.setId("xpAndGoldTwoText");
		    xpGained.setId("xpAndGoldTwoText");
		    userUpdate.setLayoutX(340);
		    userUpdate.setLayoutY(180);
		    levelUp.setText("YOU GAINED A LEVEL! You are now Level " + (hero.getLevel() + 1) 
			    + "! \n\t\t You regained 30% stamina! \n");
		    levelUp.setId("levelUpText");
		    xpCount = 0;
		    next.setVisible(true);
		    next.setOnAction(event->{ next.setDisable(true);
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
		if (floor.getFloor() == 3 ||  floor.getFloor() == 6 || floor.getFloor() == 9) {
			shopBtn.setDisable(false);
		} 		
		shopBtn.setOnAction(event -> {
			se.shopSound();
			shopScreen(primaryStage);
			});
		
		//Adding nodes to pane
		display.getChildren().addAll(hbBtn, clearedFloor, userUpdate, next);
		
		//Fade Transition
		screenFade(display);

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