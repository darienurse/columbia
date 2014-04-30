README 
Darien Nurse
don2102
Networks Program 3

The program runs with the commandline options specified in the directions, such as
./bfclient 20000 3 128.59.196.2 20000 4.1 128.59.196.2 20001 5.2 128.59.196.4 20000 3?

LINKDOWN, LINKUP, SHOWRT, and CLOSE function. But ROUTE UPDATE hasnt been tested and is in complete.

Could not figure out how to incorporate protocols and encoding into the program. At attempted to 
mimic ROUTE UPDATE using a separtate threads that are constantly sharing routing information.
Timeout is not implimented. Also, critical design flaw where only one port can be assigned to each IP Address.