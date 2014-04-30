
public class MakeChangeTest {
	
	static MakeChange maker = new MakeChange();
	static int money;
	public static void main(String[] args)
	 {
		money = maker.getMoney();
		if (maker.changeable(money))
		{
			maker.makeChange();
			while (!maker.print())
			{
				maker.makeChange();
			}
		}
	 }
}
