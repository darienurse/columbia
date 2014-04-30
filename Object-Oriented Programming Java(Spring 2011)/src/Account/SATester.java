package Account;

public class SATester 
{
	public static void main(String[] args)
	{ 
	 SavingAccount darien = new SavingAccount();
	 darien.getOwner("Darien");
	 darien.makeDeposit(1000.00);
	 darien.getInterest(20);
	 darien.updateBalanceWithInterest();
	 darien.printBalanceAndTransactions();
	 }
}
