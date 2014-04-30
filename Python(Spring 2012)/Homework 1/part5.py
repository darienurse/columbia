'''
Created on Feb 1, 2012

@author: Darien
'''
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
