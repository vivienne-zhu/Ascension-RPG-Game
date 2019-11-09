package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GameGUI extends Application {
    
    /**
     * This is the start method that enables us to run/display our JavaFX application.
     * It begins by displaying start screen and then allows us to continue through game.
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
        //Parent root = FXMLLoader.load(getClass().getResource("GameGUI.fxml"));
        Pane root = new Pane();

        Button btn = new Button("Start");
        btn.setLayoutX(600);
        btn.setLayoutY(500);
        btn.setAlignment(Pos.CENTER);
        btn.setTextFill(Color.BLACK);
        btn.setPrefSize(100, 50);
        btn.setFont(Font.font(20));

        EventHandler<MouseEvent> colourChange = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
        	chooseCharacterScreen(primaryStage);
            }
        };
        
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, colourChange);
        Text title = new Text();
        title.setText("Tower Challenge");
        title.setX(250);
        title.setY(400);
        title.setFont(Font.font ("helvetica", FontWeight.BOLD, FontPosture.ITALIC, 100));
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(3);
        DropShadow ds = new DropShadow();
        ds.setColor(Color.FIREBRICK);
        title.setEffect(ds);

        Image brick  = new Image("Brick.jpeg");
        ImagePattern fill = new ImagePattern(brick, 20, 20, 40, 40, false);
        title.setFill(fill);
        
        Image background = new Image("Tower.jpg");
        BackgroundImage background2 = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        	BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background startScreen = new Background(background2);
        root.setBackground(startScreen);
        
        root.getChildren().addAll(title,btn);
        Scene scene = new Scene(root, 1280, 720);
        
        
        primaryStage.setTitle("Tower Challenge");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method houses the code needed for the screen following the start screen 
     * that allows the player to choose their character/fighter.
     * 
     * @param primaryStage The primary Stage object of the JavaFX application GUI.
     */
    public void chooseCharacterScreen(Stage primaryStage) {
	Pane root = new Pane();
	Scene chooseChar = new Scene(root,1280,720);
	chooseChar.setFill(Color.CADETBLUE);
	Text charOption = new Text();
	charOption.setText("Choose your character type");
	charOption.setX(180);
	charOption.setY(350);
	charOption.setFont(Font.font ("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 75));
	DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        charOption.setEffect(ds);
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
	Image background = new Image("Tower.jpg");
        BackgroundImage background2 = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        	BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background startScreen = new Background(background2);
        
        root.setBackground(startScreen);
	root.getChildren().addAll(mageBtn, warriorBtn, archerBtn,charOption);
	primaryStage.setScene(chooseChar);
        primaryStage.show();
    }
    /**
     * Incomplete method. This method will be responsible for taking in the character name for the user
     * and using in to create the chosen character with the name given by the user.
     */
    public void getCharName() {
	GridPane getName = new GridPane();
	Label charName = new Label("Character Name: ");
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
