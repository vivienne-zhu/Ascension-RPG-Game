package application;

import java.util.HashMap;

/**
 * This class represents the parent class for all characters in the game (both
 * Heros and Enemies).
 * 
 * @author sharisinclair, David Cai and Jiayu Zhu
 *
 */
public class GameCharacters {
    private String name;
    private int attack;
    private int defense;
    private int stamina;
    private int currentStamina;
    private boolean hasRevive;
    private boolean isDefending;
    private int gold;
    private int xp;
    private int mana;
    private HashMap<Potion, Integer> potionMap;

    private double x;
    private double y;
    private double height;
    private double width;
    
    private String type;

    /**
     * The constructor initializes the necessary instance variables to O and false.
     */
    public GameCharacters() {
	potionMap = new HashMap<>();
	gold = 0;
	xp = 0;
	isDefending = false;
	x = 0;
	y = 0;
	height = 0.0;
	width = 0.0;
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
		if (attackValue > 0) {
			character.setCurrentStamina(character.getCurrentStamina() - attackValue);
		}
		return attackValue;
	// To Add: change x coordinate so character moves forward and back when he
	// attacks
    }

    /**
     * This method changes isDefending to true when a game character chooses the
     * option to defend.
     */
    public void defend() {
	// When a game character is attacking, if isDefending = true, attack = 0 or no
	// effect
	this.setIsDefending(true);
    }

    /**
     * This method takes in a potion object, lets the character restore their
     * stamina, and removes the potion from the potionMap.
     * 
     * @param potion Potion item being held by game character (hero or enemy)
     */
    public void usePotion(Potion potion) {
	if (getPotionMap().get(potion) > 0) {
	    setCurrentStamina(currentStamina + potion.getRestorePoint());
	    getPotionMap().put(potion, getPotionMap().get(potion) - 1);
	}
    }

    /**
     * This method allows the character to revive if the character has a revive
     */
    public void revive() {
	if (isHasRevive() == true) {
	    setCurrentStamina(this.getStamina());
	    setHasRevive(false);
	}
    }

    /**
     * This method allows the player to buy potions from the shop using gold. The
     * potion bought will be added to the potionMap.
     * 
     * @param potion  The type of potion being bought.
     * @param quantity The amount of that type of potions being bought.
     */
    public void buyPotion(Potion potion, int quantity) {
	if (getGold() >= (potion.getPrice() * quantity)) {
	setGold(getGold() - potion.getPrice() * quantity);
	if (getPotionMap().keySet().contains(potion)) {
	    getPotionMap().put(potion, getPotionMap().get(potion) + quantity);
	} else {
	    getPotionMap().put(potion, quantity);
	}
	
	}
    }

    /**
     * This method allows the player to sell potion for half of the buying price to
     * the shop. The potions he sells will be removed from the potionMap.
     * 
     * @param potion The type of potion being sold.
     * @param quantity The amount of that potion being sold.
     */
    public void sellPotion(Potion potion, int quantity) {
	if (getPotionMap().get(potion) >= quantity) {
	setGold(getGold() + ((potion.getPrice() / 2) * quantity));
	getPotionMap().put(potion, getPotionMap().get(potion) - quantity);
	}
    }

    /**
     * This method allows the player to buy revive from the shop for 200 gold.
     */
    public void buyRevive() {
	if (getGold() >=200) {
	setGold(getGold() - 200);
	setHasRevive(true);
	}
    }

    /**
     * This method allows the player to sell revive for 150 gold.
     */
    public void sellRevive() {
	if (isHasRevive() == true) {
	setGold(getGold() + 150);
	setHasRevive(false);
	}
    }

    /**
     * This method increases the attack, defense and stamina of the hero when
     * certain conditions are met.
     */
    public void levelUp() {
	// change increase number
	// can be changed to get a certain number of stats at a minimum and also a
	// maximum number of stats
	// so player cannot highroll super high stats or lowroll super bad stats
	int atk = this.getAttack();
	int rand = (int) (Math.random() * ((3) + 1));
	atk = atk + rand;
	rand = (int) (Math.random() * ((3) + 1));
	int defense = this.getDefense();
	defense = defense + rand;
	rand = (int) (Math.random() * ((3) + 1));
	int stam = this.getStamina();
	stam = stam + rand;
	if (this instanceof Mage) {
	    rand = (int) (Math.random() * ((3) + 1));
	    mana = mana + rand;
	}
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
    public int getGold() {
    	return gold;
    }

    /**
     * @param gold The value to be set to the gold instance variable.
     */
    public void setGold(int gold) {
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
    public HashMap<Potion, Integer> getPotionMap() {
    	return potionMap;
    }

    /**
     * @param potionMap the potionMap to set
     */
    public void setPotionList(HashMap<Potion, Integer> potionMap) {
    	this.potionMap = potionMap;
    }

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
