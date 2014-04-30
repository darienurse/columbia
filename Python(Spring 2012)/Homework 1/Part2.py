'''
Created on Feb 1, 2012

@author: Darien
'''
# Part 2 - Lists and for-Loops
#
# Use two nested for loops to compute the value 
# of expressions of the form 
# (a[1]+...+a[m])*(b[1]+...+b[m]).
# 
# Assume that a and b are arbitrary sequences of numbers.
# For instance, for a = [1, 2, 4] and b = [2, 3] the result would be 35.

a = [1, 2, 4]
b = [2, 3]
#Your code starts here
i = 0
aSum = 0
bSum = 0
for x in a:
    aSum = aSum + x
    i = i + 1
    if i == len(a):
        for y in b:
            bSum = bSum + y
finalProduct = aSum * bSum
print finalProduct
    

#Your code ends here