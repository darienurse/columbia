
class Money:
#this is the action listener for any money button pressed
    def __init__(self):
        self.bills = [0,0,0,0,0,0,0,0]

    def getMoneyGiveChange(self,cardValue, money):
        cardValue = cardValue * 100 #conversion of amount into cents
        currentAmountEntered = money[7]*1 + money[6]*5 + money[5]*10 + money[4]*25 + money[3]*100 + money[2]*500 + money[1]*1000 + money[0]*2000
        
        if currentAmountEntered >= cardValue:
            change = currentAmountEntered - cardValue
            self.dispenseBills(change)
            return self.bills
        else:
            amountNeeded = cardValue - currentAmountEntered
            return amountNeeded
            # Please enter amountNeeded
        
    def dispenseBills(self, amount):
        if (amount >= 2000):
            self.bills[0] = self.bills[0]+ 1 
            amount = amount- 2000
            self.dispenseBills(amount)
        elif (amount >= 1000):
            self.bills[1] = self.bills[1]+ 1 
            amount = amount- 1000
            self.dispenseBills(amount)
        elif (amount >= 500):
            self.bills[2] = self.bills[2]+ 1 
            amount = amount- 500
            self.dispenseBills(amount)
        elif (amount >= 100):
            self.bills[3] = self.bills[3]+ 1 
            amount = amount- 100
            self.dispenseBills(amount)
        elif (amount >= 25):
            self.bills[4] = self.bills[4]+ 1 
            amount = amount- 25
            self.dispenseBills(amount)
        elif (amount >= 10):
            self.bills[5] = self.bills[5]+ 1 
            amount = amount- 10
            self.dispenseBills(amount)
        elif (amount >= 5):
            self.bills[6] = self.bills[6]+ 1 
            amount = amount- 5
            self.dispenseBills(amount)
        elif (amount >= 1):
            self.bills[7] = self.bills[7]+ 1 
            amount = amount- 1
            self.dispenseBills(amount)                      
        else:
            return self.bills                  

            
                    