package hw3;

//**************************************
//
//	Test class 
//	By: Darien Nurse
//
//**************************************

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		
		System.out.println("Welcome to the 1s and 2s Game!");
		System.out.println("Enter 1 to play against a computer.\nEnter any other number to watch two computers play:");
		
		Scanner input = new Scanner(System.in);
		int userChoise = input.nextInt();
		
		//Depending on what the user entered, start a new instance of Game (human vs. computer)
		//    or a new instance of Simulation (computer vs. computer)
		if (userChoise==1) {
			System.out.println("Rules:Both players simultaneously declare one or two." +
					"\nPlayer 1 (You) wins if the sum of the two declared numbers is odd and Player 2 (Computer) wins otherwise.");
			USERvsCPU usergame = new USERvsCPU();
			usergame.start();
			usergame.whoWon();
		} else {
			CPUvsCPU cpugame = new CPUvsCPU();
			cpugame.start();
			cpugame.whoWon();
		}
	}
}
