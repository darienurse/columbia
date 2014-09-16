/*
 * NAME, etc.
 *
 * sync.c
 *
 * Synchronization routines for SThread
 */

#define _REENTRANT

#include <stdlib.h>
#include <sched.h>
#include "sync.h"
#include "sthread.h"


/*
 * semaphore routines
 */


/*
 * set the semaphore count and create the queue
 */
int sthread_sem_init(sthread_sem_t *sem, int count)
{
	sem->count = count;
	sem->mutex = malloc(sizeof(unsigned long));
	*sem->mutex = 0;
	sem->sem_queue = initQueue();
	return 0;

}

int sthread_sem_destroy(sthread_sem_t *sem)
{
	destroyQueue(sem->sem_queue);
	free ((void *)sem->mutex);
	return 0;
}

int sthread_sem_down(sthread_sem_t *sem)//will decrement the semaphore by 1 if the value of which is greater than 0 (these two steps must be atomic), or it will block until another thread releases the semaphore and wakes it up.
{
	while (test_and_set(sem->mutex))
		sched_yield();
	if(sem->count == 0){
		push(sem->sem_queue, sthread_self());
		*(sem->mutex) = 0;
		sthread_suspend();
	} else {
		sem->count--;
		*(sem->mutex) = 0;
	}
	return 0;
}

int sthread_sem_try_down(sthread_sem_t *sem) //should obtain the semaphore and return 0 if the semaphore is available, otherwise return non-zero immediately. This function does not cause the caller to block.
{
	while (test_and_set(sem->mutex))
		sched_yield();
	if (sem->count > 0) {
		sem->count--;
		*(sem->mutex) = 0;
		return 0;
	}
	*(sem->mutex) = 0;
	return -1;
}

int sthread_sem_up(sthread_sem_t *sem)//will increment the value of semaphore by 1 if nobody is being blocked on it; if there are threads waiting for the semaphore, it should wake up one of the waiting threads; these two steps must also be atomic.
{
	while (test_and_set(sem->mutex))
		sched_yield();

	sthread_t thread = pop(sem->sem_queue);

	if (thread) {
		sthread_wake(thread);
	}
	else {
		sem->count++;
	}

	*(sem->mutex) = 0;
    return 0;
}
