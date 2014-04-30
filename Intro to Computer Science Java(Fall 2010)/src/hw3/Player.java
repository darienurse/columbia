package hw3;

//**************************************
//
//	Player class
//	By: Darien Nurse
//
//**************************************

import java.util.Scanner;

public class Player {
	//This instance variable tell whether the player is human
	private boolean isHuman;
	
	//   Creates the player
	public Player(boolean human) {
		isHuman = human;
	}

	//This method contains the human player, computerpalayer1, and computerplayer2 algorithms.
	public int play() {
		if (isHuman) {
			return playAsHuman();
		} else {
			return playAsComputer();
		}
	}
	
	//Play method for a human player
	public int playAsHuman() {
		
		//Need this to get input
		Scanner input = new Scanner(System.in);
		int userInput;
		System.out.println("Enter 1 or 2 ");
		
		userInput = input.nextInt();
		
		while (userInput != 1 && userInput != 2)
		{
			System.out.println("You entered an invalid number. Enter 1 or 2");
		    userInput = input.nextInt();
		}
		
		//Returns the user's input
		return userInput;
	}
	
	//Play method for a computer player
	public int playAsComputer() 
	{
		int choose = 0;
		double t = 0.6;
		double random = Math.random();
		if (t>random)
		{
	    choose=2;
		}
		if (t<random)
		{
		choose=1;
		}
		return choose;
	}
	
	//Play method for a second computer player
	public int playAsComputer2() 
	{
		int choose = 0;
		double t = 0.4;
		double random = Math.random();
		if (t>random)
		{
	    choose=2;
		}
		if (t<random)
		{
		choose=1;
		}
		return choose;
	 }
	}
