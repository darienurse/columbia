package hw3;

//**************************************
//
//	USERvsCPU (Game) class 
//	By: Darien Nurse
//
//**************************************

	public class USERvsCPU{
		//Sets up player objects and creates balance & rounds variables.
		Player player1 = new Player(true);
		Player player2 = new Player(false);
		int player1Balance= 20;
		int player2Balance= 20;
		int rounds=0;

		//Sets up the rules for the game
	public void start() {
		while (player1Balance>0 && player2Balance>0)
		{
			rounds++;
			int choose1 = player1.playAsHuman();
			int choose2 = player2.playAsComputer();	
			System.out.println("The computer picked " + choose2);
			int total = choose1 +choose2;
			if (total==3)
			{ 
				player1Balance= player1Balance+3;
				player2Balance= player2Balance-3;
				System.out.println("You won this round. You won 3 dollars.\nYou balance is " + player1Balance + "\nComputer's balance is " + player2Balance);
			}
			if (total==2)
			{ 
				player1Balance= player1Balance-2;
				player2Balance= player2Balance+2;
				System.out.println("You lost this round. You lost 2 dollars.\nYou balance is " + player1Balance + "\nComputer's balance is " + player2Balance);
			}
			if (total==4)
			{ 
				player1Balance= player1Balance-4;
				player2Balance= player2Balance+4;
				System.out.println("You lost this round. You lost 4 dollars.\nYou balance is " + player1Balance + "\nComputer's balance is " + player2Balance);
			}
		}
	}	
		//Determines the winner of the game 		
		public void whoWon() {
			System.out.println("Player 1 money balance: " + player1Balance);
			System.out.println("Player 2 money balance: " + player2Balance);
			
			if (player1Balance > player2Balance) {
				System.out.println("Player 1 won!");
				System.out.println("This Game lasted " + rounds +" rounds");
			} else if (player1Balance < player2Balance) {
				System.out.println("Player 2 won!");
				System.out.println("This game lasted "+ rounds + " rounds.");
			} else {
				System.out.println("Tied!");
			}
		}
	}

	