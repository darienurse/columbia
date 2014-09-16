================================================================
                 COMS 4130, Fall 2013      
                 Columbia University       

           Mini-Project #2 - Concurrent HashMap
================================================================

-----------------------------------------------
DEADLINE
-----------------------------------------------
This assignment is due at 11:55pm on Oct 21.

Lateness policy:
     Assignments turned in up to 24 hours after the deadline will
     receive a 20% grade penalty. Assignments turned in any time
     after that will receive a 0.

-----------------------------------------------
COLLABORATION POLICY
-----------------------------------------------
You should work in pairs to work on this project. Groups are free to exchange ideas and approaches to the challenge problem freely. However, each group must implement and understand its own design, and be ready to present it during the discussion class.

-----------------------------------------------
FOLDER CONTENT
-----------------------------------------------

Main.x10: The testbench for the project. 
          This program runs functional and performance tests on your concurrent hashmap by calling several get and put operations from within a number of asyncs

Hash.x10: This will be your HashMap implementation

WRITEME.{txt.pdf}: Write here every information that you consider relevant for grading your assignment.
         ALSO, discuss how you optimized your implementation:
               1. How did you improve your baseline implementation?
               2. What did/didn't work and why?
               3. What improvements could you make to the code given more time?

Makefile: A minimal Makefile to compile and run the project.

Notes: we will only grade Hash.x10 and WRITEME.txt, so make sure your Hash.x10 works with the original Main.x10
	   we will compile your code with x10c++ -O -NO_CHECKS for maximum performance, make sure your code works with these flags.

-----------------------------------------------
INFRASTRUCTURE SETUP
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

3. Before running an X10 program you need to set up your environment.  We have
   provided a script for the CS machines:

   source /opt/x10/env.sh

   At present, this script sets two environment variable, but we will add
   variables as necessary throughout the course.  If you are not using the CS
   machines, you are responsible for setting your own environment variables
   (currently just X10_HOME and JAVA_HOME).

4. You can set the number of threads in the following way (8 threads in this specific case)

   export X10_NTHREADS=8

5. COMPILATION FLAGS
   
   compile with no flags for functional testing (slightly faster)
   compile with -O -NO_CHECKS for maximum performance (see the language specification for more details) and make sure your program works with these flags.

-----------------------------------------------
TESTING
-----------------------------------------------
You may use the provided Main.x10 to test your implementation. This test harness checks for both speed (in milliseconds) and correctness.
Note: Your code might have a race condition, the test can not prove that your code is race free

To compile your HashMap implementation along with the provided test harness, use
  make
To run the test harness, use
  make test

Note we will test your code with different settings, see all parameters' explanation with:
  ./Hash -h

-----------------------------------------------
TURN-IN
-----------------------------------------------
To turn-in the assignment: 
   - Submit the files Hash.x10 and WRITEME.txt via the "Assignments" tab on Courseworks as tar ball or zip.
  USE the following format: UNI1_UNI2.tar.gz

(Note: We will only grade Hash.x10 and WRITEME.txt)

-----------------------------------------------
Part II - CONCURRENT HASHMAP
-----------------------------------------------
Hashmaps, queues, graphs, and other data structures are fundamental for many applications in Computer Science. Each one of the operations in these data structures involves several intermediate machine instructions. Despite the complexity of these intermediate steps, when you learned Data Structures you only had to deal with sequential code, and you were able to essentially ignore without consequence the possibility of an interruption in the middle of an operation.

  What would happen if you accessed or modified these data structures in parallel? For example, what would happen if you tried to insert a new node to a tree while it is performing a tree-balancing operation? How would you handle the shifting of the elements in the underlying array of an arraylist if you removed two random elements at once? Would we end up with an orphan node if you tried to add two elements simultaneously at the same position of a linked list? (You don't need to answer these previous questions in the WRITEME). In general, a program with concurrent access of sequential data structures is prone to race conditions. Nonetheless, as we have seen in class with our lock-free queue implementation, it is possible to make data structures thread-safe.

  Recall that a hashmap is a data structure that keeps a collection of key->value pairs accessible by key, where the keys are unique. The structure allows for average case O(1) reads and writes. Behind the scenes, it uses a hash function (such as hashCode()) to associate a key with a 'bucket.' The value then gets stored in this bucket, taking into account for hash collisions--when several keys map to the same value.
  For this assignment, you will design and implement a concurrent (thread-safe) hashmap. Your HashMap should be free of race conditions: it should behave properly even when we call any amount of 'get' and 'put' operations on it simultaneously from multiple threads. In addition, we would like you to make your concurrent implementation as fast as you can, so wrapping each method in a big atomic block (equivalent to turning your program into a serial one) won't be enough.

  For your HashMap (with keys of type K and values of type V), you'll need to implementing the following two functions inside Hash.x10:
  
  public def put(key: K, value: V) : Long
  public def get(key:K) : Pair[Long,V]

- 'put' inserts the given key-value pair into your hashmap, overwriting an existing value if the key was there already. It returns a Long which indicates the relative order of this operation with respect to other 'put' and 'get' operations. That is, if the linearization point of this 'get' happened before that of another 'get' or 'put', then the former 'get' should return a Long that is lower than the one returned by the latter 'get' and 'put'. We use this relative order when testing your hash function for correctness.

- 'get' finds and returns the value (of type V) associated to the given key (of type K), or defaultValue if not found, along with a Long indicating the relative order of this operation with respect to other 'put' and 'get' operations (see above for what we mean by relative order).
