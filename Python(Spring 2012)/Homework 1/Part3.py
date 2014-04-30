'''
Created on Feb 1, 2012

@author: Darien
'''
# Part 3 - List Comprehension
#
# Given a sequence lst, using a list comprehension create a list that is 
# identical to lst, but without elements that have an identical element next
# to them. For instance the, list
#         lst = ['a','b','b','c','a','a','b','c','a','a']
# should produce the answer ['a','c','b','c'].
#
# Pay attention to the first and last element. 
# Instead of iterating over elements of l, you should iterate over indices 
# of lst. This should work for arbitrary sequences of arbitary length.

lst= ['a','b','b','c','a','a','b','c','a','a']
#Your code starts here
noRepeat = [lst[x] for x in range(len(lst)-1) if (lst[x] != lst[x+1] and lst[x] != lst[x-1]) or (lst[x] != lst[x+1] and x == 0)]
print noRepeat
#Your code ends here
