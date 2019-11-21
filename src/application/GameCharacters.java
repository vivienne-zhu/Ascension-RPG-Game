package application;

import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

/**
 * This class represents the parent class for all characters in the game (both
 * Heros and Enemies).
 * 
 * @author Shari Sinclair, David Cai and Jiayu Zhu
 *
 */
public class GameCharacters {
    private String name;
    private int attack;
    private int defense;
    private int stamina;
    private int currentStamina;
    private int magicAtk;
    private boolean hasRevive;
    private boolean isDefending;
    private double gold;
    private int xp;
    private int mana;
    private int currentMana;
    private int level;
    private HashMap<Potion, Double> potionMap;
    private CheapPotion cp;
    private HyperPotion hp;
    private double x;
    private double y;
    private double height;
    private double width;
    private Image characterImage;
    private Image characterImageHurt;
    private Image characterImageHeal;
    private Image magicAtkImage;
    private double magicx;
    private double magicy;
    private String type;

    /**
     * The constructor initializes the necessary instance variables to O and false,
     * initializes potionMap, cp, hp, and adds cp and hp to potionMap.
     */
    public GameCharacters() {
	// Initialize potion map
	this.potionMap = new HashMap<>();
	this.cp = new CheapPotion();
	this.hp = new HyperPotion();
	this.potionMap.put(cp, 0.0);
	this.potionMap.put(hp, 0.0);

	// for testing purpose, set the gold to 10000 and xp to 0
	this.gold = 10000;
	this.xp = 0;
	this.isDefending = false;
	this.x = 0;
	this.y = 0;
	this.height = 0.0;
	this.width = 0.0;
	this.hasRevive = false;
//	this.setLeveledThisTurn(false);
    }

    /**
     * This method takes in a game character and makes changes to its stamina when
     * it is attacked by another character.
     * 
     * @param character The character currently being attacked.
     */
    public int attack(GameCharacters character) {
	setIsDefending(false);
	int attackValue = this.getAttack() - character.getDefense();
	if (character.isDefending()) {
	    attackValue = attackValue / 2;
	}
	int oldHealth = character.getCurrentStamina();
	int newHealth = oldHealth - attackValue;
	if (attackValue > 0) {
	    if (newHealth > 0) {
		character.setCurrentStamina(newHealth);
	    } else {
		character.setCurrentStamina(0);
		attackValue = oldHealth;
	    }
	}
	return attackValue;
    }

    /**
     * This method takes in a game character and makes changes to their stamina when
     * they are attacked using a magic attack.
     * 
     * @param character The character currently being attacked.
     */
    public int magicAttack(GameCharacters character) {
	setIsDefending(false);
	int attackValue = this.getMagicAtk() - character.getDefense();
	if (character.getType().equals("Melee")) { // Add other type advantage here
	    attackValue = (int) (attackValue * 1.2);
	}
	if (character.isDefending()) {
	    attackValue = attackValue / 2;
	}
	int oldHealth = character.getCurrentStamina();
	int newHealth = oldHealth - attackValue;
	if (attackValue > 0) {
	    if (newHealth > 0) {
		character.setCurrentStamina(newHealth);
	    } else {
		character.setCurrentStamina(0);
		attackValue = oldHealth;
	    }
	}
	return attackValue;
    }

    /**
     * This method changes isDefending to true when a game character chooses the
     * option to defend.
     */
    public void defend() {
	this.setIsDefending(true);
    }

    /**
     * This method takes in a potion object, lets the character restore their
     * stamina, and removes the potion from the potionMap. It also adds/updates the
     * error message when necessary.
     * 
     * @param potion Potion item being held by game character (hero or enemy)
     */
    public void usePotion(Potion potion, Text error) {
	if (getPotionMap().get(potion) > 0) {
	    if (getCurrentStamina() == getStamina()) {
		error.setVisible(true);
		error.setText("YOU HAVE REACHED THE MAX STAMINA");
	    } else if (getCurrentStamina() + potion.getRestorePoint() > getStamina()) {
		error.setVisible(false);
		setCurrentStamina(getStamina());
		getPotionMap().put(potion, getPotionMap().get(potion) - 1);
	    } else {
		error.setVisible(false);
		setCurrentStamina(currentStamina + potion.getRestorePoint());
		getPotionMap().put(potion, getPotionMap().get(potion) - 1);
	    }
	} else {
	    error.setVisible(true);
	    error.setText("YOU DO NOT HAVE ENOUGH ITEMS");
	}
    }

    /**
     * This method allows the character to revive if the character has a revive
     */
    public void revive() {
	if (isHasRevive() == true) {
	    setCurrentStamina(this.getStamina());
	    if(getType().equals("Mage")) {
		setCurrentMana(this.getMana());
	    }
	    setHasRevive(false);
	}
    }

    /**
     * This method increases the attack, defense and stamina of the hero when
     * certain conditions are met.
     */
    public void levelUp() {
	int atk = this.getAttack();
	int atkRand = 5 + (int) (Math.random() * ((9 - 5) + 1)); // min 5, max 9
	atk = atk + atkRand;
	setAttack(atk);
	int defRand = 5 + (int) (Math.random() * ((9 - 5) + 1));
	int defense = this.getDefense();
	defense = defense + defRand;
	setDefense(defense);
	int stamRand = 5 + (int) (Math.random() * ((9 - 5) + 1));
	int stam = this.getStamina();
	stam = stam + stamRand;
	setStamina(stam);
	int manaRand = 0;
	if (this instanceof Mage) {
	    manaRand = 5 + (int) (Math.random() * ((9 - 5) + 1));
	    mana = mana + manaRand;
	    setMana(mana);
	}
	this.setLevel(this.getLevel() + 1);
	int missingHealth = this.getStamina() - this.getCurrentStamina();
	//System.out.println("Missing:" + missingHealth);
	this.setCurrentStamina(this.getCurrentStamina() + (int) (missingHealth * 0.2));
	//System.out.println("New stam" + this.getCurrentStamina());
    }

    /**
     * This method allows us to display the game character image in the GUI.
     * 
     * @param g GraphicsContext needed to draw the image in the GUI.
     */
    public void displayCharacter(GraphicsContext g, boolean delete, boolean hurt, boolean heal) {
	if (delete) {
	    g.clearRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	} else if (hurt) {
	    g.drawImage(getCharacterImageHurt(), getX(), getY());
	} else if (heal) {
	    g.drawImage(getCharacterImageHeal(), getX(), getY());
	} else {
	    g.drawImage(getCharacterImage(), getX(), getY());
	}
    }
    
    public void displayMagicAtkImage(GraphicsContext g, boolean delete, double x,double y) {
	if (delete) {
	    g.clearRect(getMagicx(),getMagicy(), 100, 50);
	} else {
	    g.drawImage(getMagicAtkImage(), getMagicx(), getMagicy());
	}
	
    }

    /**
     * This method returns a string that includes the name of the item and the
     * quantity of the item hero currently possesses.
     * 
     * @return
     */
    public String itemInfo(Potion potion) {
	String itemInfo = "";

	itemInfo = potion.toString() + "\t" + this.potionMap.get(potion).toString();
	return itemInfo;
    }

    /**
     * 
     * @return x x value of the game character image.
     */
    public double getX() {
	return x;
    }

    /**
     * @param x double to be set to instance variable x,
     */
    public void setX(double x) {
	this.x = x;
    }

    /**
     * @return y value of the game character image.
     */
    public double getY() {
	return y;
    }

    /**
     * @param y double to be set to instance variable y.
     */
    public void setY(double y) {
	this.y = y;
    }

    /**
     * @param height height of the game character image.
     */
    public void setHeight(double height) {
	this.height = height;
    }

    /**
     * @param width width of the game character image.
     */
    public void setWidth(double width) {
	this.width = width;
    }

    /**
     * @return hasRevive the boolean value indication whether the character has a
     *         revive item.
     */
    public boolean isHasRevive() {
	return hasRevive;
    }

    /**
     * @param hasRevive The boolean value to be set to the hasRevive instance
     *                  variable.
     */
    public void setHasRevive(boolean hasRevive) {
	this.hasRevive = hasRevive;
    }

    /**
     * @return name The name of the game character.
     */
    public String getName() {
	return name;
    }

    /**
     * @param name The string to be set to the name instance variable.
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return attack The value of the attack instance variable.
     */
    public int getAttack() {
	return attack;
    }

    /**
     * @param attack The value to be set to the attack instance variable.
     */
    public void setAttack(int attack) {
	this.attack = attack;
    }

    /**
     * @return defense The value of the defense instance variable.
     */
    public int getDefense() {
	return defense;
    }

    /**
     * @param defense The value to be set to the defense instance variable.
     */
    public void setDefense(int defense) {
	this.defense = defense;
    }

    /**
     * @return stamina The value of the stamina instance variable.
     */
    public int getStamina() {
	return stamina;
    }

    /**
     * @param stamina The value to be set to the stamina instance variable.
     */
    public void setStamina(int stamina) {
	this.stamina = stamina;
    }

    /**
     * @return The value of the currentStamina instance variable.
     */
    public int getCurrentStamina() {
	return currentStamina;
    }

    /**
     * @param currentStamina The value to be set to the currentStamina instance
     *                       variable.
     */
    public void setCurrentStamina(int currentStamina) {
	this.currentStamina = currentStamina;
    }

    /**
     * @return The value of the height instance variable.
     */
    public double getHeight() {
	return height;
    }

    /**
     * @return The value of the width instance variable.
     */
    public double getWidth() {
	return width;
    }

    /**
     * @return The value of the gold instance variable.
     */
    public double getGold() {
	return gold;
    }

    /**
     * @param gold The value to be set to the gold instance variable.
     */
    public void setGold(double gold) {
	this.gold = gold;
    }

    /**
     * @return The value of the xp instance variable.
     */
    public int getXp() {
	return xp;
    }

    /**
     * @param xp The value to be set to the xp instance variable.
     */
    public void setXp(int xp) {
	this.xp = xp;
    }

    /**
     * @return The boolean value of the isDefending instance variable.
     */
    public boolean isDefending() {
	return isDefending;
    }

    /**
     * @param isDefending The boolean value to be set to the isDefending instance
     *                    variable.
     */
    public void setIsDefending(boolean isDefending) {
	this.isDefending = isDefending;
    }

    /**
     * @return The value of the mana instance variable.
     */
    public int getMana() {
	return mana;
    }

    /**
     * 
     * @param mana The value to be set to the mana instance variable.
     */
    public void setMana(int mana) {
	this.mana = mana;
    }

    /**
     * @return the potionList
     */
    public HashMap<Potion, Double> getPotionMap() {
	return potionMap;
    }

    /**
     * @param potionMap the potionMap to set
     */
    public void setPotionList(HashMap<Potion, Double> potionMap) {
	this.potionMap = potionMap;
    }

    /**
     * @return the type of character
     */
    public String getType() {
	return type;
    }

    /**
     * @param type the type of character to set
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @return the level of character
     */
    public int getLevel() {
	return level;
    }

    /**
     * @param level the level of character to set
     */
    public void setLevel(int level) {
	this.level = level;
    }

    /**
     * @return the characterImage
     */
    public Image getCharacterImage() {
	return characterImage;
    }

    /**
     * @return the characterImage when hurt
     */
    public Image getCharacterImageHurt() {
	return characterImageHurt;
    }

    /**
     * @param characterImage the characterImage to set
     */
    public void setCharacterImage(Image characterImage) {
	this.characterImage = characterImage;
    }

    /**
     * @param characterImage the characterImage to set when character is hurt
     */
    public void setCharacterImageHurt(Image characterImageHurt) {
	this.characterImageHurt = characterImageHurt;
    }

    /**
     * @return the cp
     */
    public CheapPotion getCp() {
	return cp;
    }

    /**
     * @param cp the cp to set
     */
    public void setCp(CheapPotion cp) {
	this.cp = cp;
    }

    /**
     * @return the hp
     */
    public HyperPotion getHp() {
	return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(HyperPotion hp) {
	this.hp = hp;
    }

    /**
     * @return the characterImageHeal
     */
    public Image getCharacterImageHeal() {
	return characterImageHeal;
    }

    /**
     * @param characterImageHeal the characterImageHeal to set
     */
    public void setCharacterImageHeal(Image characterImageHeal) {
	this.characterImageHeal = characterImageHeal;
    }

    /**
     * @return the magicAtk
     */
    public int getMagicAtk() {
        return magicAtk;
    }

    /**
     * @param magicAtk the magicAtk to set
     */
    public void setMagicAtk(int magicAtk) {
        this.magicAtk = magicAtk;
    }

    /**
     * @return the currentMana
     */
    public int getCurrentMana() {
        return currentMana;
    }

    /**
     * @param currentMana the currentMana to set
     */
    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
    
    /**
     * @return the magicAtkImage
     */
    public Image getMagicAtkImage() {
        return magicAtkImage;
    }

    /**
     * @param magicAtkImage the magicAtkImage to set
     */
    public void setMagicAtkImage(Image magicAtkImage) {
        this.magicAtkImage = magicAtkImage;
    }

    /**
     * @return the magicx
     */
    public double getMagicx() {
        return magicx;
    }

    /**
     * @param magicx the magicx to set
     */
    public void setMagicx(double magicx) {
        this.magicx = magicx;
    }

    /**
     * @return the magicy
     */
    public double getMagicy() {
        return magicy;
    }

    /**
     * @param magicy the magicy to set
     */
    public void setMagicy(double magicy) {
        this.magicy = magicy;
    }
    
}
