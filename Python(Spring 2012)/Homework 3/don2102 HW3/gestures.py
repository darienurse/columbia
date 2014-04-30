'''
Created on Feb 15, 2012

@author: Darien
'''
import random

class Gesture ( object ):
    def __cmp__ ( self , other ):
        if (self.type() == other.type()):
            return 0
        elif (self.type() == 1 and other.type() == 2):
            return -1
        elif (self.type() == 2 and other.type() == 3):
            return -1
        elif (self.type() == 3 and other.type() == 1):
            return -1
        elif (self.type() == 2 and other.type() == 1):
            return 1
        elif (self.type() == 3 and other.type() == 2):
            return 1
        elif (self.type() == 1 and other.type() == 3):
            return 1
        
class Rock(Gesture):
    def __init__ ( self ):
        self.value = 1
    def type ( self ):
        return int(self.value)

class Paper(Gesture):
    def __init__ ( self ):
        self.value = 2
    def type ( self ):
        return int(self.value)

class Scissor(Gesture):
    def __init__ ( self ):
        self.value = 3
    def type ( self ):
        return int(self.value)

class Player:   
    def __init__(self, choice):
        self.choice = choice
    def play(self):
        if (self.choice == 1):
            print "CPU chose Rock"
            return Rock()
        if (self.choice == 2):
            print "CPU chose Paper"
            return Paper()
        if (self.choice == 3):    
            print "CPU chose Scissor"
            return Scissor()

class HumanPlayer(Player):
    def __init__ ( self ):
        Player.__init__( self, int (raw_input ('Enter 1 for Rock \nEnter 2 for Paper \nEnter 3 for Scissor\n' )))
    def play(self):
        if (self.choice == 1):
            print "You chose Rock"
            return Rock()
        if (self.choice == 2):
            print "You chose Paper"
            return Paper()
        if (self.choice == 3):    
            print "You chose Scissor"
            return Scissor()
def main():
    
    contin = True
    cScore = 0
    hScore = 0 
    
    while (contin):
        cpu = Player(random.randrange(1, 4))
        human = HumanPlayer()
        cChoice = cpu.play()
        hChoice = human.play()
        if (cChoice > hChoice):
            print "CPU wins\n"
            cScore = cScore + 1
        elif (cChoice < hChoice):
            print "You win\n"
            hScore = hScore + 1
        elif (cChoice == hChoice):
            print "Draw\n"
        print "The Score is now... \nYou: " + str(hScore) + "\nCPU: "+ str(cScore) + "\n"
        c = str (raw_input ("Continue Playing? Enter 'no' to stop. Enter any key to Continue\n"))
        if (c == 'no'):
            contin = False
main()        
      
            