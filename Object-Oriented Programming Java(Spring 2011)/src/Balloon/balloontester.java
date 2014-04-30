package Balloon;
public class balloontester //creates a balloon with a parameter equal to 100 then another set to 200
{
  public static void main(String[] args)
  {
   balloon balloon1 = new balloon(); //creates a balloon object
   balloon1.addAir(100);
   System.out.print("Air: "+balloon1.getVolume()+"cm cubed"+ "   "); //shows the Air in balloon1);
   System.out.print("Radius: "+balloon1.getRadius()+"cm"+ "   "); //shows the Radius of balloon1);
   System.out.print("Surface Area: "+balloon1.getSurfacearea()+"cm squared"+ "   "); //shows the Surface Area of ballloon1);
   System.out.print("Volume: "+balloon1.getVolume()+"cm cubed"+ "   "); //shows the Volume of balloon1;
   balloon1.addAir(100);
   System.out.print("\nAir: "+balloon1.getVolume()+"cm cubed"+ "   "); //shows the Air in balloon1);
   System.out.print("Radius: "+balloon1.getRadius()+"cm"+ "   "); //shows the Radius of balloon1);
   System.out.print("Surface Area: "+balloon1.getSurfacearea()+"cm squared"+ "   "); //shows the Surface Area of ballloon1);
   System.out.print("Volume: "+balloon1.getVolume()+"cm cubed"+ "   "); //shows the Volume of balloon1;
   balloon1.addMyAir(0);
   System.out.print("\nAir: "+balloon1.getVolume()+"cm cubed"+ "   "); //shows the Air in balloon1);
   System.out.print("Radius: "+balloon1.getRadius()+"cm"+ "   "); //shows the Radius of balloon1);
   System.out.print("Surface Area: "+balloon1.getSurfacearea()+"cm squared"+ "   "); //shows the Surface Area of ballloon1);
   System.out.print("Volume: "+balloon1.getVolume()+"cm cubed"+ "   "); //shows the Volume of balloon1;
    }   
 }
 
