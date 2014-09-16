Matthew Patey mep2167
Darien Nurse don2102
Kevin Walters kmw2168

Our semaphore struct contains a queue, a count and a mutex. The queue is a linked list holding all threads sleeping on the semaphore. The first step in any function besides init and destoy is to obtain mutual exclusion, which is done with a spinlock constructed from the mutex field and test and set. In down, we check the count. If it is 0 the threads adds itself to the queue and suspends. Otherwise the lock is released and the function returns. try down is similar, but instead of suspending returns an error if count is 0. Up wakes up one thread from the queue, or if none are there it increments the count.
In order to implement the semaphore, we created a queue data structure (unspecified in the homework instructions) that holds all threads currently waiting on the semaphore. This is defined in queue.c with header queue.h. The data structure supports pushing, popping, creating and destroying queues, and takes care of all memory management. Our queue works on a First-In-First-Out basis.
