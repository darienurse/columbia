#include <stdlib.h>
#include "queue.h"

struct Node {
	void *data;
	struct Node *next;
};

typedef struct Node Node;

struct Queue {
	Node *head;
	Node *tail;
};

typedef struct Queue Queue;

Queue *initQueue() {
	Queue *queue = malloc(sizeof(Queue));
    queue->head = NULL;
    queue->tail = NULL;
    return queue;
}

void destroyQueue(Queue *queue) {
	Node *node = queue->head;

	while (node != NULL) {
		Node *next = node->next;
		free(node);
		node = next;
	}

	free(queue);
}

void push(Queue *queue, void *data) {
	Node *node = malloc(sizeof(Node));
	node->data = data;
	node->next = NULL;

	if (queue->head == NULL) {
		queue->head = node;
		queue->tail = node;
	}
	else {
		queue->tail->next = node;
		queue->tail = node;
	}
}

void *pop(Queue *queue) {
	if (queue->head == NULL) {
		return NULL;
	}

	Node *node = queue->head;
	void *data = node->data;
	queue->head = node->next;

	free(node);
	return data;
}
