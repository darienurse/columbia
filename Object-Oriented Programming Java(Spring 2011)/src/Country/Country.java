package Country;
import java.util.Comparator;


/**
   A country with a name and area.
*/
public class Country implements Comparator<Country>
{
   /**
      Constructs a country.
      @param aName the name of the country
      @param anArea the area of the country
   */
   public Country(String aName, String anArea)
   {
      name = aName;
      area = anArea;
   }

   /**
      Gets the name of the country.
      @return the name
   */
   public String getName()
   {
      return name;
   }

   /**
      Gets the area of the country.
      @return the area
   */
   public String getArea()
   {
      return area;
   }

   
   public static Comparator<Country> createComparatorByName(final boolean increasing)
   {
	   
	   return new Comparator<Country>() 
	   {
		   {
		   if (increasing) direction =1; else direction =-1;
		   }
		   public int compare (Country first, Country other)
		   {
			   return direction*first.getName().compareTo(other.getName());
		   }
		     private int direction;
	   };  
   }
   
   public static Comparator<Country> createComparatorByArea(final boolean increasing)
   {
	   
	   return new Comparator<Country>() 
	   {
		   {
		   if (increasing) direction =1; else direction =-1;
		   }
		   public int compare (Country first, Country other)
		   {
			   return direction*first.getArea().compareTo(other.getArea());
		   }
		     private int direction;
	   };  
   }
   
   private String name;
   private String area;
@Override
public int compare(Country o1, Country o2) {
	// TODO Auto-generated method stub
	return 0;
}
}