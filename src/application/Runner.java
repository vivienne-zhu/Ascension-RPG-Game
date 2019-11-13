//package application;
//import java.util.*;
//
//import javafx.embed.swing.JFXPanel;
//
//
///**
// * @author David Cai
// * This Runner class is for TESTING purposes only. It allows us to test the game
// * in console before the completion of the GUI interface. It lets us prototype
// * and balance the game quickly in concurrence with the development of the GUI.
// * 
// * THIS CLASS WILL BE DEPRECATED as soon as GUI implementation is complete. 
// * Furthermore, design in this particular class is not reflective of the design 
// * implementation for this project due to the focus on quick prototyping.
// */
//public class Runner {
//	public static void main(String[] args) {
//		JFXPanel jfxPanel = new JFXPanel();
//		int floor = 1;
//		Boolean gameOn = true;
//		Scanner scan = new Scanner(System.in);
//		System.out.println("What class are you: Warrior/Archer/Mage?");
//		String playerChoice = scan.next();
//		while (!playerChoice.equals("Warrior") && !playerChoice.equals("Archer") && !playerChoice.equals("Mage")) {
//			System.out.println("Invalid choice. Please choose either Warrior/Archer/Mage.");
//			playerChoice = scan.next();
//		}
//
//		GameCharacters player = null;
//
//
//		//Creates player class based on user input
//		if (playerChoice.equals("Warrior")) {
//			player = new Warrior();
//		} else if (playerChoice.equals("Archer")) {
//			player = new Archer();
//		} else {
//			player = new Mage();
//		}
//
//		//Loop covers functionality of entire game cycle
//		while (gameOn) {
//			int healerCount = 0; //keeps track of enemy healers
//			ArrayList<GameCharacters> enemyList = new ArrayList<GameCharacters>();
//			//min 1 enemy + random num from 0 to 1 + sqrt(floor) rounded down
//			//	int numEnemies = 1 + (int) (Math.random() * ((1) + 1)) + (int) Math.sqrt(floor); 
//			int numEnemies = 1 + (int) Math.sqrt(floor); //no randomization
//			for (int i = 0; i < numEnemies; i++) {
//				if (floor == 10) {
//					enemyList.add(new BossEnemy(floor));
//				} else {
//					int randEnemy = (int) (Math.random() * ((2) + 1));
//					while (randEnemy == 2 && healerCount == 1) { //max one healer per battle
//						randEnemy = (int) (Math.random() * ((2) + 1));
//					}
//
//					//Random generation of enemy type
//					if (randEnemy == 0) {
//						enemyList.add(new MeleeEnemy(floor));
//					} else if (randEnemy == 1) {
//						enemyList.add(new RangedEnemy(floor));
//					} else {
//						enemyList.add(new HealerEnemy(floor));
//						healerCount++;
//					}
//				}
//			}
//
//			//Loop covers battle phase between player and enemy
//			while (player.getCurrentStamina() > 0 && numEnemies > 0 && gameOn && floor <= 10) {
//				//player turn
//				System.out.println("\nThis is floor: " + floor);
//				System.out.println("\nYour health: " + player.getCurrentStamina());
//				System.out.println("\nYour level: " + player.getLevel());
//				System.out.println("\nYou are facing these enemies: ");
//				for (int i = 0; i < numEnemies; i++) {
//					System.out.println(enemyList.get(i).getType() + " - Health: " + enemyList.get(i).getCurrentStamina());
//				}
//				System.out.println("\nIt is your turn. Do you want to:\n1. Attack\n2. Defend\n3. Use item\nOnly attack and defense work now");
//				int choice = scan.nextInt();
//				if (choice == 1) { //attack
//					System.out.println("\nWho do want to attack?");
//					for (int i = 0; i < numEnemies; i++) {
//						System.out.println(i + 1 + ". " + enemyList.get(i).getType());
//					}
//					int attackChoice = scan.nextInt();
//
//					int attackAmount = player.attack(enemyList.get(attackChoice - 1));
//					System.out.println("\nYou dealt " + attackAmount + " damage. Target enemy health: " + enemyList.get(attackChoice - 1).getCurrentStamina());
//					if (enemyList.get(attackChoice - 1).getCurrentStamina() <= 0) {
//						System.out.println("\nYou have killed the enemy.");
//						int xpGain = 20 + floor * 10;
//						player.setXp(player.getXp() + xpGain);
//						System.out.println("\nYou have gained " + xpGain + " experience points!");
//						System.out.println("Experience: " + player.getXp() + "/" + (40 + player.getLevel() * 60));
//						if ((player.getXp() / (50 + player.getLevel() * 50)) > 0) {
//							int[] gains = player.levelUp();
//							player.setXp(0);
//							System.out.println("\nCongragulations! You have leveled up!");
//							System.out.println("You are now Level " + player.getLevel() + ".");
//							System.out.println("Your stats have been upgraded!");
//							System.out.println("Attack: +" + gains[0]);
//							System.out.println("Defense: +" + gains[1]);
//							System.out.println("Stamina: +" + gains[2]);
//							System.out.println("\nYou have healed for 20% of your missing health.");
//							int missingHealth = player.getStamina() - player.getCurrentStamina();
//							player.setCurrentStamina(player.getCurrentStamina() + (int) (missingHealth * 0.2));
//							if (player instanceof Mage) {
//								System.out.println("Mana: +" + gains[3]);
//							}
//
//						}
//						enemyList.remove(attackChoice - 1);
//						numEnemies--;
//					}
//				} else if (choice == 2) { //defend
//					player.setIsDefending(true);
//
//				}
//
//				//enemy turn
//				if (numEnemies > 0) {
//					System.out.println("\nIt is the enemy's turn.");
//					for (int i = 0; i < numEnemies; i++) {
//						if (player.getCurrentStamina() > 0) {
//							int attackAmount = enemyList.get(i).attack(player);
//							System.out.println("The " + enemyList.get(i).getType() + " enemy attacked you!");
//							if (attackAmount <= 0) {
//								System.out.println("The enemy's attack had no effect on you!");
//							} else {
//								System.out.println("Your health is now " + player.getCurrentStamina() + ".");
//								if (player.isDefending()) {
//									System.out.println("Your defense blocked " + attackAmount / 2 + " damage!");
//								}
//							}
//						}
//					}
//				}
//				player.setIsDefending(false);
//				if (player.getCurrentStamina() <= 0) {
//					System.out.println("\nGame Over.");
//					gameOn = false;
//				}
//				if (numEnemies == 0) {
//					System.out.println("\nCongratulations, you have advanced to the next floor!");
//					floor++;
//					gameOn = true;
//				}
//			}
//
//			if (floor == 11) {
//				System.out.println("\nCongratulations, you have finished the game!");
//				gameOn = false; //finish game
//			}	
//		}
//	}
//}