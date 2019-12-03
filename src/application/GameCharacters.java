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
    private boolean isEmpowered;
    private int gold;
    private int xp;
    private int mana;
    private int currentMana;
    private int level;
    private int attackUp;
    private int defenseUp;
    private int staminaUp;
    private int manaUp;
    private int magicAtkUp;
    private HashMap<Potion, Integer> potionMap;
    private CheapPotion cp;
    private HyperPotion hp;
    private double x;
    private double y;
    private double height;
    private double width;
    private Image characterImage;
    private Image characterImageHurt;
    private Image characterImageHeal;
    private Image characterImageSlash;
    private Image magicAtkImage;
    private double magicx;
    private double magicy;
    private double slashx;
    private double slashy;
    private double oldMagicx; //for ranged enemy
    private String type;
    private SoundEffect se;

    /**
     * The constructor initializes the necessary instance variables to O and false,
     * initializes sound effects, potionMap, cp, hp, and adds cp and hp to potionMap.
     */
    public GameCharacters() {
	// Initialize potion map
	this.potionMap = new HashMap<>();
	this.cp = new CheapPotion();
	this.hp = new HyperPotion();
	this.potionMap.put(cp, 0);
	this.potionMap.put(hp, 0);

	// for testing purpose, set the gold to 10000.
	this.gold = 0;
	this.xp = 0;
	this.isDefending = false;
	this.x = 0;
	this.y = 0;
	this.height = 0.0;
	this.width = 0.0;
	this.hasRevive = false;
	this.se = new SoundEffect();
//	this.setLeveledThisTurn(false);
    }

    /**
     * This method takes in a game character and makes changes to its stamina when
     * it is attacked by another character. Returns the value of that attack.
     * 
     * @param character The character currently being attacked.
     * @return attackValue the int value of the attack dealt
     */
    public int attack(GameCharacters character, Boolean outrage, Boolean empowered) {
	setIsDefending(false);
	int attackValue = this.getAttack() - character.getDefense();
	if (outrage) {
		attackValue = attackValue * 3;
	}
	if (empowered) {
		attackValue = attackValue * 2;
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
     * This method takes in a game character and makes changes to their stamina when
     * they are attacked using a magic attack.Returns the value of that attack.
     * 
     * @param character The character currently being attacked.
     * @return attackValue the int value of the magic attack dealt
     */
    public int magicAttack(GameCharacters character, Boolean isEmpowered) {
	setIsDefending(false);
	int attackValue = this.getMagicAtk() - character.getDefense();
	if (isEmpowered) {
		attackValue = attackValue * 2;
	}
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
     * This method is used by enemy healer exclusively to heal itself or rest of team
     * @param character GameCharacters chosen to get heal
     * @return Amount of health healed
     */
    public int enemyHeal(GameCharacters character) {
    	int healValue = this.getAttack() * 1;
    	int oldHealth = character.getCurrentStamina();
    	int newHealth = oldHealth + healValue;
    	if (newHealth < character.getStamina()) {
    		character.setCurrentStamina(newHealth);
    	} else {
    		healValue = character.getStamina() - character.getCurrentStamina();
    		character.setCurrentStamina(character.getStamina());
    	}
    	return healValue;
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
     * @param error The text error message that is displayed/updated as necessary
     */
    public void usePotion(Potion potion, Text error) {
	if (getPotionMap().get(potion) > 0) {
	    if (getCurrentStamina() == getStamina()) {
	    se.errorSound();
		error.setVisible(true);
		error.setText("YOU HAVE REACHED THE MAX STAMINA");
	    } else if (getCurrentStamina() + potion.getRestorePoint() > getStamina()) {
	    se.healSound();
		error.setVisible(false);
		setCurrentStamina(getStamina());
		getPotionMap().put(potion, getPotionMap().get(potion) - 1);
	    } else {
		error.setVisible(false);
		setCurrentStamina(currentStamina + potion.getRestorePoint());
		getPotionMap().put(potion, getPotionMap().get(potion) - 1);
	    }
	} else {
		se.errorSound();
	    error.setVisible(true);
	    error.setText("YOU DO NOT HAVE ENOUGH ITEMS");
	}
    }

    /**
     * This method allows the character to revive if the character has a revive.
     * It replenishes both stamina and mana (depending on player type)
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
     * This method increases the attack, defense, stamina and mana of the hero when
     * certain conditions are met. It also sets the level as it increases.
     */
    public void levelUp() {
	int atk = this.getAttack();
	int atkRand = 5 + (int) (Math.random() * ((9 - 5) + 1)); // min 5, max 9
	if (this instanceof Rogue) {
		atkRand += 2;
	}
	setAttackUp(atkRand);
	atk = atk + attackUp;
	setAttack(atk); 
	int defRand = 5 + (int) (Math.random() * ((9 - 5) + 1)); 
	if (this instanceof Warrior) {
		defRand += 2;
	}
	int defense = this.getDefense();
	setDefenseUp(defRand);
	defense = defense + defenseUp;
	setDefense(defense);
	int stamRand = 40 + (int) (Math.random() * ((60 - 40) + 1)); 
	setStaminaUp(stamRand);
	int stam = this.getStamina();
	stam = stam + staminaUp;
	setStamina(stam);
	//manaUp = 0; 
	if (this instanceof Mage) {
	    int m = this.getMana();
	    int manaGain = 25;
	    setManaUp(manaGain);
	    m = m + manaUp;
	    setMana(m);
	    int mAtk = this.getMagicAtk();
	    int magicAtkRand = 7 + (int) (Math.random() * ((9 - 7) + 1)); 
	    setMagicAtkUp(magicAtkRand);
	    mAtk = mAtk + magicAtkUp;
	    setMagicAtk(mAtk); 
	}
	this.setLevel(this.getLevel() + 1);
	int missingHealth = this.getStamina() - this.getCurrentStamina();
	//System.out.println("Missing:" + missingHealth);
	this.setCurrentStamina(this.getCurrentStamina() + (int) (missingHealth * 0.3));
	//System.out.println("New stam" + this.getCurrentStamina());
    }

    /**
     * This method allows us to display the game character image in the GUI.
     * 
     * @param g GraphicsContext needed to draw/remove the image in the GUI.
     * @param delete Boolean saying whether to clear the image or not.
     * @param hurt Boolean saying whether to display the image of the character when hurt.
     * @param heal Boolean saying whether to display the image of the character when they heal.
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
    
    /**
     * This method allows us to display and move the image of a magic attack
     * 
     * @param g GraphicsContext needed to draw/remove the image in the GUI.
     * @param delete Boolean saying whether to clear the image or not.
     * @param x x coordinate of the image
     * @param y y coordinate of the image
     */
    public void displayMagicAtkImage(GraphicsContext g, boolean delete, double x,double y) {
	if (delete) {
	    g.clearRect(getMagicx(),getMagicy(), 100, 50);
	} else {
	    g.drawImage(getMagicAtkImage(), getMagicx(), getMagicy());
	}
	
    }
    
    /**
     * This method allows us to display a slash when attacking
     * 
     * @param g GraphicsContext needed to draw/remove the image in the GUI.
     * @param delete Boolean saying whether to clear the image or not.
     * @param x x coordinate of the image
     * @param y y coordinate of the image
     */
    public void displaySlashImage(GraphicsContext g, boolean delete, double x, double y) {
    	if (delete) {
    		g.clearRect(getSlashx(),getSlashy(), 200, 125);
    	} else {
    		g.drawImage(getCharacterImageSlash(), x, y);
    	}
    }

    /**
     * This method returns a string that includes the name of the item and the
     * quantity of the item hero currently possesses.
     * 
     * @return itemInfo name/type and amount of the item
     */
    public String itemInfo(Potion potion) {
	String itemInfo = "";

	itemInfo = potion.toString() + "\t\tx" + this.potionMap.get(potion).toString();
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
    public int getGold() {
	return gold;
    }

    /**
     * @param d The value to be set to the gold instance variable.
     */
    public void setGold(int d) {
	this.gold = d;
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
     * @return The boolean value of the isEmpowered instance variable.
     */
    public boolean isEmpowered() {
	return isEmpowered;
    }
    
    /**
     * @param isEmpowered The boolean value to be set to the isEmpowered instance
     *                    variable.
     */
    public void setIsEmpowered(boolean isEmpowered) {
	this.isEmpowered = isEmpowered;
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
    
    /**
     * @return the oldMagicx
     */
    public double getOldMagicx() {
        return oldMagicx;
    }

    /**
     * @param magicy the oldMagicx to set
     */
    public void setOldMagicx(double oldMagicx) {
        this.oldMagicx = oldMagicx;
    }
    
    /**
     * @return the characterImageSlash when sword attack animation
     */
    public Image getCharacterImageSlash() {
	return characterImageSlash;
    }

    /**
     * @param characterImageSlash the characterImageSlash to set
     */
    public void setCharacterImageSlash(Image characterImageSlash) {
	this.characterImageSlash = characterImageSlash;
    }
    
    /**
     * @return the slashx
     */
    public double getSlashx() {
        return slashx;
    }

    /**
     * @param slashx the slashx to set
     */
    public void setSlashx(double slashx) {
        this.slashx = slashx;
    }

    /**
     * @return the slashy
     */
    public double getSlashy() {
        return slashy;
    }

    /**
     * @param slashx the slashx to set
     */
    public void setSlashy(double slashy) {
        this.slashy = slashy;
    }

    /**
     * @return the attackUp
     */
    public int getAttackUp() {
        return attackUp;
    }

    /**
     * @return the defenseUp
     */
    public int getDefenseUp() {
        return defenseUp;
    }

    /**
     * @return the staminaUp
     */
    public int getStaminaUp() {
        return staminaUp;
    }

    /**
     * @return the manaUp
     */
    public int getManaUp() {
        return manaUp;
    }

    /**
     * @return the magicAtkUp
     */
    public int getMagicAtkUp() {
        return magicAtkUp;
    }

    /**
     * @param attackUp the attackUp to set
     */
    public void setAttackUp(int attackUp) {
        this.attackUp = attackUp;
    }

    /**
     * @param defenseUp the defenseUp to set
     */
    public void setDefenseUp(int defenseUp) {
        this.defenseUp = defenseUp;
    }

    /**
     * @param staminaUp the staminaUp to set
     */
    public void setStaminaUp(int staminaUp) {
        this.staminaUp = staminaUp;
    }

    /**
     * @param manaUp the manaUp to set
     */
    public void setManaUp(int manaUp) {
        this.manaUp = manaUp;
    }

    /**
     * @param magicAtkUp the magicAtkUp to set
     */
    public void setMagicAtkUp(int magicAtkUp) {
        this.magicAtkUp = magicAtkUp;
    }
    
    
}
