package Account;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class SavingAccountTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOwner() {
		SavingAccount A1 = new SavingAccount();
		String theOwner = ("Darien"); 
		assertSame(theOwner, A1.getOwner(theOwner));
	}

	@Test
	public void testGetInterest() {
		SavingAccount A1 = new SavingAccount();
		double theInterest = 0.2; 
		assertEquals(theInterest, A1.getInterest(theInterest),10);
	}

	@Test
	public void testGetBalance() {
		SavingAccount A1 = new SavingAccount();
		double theAmount = 1000.00; 
		A1.makeDeposit(theAmount);
		assertEquals(theAmount, A1.getBalance(),10);
	}

	@Test
	public void testMakeDeposit() {
		SavingAccount A1 = new SavingAccount();
		double theAmount = 1000.00; 
		A1.makeDeposit(theAmount);
		assertEquals(theAmount, A1.getBalance(),10);
	}

	@Test
	public void testMakeWithdrawal() {
		SavingAccount A1 = new SavingAccount();
		double theAmount = 1000.00; 
		A1.makeWithdrawal(theAmount);
		assertEquals(-theAmount, A1.getBalance(),10);
	}

	@Test
	public void testUpdateBalanceWithInterest() {
		SavingAccount A1 = new SavingAccount();
		double theInterest = 20; 
		A1.getInterest(theInterest);
		double theAmount = 1000.00; 
		A1.makeDeposit(theAmount);
		A1.updateBalanceWithInterest();
		assertEquals(theAmount*((A1.getInterest(theInterest)+1)), A1.getBalance(),10);
	}

	@Test
	public void testPrintBalance() {
		SavingAccount A1 = new SavingAccount();
		double theAmount = 1000; 
		A1.makeDeposit(theAmount);
		assertTrue(A1.printBalanceAndTransactions().equals("Deposited $" +theAmount));
	}
	
	@Test
	public void Sequence1() {
		SavingAccount A1 = new SavingAccount();
		double theAmount = 1000.00; 
		A1.makeDeposit(theAmount);
		A1.makeWithdrawal(200);
		A1.makeWithdrawal(100);
		A1.makeWithdrawal(500);
		assertEquals(theAmount-200-100-500, A1.getBalance(),10);
	}
	
	@Test
	public void Sequence2() {
		SavingAccount A1 = new SavingAccount();
		double theAmount = 1000.00; 
		A1.makeDeposit(theAmount);
		A1.makeWithdrawal(800);
		A1.makeWithdrawal(100);
		A1.makeWithdrawal(500);
		assertEquals(theAmount-800-100-500, A1.getBalance(),10);
	}

}
