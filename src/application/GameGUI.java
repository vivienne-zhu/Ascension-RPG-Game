package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the GUI of the game and houses the instance variables
 * needed to capture user input and run the game. Can be run to see preliminary phases of GUI 
 * (Start scene, Character selection and naming, incomplete floor 1 of the tower). 
 * Currently this class is only to begin testing GUI elements and its design will be 
 * revised heavily before final submission (class will be made more cohesive and DRY).
 * 
 * @author Shari Sinclair, JiayuZhu and David Cai
 *
 */
public class GameGUI extends Application {
	private boolean isMage;
	private boolean isWarrior;
	private boolean isArcher;
	private GameCharacters hero;
	private String heroName;
	private HashMap<Integer, ArrayList<GameCharacters>> allEnemies;
	private int totalCount;
	private Shop shop;
	private Floor floor;
	

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
		allEnemies = new HashMap<Integer, ArrayList<GameCharacters>>();
		shop = new Shop();
		floor = new Floor();

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
		
		//EventHandling and error checking for empty textfield
		Text error = new Text();
		getName.add(error, 0, 4);
		
		submitBtn.setOnAction(event -> {
		    if (charNameBox.getText().isEmpty() == true) {
			error.setFill(Color.RED);
			error.setStyle(" -fx-font: normal bold 30px 'serif' ");
			error.setText("Please enter name to continue.");
		    } else {
			String name = charNameBox.getText();
			setHeroName(name);
			createHero();
			fullGame(primaryStage);
		    }
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
		//These will not be hardcoded in the future
		ArrayList<GameCharacters> floorOne = new ArrayList<GameCharacters>();
		MeleeEnemy orc = new MeleeEnemy(floor.getFloor(), 0);
		floorOne.add(orc);
		MeleeEnemy dummy = new MeleeEnemy(floor.getFloor(), 1);
		floorOne.add(dummy);
		allEnemies.put(1, floorOne);

		ArrayList<GameCharacters> floorTwo = new ArrayList<GameCharacters>();
		MeleeEnemy orcTwo = new MeleeEnemy(floor.getFloor(), 0);
		floorTwo.add(orcTwo);
		//	MeleeEnemy dummyTwo = new MeleeEnemy(floor.getFloor(), 1);
		//	floorTwo.add(dummyTwo);
		allEnemies.put(2, floorTwo);
		
		totalCount = allEnemies.get(floor.getFloor()).size();

		hero.setCurrentStamina(hero.getStamina());

		// Creation of pane -->currently here for GUI testing
		//System.out.println(allEnemies.get(0));
		Pane towerLevel = createTowerLevels(primaryStage, allEnemies.get(floor.getFloor()));

		//Code to be added when enemy hashMap completed
		//ArrayList<GameCharacters> tempEnemies = new ArrayList<>();
		//tempEnemies = allEnemies.get(floor.getFloor()); --> get arrayList from enemy hashMap

		// Pane towerLevel = createTowerLevels(primaryStage, tempEnemies); --> Will replace above code

		/*   
		 *   Logics:
		 *   	//in enemyTurn --> if hero dies --> GameOver Screen() (REVIVE MECHANICS built into game over screen)
		 *     //heroTurn -->when all enemies die: Transition page saying player cleared the floor
		 *     					if floor = 3,6, 9 , shop button active--> Shop scene, w/return button
		 *     		                      --> else (not floor 3,6,9): continue fighting 
		 *  			If there is and event-->eventScene tells what event happened & has continueBtn to continue fighting
		 * If win on floor 10 -->  youWinScreen built in
		 */


		// Button to shop --> REMOVE COMMMENTS TO QUICKLY TEST SHOP
		// This has been added to correct scene & will be removed when the full game method is completed 
		//Button shopBtn = new Button("Go to the Magic Shop");
		//shopBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");

		// Event handling for shop button 
		//shopBtn.setOnAction(event -> {
		//shop(primaryStage);});

		//towerLevel.getChildren().add(shopBtn);

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
	public Pane createTowerLevels(Stage primaryStage, ArrayList<GameCharacters> allChar) {
		Pane towerLevels = new Pane();

		// To display the background for the floor
		Image tower = new Image("pixelBack.png");
		BackgroundImage background = new BackgroundImage(tower, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background insideTowerBackground = new Background(background);
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

		// TEST - Adding hero and boss images
		hero.displayCharacter(gc, false, false);
		allChar.get(0).displayCharacter(gc, false, false);
		for (int i = 1; i < allChar.size(); i++) {
			allChar.get(i).displayCharacter(gc, false, false);
		}
		BattlePhase battle = new BattlePhase(primaryStage, floor.getFloor());
		battle.dispCombatInfo(hero, allEnemies, floor.getFloor());
		battle.dispDialogue();
		battle.initButtons();
		battle.eventButtons(allEnemies, hero, gc, shop, totalCount, transitionScreen(primaryStage), youWinScreen(primaryStage));
		GridPane grid = battle.gridLayout(allEnemies.get(floor.getFloor()).size());

		// Setting Background for Pane, adding grid to Pane 
		towerLevels.setBackground(insideTowerBackground);
		towerLevels.getChildren().addAll(grid, floorNum);

		return towerLevels;
	}

	/**
	 * This will generate the shop screen, where player is able to buy and sell
	 * items.
	 */
	public void shop(Stage primaryStage) {
		// Create grid pane
		GridPane root = new GridPane();

		// Create the magic shop text
		Text welcome = new Text("Magic Shop");
		welcome.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 70));
		welcome.setFill(Color.GOLDENROD);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLDENROD);
		welcome.setEffect(ds);
		GridPane.setHalignment(welcome, HPos.CENTER);

		// Error message
		Text errorMsg = new Text("BLABLABLABLA");
		errorMsg.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		errorMsg.setFill(Color.GOLDENROD);
		errorMsg.setVisible(false);

		// Display all items currrently in the hero's bag
		Text potionList = new Text(shop.shopDisplay(hero));
		potionList.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		potionList.setFill(Color.GOLDENROD);

		// Description for cheap potion
		Text potion1 = new Text("+CHEAP POTION+ \n HP +100 \n PRICE: 50 GOLD");
		potion1.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		potion1.setFill(Color.GOLDENROD);

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
		potion2.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		potion2.setFill(Color.GOLDENROD);

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
		Text revive = new Text("+REVIVE STONE+ \n MAGIC POWER \n BRING THE DEAD BACK TO LIFE \n PRICE: 200 GOLD");
		revive.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
		revive.setFill(Color.GOLDENROD);
		GridPane.setHalignment(revive, HPos.CENTER);

		// Buy and sell for revive 
		Button btnBuy3 = new Button("Buy");
		this.shop.buyRevive(hero, btnBuy3, errorMsg, potionList);
		Button btnSell3 = new Button("Sell");
		this.shop.sellRevive(hero, btnSell3, errorMsg, potionList);

		// Create imageView for the items at the shop
		ImageView ivPotion1 = new ImageView(this.shop.getCpImage());
		ImageView ivPotion2 = new ImageView(this.shop.getHpImage());
		ImageView ivRevive = new ImageView(this.shop.getReviveImage());

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
		continueBtn.setOnAction(event -> {
			floor.incrementFloor();
			fullGame(primaryStage);
		});
		
		// Add nodes to the grid pane
		root.setGridLinesVisible(false);
		root.setHgap(10);
		root.setVgap(5);
		root.add(welcome, 2, 0);
		root.add(ivPotion1, 1, 1);
		root.add(potion1, 1, 2);
		root.add(quantity1, 1, 3);
		root.add(btnBuy1, 1, 4);
		root.add(btnSell1, 1, 5);
		root.add(ivPotion2, 2, 1);
		root.add(potion2, 2, 2);
		root.add(quantity2, 2, 3);
		root.add(btnBuy2, 2, 4);
		root.add(btnSell2, 2, 5);
		root.add(ivRevive, 3, 1);
		root.add(revive, 3, 2);
		root.add(btnBuy3, 3, 3);
		root.add(btnSell3, 3, 4);
		root.add(potionList, 1, 7);
		root.add(errorMsg, 1, 8);
		root.setAlignment(Pos.CENTER);
		root.add(continueBtn, 4, 9);

		// Set background
		BackgroundImage shopBg1 = new BackgroundImage(this.shop.getShopBg(), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background shopBg2 = new Background(shopBg1);
		root.setBackground(shopBg2);

		// Create the scene
		Scene shopScene = new Scene(root, 1280, 720);
		primaryStage.setScene(shopScene);
		primaryStage.show();

	}
	
	/**
	 * This method creates the screen when a special event occurs. 
	 * 
	 * @param primaryStage
	 */
	public void event(Stage primaryStage) {
		GridPane grid = new GridPane();
		
		// Text for the event 
		Text txtEvent = new Text("A MYSTERIOUS BOX...");
		txtEvent.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 70));
		txtEvent.setFill(Color.GOLDENROD);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLDENROD);
		txtEvent.setEffect(ds);
				
		// Image for the treasure chest 
		Image closedBox = new Image("closed_treasure.png", 300, 300, false, false);
		Image openBox = new Image("open_treasure.png", 300, 300, false, false);
		ImageView closedIV = new ImageView(closedBox);		
		ImageView openIV = new ImageView(openBox);
		
		// Create 'Open' Button 
		Button openBtn = new Button("OPEN");
		openBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		
		openBtn.setOnAction(Event -> {
			grid.getChildren().remove(closedIV);
			grid.add(openIV, 2, 1);
			
			GridPane.setHalignment(openIV, HPos.CENTER);
			openBtn.setVisible(false);
		}); 
		
		//Creating continue button and adding event handling
		Button continueBtn = new Button("NEXT FLOOR");
		continueBtn.setLayoutX(500);
		continueBtn.setLayoutY(700);
		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");		
		continueBtn.setOnAction(event -> {
				floor.incrementFloor();
				fullGame(primaryStage);
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
		grid.add(continueBtn, 4, 6);
	    grid.setVgap(20); 
	    grid.setHgap(20); 
		
		// Set background 
		Image tower = new Image("pixelBack.png");
		BackgroundImage background = new BackgroundImage(tower, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background towerBackground = new Background(background);
		grid.setBackground(towerBackground);
		
		// Create the scene
		Scene eventScene = new Scene(grid, 1280, 720);
		primaryStage.setScene(eventScene);
		primaryStage.show();
		
	}

	/**
	 * This method creates screen when the player wins the game
	 * 
	 * @param primaryStage The primary stage/ window for displaying the GUI.
	 */
	public Scene youWinScreen(Stage primaryStage) {

		//Creating the treasure images for the Pane and adding effects
		Image treasureChest = new Image("gold_treasure.png");
		ImageView treasureChest1 = new ImageView(treasureChest);
		Image treasureChest2 = new Image("gold_treasure.png");
		ImageView treasureChest3 = new ImageView(treasureChest2);
		Image treasureChest4 = new Image("closed_treasure.png");
		ImageView treasureChest5 = new ImageView(treasureChest4);
		treasureChest1.setLayoutX(250);
		treasureChest1.setLayoutY(300);
		treasureChest3.setLayoutX(750);
		treasureChest3.setLayoutY(300);
		treasureChest5.setLayoutX(500);
		treasureChest5.setLayoutY(200);

		//Adding text to Pane
		Text youWin = new Text();
		youWin.setText("Congratulations. YOU WON!");
		youWin.setX(120);
		youWin.setY(130);
		youWin.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 75));
		DropShadow ds = new DropShadow();
		ds.setColor(Color.CHOCOLATE);
		youWin.setEffect(ds);
		Text thankYou = new Text();
		thankYou.setText("Thank you for playing!");
		thankYou.setX(350);
		thankYou.setY(550);
		thankYou.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 50));
		thankYou.setEffect(ds);

		//Creating Pane 
		Pane gameWon = new Pane();

		//Creating the buttons to exit the game or play again
		Button exitBtn = new Button("Exit game");
		exitBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		Button playAgainBtn = new Button("Play again");
		playAgainBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().addAll(exitBtn, playAgainBtn);
		hbBtn.setLayoutX(500);
		hbBtn.setLayoutY(600);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);

		//Adding eventHandling for buttons
		exitBtn.setOnAction(event-> {primaryStage.close();;});
		playAgainBtn.setOnAction(event-> {try {
			start(primaryStage);
		} catch (FileNotFoundException e) { 
			// Temporary handling of exception, will change what happens once tested.
			primaryStage.close();
		}});

		//Adding nodes to pane
		gameWon.getChildren().addAll(treasureChest1,treasureChest3,treasureChest5, hbBtn, youWin, thankYou);
		gameWon.setStyle(" -fx-background-color: gold");


		//Adding Pane to Scene and Scene to Stage
		Scene gWon = new Scene(gameWon, 1280, 720);
		gWon.setFill(Color.GOLD);
		return gWon;

	}


	/**
	 * This method creates game over screen when the player loses to the enemy
	 * 
	 * @param primaryStage The primary stage/ window for displaying the GUI.
	 */
	public Scene gameOverScreen(Stage primaryStage) {

		//Creating the game over image text for the Pane and adding effects
		Image gameOverText = new Image("gameover.png");
		ImageView gameOverText2 = new ImageView(gameOverText);
		DropShadow ds = new DropShadow();
		ds.setColor(Color.DARKRED);
		gameOverText2.setEffect(ds);
		gameOverText2.setLayoutX(170);
		gameOverText2.setLayoutY(10);

		//Creating the buttons to exit the game or play again
		Button exitBtn = new Button("Exit game");
		exitBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		Button playAgainBtn = new Button("Play again");
		playAgainBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		Button reviveBtn = new Button("Use Revive");
		reviveBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().addAll(exitBtn, playAgainBtn, reviveBtn);
		hbBtn.setLayoutX(430);
		hbBtn.setLayoutY(600);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);

		if (hero.isHasRevive() == false) {
			reviveBtn.setDisable(true);
		}

		//Adding eventHandlint for buttons
		exitBtn.setOnAction(event-> {primaryStage.close();;});
		playAgainBtn.setOnAction(event-> {try {
			start(primaryStage);
		} catch (FileNotFoundException e) { 
			// Temporary handling of exception, will change what happens once tested.
			primaryStage.close();
		}});
		reviveBtn.setOnAction(event-> {hero.revive();
		fullGame(primaryStage);
		});

		//Creating Pane and adding nodes
		Pane gameOver = new Pane();
		gameOver.getChildren().addAll(gameOverText2, hbBtn);
		gameOver.setStyle(" -fx-background-color: black");


		//Adding Pane to Scene and Scene to Stage
		Scene gOver = new Scene(gameOver, 1280, 720);
		gOver.setFill(Color.BLACK);
		return gOver;

	}
	/**
	 * This method creates the transition page after the user has cleared the floor.
	 * 
	 * @param primaryStage The primary stage/window of the GUI.
	 */
	public Scene transitionScreen(Stage primaryStage) {
	    	//Creating text for the page
		Text clearedFloor = new Text();
		clearedFloor.setText("You cleared floor " + floor.getFloor() + "!");
		clearedFloor.setX(280);
		clearedFloor.setY(200);
		clearedFloor.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 75));
		DropShadow ds = new DropShadow();
		ds.setColor(Color.BLUE);
		clearedFloor.setEffect(ds);
			
		//Creating text for gold and xp gained
		Text goldGained = new Text();
		int gold = 10 + (int)(Math.random() * ((4) + 1) * floor.getFloor());
		hero.setGold(hero.getGold() +  gold);
		goldGained.setText("You gained " + (int)gold + " gold! Gold = " + hero.getGold());
		goldGained.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
	
		Text xpGained = new Text();
		int xp = 100 + (int) (Math.random() * ((6) + 1) * floor.getFloor());
		hero.setXp(hero.getXp()+ xp);
		xpGained.setText("You gained " + xp + " xp! Xp = " + hero.getXp());
		xpGained.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
		
		//Creating text for level up and the conditions to display it
		Text levelUp = new Text();
	
		if (hero.getXp() > 300) {
		    hero.levelUp();
		    hero.setXp(hero.getXp() - 300);
		    levelUp.setText("YOU GAINED A LEVEL! You are now Level " + hero.getLevel());
		    levelUp.setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
		    levelUp.setFill(Color.PURPLE);
		    
		}
		
		//Creating VBox for user update text on gold and xp gained
		VBox userUpdate = new VBox(30);
		userUpdate.getChildren().addAll(goldGained, xpGained, levelUp);
		userUpdate.setLayoutX(340);
		userUpdate.setLayoutY(350);
		userUpdate.setAlignment(Pos.CENTER);

		//Creating Pane and 
		Pane display = new Pane();
		

		//Creating the buttons play for the player to continue on
		Button shopBtn = new Button("Go to the Magic Shop");
		shopBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");

		Button continueBtn = new Button("Continue playing");
		continueBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().addAll(shopBtn, continueBtn);
		hbBtn.setLayoutX(430);
		hbBtn.setLayoutY(600);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);

		//Adding eventHandling for buttons
		Event e = new Event();
		e.eventHappen();
		if ( e.isEvent() == true) {
			continueBtn.setOnAction(event -> {
				e.eventScene(primaryStage, this, floor);});
		} else {
			continueBtn.setOnAction(event -> {
				floor.incrementFloor();
				fullGame(primaryStage);});
		}
		
		if (floor.getFloor() != 3 ||  floor.getFloor() != 6 || floor.getFloor() != 9) {
			shopBtn.setDisable(true);
		} 
		
		shopBtn.setOnAction(event -> {
			shop(primaryStage);});
		
		//Adding nodes to pane
		display.getChildren().addAll(hbBtn, clearedFloor, userUpdate);
		display.setStyle(" -fx-background-color: cornflowerblue");


		//Adding Pane to Scene and Scene to Stage
		Scene transition = new Scene(display, 1280, 720);
		transition.setFill(Color.GREY);
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

	public static void main(String[] args) {
		launch(args);
	}
}