package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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

import java.io.FileNotFoundException;

/**
 * This class represents the GUI of the game and houses the instance variables
 * needed to capture user input and run the game. Can be run to see preliminary phases of GUI 
 * (Start scene, Character selection and naming, incomplete floor 1 of the tower). 
 * Currently this class is only to begin testing GUI elements and its design will be 
 * revived heavily before final submission.
 * 
 * @author Shari Sinclair
 *
 */
public class GameGUI extends Application {
    private boolean isMage;
    private boolean isWarrior;
    private boolean isArcher;
    private GameCharacters hero;
    private String heroName;
    private GameCharacters[] allEnemies;

    /**
     * The constructor creates a new character, sets all booleans variables to
     * false, initialized name as an empty string and enemy array to hold 10
     * enemies.
     */
    public GameGUI() {
	isMage = false;
	isWarrior = false;
	isArcher = false;
	hero = new GameCharacters();
	allEnemies = new GameCharacters[10];
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
	MeleeEnemy m = new MeleeEnemy(1);
	allEnemies[0] = m;

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
	 * 		If continue to next floor = true (i.e. hero has stam, enemy dead)--> increment floor (which corresponds to enemy[]), 
	 * 			possibly load shop screen if floor==3,6,9, re-enter original while loop and fight again 
	 * 		Else if Enemy alive ,hero dead --> end game = true and we exit overall while loop
	 *		
	 *  } Exit overall while loop if endgame =true if 
	 *  	Since endGame = true :
	 *  		if floor less than 10 -->Load game over screen (make game over screen)
	 *  		Else --> Congrats you win screen(make congrats screen)
	 * 
	 */

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
	Image tower = new Image("towerpossible.jpg");
	BackgroundImage background = new BackgroundImage(tower, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
	Background insideTowerBackground = new Background(background);
	Canvas canvas = new Canvas(1280, 720);
	towerLevels.getChildren().add(canvas);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	// To display current stamina of hero and enemy.
	Text heroName = new Text(hero.getType() + ": " + this.heroName);
	heroName.setStyle(" -fx-font: normal bold 30px 'serif' ");
	heroName.setFill(Color.DODGERBLUE);
	Text heroStam = new Text("Stamina: " + this.hero.getCurrentStamina());
	heroStam.setFill(Color.DODGERBLUE);
	heroStam.setStyle(" -fx-font: normal bold 30px 'serif' ");
	Text enemyName = new Text("Enemy Type: " + this.allEnemies[0].getType());
	enemyName.setStyle(" -fx-font: normal bold 30px 'serif' ");
	enemyName.setFill(Color.DARKRED);
	Text enemyStam = new Text("Stamina: " + this.allEnemies[0].getCurrentStamina());
	enemyStam.setStyle(" -fx-font: normal bold 30px 'serif' ");
	enemyStam.setFill(Color.DARKRED);
	
	//Adding hero image
	hero.displayCharacter(gc);

	// Creating buttons for player to fight enemies
	Button attackBtn = new Button("Attack");
	attackBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
	Button defendBtn = new Button("Defend");
	defendBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
	Button healBtn = new Button("Heal");
	healBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
	HBox hbBtn = new HBox(10);
	hbBtn.setAlignment(Pos.BOTTOM_LEFT);
	hbBtn.getChildren().addAll(attackBtn, defendBtn, healBtn);

	// Adding all nodes to grid
	GridPane grid = new GridPane();
	grid.add(heroName, 0, 0);
	grid.add(heroStam, 0, 1);
	grid.add(enemyName, 18, 0);
	grid.add(enemyStam, 18, 1);
	grid.setVgap(15);
	grid.setHgap(20);
	grid.setPadding(new Insets(10, 10, 10, 10));
	grid.setAlignment(Pos.TOP_CENTER);
	grid.setLayoutX(80);
	grid.setLayoutY(60);
	grid.setMinSize(1100, 700);
	grid.add(hbBtn, 0, 3);

	towerLevels.setBackground(insideTowerBackground);
	towerLevels.getChildren().add(grid);

	return towerLevels;
    }

    /**
     * This will generate the shop screen, where player is able to buy and sell
     * items.
     */
    public void shop(Stage primaryStage) {
	// return will be Scene shopScene
	// Scene shopScene = new Scene();
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
