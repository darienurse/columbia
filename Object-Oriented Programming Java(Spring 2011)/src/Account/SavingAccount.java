package Account;
import java.util.ArrayList;

public class SavingAccount 
{
	private String owner;
	private double balance=0;
	private double interest;
	private double transCounter=0;
	ArrayList<String> transactions = new ArrayList<String>();
	public String getOwner(String n)
	{
		owner=n;
		return owner;
	}
	public double getInterest(double interestPercentage)
	{
		interest=interestPercentage*0.01;
		return interest;
	}
	public double getBalance()
	{
		return balance;
	}
	public void makeDeposit(double aValue)
	{
		transCounter++;
		transactions.add("Deposited $"+aValue);
		balance=balance+aValue;
	}
	public void makeWithdrawal(double aValue)
	{
		transCounter++;
		transactions.add("Withdrew $"+aValue);
		balance=balance-aValue;
	}
	public void updateBalanceWithInterest()
	{
		transCounter++;
		double current=balance;
		balance=balance*(interest+1);
		transactions.add("Interest- Balance has increased from $"+current+" to $"+balance);
	}
	public String printBalanceAndTransactions()
	{
		int current=0;
		System.out.println("For Account belonging to " +owner+"\n"+ "Your current balance: $" + balance); 
		System.out.println("Transactions:");	
		for(current=0; current<transCounter; current++)
		{
			System.out.println(current+1+". "+transactions.get(current));
		}
		return transactions.get(current-1);
	}
}
