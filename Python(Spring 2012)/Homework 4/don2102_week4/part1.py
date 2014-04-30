'''
Created on Feb 22, 2012

@author: Darien
'''
import sys
import os

def DrugSearch(directory):
    fileList = []
    searchList = ['marijuana', 'marihuana', 'cannabis', 'weed', 'mary_jane']
    for x in os.listdir(directory):
        x = os.path.join(directory,x)
        fileList.append(x)
    for y in fileList:
        if (os.path.isdir(y) == True):
            nextList = os.listdir(y)
            pathName = os.path.join(os.path.dirname(y),os.path.basename(y))
            fileList.remove(y) 
            for z in nextList:
                fileList.append(os.path.join(pathName,z))
            nextList= []
    for x in fileList:
        for y in searchList:
            if (caseInsenStringCompare(y, os.path.basename(x)) > 0):
                print x          
        
def caseInsenStringCompare(s1, s2):
    try:
        return s2.lower().find(s1.lower()) + 1
    except AttributeError:
        print "Please only pass strings into this method."
        print "You passed a %s and %s" % (s1.__class__, s2.__class__)

def dir_list_folder(head_dir, dir_name):
    finalPathList = []
    for root, dirs, files in os.walk(head_dir):
        for d in dirs:
            if d.upper() == dir_name.upper():
                finalPathList.append(os.path.join(root, d))
    return finalPathList
                       
def main():
    path = dir_list_folder(os.getcwd(),sys.argv[1])[0]
    if (os.path.isdir(path) == False):
        print "Directory not found!"
    else: 
        DrugSearch(path)
    
main()