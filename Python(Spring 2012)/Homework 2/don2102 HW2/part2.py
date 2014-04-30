'''
Created on Feb 9, 2012

@author: Darien
'''
testList1 = [1,2,3]
testList2 = [1,2,3]

def multigen (list1,list2) : 
    for x in range(len(list1)) :
        multi = list1[x] * list2[x]
        yield multi
        
for product in multigen (testList1,testList2):
    print product
            