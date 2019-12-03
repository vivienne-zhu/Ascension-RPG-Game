#Welcome to Tower Game (title TBD)
## A project by David Cai, Shari Sinclair, and Jiayu Vivienne Zhu.

This is a turn-based RPG in which the player aims to climb a 10 floor tower to reach the treasure.

On your way, you will encounter various monsters, mysterious events, secret mechanics, an elusive magic shop, and a final boss.

Th best part of it is that no two plays will be the same thanks to controlled randomization of stats, minion generation, and special events.

Choose from 3 classes: warrior, mage, and rogue, and experience different playstyles through unique stat focuses and battle perks.

**Will you succeed?**

-------
## How to Play
1. **Start:** After the Start screen, select the class of hero you would like to play with. If you hover over the class of hero hyou can see the differences in stats. A ***Mage*** has the benefit of limited powerful magic attacks, the ***Warrior*** has increased stamina and defence, and the ***Rogue*** has the advantage of a swift double attack. Next, you will enter the chosen name for your hero. 

2. **Floors and Enemies:** You will enter floor 1 of the tower. Each floor of the tower will have the same display and controls (seen below) but will have differing randomly generated enemies. Floor 1-3 will have 1 enemy, Floor 4-6 will have 2 enemies, and Floor 7-9 will have 3. Enemy types can either be the defensive and hard hitting ***Melee enemy***, the ***Ranged enemy*** with its magical attacks, or the ***Healer enemy*** who can heal the most weakened enemy on the floor. As you climb the towers the enemies will become increasingly stronger. The final floor, 10, will have one ***Boss enemy***. This the strongest of all enemies and can even more powerful _outrage_ attack at lower stamina. 

3. **Controls:** Each character will have an _Attack, Defend, Heal_ button, and ***Mages*** will have and additional _Magic Atk_ button. The Attack button allows you to atack the enemy of choice each turn. Just click the _Attack_ button above the correstponding enemy to tell you hero who to attack. If you choose the _Defend_ button, enemy attacks will be partially shielded and you you will be _Empowered_ and have a higher attack next turn. The _Heal_ will provide a drop down to you _Item bag_ and if you have any potions you will have a chance to use them before attacking or defening. You will not lose a turn if you chose to use an item, and you can use as many as needed in one go. The **Mages*** _Magic Atk_ button allows them to use a powerful magic blast attack, but uses _Mana_ so be careful to gauge you _Mana_ use. _Mana_ is fully restored before each new floor.  


4. **Transition**: After clearing a floor you will see a _transition screen_ which lets you know how much gold and experience (xp) you received. If you gained a suffiecient amount of xp, you will level up and receive special stat increases depending on the type of hero. The stat gained when you level up will be shown on this screen as well. 

5. **Magic Shop:** After clearing floors 3,6, and 9, you will be given the option on the _transition screen_ to 
enter a magical shop, where you will be able to but _potions_ and _revives_. There are two types of potions: Cheap Potions, which cost 50 gold and heals 100 stamina, and Hyper Potions, which cost 150 and heal 250 stamina.
Revives cost 250 gold and bring you back to life (i.e. renew all stamina and mana, and allow you to replay the floor on which you died). 

6. **Events:** Before each new floor, the is a chance of a random event occuring. Events can cause you to gain or lose gold, or even go down or up in floors. **Your luck will decide you fate!**

-------

To test out GUI, run GameGUI. To check out our current test cases, run GameCharactersTest as a JUnit. We are currently building out the GUI and linking functionality with the game logic. Furthermore, we use a console-based version of our game in the Runner class in order to rapidly prototype, test features, and balance mechanics. This class will be commented out as it will be deprecated upon GUI completion and will not be incorporated into the nature of the project's design.
