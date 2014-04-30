from restaurant import Guest
from restaurant import Restaurant 
from restaurant import FancyRestaurant 

def main():
    """ Test the restaurant module.
    """

    g1 = Guest("Alice",1)
    g2 = Guest("John",2)
    g3 = Guest("Mary",3)

    guests = [g1, g2, g3]
    choice = int (raw_input ('Where would you like to eat? \nEnter 1 For the Restaurant \nEnter 2 For the Fancy Restaurant\n' ))
    while (choice != 1 and choice != 2 ):
        print ('Invalid Input') 
        choice = int (raw_input ('Where would you like to eat? \nEnter 1 For the Restaurant \nEnter 2 For the Fancy Restaurant\n' ))
    if (choice == 1):    
        r = Restaurant(2)
    else:
        r = FancyRestaurant(2)
    
    # As long as there are still guests waiting in front of the 
    # Restaurant
    while guests: 
        if r.seat(guests[-1]): # Try to seat the next guest
            guests.pop()
        r.serve()              # Serve a guest 

    ### Desired output of part (b):
    # Seating guest Mary at table 0.
    # Serving guest Mary.
    # Seating guest John at table 1.
    # Serving guest Mary.
    # No free table.
    # Serving guest Mary.
    # Mary: Burp!
    # Seating guest Alice at table 0.
    # Serving guest Alice.
    # Alice: Burp!

if __name__ == "__main__": # Default "main method" idiom.
    main()
