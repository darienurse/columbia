typedef struct Queue queue;

queue *initQueue();
void destroyQueue(queue *q);
void push(queue *q, void *data);
void *pop(queue *q);