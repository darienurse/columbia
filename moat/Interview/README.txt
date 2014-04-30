This executable currently runs the following cases
	incr_dict(dct,'r')
    incr_dict(dct,('a','b','c'))
    incr_dict(dct,('a','b','c'))
    incr_dict(dct,('a','b','f'))
    incr_dict(dct,('a','r','f','a','r','f'))
    incr_dict(dct,('x','b','f','x','b','f','a','b','f','k'))

My implementation uses recursion. Therefore, it should be able to handle extremely long tuples if there is space on the memory stack for repeated function calls. An iterative solution would eliminate this memory cost, but it would take a little longer to write.