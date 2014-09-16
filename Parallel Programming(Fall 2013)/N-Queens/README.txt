================================================================
                 COMS 4130, Fall 2013      
                 Columbia University       

           Mini-Project #1 - N QUEENS WITH PAWNS
================================================================

-----------------------------------------------
DEADLINE
-----------------------------------------------
This assignment is due at 11:55pm on Sep 30.

Lateness policy:
     Assignments turned in up to 24 hours after the deadline will
     receive a 20% grade penalty. Assignments turned in any time
     after that will receive a 0.

-----------------------------------------------
COLLABORATION POLICY
-----------------------------------------------
You should work in pairs and form teams (or request partners) by the end of this week using: 
https://docs.google.com/forms/d/1aFgw4loXSr2Jh-35fsMRDvPw6BuBG60ezUHpKzWGewY/viewform 

Groups are free to exchange ideas and approaches to the challenge problem freely. However, each group must implement and understand its own design, and be ready to present it during the discussion class.

-----------------------------------------------
FOLDER CONTENT
-----------------------------------------------

Main.x10: The testbench for the project. 
          Opens a file of different board configurations supplied through the command
          line. Invokes the Solver.solve() method for every board. 

Solver.x10: The file to modify with a parallel version of the solve method.

WRITEME.txt: Write here every information that you consider relevant for grading your assignment.
         ALSO, discuss how you optimized your implementation:
               1. How did you improve your baseline implementation?
               2. What did/didn't work and why?
               3. What improvements could you make to the code given more time?

configurations: A set of possible configurations to test.
                These configurations cover various cases for which your program must return a correct result.
                Depending on the status of your implementation feel free to modify or create new configurations.
                We will use a similar file for grading.

Makefile: A minimal Makefile to compile and run the project.

-----------------------------------------------
CONFIGURATION FORMAT
-----------------------------------------------

A board configuration specified in ./configurations follows this format:

<boolean indicator test or benchmark> ; <size of the board> ; <correct number of solutions> ; <pawns coordinates>*

If a configuration begins with a 0 it is treated as a functional test, i.e. no timings are taken by the testbench, only the correctness of the result is considered.
Otherwise three timings are taken and the median is outputted on stdout (correctness is also checked).

We suggest that you test on the simpler (smaller and/or no pawns) case first using sequential code and then move on to more complicated configurations. 

-----------------------------------------------
PART I - INFRASTRUCTURE SETUP
-----------------------------------------------
1. Create a CS Account

  There is a $50 account fee for the semester of use.  This is the only
  expense associated with this class, as we require no textbooks.
   
  Go to http://www.cs.columbia.edu/~crf/ and navigate to "CS Accounts Page",
  then "Apply for a CS Account"
   
  On this page, you should complete the application form.  
  * Indicate that your account is *not* sponsored by a CS faculty member.  
  * Use your UNI as your CS account name.  It is important that you do so as
    we will use your UNIs when setting permissions.

2. Make sure you can log into the CS machines.  For now, you may use any clic-lab machine:

   ssh UNI@clic-lab.cs.columbia.edu

3. Before running an X10 program you need to set your environment.  We have
   provided a script for the CS machines:

   source /opt/x10/env.sh

   At present, this script sets just one environment variable, but we will add
   variables as necessary throughout the course.  If you are not using the CS
   machines, you are responsible for setting your own environment variables
   (currently just X10_HOME).

4. You can set the number of threads in the following way (8 threads in this specific case)

   export X10_NTHREADS=8

5. Having completed the above you should be able to run the test program:

   make Hello
   ./Hello

6. To turn-in the assignment: 
   - Submit the files Solver.x10 and WRITEME.txt via the "Assignments" tab on Courseworks as tar ball or zip.
	USE the following format: UNI1_UNI2.tar.gz


-----------------------------------------------
Part II - GETTING FAMILIAR WITH X10
-----------------------------------------------
    The n queens problem (http://en.wikipedia.org/wiki/Eight_queens_puzzle) is a well known puzzle in the field of Artificial Intelligence. Recall that in this problem you are given an N-by-N chessboard and are asked to place N queens such that no two queens attack each other. Queens in chess mutually attack each other when they are within the same row, column, or diagonal and there is a line of empty squares between them.

    For this mini-project, you'll be considering a variation to this puzzle: The N queens with pawns problem. In this variation, the board has 0 or more pawns initially on the board.

                               - - - -
                               - - P P
                               P - P -
                               - - - -
                            
               Figure 1 - An example initial configuration for N=4, where
                          a P represents a pawn and a dash represents an
                          empty space


                               - - - - -
                               - - - - -
                               - - - - -
                               - - - - -
                               - - - - -
                            
               Figure 2 - An example initial configuration for N=5.

Given an initial board configuration, your challenge is to compute the total number of valid queen configurations (placements of the N queens into empty spaces on the board such that no two queens attack each other). That is, implement the solve() function inside of Solver.x10.


                               - - - Q
                               - Q P P
                               P - P Q
                               Q - - -

               Figure 3 - One valid configuration for the board shown
                          on Figure 1

You can test the accuracy of your solution with the provided test cases by running:
  make test

-----------------------------------------------
Part III - ADDING CONCURRENCY
-----------------------------------------------
    Having implemented the solver, your task is to use the 'finish' and 'async' constructs in X10 to implement the n queens with pawns algorithm in parallel. Implement this parallel implementation inside the Solve() method within Solver.x10.

The "CONFIGURATION FORMAT" section of this README file indicates what you need to change in the configuration file in order to run several tests and measure the speed of your solution.

-----------------------------------------------
HINTS
-----------------------------------------------

- A reference parallel implementations of the N-Queens problem (without pawns) can be found under the section "The APGAS model" at: http://x10.sourceforge.net/documentation/intro/latest/html/
- X10 documentation: http://x10.sourceforge.net/x10doc/latest/