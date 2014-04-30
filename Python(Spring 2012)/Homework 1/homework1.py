###############################################
# Darien Nurse
# COMS3101.003 - Python - Homework 1
#
# Please fill in your solutions for part 2 to 5 
# below. 


################################################
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


#################################################
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


#################################################
# Part 4 - List Comprehension
#
# Assume we have the following dictionary:
# fruit_to_color = {'banana':'yellow',
#                  'blueberry':'blue',
#                  'cherry':'red',
#                  'lemon':'yellow',
#                  'kiwi':'green',
#                  'strawberry':'red',
#                  'tomato':'red'}
#
# Write a program that creates a dictionary that maps colors to lists of
# fruits that have this color, e.g. color_to_fruits['yellow'] should produce
# ['banana','lemon'].
#
# Hint: Before you can add an item to a list it has to be initialize. This 
# has to be done the first time you add any fruit of a color (i.e. when color
# to fruits does not yet have a key for this color).

fruit_to_color = {'banana':'yellow',
                  'blueberry':'blue',
                  'cherry':'red',
                  'lemon':'yellow',
                  'kiwi':'green',
                  'strawberry':'red',
                  'tomato':'red'}
#Your code starts here
color_to_fruit = {}
for a,b in dict(fruit_to_color).iteritems():
    try:
      color_to_fruit[b].append(a)
    except KeyError:
       color_to_fruit[b]=[a]
print color_to_fruit
#Your code ends here


#################################################
# Part 5 - Dictionary Comprehension
#
# Dictionary comprehensions can create dictionaries from sequence in much the
# same way that list comprehensions create lists. They are a new feature in
# Python 2.7 and 3.x.
#
# The basic syntax is:
#      {key expression : value expression for x in sequence if condition}
#
# Given the following list of words, use a dictionary comprehension to build
# a dictionary that maps each word to its length.

words = ['you','must','return','here','with','a','shrubbery','or','else']
#Your code starts here
length = [len(words[x]) for x in range(len(words))]
wordsWithLength = dict(zip(words,length))
print wordsWithLength
#Your code ends here
