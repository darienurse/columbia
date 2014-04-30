'''
Created on Feb 9, 2012

@author: Darien
'''
testList1 = [1,2,3]
testList2 = [1,2,3]
genmap = lambda x,y: x*y
 
def multigen (map,list1,list2) : 
    for x in range(len(list1)) :
        yield map(list1[x],list2[x])   
        
for product in multigen (genmap,testList1,testList2):
    print product