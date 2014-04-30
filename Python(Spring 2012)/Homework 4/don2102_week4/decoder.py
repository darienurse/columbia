'''
Created on Feb 22, 2012

@author: Darien
'''
import sys
import os
import pickle

def decode(eMessage, rKey):
    decodedMessage = ""
    for x in eMessage:
        y = rKey.get(len(x),None)
        decodedMessage = decodedMessage + y
    return decodedMessage

def main():
    eMessage = pickle.load(open(os.path.abspath(sys.argv[1]),"rb"))
    key = pickle.load(open(os.path.abspath(sys.argv[2]),"rb"))
    rKey = dict(zip(key.values(), key.keys()))
    message = decode(eMessage, rKey)
    print "Your original message was '" + message + "'"
    return message

main()