'''
Created on Feb 22, 2012

@author: Darien
'''
from gen_key import Key
import sys
import os
import pickle

encoded = []

class EncodingException(Exception):
    def __init__(self, value):
        self.value = value
    def __str__(self):
        return repr(TypeError)

def encode(message, key):
    encodedMessage = []
    for x in message:
        try:
            x = x.capitalize()
            y = key.get(x)
            tempList = []
            for z in range(y):
                tempList.append(1)
            encodedMessage.append(tempList)
        except TypeError: 
            print x + " cannot be coded. Please try again."
            return None
    return encodedMessage

def retry():
     message = raw_input ('Write a message that you would like to encode.\n' )
     pickle.dump(message,open( "message.p", "wb"))
     return pickle.load(open(os.path.abspath(sys.argv[1]),"rb"))

def main():
    message = pickle.load(open(os.path.abspath(sys.argv[1]),"rb"))
    key = pickle.load(open(os.path.abspath(sys.argv[2]),"rb"))
    encoded = encode(message,key)
    while encoded == None:
        encoded = encode(retry(),key)
    pickle.dump(encoded,open( "encodedMessage.p", "wb" ))
    print "The command-line argument for decode.py is "
    print "encodedMessage.p dictionary.p"
    return 'encodedMessage.p dictionary.p'
    
main()