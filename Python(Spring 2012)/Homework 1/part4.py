'''
Created on Feb 1, 2012

@author: Darien
'''
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
