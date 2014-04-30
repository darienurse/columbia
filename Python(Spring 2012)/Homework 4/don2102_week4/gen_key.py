'''
Created on Feb 22, 2012

@author: Darien
'''
from random import shuffle
import pickle

class Key (dict): 
    def __init__(self):
        self
    def Letters_to_Numbers(self):  
        letters = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"," "]
        num = [i for i in range(len(letters))]
        shuffle(num)
        code = dict(zip(letters,num))
        return code

def main():        
    k = Key()
    pickle.dump(k.Letters_to_Numbers(),open( "dictionary.p", "wb" ))
    message = raw_input ('Write a message that you would like to encode.\n' )
    pickle.dump(message,open( "message.p", "wb"))
    print "The command-line argument for encode.py is"
    print "message.p dictionary.p"
    return 'message.p dictionary.p'
main()
            
        
            