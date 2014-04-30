'''
Created on Apr 25, 2014

@author: darienurse
'''

def incr_dict(dct, my_input):
    try:
        value = my_input[0]
        if len(my_input) == 1:
            if value in dct:
                dct[value] += 1
            else:
                dct[value] = 1        
        else:          
            if value not in dct:
                dct[value] = {}   
            incr_dict(dct[value], my_input[1:])
    except IndexError:
        print 'Input is empty'
    except TypeError:
        print 'The order in which youre performing incr_dict violates dictionary convention.'
    
if __name__ == "__main__":
    dct = {}
    incr_dict(dct,'r')
    incr_dict(dct,('a','b','c'))
    incr_dict(dct,('a','b','c'))
    incr_dict(dct,('a','b','f'))
    incr_dict(dct,('a','r','f','a','r','f'))
    incr_dict(dct,('x','b','f','x','b','f','a','b','f','k'))
    print dct