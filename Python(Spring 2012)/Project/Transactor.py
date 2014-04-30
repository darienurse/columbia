from Bank import *


def getMoneyValues(card, bills):
    register = Money()
    change = register.getMoneyGiveChange(card, bills)
    if type(change) == type(int()):
        return False
    else:
        return change


