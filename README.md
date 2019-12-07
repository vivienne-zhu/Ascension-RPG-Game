# Welcome to Ascension!
## A project by David Cai, Shari Sinclair, and Jiayu Vivienne Zhu.

This is a turn-based RPG in which the player aims to climb a 10 floor tower to reach the treasure.
On your way, you will encounter various monsters, mysterious events, secret mechanics, an elusive magic shop, and a final boss.
The best part of it is that no two plays will be the same thanks to controlled randomization of stats, minion generation, and special events.
Choose from 3 classes: warrior, mage, and rogue, and experience different playstyles through unique stat focuses and battle perks.

**Will you succeed?**

-------
## Getting Started

This game is a JavaFX application. In order to run this game you will have to have JavaFX installed or set up within your IDE.

To play the game, please run the **GameGUI class**. 

-------
## How to Play
1. **Start:** 
	Click the logo in the center of the screen to start the game!
	
	![Start Screen](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/Start.png)
	
	
	After the Start screen, select the class of hero you would like to play with. 
	
	>A **Mage** has the benefit of powerful magic attacks, the **Warrior** has higher stamina and defense, and the **Rogue** has the advantage of a swift double attack. 
	
	![Character Choice Screen](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/ChooseChar.png)
	
	**Hover over each class of hero to learn more about them before choosing.** 
	
	![Mage info](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/mageInfo.png)
	![Warrior info](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/warriorInfo.png)
	![Rogue info](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/rogueinfo.png)
	
	
	Next, enter the chosen name of your hero, and click 'Enter Floor 1' button to begin playing. 
	
	![Character Name](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/nameChar.png)


2. **Floors and Enemies:** 
	Each floor of the tower will have the same display and controls (seen controls below) but will have differing numbers of randomly generated enemies. Floor 1-3 will have 1 enemy, Floor 4-6 will have 2 enemies, and Floor 7-9 will have 3. 
	>Enemy types can either be the defensive **Melee enemy**, the high attack **Ranged enemy**, or the **Healer enemy** who can heal the most weakened enemy on the floor. As you climb the towers the enemies will become increasingly stronger. 
	
	![All enemy types](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/enemies.png)

	>The final floor, 10, will have one **Boss enemy**. This is the strongest of all enemies that can do more powerful _outrage_ attacks at lower levels of stamina. 
	
	![Boss enemy](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/Boss.png)
	
	>Enemies have random stats that are generated on the fly that scale with floor count. This is the ensure that every playthrough will be different and the player would need to adapt to kill off the most threatening of enemies first. 
	
	>The **Boss enemy** has a unique ability called outrage. We will leave it to the player to find out the trigger behind it!


3. **Controls:** 
	Each character will have an **Attack, Defend,** and **Heal** button, and **Mages** will have an additional **Magic Atk** button. 
	>When you click the **Attack** button, similar **Attack** button(s) will appear above the enemy/enemies. Click the **Attack** button above the corresponding enemy to do damage to that enemy.   
	
	![Attack Button](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/AtkBtns.png)
	
	>If you choose the **Defend** button, enemy attack damage sustained will be 75% less. You will also be _Empowered_ and have 1.5x attack power on your next turn. The text in the centre of the screen will update to let you know all the details of the battle!
	
	![Defend Button](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/defendBtn.png)
	
	 >The **Heal** button will provide a drop down to your _Item bag_, and if you have any potions you will have a chance to use them before attacking or defending. You will not lose a turn if you choose to use an item, and you can use as many items as needed in one go. 
	
	![Heal Button](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/healItems.png)


	>The **Mage's** **Magic Atk** button works in the same way as the **Attack** button, but allows them to use a powerful fire blast instead. Magic attacks require _Mana_ so be careful to gauge your _Mana_ use._Mana_ is fully restored before each new floor and you regain 25 _Mana_ each time you defend or use your normal attack during battle.  
	
 	![Magic Atk Button](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/magicAtkBtn.png)
 	
	>Hero statistics are available in battle if you hover over the (i) beside their name.
	
	![Hero stats](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/heroStats.png)

4. **Transition**: 
	After clearing a floor you will see a _transition screen_ which lets you know how much gold and experience (xp) you received. **The higher the floor and the more enemies you defeat, the more gold and xp gained!** If you gained a sufficient amount of xp, you will level up and receive stat increases that vary depending on the type of hero. In addition to regular stat increases, Rogues gain slightly more attack power, and Warriors gain slightly more defense with each level up. Mages get increases in magic attack power and mana in addition to the other stat increase. You also regain 30% of your stamina when you level up. The stat increases gained when you level up will be shown on this _transition screen_ as well. 
	 
	![Transition Screen](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/transition.png)
	![Transition Screen Level Up](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/levelUP.png)
	
 	> Regular transition screen and transition screen on level up.

5. **Magic Shop:** 
	After clearing floors 3, 6, and 9, you will be given the option on the _transition screen_ to 
	enter a **magical shop**, where you will be able to buy _potions_ and _revives_. There are two types of potions: **Cheap Potions**, which cost 50 gold and heal 100 stamina, and **Hyper Potions**, which cost 150 and heal 350 stamina.
	**Revives** cost 250 gold and bring you back to life (i.e. renew all stamina and mana, and allow you to replay the floor on which you died). 
	
	![Magic Shop](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/Shop.png)
	
	>The Magic shop!

6. **Events:** 
	Before each new floor, there is a 20% chance of a random event occurring. Events can cause you to gain or lose gold, increase attack or defense stats, and even go up or down in floors.**Your luck will decide your fate!**
	
	![Event Screen](https://github.com/UPenn-CIT599/final-project-team31_towergame/blob/master/Screenshots/event.png)
	
	>Mysterious Event Screen. 

 
------

<p align="center">We hope you enjoy our game!</p>
