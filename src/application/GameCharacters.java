package application;

import java.util.HashMap;

/**
 * This class represents the parent class for all characters in the game (both Heros and Enemies).
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

    /**
     * The constructor initializes the necessary instance variables to O and false.
     */
    public GameCharacters(){
		gold = 0;
		xp = 0;
		isDefending = false;
		x = 0;
		y = 0;
		height = 0.0;
		width = 0.0;
    }

    /**
     * This method takes in a game character and makes changes to its stamina when it is attacked by another character.
     * 
     * @param character The character currently being attacked.
     */
    public void attack(GameCharacters character){ 
		setIsDefending(false);
		int attackValue = this.getAttack() - character.getDefense();
		if (character.isDefending()) {
			attackValue = attackValue / 2;
		}
		character.setCurrentStamina(character.getCurrentStamina() - attackValue);
	//To Add: change x coordinate so character moves forward and back when he attacks
    }
    
    /**
     * This method changes isDefending to true when a game character chooses the option to defend.
     */
    public void defend() { 
	//When game character is attacking, if isDefending = true, attack = 0/ no effect
		this.setIsDefending(true);
    } 

    /**
     * This method takes a potion object, let the character restore the stamina,
     * and removes the potion from the potion list.
     * @param potion
     */
    public void usePotion(Potion potion){
    	setCurrentStamina(currentStamina + potion.getRestorePoint());   	
    	getPotionMap().put(potion, getPotionMap().get(potion) - 1);      	
    }
    
    /** 
     * This method allows the character to revive
     * if the character has a revive 
     */
    public void revive() {
    	setCurrentStamina(this.getStamina());
    	setHasRevive(false);
    }
    
    /**
     * This method allows the player to buy potions from the shop
     * using gold. The potion bought will be added to the potion list.
     * @param potion
     * @param quantity
     */
	public void buyPotion(Potion potion, int quantity){
		setGold(getGold() - potion.getPrice() * quantity);
		getPotionMap().put(potion, getPotionMap().get(potion) + quantity);	
	}
	
	/**
	 * This method allows the player to sell potion for half of the buying price to the shop.
	 * The potions he sells will be removed from the potion list.
	 * @param potion
	 * @param quantity
	 */
	public void sellPotion(Potion potion, int quantity) {
		setGold(getGold() + potion.getPrice()/2 * quantity);
		getPotionMap().put(potion, getPotionMap().get(potion) - quantity);	
	}
	
	/**
	 * This method allows the player to buy revive from the shop for 200 gold. 
	 */
	public void buyRevive() {
		setGold(getGold() - 200);
		setHasRevive(true);		
	}
	
	/**
	 * This method allows the player to sell revive for 150 gold. 
	 */
	public void sellRevive() {
		setGold(getGold() + 150);
		setHasRevive(false);
		
	}

    /**
     * This method increases the attack, defense and stamina of the hero when certain conditions are met.
     */
    public void levelUp(){
	//change increase number
    //can be changed to get a certain number of stats at a minimum and also a maximum number of stats
    //so player cannot highroll super high stats or lowroll super bad stats
		int atk = this.getAttack();
		int rand = (int)(Math.random() * ((3) + 1));
		atk = atk + rand;
		rand = (int)(Math.random() * ((3) + 1));
		int defense = this.getDefense();
		defense = defense + rand;
		rand = (int)(Math.random() * ((3) + 1));
		int stam = this.getStamina();
		stam = stam + rand;
		if (this instanceof Mage) {
			rand = (int)(Math.random() * ((3) + 1));
			mana = mana + rand;
		}
    }

    public double getX() {
    	return x;
    }
    public void setX(double x) {
    	this.x = x;
    }

    public double getY() {
    	return y;
    }

    public void setY(double y) {
    	this.y = y;
    }

    public void setHeight(double height) {
    	this.height = height;
    }

    public void setWidth(double width) {
    	this.width = width;
    }

    public boolean isHasRevive() {
    	return hasRevive;
    }

    public void setHasRevive(boolean hasRevive) {
    	this.hasRevive = hasRevive;
    }

    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public int getAttack() {
    	return attack;
    }

    public void setAttack(int attack) {
    	this.attack = attack;
    }

    public int getDefense() {
    	return defense;
    }

    public void setDefense(int defense) {
    	this.defense = defense;
    }

    public int getStamina() {
    	return stamina;
    }

    public void setStamina(int stamina) {
    	this.stamina = stamina;
    }

    public int getCurrentStamina() {
    	return currentStamina;
    }

    public void setCurrentStamina(int currentStamina) {
    	this.currentStamina = currentStamina;
    }

    public double getHeight() {
    	return height;
    }

    public double getWidth() {
    	return width;
    }

    public int getGold() {
    	return gold;
    }

    public void setGold(int gold) {
    	this.gold = gold;
    }

    public int getXp() {
    	return xp;
    }

    public void setXp(int xp) {
    	this.xp = xp;
    }

    public boolean isDefending() {
    	return isDefending;
    }

    public void setIsDefending(boolean isDefending) {
    	this.isDefending = isDefending;
    }

    public int getMana() {
    	return mana;
    }

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
    
    


}
