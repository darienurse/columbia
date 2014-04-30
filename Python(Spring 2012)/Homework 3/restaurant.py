'''
Created on Feb 14, 2012

@author: Darien
'''
seats = []
class Guest (object):
    def __init__(self, name, hunger):
        self.name = name
        self.hunger = hunger
    def eat(self):
        self.hunger = self.hunger-1;
        if (self.hunger == 0):
            print self.name + ": Burp!"
        return self.hunger
    
class Restaurant(object):
    def __init__(self, size):
        self.size = size
        while (len(seats) < self.size):
            seats.append(None)
    def seat(self, guest):
        for x in range(len(seats)): 
            if (seats[x] == None):
                seats[x] = guest
                print "Seating guest " + guest.name + " at table "  + str(seats.index(guest))
                return True
        print "No free table"
        return False
    def serve(self):
        for x in range(len(seats)):
            if (seats[x] != None):
                if (seats[x].hunger > 0):
                    print "Serving guest " + seats[x].name
                    status = seats[x].eat()
                    if (status == 0):
                        seats[x] = None
                    return
        if (seats is None):
            print "No guest to serve"
            
class FancyRestaurant(Restaurant):
    def __init__ (self, size):
       Restaurant.__init__(self,size)
    def seat(self, guest):
        for x in range(len(seats)): 
            if (seats[x] == None):
                seats[x] = guest
                print "Seating guest " + guest.name + " at table "  + str(seats.index(guest))
                return True
        print "No free table"
        return False
    def serve(self):
        for x in range(len(seats)):
            if (seats[x] != None):
                if (seats[x].hunger > 0):
                    print "Serving guest " + seats[x].name
                    if (seats[x].eat() == 0):
                        seats[x] = None
        if (seats is None):
            print "No guest to serve"            
                
                
        
    