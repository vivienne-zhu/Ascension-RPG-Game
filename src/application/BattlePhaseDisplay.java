package application;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class contains the display elements of the battle phase.
 * 
 * @author Shari Sinclair, David Cai and JiayuZhu
 *
 */
public class BattlePhaseDisplay {
    private Button attackBtn;
    private Button defendBtn;
    private Button healBtn;
    private Button magicAtkBtn;
    private VBox itemBag;
    private Button potionBtn;
    private Button hyperPotionBtn;
    private Button reviveBtn;
    private HBox hbBtn;
    private Button chooseEnemyBtn;
    private Button chooseEnemyTwoBtn;
    private Button chooseEnemyThreeBtn;
    private Text error;
    private Text dialogue;
    private Text dialogueTwo;
    private Text dialogueThree;
    private Text empowered;
    private Text outraged;
    private Text heroStam;
    private Text heroMana;
    private Text enemyStam;
    private Text enemyTwoStam;
    private Text enemyThreeStam;
    private Text heroName;
    private Text enemyName;
    private Text enemyTwoName;
    private Text enemyThreeName;
    private Rectangle staminaBar;
    private Rectangle manaBar;
    private Rectangle fullStamBar;
    private Rectangle fullManaBar;
    private Rectangle enemyOneStamBar;
    private Rectangle enemyTwoStamBar;
    private Rectangle enemyThreeStamBar;
    private Rectangle enemyOneFullStamBar;
    private Rectangle enemyTwoFullStamBar;
    private Rectangle enemyThreeFullStamBar;
    private VBox heroStats;

    public BattlePhaseDisplay() {
	itemBag = new VBox();
	heroStats = new VBox();
    }

    public void healFunctionDisplay(GameCharacters hero) {	
	itemBag.setMaxWidth(200);

	// Error message 
	error = new Text("you can't see me");
	error.setId("battlePhase");
	error.setVisible(false);

	// cheap potion button
	String btnInfo1 = hero.itemInfo(hero.getCp());	
	potionBtn = new Button(btnInfo1);
	potionBtn.setId("battlePhaseHealBtn");
	potionBtn.setMaxWidth(200);

	// hyper potion button 
	String btnInfo2 = hero.itemInfo(hero.getHp());		
	hyperPotionBtn = new Button(btnInfo2);
	hyperPotionBtn.setId("battlePhaseHealBtn");
	hyperPotionBtn.setMaxWidth(200);


	// revive button 
	String btnInfo3 = "Revive\t\t\t";
	if (hero.isHasRevive() == true) {
	    btnInfo3 += "x1";
	} else {
	    btnInfo3 += "x0";
	}
	reviveBtn = new Button(btnInfo3);
	reviveBtn.setId("battlePhaseHealBtn");
	reviveBtn.setMaxWidth(200);
	reviveBtn.setDisable(true);

	// set background
	itemBag.setStyle("-fx-background-color: gainsboro");
	itemBag.getChildren().addAll(potionBtn, hyperPotionBtn, reviveBtn);
	itemBag.setVisible(false);
    }

    /**
     * This method initializes the style for any types of the bars in the game.
     * 
     * @param barType
     * @param width
     * @param gm
     */
    private void infoBar(Rectangle barType, double width, GameCharacters gm) {
	barType.setArcWidth(20.0); 
	barType.setArcHeight(15.0);  
	barType.setStroke(Color.BLACK);	

    }

    /**
     * This method reset the width of the bar based on game characters' stats in the game.
     * If type is equal to 0, it means that it is a stamina bar. 
     * If type is equal to 1, it means that it is a mana bar. 
     * 
     * @param type
     * @param barType
     * @param width
     * @param gm
     */
    public void resetInfoBar(int type, Rectangle barType, double width, GameCharacters gm) {
	if (type == 0) {
	    barType.setWidth(width * (double) gm.getCurrentStamina() / (double) gm.getStamina());
	} else if (type == 1) {
	    barType.setWidth(width * (double) gm.getCurrentMana() / (double) gm.getMana());

	}

    }
    
    /**
     * This method generates the display of the hero stats. 
     * 
     * @param hero
     */
    public VBox heroStatsDisplay(GameCharacters hero) {
    	// To initialize heroStats VBox 
        heroStats.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
    	Text stats = new Text("Level: " + hero.getLevel() + "\t\t" +
    	"Gold: " + hero.getGold() + "\n" +
        "Attack: " + hero.getAttack() + "\t" + 
    	"Defense: " + hero.getDefense());
    	stats.setId("heroStatsText");
    	
    	heroStats.getChildren().add(stats);
    	heroStats.setVisible(false);
    	
    	heroStats.setLayoutX(330);
    	heroStats.setLayoutY(70);
    	return heroStats;
    }

    /**
     * This method will display relevant combat information like  player/enemy names and health
     * @param hero The character the player controls
     * @param allEnemies The hashMap of all enemies
     * @param floor The current floor the hero is on
     */
    public void dispCombatInfo(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor) {
	// Initialize stamina bar for hero
	staminaBar = new Rectangle(300, 10, Color.RED);
	infoBar(staminaBar, 300.0, hero);
	resetInfoBar(0, staminaBar, 300, hero);

	// Initialize full stamina bar for hero
	fullStamBar = new Rectangle(300.0, 10, Color.BLACK);
	infoBar(fullStamBar, 300.0, hero);

	// Initialize stamina bar for enemy one
	enemyOneStamBar = new Rectangle(200.0, 10, Color.RED);
	infoBar(enemyOneStamBar, 200, allEnemies.get(floor).get(0));

	// Initialize full stamina bar for enemy one
	enemyOneFullStamBar = new Rectangle(200.0, 10, Color.BLACK);
	infoBar(enemyOneFullStamBar, 200, allEnemies.get(floor).get(0));

	if (allEnemies.get(floor).size() > 1) {
	    // Initialize stamina bar for enemy two
	    enemyTwoStamBar = new Rectangle(200.0, 10, Color.RED);
	    infoBar(enemyTwoStamBar, 200, allEnemies.get(floor).get(1));

	    // Initialize full stamina bar for enemy two
	    enemyTwoFullStamBar = new Rectangle(200.0, 10, Color.BLACK);
	    infoBar(enemyTwoFullStamBar, 200, allEnemies.get(floor).get(1));
	}

	if (allEnemies.get(floor).size() > 2) {
	    // Initialize stamina bar for enemy three
	    enemyThreeStamBar = new Rectangle(200.0, 10, Color.RED);
	    infoBar(enemyThreeStamBar, 200, allEnemies.get(floor).get(2));

	    // Initialize full stamina bar for enemy three
	    enemyThreeFullStamBar = new Rectangle(200.0, 10, Color.BLACK);
	    infoBar(enemyThreeFullStamBar, 200, allEnemies.get(floor).get(2));
	}

	
	// To display current stamina of hero and enemy (using tester enemy[0]).
	heroName = new Text(hero.getType() + ": " + hero.getName());
	heroName.setId("battlePhase");	
	heroName.setOnMouseEntered(event -> {
		heroName.setFill(Color.LIGHTSKYBLUE);
		heroStats.setVisible(true);		
	});
	heroName.setOnMouseExited(event -> {
		heroName.setFill(Color.WHITE);
		heroStats.setVisible(false);
	});
	
	heroStam = new Text("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
	heroStam.setId("battlePhase");
	enemyName = new Text("Enemy Type: " + allEnemies.get(floor).get(0).getType());
	enemyName.setId("battlePhase");
	enemyStam = new Text("Stamina: " + allEnemies.get(floor).get(0).getCurrentStamina() + " / " + allEnemies.get(floor).get(0).getStamina());
	enemyStam.setId("battlePhase");
	if (hero.getType().equals("Mage")) {
	    heroMana = new Text("Mana: "  + hero.getCurrentMana()+ " / " + hero.getMana());
	    heroMana.setId("battlePhaseMana");

	    // Initialize mana bar
	    manaBar = new Rectangle(200.0, 10, Color.BLUE);		
	    infoBar(manaBar, 200, hero);
	    manaBar.setVisible(false);

	    // Initialize full mana bar
	    fullManaBar = new Rectangle(200.0, 10, Color.BLACK);
	    infoBar(fullManaBar, 200, hero);
	    fullManaBar.setVisible(false);
	}

	if (allEnemies.get(floor).size() > 1) {
	    enemyTwoName = new Text("Enemy Type: " + allEnemies.get(floor).get(1).getType());
	    enemyTwoName.setId("battlePhase");
	    enemyTwoStam = new Text("Stamina: " + allEnemies.get(floor).get(1).getCurrentStamina() + " / " + allEnemies.get(floor).get(1).getStamina());
	    enemyTwoStam.setId("battlePhase");
	}
	if (allEnemies.get(floor).size() > 2) {
	    enemyThreeName = new Text("Enemy Type: " + allEnemies.get(floor).get(2).getType());
	    enemyThreeName.setId("battlePhase");
	    enemyThreeStam = new Text("Stamina: " + allEnemies.get(floor).get(2).getCurrentStamina() + " / " + allEnemies.get(floor).get(2).getStamina());
	    enemyThreeStam.setId("battlePhase");
	}
    }

    /**
     * This method will properly create and format the three dialogue boxes used for combat information
     */
    public void dispDialogue() {
	// To display dialogue and other relevant battle info
	dialogue = new Text("");
	dialogue.setId("battlePhaseDialogue");
	dialogueTwo = new Text("");
	dialogueTwo.setId("battlePhaseDialogue");
	dialogueThree = new Text("");
	dialogueThree.setId("battlePhaseDialogue");
	empowered = new Text("EMPOWERED");
	empowered.setId("battlePhaseEmpowered");
	empowered.setVisible(false);
	outraged= new Text("OUTRAGED");
	outraged.setId("battlePhaseOutraged");
	outraged.setVisible(false);
    }
 
    
    /**
     * This method will create the necessary action buttons during the battle phase
     */
    public void initButtons(GameCharacters hero) {

	// Creating buttons for player to fight enemies
	this.attackBtn = new Button("Attack");
	attackBtn.setId("whiteBtn");
	this.defendBtn = new Button("Defend");
	defendBtn.setId("whiteBtn");
	this.healBtn = new Button("Heal");
	healBtn.setId("whiteBtn");
	this.magicAtkBtn = new Button("Magic Atk");
	magicAtkBtn.setId("whiteBtn");
	magicAtkBtn.setVisible(false);
	if(hero.getType().equals("Mage")) {
	    magicAtkBtn.setVisible(true);
	    manaBar.setVisible(true);
	    fullManaBar.setVisible(true);
	} 		

	this.hbBtn = new HBox(10);
	hbBtn.setAlignment(Pos.CENTER_LEFT);
	hbBtn.getChildren().addAll(attackBtn, defendBtn, healBtn);

	// Button to choose enemy
	this.chooseEnemyBtn = new Button("Attack");
	chooseEnemyBtn.setId("whiteBtn");
	chooseEnemyBtn.setVisible(false);
	this.chooseEnemyTwoBtn = new Button("Attack");
	chooseEnemyTwoBtn.setId("whiteBtn");
	chooseEnemyTwoBtn.setVisible(false);
	this.chooseEnemyThreeBtn = new Button("Attack");
	chooseEnemyThreeBtn.setId("whiteBtn");
	chooseEnemyThreeBtn.setVisible(false);
    }

    /**
     * This method properly formats the GridPane layout used to display most information
     * like character name, character health, the three dialogue boxes, and various buttons
     * @param enemyCount The number of enemies on the floor
     * @param hero The players chosen hero GameCharacters
     * @return The GridPane itself so it can be used in GameGUI.java
     */
    public GridPane gridLayout(int enemyCount, GameCharacters hero) {
	// Adding all nodes to grid
	GridPane grid = new GridPane();

	//Placements for various textboxes and buttons
	grid.add(heroName, 0, 0);
	grid.add(heroStam, 0, 1);
	grid.add(fullStamBar, 0, 2);
	grid.add(staminaBar, 0, 2);
	grid.add(hbBtn, 0, 3);
	grid.add(itemBag, 0, 4);
	grid.add(error, 0, 5);
	grid.add(empowered, 0, 6);
	if(hero.getType().equals("Mage")) {
	    grid.add(heroMana, 1, 1);
	    grid.add(fullManaBar, 1, 2);
	    grid.add(manaBar, 1, 2);
	    grid.add(magicAtkBtn, 1, 3);
	    GridPane.setHalignment(heroMana, HPos.CENTER);
	    GridPane.setHalignment(magicAtkBtn, HPos.CENTER);
	}
	grid.add(dialogue, 2, 5);
	grid.add(dialogueTwo, 2, 6);
	grid.add(dialogueThree, 2, 7);
	grid.add(enemyName, 4, 0);
	grid.add(enemyStam, 4, 1);
	grid.add(enemyOneFullStamBar, 4, 2);
	grid.add(enemyOneStamBar, 4, 2);
	grid.add(chooseEnemyBtn, 4, 3);
	grid.add(outraged, 4, 5);
	if (enemyCount > 1) {
	    grid.add(enemyTwoName, 3, 0);
	    grid.add(enemyTwoStam, 3, 1);
	    grid.add(enemyTwoFullStamBar, 3, 2);
	    grid.add(enemyTwoStamBar, 3, 2);
	    grid.add(chooseEnemyTwoBtn, 3, 3);
	}
	if (enemyCount > 2) {
	    grid.add(enemyThreeName, 2, 0);
	    grid.add(enemyThreeStam, 2, 1);
	    grid.add(enemyThreeFullStamBar, 2, 2);
	    grid.add(enemyThreeStamBar, 2, 2);
	    grid.add(chooseEnemyThreeBtn, 2, 3);
	}

	//Set vertical and horizontal gap spacing
	grid.setVgap(10);
	grid.setHgap(30);
	grid.setPadding(new Insets(10, 10, 10, 10));
	grid.setAlignment(Pos.TOP_CENTER);

	//Set location of grid
	grid.setLayoutX(20);
	grid.setLayoutY(60);
	grid.setMinSize(1200, 700);

	//Add specific size constraints to lock in formatting
	grid.getColumnConstraints().add(new ColumnConstraints(300));
	grid.getColumnConstraints().add(new ColumnConstraints(200));
	grid.getColumnConstraints().add(new ColumnConstraints(200));
	grid.getColumnConstraints().add(new ColumnConstraints(200));
	grid.getColumnConstraints().add(new ColumnConstraints(200));

	if (enemyCount > 1) {
	    GridPane.setHalignment(enemyTwoName, HPos.CENTER);
	    GridPane.setHalignment(enemyTwoStam, HPos.CENTER);
	}
	if (enemyCount > 2) {
	    GridPane.setHalignment(enemyThreeName, HPos.CENTER);
	    GridPane.setHalignment(enemyThreeStam, HPos.CENTER);
	}

	//Center all text within each grid panel
	GridPane.setHalignment(dialogue, HPos.CENTER);
	GridPane.setHalignment(dialogueTwo, HPos.CENTER);
	GridPane.setHalignment(dialogueThree, HPos.CENTER);
	GridPane.setHalignment(empowered, HPos.CENTER);
	GridPane.setHalignment(outraged, HPos.CENTER);
	GridPane.setHalignment(heroName, HPos.CENTER);
	GridPane.setHalignment(heroStam, HPos.CENTER);
	GridPane.setHalignment(enemyName, HPos.CENTER);
	GridPane.setHalignment(enemyStam, HPos.CENTER);
	GridPane.setHalignment(chooseEnemyBtn, HPos.CENTER);
	GridPane.setHalignment(chooseEnemyTwoBtn, HPos.CENTER);
	GridPane.setHalignment(chooseEnemyThreeBtn, HPos.CENTER);
	GridPane.setHalignment(itemBag, HPos.CENTER);
	hbBtn.setAlignment(Pos.CENTER);

	//Make gridlines visible - only for development phase
	grid.setGridLinesVisible(false);

	return grid;
    }

    /** This method disables/enables all user input buttons
     * 
     * @param disable If true, disable all buttons. Enable otherwise.
     * @param attackBtn Button to attack
     * @param healBtn Button to use item
     * @param defendBtn Button to defend
     * @param magicAtkBtn Button to do a magic attack
     */
    public void disableButtons(boolean disable) {
	attackBtn.setDisable(disable);
	healBtn.setDisable(disable);
	defendBtn.setDisable(disable);
	magicAtkBtn.setDisable(disable);
    }

    /**
     * @return the attackBtn
     */
    public Button getAttackBtn() {
	return attackBtn;
    }

    /**
     * @param attackBtn the attackBtn to set
     */
    public void setAttackBtn(Button attackBtn) {
	this.attackBtn = attackBtn;
    }

    /**
     * @return the defendBtn
     */
    public Button getDefendBtn() {
	return defendBtn;
    }

    /**
     * @param defendBtn the defendBtn to set
     */
    public void setDefendBtn(Button defendBtn) {
	this.defendBtn = defendBtn;
    }

    /**
     * @return the healBtn
     */
    public Button getHealBtn() {
	return healBtn;
    }

    /**
     * @param healBtn the healBtn to set
     */
    public void setHealBtn(Button healBtn) {
	this.healBtn = healBtn;
    }

    /**
     * @return the magicAtkBtn
     */
    public Button getMagicAtkBtn() {
	return magicAtkBtn;
    }

    /**
     * @param magicAtkBtn the magicAtkBtn to set
     */
    public void setMagicAtkBtn(Button magicAtkBtn) {
	this.magicAtkBtn = magicAtkBtn;
    }

    /**
     * @return the itemBag
     */
    public VBox getItemBag() {
	return itemBag;
    }

    /**
     * @param itemBag the itemBag to set
     */
    public void setItemBag(VBox itemBag) {
	this.itemBag = itemBag;
    }

    /**
     * @return the hbBtn
     */
    public HBox getHbBtn() {
	return hbBtn;
    }

    /**
     * @param hbBtn the hbBtn to set
     */
    public void setHbBtn(HBox hbBtn) {
	this.hbBtn = hbBtn;
    }

    /**
     * @return the chooseEnemyBtn
     */
    public Button getChooseEnemyBtn() {
	return chooseEnemyBtn;
    }

    /**
     * @param chooseEnemyBtn the chooseEnemyBtn to set
     */
    public void setChooseEnemyBtn(Button chooseEnemyBtn) {
	this.chooseEnemyBtn = chooseEnemyBtn;
    }

    /**
     * @return the chooseEnemyTwoBtn
     */
    public Button getChooseEnemyTwoBtn() {
	return chooseEnemyTwoBtn;
    }

    /**
     * @param chooseEnemyTwoBtn the chooseEnemyTwoBtn to set
     */
    public void setChooseEnemyTwoBtn(Button chooseEnemyTwoBtn) {
	this.chooseEnemyTwoBtn = chooseEnemyTwoBtn;
    }

    /**
     * @return the chooseEnemyThreeBtn
     */
    public Button getChooseEnemyThreeBtn() {
	return chooseEnemyThreeBtn;
    }

    /**
     * @param chooseEnemyThreeBtn the chooseEnemyThreeBtn to set
     */
    public void setChooseEnemyThreeBtn(Button chooseEnemyThreeBtn) {
	this.chooseEnemyThreeBtn = chooseEnemyThreeBtn;
    }

    /**
     * @return the error
     */
    public Text getError() {
	return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(Text error) {
	this.error = error;
    }

    /**
     * @return the dialogue
     */
    public Text getDialogue() {
	return dialogue;
    }

    /**
     * @param dialogue the dialogue to set
     */
    public void setDialogue(Text dialogue) {
	this.dialogue = dialogue;
    }

    /**
     * @return the dialogueTwo
     */
    public Text getDialogueTwo() {
	return dialogueTwo;
    }

    /**
     * @param dialogueTwo the dialogueTwo to set
     */
    public void setDialogueTwo(Text dialogueTwo) {
	this.dialogueTwo = dialogueTwo;
    }

    /**
     * @return the dialogueThree
     */
    public Text getDialogueThree() {
	return dialogueThree;
    }

    /**
     * @param dialogueThree the dialogueThree to set
     */
    public void setDialogueThree(Text dialogueThree) {
	this.dialogueThree = dialogueThree;
    }

    /**
     * @return the outraged text value
     */
    public Text getOutraged() {
	return outraged;
    }

    /**
     * @param empowered the empowered to set
     */
    public void setOutraged(Text outraged) {
	this.outraged = outraged;
    }
    
    /**
     * @return the empowered
     */
    public Text getEmpowered() {
	return empowered;
    }

    /**
     * @param empowered the empowered to set
     */
    public void setEmpowered(Text empowered) {
	this.empowered = empowered;
    }

    /**
     * @return the heroStam
     */
    public Text getHeroStam() {
	return heroStam;
    }

    /**
     * @param heroStam the heroStam to set
     */
    public void setHeroStam(Text heroStam) {
	this.heroStam = heroStam;
    }

    /**
     * @return the heroMana
     */
    public Text getHeroMana() {
	return heroMana;
    }

    /**
     * @param heroMana the heroMana to set
     */
    public void setHeroMana(Text heroMana) {
	this.heroMana = heroMana;
    }

    /**
     * @return the enemyStam
     */
    public Text getEnemyStam() {
	return enemyStam;
    }

    /**
     * @param enemyStam the enemyStam to set
     */
    public void setEnemyStam(Text enemyStam) {
	this.enemyStam = enemyStam;
    }

    /**
     * @return the enemyTwoStam
     */
    public Text getEnemyTwoStam() {
	return enemyTwoStam;
    }

    /**
     * @param enemyTwoStam the enemyTwoStam to set
     */
    public void setEnemyTwoStam(Text enemyTwoStam) {
	this.enemyTwoStam = enemyTwoStam;
    }

    /**
     * @return the enemyThreeStam
     */
    public Text getEnemyThreeStam() {
	return enemyThreeStam;
    }

    /**
     * @param enemyThreeStam the enemyThreeStam to set
     */
    public void setEnemyThreeStam(Text enemyThreeStam) {
	this.enemyThreeStam = enemyThreeStam;
    }

    /**
     * @return the heroName
     */
    public Text getHeroName() {
	return heroName;
    }

    /**
     * @param heroName the heroName to set
     */
    public void setHeroName(Text heroName) {
	this.heroName = heroName;
    }

    /**
     * @return the enemyName
     */
    public Text getEnemyName() {
	return enemyName;
    }

    /**
     * @param enemyName the enemyName to set
     */
    public void setEnemyName(Text enemyName) {
	this.enemyName = enemyName;
    }

    /**
     * @return the enemyTwoName
     */
    public Text getEnemyTwoName() {
	return enemyTwoName;
    }

    /**
     * @param enemyTwoName the enemyTwoName to set
     */
    public void setEnemyTwoName(Text enemyTwoName) {
	this.enemyTwoName = enemyTwoName;
    }

    /**
     * @return the enemyThreeName
     */
    public Text getEnemyThreeName() {
	return enemyThreeName;
    }

    /**
     * @param enemyThreeName the enemyThreeName to set
     */
    public void setEnemyThreeName(Text enemyThreeName) {
	this.enemyThreeName = enemyThreeName;
    }

    /**
     * @return the staminaBar
     */
    public Rectangle getStaminaBar() {
	return staminaBar;
    }

    /**
     * @param staminaBar the staminaBar to set
     */
    public void setStaminaBar(Rectangle staminaBar) {
	this.staminaBar = staminaBar;
    }

    /**
     * @return the manaBar
     */
    public Rectangle getManaBar() {
	return manaBar;
    }

    /**
     * @param manaBar the manaBar to set
     */
    public void setManaBar(Rectangle manaBar) {
	this.manaBar = manaBar;
    }

    /**
     * @return the fullStamBar
     */
    public Rectangle getFullStamBar() {
	return fullStamBar;
    }

    /**
     * @param fullStamBar the fullStamBar to set
     */
    public void setFullStamBar(Rectangle fullStamBar) {
	this.fullStamBar = fullStamBar;
    }

    /**
     * @return the fullManaBar
     */
    public Rectangle getFullManaBar() {
	return fullManaBar;
    }

    /**
     * @param fullManaBar the fullManaBar to set
     */
    public void setFullManaBar(Rectangle fullManaBar) {
	this.fullManaBar = fullManaBar;
    }

    /**
     * @return the enemyOneStamBar
     */
    public Rectangle getEnemyOneStamBar() {
	return enemyOneStamBar;
    }

    /**
     * @param enemyOneStamBar the enemyOneStamBar to set
     */
    public void setEnemyOneStamBar(Rectangle enemyOneStamBar) {
	this.enemyOneStamBar = enemyOneStamBar;
    }

    /**
     * @return the enemyTwoStamBar
     */
    public Rectangle getEnemyTwoStamBar() {
	return enemyTwoStamBar;
    }

    /**
     * @param enemyTwoStamBar the enemyTwoStamBar to set
     */
    public void setEnemyTwoStamBar(Rectangle enemyTwoStamBar) {
	this.enemyTwoStamBar = enemyTwoStamBar;
    }

    /**
     * @return the enemyThreeStamBar
     */
    public Rectangle getEnemyThreeStamBar() {
	return enemyThreeStamBar;
    }

    /**
     * @param enemyThreeStamBar the enemyThreeStamBar to set
     */
    public void setEnemyThreeStamBar(Rectangle enemyThreeStamBar) {
	this.enemyThreeStamBar = enemyThreeStamBar;
    }

    /**
     * @return the enemyOneFullStamBar
     */
    public Rectangle getEnemyOneFullStamBar() {
	return enemyOneFullStamBar;
    }

    /**
     * @param enemyOneFullStamBar the enemyOneFullStamBar to set
     */
    public void setEnemyOneFullStamBar(Rectangle enemyOneFullStamBar) {
	this.enemyOneFullStamBar = enemyOneFullStamBar;
    }

    /**
     * @return the enemyTwoFullStamBar
     */
    public Rectangle getEnemyTwoFullStamBar() {
	return enemyTwoFullStamBar;
    }

    /**
     * @param enemyTwoFullStamBar the enemyTwoFullStamBar to set
     */
    public void setEnemyTwoFullStamBar(Rectangle enemyTwoFullStamBar) {
	this.enemyTwoFullStamBar = enemyTwoFullStamBar;
    }

    /**
     * @return the enemyThreeFullStamBar
     */
    public Rectangle getEnemyThreeFullStamBar() {
	return enemyThreeFullStamBar;
    }

    /**
     * @param enemyThreeFullStamBar the enemyThreeFullStamBar to set
     */
    public void setEnemyThreeFullStamBar(Rectangle enemyThreeFullStamBar) {
	this.enemyThreeFullStamBar = enemyThreeFullStamBar;
    }

    /**
     * @return the potionBtn
     */
    public Button getPotionBtn() {
        return potionBtn;
    }

    /**
     * @param potionBtn the potionBtn to set
     */
    public void setPotionBtn(Button potionBtn) {
        this.potionBtn = potionBtn;
    }

    /**
     * @return the hyperPotionBtn
     */
    public Button getHyperPotionBtn() {
        return hyperPotionBtn;
    }

    /**
     * @param hyperPotionBtn the hyperPotionBtn to set
     */
    public void setHyperPotionBtn(Button hyperPotionBtn) {
        this.hyperPotionBtn = hyperPotionBtn;
    }

    /**
     * @return the reviveBtn
     */
    public Button getReviveBtn() {
        return reviveBtn;
    }

    /**
     * @param reviveBtn the reviveBtn to set
     */
    public void setReviveBtn(Button reviveBtn) {
        this.reviveBtn = reviveBtn;
    }

    

}
