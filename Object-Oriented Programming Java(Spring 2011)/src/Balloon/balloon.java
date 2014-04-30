package Balloon;
import java.util.Scanner;
public class balloon
{
  public static double air; //declares air variable
  public static double Volume; //declares Volume variable
  public static double Radius; //declares Radius variable
  public static double Surfacearea; //declares Surface Area variable
  public balloon() //creates balloon object 
  { 
	  air=0;
  }
  public void addAir(double amount) //method for adding air
  {
	  air = air +amount;  
  }
  public double getVolume() //method for calculating volume
  {
	  double Volume = (4/3)*Math.PI*(air/((4/3)*Math.PI));
   	  return Volume;
  }
  public double getRadius() //method for calculating radius
  {  
	  double Radius = Math.cbrt(((air*0.75)/Math.PI));
      return Radius;
  }
  public double getSurfacearea() //method for calculating surface area
  {
	  double Surfacearea = (4*Math.PI*Math.pow((Math.cbrt(((air*0.75)/(Math.PI)))),2));
  	  return Surfacearea;
  }
  public void addMyAir(double amount)// allows the user to enter his/her own value for the air
  {
	  Scanner scanner1 = new Scanner(System.in);
	  System.out.println("\nWould you like to make your own balloon? Please enter a postive interger. ");
	  amount = scanner1.nextInt();
	  air=0;
	  air = air +amount;  
  }
}