'''
Created on Mar 27, 2012

@author: Darien
'''

from datetime import datetime, timedelta
from dateutil.relativedelta import relativedelta

class Card:                         ### (1)
    def __init__(self, cardType, balance):      ### (1a)
        self.name = datetime.today()
        self.balance = balance
        if (cardType == "regular"):
            self.balance = 0
            self.cardType = "regular"
            self.image = "regular.gif"
        elif (cardType == "one-day"):
            self.cost = 8.25
            self.cardType = "one-day"
            self.image = "one-day.gif"
            self.expire = self.name + timedelta(days=1)
        elif (cardType == "weekly"):
            self.cost = 29
            self.cardType = "weekly"
            self.image = "weekly.gif"
            self.expire = self.name + timedelta(days=7)
        elif (cardType == "biweekly"):
            self.cost = 51.50
            self.cardType = "biweekly"
            self.image = "biweekly.gif"
            #self.expire = self.name.replace(day=self.name.day + 14)
            self.expire = self.name + timedelta(days=14)
        elif (cardType == "monthly"):
            self.cost = 104
            self.cardType = "monthly"
            self.image = "monthly.gif"
            self.expire = self.name + relativedelta(months=1)
            
    def useCard(self, fee):
        if (self.cardType == "regular" and fee <= self.balance):
            self.balance = self.balance-fee
        elif (self.cardType == "regular" and fee>self.balance):
            print ("insufficient funds")
        else:
            print ("card accepted")   