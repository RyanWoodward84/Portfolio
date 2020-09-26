/*
Ryan Woodward
V00857268
CSC 360 - Lab 1
Jan 14, 2019
*/

// Doubly Linked List

#include <stdlib.h>
#include <stdio.h>


/*
Node - the structure of each node

int data - will hold the key of the node in the list
Node* next - a pointer to the next struct Node
Node* prev - a pointer to the previous struct Node
*/
struct Node
{
	int data;
	struct Node* next;
	struct Node* prev;
};

/*
Push - push a new node to the head of the list
*/
void push(struct Node** head_ref, int new_data)
{
	//Allocating memory for the new node
	struct Node* new_node = (struct Node*)malloc(sizeof(struct Node));

	//put in the data
	new_node->data = new_data;
	
	//Making the new node as the head, with it's prev pointing to null
	new_node->next = (*head_ref);
	new_node->prev = NULL;

	//change prev of the prior head node to NULL if it isn't a null node
	if ((*head_ref) != NULL)
		(*head_ref)->prev = new_node;

	//make the head reference put to the new node
	(*head_ref) = new_node;
}

/*
insertAfter - Given a node, insert a new node after it
*/

void insertAfter(struct Node* prev_node, int new_data)
{
	//First check if the given node is null
	if (prev_node == NULL)
	{
		printf("The given previous node cannot be null\n");
		return;
	}

	//allocate memory for a new node
	struct Node* new_node = (struct Node*)malloc(sizeof(struct Node));

	//put in the new data
	new_node->data = new_data;

	//make the next of the new  node as the next of the prev  node
	new_node->next = prev_node->next;

	//make the next of the prev node as the next of the new node
	prev_node->next = new_node;

	//make prev node as the prev of new node
	new_node->prev = prev_node;
	
	//change the prev of next node to the new node if it isn't pointing to null
	if (new_node->next != NULL)
		new_node->next->prev = new_node;
}

/*
append - at a node at the end of the list
*/

void append(struct Node** head_ref, int new_data)
{
	//allocate memory for the new node
	struct Node* new_node = (struct Node*)malloc(sizeof(struct Node));

	struct Node* last = *head_ref;

	//put in the data
	new_node->data = new_data;

	//new node will be last node so next must be bull
	new_node->next = NULL;

	//if linked list is empty then make new node as head
	if (*head_ref == NULL)
	{
		new_node->prev = NULL;
		*head_ref = new_node;
		return;
	}

	//else traverse to the last node
	while (last->next != NULL)
		last = last->next;

	//change the next of last node to the new node
	last->next = new_node;

	//make the prev of the new node point to the last node
	new_node->prev = last;

	return;
}

/*
insertBefore - insert a node before a node in the list
*/

void insertBefore(struct Node** head_ref, struct Node* next_node, int new_data)
{
	//check if the next node is null, if it is return from function
	if (next_node == NULL)
	{
		printf("The next node given cannot be null.\n");
		return;
	}
	
	//allocate memory for the new node
	struct Node* new_node = (struct Node*)malloc(sizeof(struct Node));
	
	//set the data for the new node
	new_node->data = new_data;
	
	//set prev pointer of new node as prev pointer of next node
	new_node->prev = next_node->prev;
	
	//set prev pointer of next node to point to new node
	next_node->prev = new_node;

	//set next pointer of new node to the next node
	new_node->next = next_node;

	//if the prev pointer of the new node is not null then set the next pointer of the prev to the new node
	if (new_node->prev != NULL)
		new_node->prev->next = new_node;
	else
	{
		(*head_ref) = new_node;
	}
	
}

/*
pop - will remove the first item of the doubly linked list
*/

int pop(struct Node** head_ref)
{
	struct Node* next_node = NULL;
	int retval = -1;

	//check to see if list is empty
	if (*head_ref == NULL)
	{
		printf("list is empty, cannot be popped\n");
		return retval;
	}


	next_node = (*head_ref)->next;
	retval = (*head_ref)->data;

	free(*head_ref);


	
	*head_ref = next_node;
	if (*head_ref != NULL)
		(*head_ref)->prev = NULL;
	
	if (*head_ref == NULL)
	{
		printf("\nList is now empty\n");
		return -1;
	}
	return retval;
}


int removeLast(struct Node** head_ref)
{
	int retval = 0;
	
	//if no items in list
	if (*head_ref == NULL)
		return -1;

	//case 2: only one item in the list
	if ((*head_ref)->next == NULL)
	{
		retval = (*head_ref)->data;
		free(*head_ref);
		return retval;
	}

	//case 3:more than one item in the list
	struct Node* current = (struct Node*)malloc(sizeof(struct Node));

	current = *head_ref;
	while (current->next->next != NULL)
	{
		current = current->next;
	}

	retval = current->next->data;
	free(current->next);
	current->next = NULL;
	
	return retval;
}
/*
getNode
*/

struct Node* getNodeData(struct Node** head_ref, int data)
{
	//base case - if no linked list exists
	if (*head_ref == NULL)
		return;

	struct Node* nodeptr = (struct Node*)malloc(sizeof(struct Node));

	nodeptr = *head_ref;

	while(nodeptr != NULL)
	{
		if (nodeptr->data == data)
			return nodeptr;
		else if (nodeptr->next == NULL)
		{
			printf("\nNode with data %d doesn't exist within linked list\n", data);
			return NULL;
		}
		nodeptr = nodeptr->next;
	}
}
/*
Swap
*/
void swap(struct Node* ref1, struct Node* ref2)
{
	//base case - neither node exists
	if (ref1 == NULL || ref2 == NULL)
	{
		printf("\nA node didn't exist, swap failed\n");
		return;
	}

	//base case 2 - same node passed
	if (ref1 == ref2)
	{
		printf("\nSame node passed, swap failed\n");
		return;
	}
	struct Node* temp1 = (struct Node*)malloc(sizeof(struct Node));
	struct Node* temp2 = (struct Node*)malloc(sizeof(struct Node));
	

	//case 1 - ref1 head
	if (ref1->prev == NULL)
	{
		//case 1.1 - ref2 in middle
		if (ref2->next != NULL)
		{
			temp2->next = ref2->next;
			temp2->prev = ref2->prev;
			
			temp1->next = ref1->next;;
			temp1->prev = NULL;

			temp2->prev->next = ref1;
			temp2->next->prev = ref1;

			temp1->next->prev = ref2;
			
			ref1->prev = temp2->prev;
			ref1->next = temp2->next;

			ref2->next = temp1->next;
			ref2->prev = NULL;
			
			temp1 = NULL;
			temp2 = NULL;
			free(temp1);
			free(temp2);
		}

		//case 1.2 - ref2 on end
		//no need for if statement since that's the only place it could be now
		temp1->next = ref1->next;
		temp1->prev = NULL;

		temp2->next = NULL;
		temp2->prev = ref2->prev;
		
		temp2->prev->next = ref1;
		
		temp1->next->prev = ref2;

		ref1->prev = temp2->prev;
		ref1->next = temp2->next;

		ref2->next = temp1->next;
		ref2->prev = temp1->prev;

		temp1 = NULL;
		temp2 = NULL;
		free(temp1);
		free(temp2);
	}

	//case 2 - ref2 is head
	if (ref2->prev == NULL)
	{
		//case 2.1 - ref1 is middle
		if (ref1->next != NULL)
		{
			temp1->next = ref1->next;
			temp1->prev = ref1->prev;

			temp2->next = ref2->next;
			temp2->prev = NULL;

			temp1->prev->next = ref2;
			temp1->next->prev = ref2;
	
			temp2->next->prev = ref1;

			ref2->prev = temp1->prev;
			ref2->next = temp1->next;

			ref1->next = temp2->next;
			ref1->prev = temp2 -> prev;

			temp1 = NULL;
			temp2 = NULL;
			free(temp1);
			free(temp2);
				
			return;			
		}
		//case 2.2 - ref1 on end
		temp2->next = ref2->next;
		temp2->prev = NULL;

		temp1->next = NULL;
		temp1->prev = ref1->prev;

		temp1->prev->next = ref2;

		temp2->next->prev = ref1;

		ref2->prev = temp1->prev;
		ref2->next = temp1->next;

		ref1->next = ref2->next;
		ref1->prev = ref2->prev;

		temp1 = NULL;
		temp2 = NULL;
		free(temp1);
		free(temp2);
		return;

	}
	//case 3 - ref1 on end, ref2 in middle
	if (ref1->next == NULL)
	{
		temp2->next = ref2->next;
		temp2->prev = ref2->prev;

		temp1->next = NULL;
		temp1->prev = ref1->prev;

		temp1->prev->next = ref2;

		temp2->prev->next = ref1;
		temp2->next->prev = ref1;

		ref2->prev = temp1->prev;
		ref2->next = temp1->next;

		ref1->prev = temp2->prev;
		ref1->next = temp2->next;

		temp1 = NULL;
		temp2 = NULL;
		free(temp1);
		free(temp2);
		return;
	}
	//case 4 - ref2 on end, ref1 in middle
	if (ref2->next == NULL)
	{
		temp1->next = ref1->next;
		temp1->prev = ref1->prev;

		temp2->next = NULL;
		temp2->prev = ref2->prev;

		temp2->prev->next = ref1;
	
		temp1->prev->next = ref2;
		temp1->next->prev = ref2;

		ref1->prev = temp2->prev;
		ref1->prev = temp2->prev;
		
		ref2->prev = temp1->prev;
		ref2->prev = temp1->prev;

		temp1 = NULL;
		temp2 = NULL;
		free(temp1);
		free(temp2);
		return;
	}
	
	//case 5 - both in middle
	temp1->next = ref1->next;
	temp1->prev = ref1->prev;

	temp2->prev = ref2->prev;
	temp2->next = ref2->next;

	temp1->prev->next = ref2;
	temp1->next->prev = ref2;

	temp2->prev->next = ref1;
	temp2->next->prev = ref1;

	ref1->prev = temp2->prev;
	ref1->next = temp2->next;

	ref2->prev = temp1->prev;
	ref2->next = temp1->next;

	temp1 = NULL;
	temp2 = NULL;
	free(temp1);
	free(temp2);
	return;
	
	return;
	
}

/*
shift_left

Shifts the nodes in a list from position ref left by n

*/

struct Node* shift_left(struct Node* ref, int n)
{
	int temp = 0;
	//base case if ref doesn't exist
	if (ref == NULL)
	{
		
		return ref;
	}

	int i = 0;
	for (i; i < n; i++)
	{
		if (temp  == -1)
		{
			break;
		}
		temp = pop(&ref);
	}
		
	return ref;
}
/*
removeByIndex - will remove the nth member of a linked list
*/

/* Under Construction


int removeByIndex(struct Node** head_ref, int n)
{
	int i = 0;
	int retval = -1;
	struct Node* del = (struct Node*)malloc(sizeof(struct Node));

	//base case
	if (*head_ref == NULL)
		return retval;

	//if head node is being deleted
	if (n == 0)
	{
		free(del
		return pop(head_ref);
	}

	return retval;
}
*/
/*
printList - will print the doubly linked list
*/

void printList(struct Node* node)
{
	if (node == NULL)
		return;
	struct Node* last;
	printf("\nTraversal in forward direction \n");
	while (node != NULL)
	{
		printf(" %d ", node->data);
		last = node;
		node = node->next;
	}

	printf("\nTraversal in reverse direction \n");
	while (last != NULL)
	{
		printf(" %d ", last->data);
		last = last->prev;
	} 
}

/*
main - driver program to test functions
*/

int main()
{
	struct Node* head = NULL;

	int popTest = 0;
	popTest = pop(&head);
	//testing push
	push (&head, 7);
	
	push (&head, 1);

	push (&head, 4);

	//testing insert before with 8 before 1

	insertBefore(&head, head->next, 8);

	printList(head);

	//testing append
	append(&head, 6);
	append(&head, 4);

	printf("\nTesting push, insertBefore, and append\n");
	printList(head);

	//testing insert after
	insertAfter(head->next, 8);

	printf("\nTesting insertAfter\n");
	printList(head);
	

	//testing pop
	int returnValue = pop(&head);

	printf("\nTesting pop\n");
	printList(head);

	//testing removelast
	int retLastVal = removeLast(&head);
	
	printf("\nTesting removeLast\n");
	printList(head);

	//testing swap
	struct Node* test1 = getNodeData(&head, 6);
	struct Node* test2 = getNodeData(&head, 1);
	
	printf("\nTesting swap function\n");
	swap(test1, test2);

	printList(head);

	//testing Shift Left
	head = shift_left(head, 1);

	printf("\nTesting shift left\n");
	printList(head);

	head = shift_left(head, 4);
	printList(head);

	returnValue = pop(&head);


	//testing removeByIndex with the head
	//int retIndVal = removeByIndex(&head, 0);

	//printf("\nTesting removeByIndex with val 0: %d\n", retIndVal);
	//printList(head);	
	//testing removeByIndex with index 2
	//int retIndVal2 = removeByIndex(&head, 2);
	
	//printf("\nTesting removeByIndex with val 2; %d\n", retIndVal2);
	//printList(head);

	getchar();
	return 0;
}
