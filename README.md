# Welcome to Tower Game (title TBD)
## A project by David Cai, Shari Sinclair, and Jiayu Vivienne Zhu.

This is a turn-based RPG in which the player aims to climb a 10 floor tower to reach the treasure.
On your way, you will encounter various monsters, mysterious events, secret mechanics, an elusive magic shop, and a final boss.
The best part of it is that no two plays will be the same thanks to controlled randomization of stats, minion generation, and special events.
Choose from 3 classes: warrior, mage, and rogue, and experience different playstyles through unique stat focuses and battle perks.

**Will you succeed?**

-------
## How to Play
1. **Start:** 
	After the Start screen, select the class of hero you would like to play with. A **Mage** has the benefit of limited powerful magic attacks, the **Warrior** has higher stamina and defence, and the **Rogue** has the advantage of a swift double attack. Next, enter the chosen name of your hero. 
	
	<img width="400" alt="Screen Shot 2019-12-03 at 8 25 20 AM" src="https://user-images.githubusercontent.com/57401664/70052103-f1953d00-15a8-11ea-916f-2993dd1b9510.png"><img width="400" alt="Screen Shot 2019-12-03 at 8 25 37 AM" src="https://user-images.githubusercontent.com/57401664/70052349-7bdda100-15a9-11ea-9e5d-6f6a4f1d7623.png">
	<img width="400" alt="Screen Shot 2019-12-03 at 8 25 53 AM" src="https://user-images.githubusercontent.com/57401664/70052412-9c0d6000-15a9-11ea-8f4d-9e478bf618c1.png">


2. **Floors and Enemies:** 
	Each floor of the tower will have the same display and controls (seen controls below) but will have differing numbers of randomly generated enemies. Floor 1-3 will have 1 enemy, Floor 4-6 will have 2 enemies, and Floor 7-9 will have 3. Enemy types can either be the defensive and hard hitting **Melee enemy**, the **Ranged enemy** with its magical attacks, or the **Healer enemy** who can heal the most weakened enemy on the floor. As you climb the towers the enemies will become increasingly stronger. 
	The final floor, 10, will have one **Boss enemy**. This the strongest of all enemies and can do more powerful _outrage_ attacks at lower levels of stamina. 

3. **Controls:** 
	Each character will have an _Attack, Defend,_ and _Heal_ button, and **Mages** will have an additional _Magic Atk_ button. 
	The Attack button allows you to attack the enemy of choice each turn. To choose an enemy to attack, click the _Attack_ button above the correstponding enemy.  The text in the centre of the screen will update to let you know all the details of the battle!
	
	<img width="400" alt="Screen Shot 2019-12-03 at 9 03 25 AM" src="https://user-images.githubusercontent.com/57401664/70054346-c4975900-15ad-11ea-8b2b-bcaa43ae6436.png">
	
	If you choose the _Defend_ button, enemy attacks will be partially shielded and you will be _Empowered_ and have a higher attack next turn. 
	
	<img width="400" alt="Screen Shot 2019-12-03 at 8 58 25 AM" src="https://user-images.githubusercontent.com/57401664/70054767-88b0c380-15ae-11ea-8970-9b1be8902831.png">
	
	 The _Heal_ will provide a drop down to you _Item bag_ and if you have any potions you will have a chance to use them before attacking or defening. You will not lose a turn if you chose to use an item, and you can use as many as needed in one go. 
	
	<img width="400" alt="Screen Shot 2019-12-03 at 8 26 28 AM" src="https://user-images.githubusercontent.com/57401664/70054815-9cf4c080-15ae-11ea-80a4-f27519a55aa4.png">

	The **Mages** _Magic Atk_ button allows them to use a powerful magic blast attack, but uses _Mana_ so be careful to gauge your _Mana_ use. _Mana_ is fully restored before each new floor.  
	
 	<img width="400" alt="Screen Shot 2019-12-03 at 8 26 17 AM" src="https://user-images.githubusercontent.com/57401664/70054442-f6102480-15ad-11ea-9047-d75ac9308e08.png">
 


4. **Transition**: 
	After clearing a floor you will see a _transition screen_ which lets you know how much gold and experience (xp) you received. **The higher the floor and the more enemies you defeat, the more gold and xp gained!** If you gained a suffiecient amount of xp, you will level up and receive special stat increases that vary depending on the type of hero. The stat increases gained when you level up will be shown on this screen as well. 
	
	<img width="400" alt="Screen Shot 2019-12-03 at 9 00 00 AM" src="https://user-images.githubusercontent.com/57401664/70053790-8e0d0e80-15ac-11ea-8f2d-68a36737257e.png"><img width="400" alt="Screen Shot 2019-12-03 at 9 01 25 AM" src="https://user-images.githubusercontent.com/57401664/70053986-0542a280-15ad-11ea-872b-2b25a2f6d5b3.png">


5. **Magic Shop:** 
	After clearing floors 3,6, and 9, you will be given the option on the _transition screen_ to 
	enter a magical shop, where you will be able to buy _potions_ and _revives_. There are two types of potions: **Cheap Potions**, which cost 50 gold and heal 100 stamina, and **Hyper Potions**, which cost 150 and heal 250 stamina.
	**Revives** cost 250 gold and bring you back to life (i.e. renew all stamina and mana, and allow you to replay the floor on which you died). 
	
	<img width="400" alt="Screen Shot 2019-12-03 at 8 28 40 AM" src="https://user-images.githubusercontent.com/57401664/70053647-42f2fb80-15ac-11ea-9c1b-8fa701c6d271.png">

6. **Events:** 
	Before each new floor, the is a chance of a random event occuring. Events can cause you to gain or lose gold, or even go down or up in floors. **Your luck will decide you fate!**
	
	<img width="400" alt="Screen Shot 2019-12-03 at 8 59 06 AM" src="https://user-images.githubusercontent.com/57401664/70053707-5dc57000-15ac-11ea-95be-97ee6e34690d.png">


-------

To test out GUI, run GameGUI. To check out our current test cases, run GameCharactersTest as a JUnit. We are currently building out the GUI and linking functionality with the game logic. Furthermore, we use a console-based version of our game in the Runner class in order to rapidly prototype, test features, and balance mechanics. This class will be commented out as it will be deprecated upon GUI completion and will not be incorporated into the nature of the project's design.
