package application;
import java.util.*;

public class Runner {
	public static void main(String[] args) {
		int floor = 1;
		Boolean gameOn = true;
		Scanner scan = new Scanner(System.in);
		System.out.println("What class are you: Warrior/Archer/Mage?");
		String playerChoice = scan.next();
		while (!playerChoice.equals("Warrior") && !playerChoice.equals("Archer") && !playerChoice.equals("Mage")) {
			System.out.println("Invalid choice. Please choose either Warrior/Archer/Mage.");
			playerChoice = scan.next();
		}
		
		GameCharacters player = null;
		
		if (playerChoice.equals("Warrior")) {
			player = new Warrior();
		} else if (playerChoice.equals("Archer")) {
			player = new Archer();
		} else {
			player = new Mage();
		}

		
		
		while (gameOn) {
			int healerCount = 0;
			ArrayList<GameCharacters> enemyList = new ArrayList<GameCharacters>();
			//min 1 enemy + random num from 0 to 1 + sqrt(floor) rounded down
			int numEnemies = 1 + (int) (Math.random() * ((1) + 1)) + (int) Math.sqrt(floor); 
		//	int totalEnemyHealth = 0;
			for (int i = 0; i < numEnemies; i++) {
				
				int randEnemy = (int) (Math.random() * ((2) + 1));
				while (randEnemy == 2 && healerCount == 1) { //max one healer per battle
					randEnemy = (int) (Math.random() * ((2) + 1));
				}
				
				if (randEnemy == 0) {
					enemyList.add(new MeleeEnemy(floor));
				} else if (randEnemy == 1) {
					enemyList.add(new RangedEnemy(floor));
				} else {
					enemyList.add(new HealerEnemy(floor));
					healerCount++;
				}
			//	totalEnemyHealth += enemyList.get(i).getCurrentStamina();
			}
		
			
			
			//player turn
			while (player.getCurrentStamina() > 0 && numEnemies > 0 && gameOn) {
				//player turn
				System.out.println("\nThis is floor: " + floor);
				System.out.println("\nYour health: " + player.getCurrentStamina());
				System.out.println("\nYou are facing these enemies: ");
				for (int i = 0; i < numEnemies; i++) {
					System.out.println(enemyList.get(i).getType() + " - Health: " + enemyList.get(i).getCurrentStamina());
				}
				System.out.println("\nIt is your turn. Do you want to:\n1. Attack\n2. Defend\n3. Use item\nOnly attack and defense work now");
				int choice = scan.nextInt();
				if (choice == 1) { //attack
					System.out.println("\nWho do want to attack?");
					for (int i = 0; i < numEnemies; i++) {
						System.out.println(i + 1 + ". " + enemyList.get(i).getType());
					}
					int attackChoice = scan.nextInt();
				
					int attackAmount = player.attack(enemyList.get(attackChoice - 1));
					System.out.println("\nYou dealt " + attackAmount + " damage. Target enemy health: " + enemyList.get(attackChoice - 1).getCurrentStamina());
					if (enemyList.get(attackChoice - 1).getCurrentStamina() <= 0) {
						System.out.println("You have killed the enemy.");
						enemyList.remove(attackChoice - 1);
						numEnemies--;
					}
				} else if (choice == 2) { //defend
					player.setIsDefending(true);
					
				}


				//enemy turn
				System.out.println("\nIt is the enemy's turn.");
				for (int i = 0; i < numEnemies; i++) {
					int attackAmount = enemyList.get(i).attack(player);
					System.out.println("The " + enemyList.get(i).getType() + " enemy attacked you!");
					if (attackAmount <= 0) {
						System.out.println("The enemy's attack had no effect on you!");
					} else {
						System.out.println("Your health is now " + player.getCurrentStamina() + ".");
						if (player.isDefending()) {
							System.out.println("Your defense blocked " + attackAmount / 2 + " health!");
						}
					}
				}
				player.setIsDefending(false);
				if (player.getCurrentStamina() <= 0) {
					System.out.println("\nGame Over.");
					gameOn = false;
				}
				if (numEnemies == 0) {
					System.out.println("\nCongratulations, you have advanced to the next floor!");
					floor++;
					gameOn = true;
				}
			}
			

		}
	}
}
