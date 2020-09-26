/*
Ryan Woodward
V00857268
SENG 265
Assignment 1 - dynamic_array.cpp
*/



#include <iostream>										//-
#include <string.h>										//-
#include "dynamic_array.h"									//-
												//-
using namespace std;										//-
												//-
// ********** public functions **********							//-
												//-
Dynamic_array::Dynamic_array() {								//-
	head_p = NULL;										//-
	size = 0;										//-
}												//-
												//-
Dynamic_array::Dynamic_array(Dynamic_array & d) {						//-
	size = d.size;
	head_p = copy_blocks(d.head_p);
	
}												//-
												//-
Dynamic_array &Dynamic_array::operator=(Dynamic_array & d) {					//-
	size = d.size;

	if (head_p != NULL)
	{
		Block* current = head_p;
		while (current != NULL)
		{
			Block* next = current->next_p;
			delete current;
			current = next;
		}
	}
	head_p = copy_blocks(d.head_p);

	return *this;										//-
}												//-
												//-
Dynamic_array::~Dynamic_array() {								//-
	Block* current = head_p;
	while (current != NULL)
	{
		Block* next = current->next_p;
		delete current;
		current = next;
	}
	
}												//-
												//-
void Dynamic_array::print_state(void) {								//-
	cout << "size:" << size << endl;							//-
	for (Block * p = head_p; p != NULL; p = p->next_p) {					//-
		cout << "\tsize:" << p->size << " elements:";					//-
		for (int i = 0; i < p->size; i++) {						//-
			cout << p->a[i] << ' ';							//-
		}										//-
		cout << endl;									//-
	}											//-
}												//-
												//-
int Dynamic_array::get_size(void) {								//-
	return size;
}												//-
												//-
int& Dynamic_array::operator[](int i) {								//-
	// check i for range error								//-
	if (i < 0 || i >= size) {								//-
		throw Subscript_range_exception();						//-
	}											//-
												//-
	// find target block and index								//-
	Block_position position = find_block(i);						//-
												//-
	// return element at index i								//-
	return position.block_p->a[position.i];							//-
}												//-
												//-
void Dynamic_array::insert(int x, int i) {							//-
	// case 1: range error									//-
	if (i < 0 || i > size) {								//-
		throw Subscript_range_exception();						//-
	}											//-
												//-
	// case 2: empty array									//-
	if (size == 0) {									//-
		// create linked list consisting of a single new block				//-
		Block * new_block_p = new Block;						//-
		new_block_p->size = 1;								//-
		new_block_p->a[0] = x;								//-
		new_block_p->next_p = NULL;							//-
												//-
		// insert new block								//-
		insert_blocks(NULL, new_block_p);						//-
												//-
		// update total size								//-
		size++;										//-
												//-
		return;										//-
	}											//-
												//-
	// find target block and index								//-
	Block_position position = find_block(i);						//-
												//-
	// case 3: non-empty array; new block not needed					//-
	if (position.block_p->size < BLOCK_SIZE) {						//-
		// shift block array right							//-
		for (int j = position.block_p->size; j > position.i; j--) {			//-
			position.block_p->a[j] = position.block_p->a[j-1];			//-
		}										//-
												//-
		// assign x									//-
		position.block_p->a[position.i] = x;						//-
												//-
		// update array and block size							//-
		size++;										//-
		position.block_p->size++;							//-
												//-
	// case 4: non-empty array; new block needed						//-
	} else {										//-
		// create new block								//-
		Block * new_block_p = new Block;						//-
		new_block_p->size = 1;								//-
		new_block_p->next_p = NULL;							//-
												//-
		// case 4.a: insert x into old block						//-
		if (position.i < BLOCK_SIZE) {							//-
			// copy last array element to new block					//-
			new_block_p->a[0] = position.block_p->a[BLOCK_SIZE-1];			//-
												//-
			// shift old block array right						//-
			for (int j = position.block_p->size-1; j > position.i; j--) {		//-
				position.block_p->a[j] = position.block_p->a[j-1];		//-
			}									//-
												//-
			// assign x into old block						//-
			position.block_p->a[position.i] = x;					//-
												//-
		// case 4.b: insert x into new block						//-
		} else {									//-
			new_block_p->a[0] = x;							//-
		}										//-
												//-
		// update total size								//-
		size++;										//-
												//-
		// insert new block into linked list						//-
		insert_blocks(position.block_p, new_block_p);					//-
	}											//-
}												//-
												//-
void Dynamic_array::insert(Dynamic_array &p, int i) {						//-
	// case 1: range error									//-
	if ( i < 0 || i > size)
		throw Subscript_range_exception();	
	// case 2: parameter array empty							//-
	if (p.size == 0)
		return;
	// case 3: array empty									//-
	if (size == 0)
	{
		head_p = copy_blocks(p.head_p);
		size += p.size;
		return;
	}
	// find target block and index								//-
	Block_position position = find_block(i);						//-
	// case 4: array non-empty; new blocks not needed					//-
	if (size > 0 && (size + p.size) < BLOCK_SIZE)
	{
		//shifting the elements of the array right
		for (int j = i; j + p.size < BLOCK_SIZE; j++)
		{
			position.block_p->a[j + p.size] = position.block_p->a[j];
		}
		
		//copying the new elements from into the array
		int pIndex = 0;
		for(int k = i; k + p.size < BLOCK_SIZE; k++)
		{
			position.block_p->a[k] = p.head_p->a[pIndex];
			pIndex++;
			position.block_p->size++;
		}
		
		size += p.size;
		return;

	}
	// case 5: array non-empty; new blocks needed						//-
		// copy p									//-
		Block * copy_p = copy_blocks(p.head_p);						//-
		// case 5.a: insert position at start of block					//-
		if (i % BLOCK_SIZE == 0)
		{
			//inserting new block
			insert_blocks(head_p, copy_p);
			size += p.size;
		}
		// case 5.b: insert position at middle of block					//-
		if (i % BLOCK_SIZE != 0 && i % size != 0)
		{
			//initializing new blocks
			Block * newBlock = new Block;
			newBlock->size = 0;
			newBlock->next_p = NULL;


			//copy elements from old block to new block
			for (int j = 0; j < i % BLOCK_SIZE; j++)
			{
				newBlock->a[j] = position.block_p->a[j];
				newBlock->size++;
			}
			
			for (int k = 0; k < i % BLOCK_SIZE; k++)
			{
				position.block_p->a[k] = position.block_p->a[(i % BLOCK_SIZE) + k];
				position.block_p->size--;
			}
			insert_blocks(head_p, newBlock);
			insert_blocks(newBlock, copy_p);
			size += p.size;
			return;
		}
		// case 5.c: insert position after end of block					//-
		if (i % size == 0)
		{
			insert_blocks(position.block_p, copy_p);
			size += p.size;
			return;	
		}
		// update total size								//-
		
}												//-
												//-
void Dynamic_array::remove(int i) {								//-
	int globalSizeReductionCounter = 0;
	// case 1: range error									//-
	if (i < 0 || i >= size)
		throw Subscript_range_exception();
	// find target block and index								//-
	Block_position position = find_block(i);
	// case 2: block size == 1								//-
	if (position.block_p->size == 1) {
		remove_blocks(position.pre_block_p, position.block_p, position.block_p);
		size--;		
	}

	// case 3: block size > 1								//-
	if (position.block_p->size > 1) {
		
		for (int j = i; j < position.block_p->size; j++)
		{
			if (j == position.block_p->size - 1)
			{
				position.block_p->size--;
				globalSizeReductionCounter++;
				break;
			}
			else
			{
			position.block_p->a[j] = position.block_p->a[j+1];
			}
		}
		
	}							
	// update total size									//-
	size -= globalSizeReductionCounter;
}												//-
												//-
void Dynamic_array::remove(int start, int end) {						//-
	//Case 1: range error
	if (start < 0 || start > size)
		throw Subscript_range_exception();
	if (end < 0 || end > size)
		throw Subscript_range_exception();
	if (start > end)
		throw Subscript_range_exception();
	//Case 2: Empty array (so don't need to do anything)
	if (size == 0)
		return;
	//Case 2b: Start and End are equivalent (nothing to remove)
	if (start == end)
		return;
	//Find the target block and index
	Block_position positionStart = find_block(start);
	Block_position positionEnd = find_block(end - 1);

	//Case 3: Removing the end block
	if (start % BLOCK_SIZE == 0 && end == size && (end - start != size))
	{
		size = size - positionStart.block_p->size;
		remove_blocks(positionStart.pre_block_p, positionStart.block_p, positionStart.block_p);
		return;
	}
	//Case 4: Removal is isolated to within a block
	if ( (positionStart.i + ( (end - start) % BLOCK_SIZE)) < BLOCK_SIZE && (end - start != size) && (end - start < BLOCK_SIZE ) )
	{
		for (int j = positionStart.i; j + (end - start) <= BLOCK_SIZE; j++)
		{
			positionStart.block_p->a[j] = positionStart.block_p->a[j + (end-start)];
		}
		positionStart.block_p->size = positionStart.block_p->size - (end - start);
		size = size - (end - start);
		return;
	}
	//Case 5: Removing the entire block set
	if (end - start == size)
	{
		remove_blocks(NULL, positionStart.block_p, positionEnd.block_p);
		size = 0;
		return;
	}
	//Case 6: Removal requires removing interior blocks as whole	
	if ( positionStart.i == 0 && (end - start > BLOCK_SIZE) && (positionEnd.i == 4) )
	{
		remove_blocks(positionStart.pre_block_p, positionStart.block_p, positionEnd.block_p);
		size = size - (end - start);
		return;
	}
	//Case 7: General Removal
	else
	{
		Block_position positionCurrent;
		positionCurrent = positionStart;
		Block_position positionNext;
		int leftToRemove = end - start;
		while (leftToRemove > 0 )
		{
			//When can we remove the entire block?
			//	Only if positionCurrent.i = 0 and removalCounter > 4
			//	We then have to adjust positionNext to the next Block. Remove positionCurrent block, and reset positionCurrent to positionNext
			//	Don't forget to adjust the removalCounter by (-5) since you just removed 5 items
			
			if (positionCurrent.i == 0 && (leftToRemove >= positionCurrent.block_p->size))
			{
				positionNext.block_p = positionCurrent.block_p->next_p;
				leftToRemove -= positionCurrent.block_p->size;
				remove_blocks(positionCurrent.pre_block_p, positionCurrent.block_p, positionCurrent.block_p);
				positionCurrent.block_p = positionNext.block_p;
				if (positionCurrent.block_p != NULL)
					positionCurrent.i = 0;
				
			}
			if (positionCurrent.block_p != NULL)
			{
	
				//If the following block can be removed as well just continue
				if (positionCurrent.i == 0 && (leftToRemove > positionCurrent.block_p->size))
					continue;
			
				//If we are at the start of the block then we can shift left
				//Now let's shift items left until removalCounter - end - start == 0
			
			
				while (positionCurrent.i == 0 && leftToRemove > 0 && positionCurrent.block_p->size > leftToRemove)
				{

					for (int j = positionCurrent.i; j < (positionCurrent.block_p->size) - 1; j++)
					{
						positionCurrent.block_p->a[j] = positionCurrent.block_p->a[j+1];
					}

					leftToRemove--;
					positionCurrent.block_p->size--;

					if (positionCurrent.i >= BLOCK_SIZE)
						break;
				}
				
				//At this point we are inside the block but the index doesn't equal 0. We have to remove and resize
				//the current block and then move on to the next block
				int counter = 0;
				while (positionCurrent.i != 0 && leftToRemove > 0 && positionCurrent.i < positionCurrent.block_p->size)
				{
					counter++;
					leftToRemove--;
			
					positionCurrent.i++;
					if (positionCurrent.i == positionCurrent.block_p->size)
					{
						positionCurrent.block_p->size -= counter;
						positionNext.block_p = positionCurrent.block_p->next_p;
						positionCurrent.pre_block_p = positionCurrent.block_p;
						positionCurrent.block_p = positionNext.block_p;
						positionCurrent.i = 0;
						break;
					
					}
				}
			}

		}
		size -= (end - start);
		
	}
	

}												//-
												//-
// ********** private functions **********							//-
												//-
// purpose											//-
//	return b where										//-
//		if i < size									//-
//			b.block_p->a[b.i] is the ith element overall				//-
//		else										//-
//			b.block_p->a[b.i-1] is the i-1st element overall			//-
//												//-
//		if b.block_p == head_p								//-
//			b.pre_block_p is NULL							//-
//		else										//-
//			b.pre_block_p points to block preceding b.block_p			//-
// preconditions										//-
//	i in [1..size]										//-
Dynamic_array::Block_position Dynamic_array::find_block(int i) {				//-
	Block_position position;								//-
												//-
	// scan Block list									//-
	position.i = i;										//-
	position.pre_block_p = NULL;								//-
	for (position.block_p = head_p;								//-
	position.block_p != NULL;								//-
	position.block_p = position.block_p->next_p) {						//-
		// found in current block							//-
		if (position.i < position.block_p->size) {					//-
			break;									//-
		}										//-
												//-
		// special case: insert just after last element					//-
		if (i == size && position.block_p->next_p == NULL) {				//-
			break;									//-
		}										//-
												//-
		// not found yet: advance							//-
		position.pre_block_p = position.block_p;					//-
		position.i -= position.block_p->size;						//-
	}											//-
												//-
	return position;									//-
}												//-
												//-
// purpose											//-
//	create a new linked list which is a copy of the list pointed to p			//-
//	return a pointer to the head of the new linked list					//-
// preconditions										//-
//	p is the head of a possibly empty linked list of blocks					//-
Dynamic_array::Block * Dynamic_array::copy_blocks(Block * p) {					//-
	Block * new_head_p = NULL;								//-
	Block * new_p;										//-
	while (p != NULL) {									//-
		// allocate and link in new block						//-
		if (new_head_p == NULL) {							//-
			new_p = new Block;							//-
			new_head_p = new_p;							//-
		} else {									//-
			new_p->next_p = new Block;						//-
			new_p = new_p->next_p;							//-
		}										//-
												//-
		// copy the elements								//-
		new_p->size = p->size;								//-
		for (int i = 0; i < p->size; i++) {						//-
			new_p->a[i] = p->a[i];							//-
		}										//-
												//-
		// advance									//-
		p = p->next_p;									//-
	}											//-
												//-
	// terminate new list									//-
	if (new_head_p != NULL) {								//-
		new_p->next_p = NULL;								//-
	}											//-
												//-
	return new_head_p;									//-
}												//-
												//-
// purpose											//-
//	insert the list headed by src_p into the list headed by head_p				//-
//	if dst_p is NULL									//-
//		insert the list at the start of the list headed by head_p			//-
//	else											//-
//		insert the list just after block dst_p						//-
// preconditions										//-
//	list headed by src_p is non-empty							//-
//	list headed by src_p has no blocks in common with the list headed by head_p		//-
void Dynamic_array::insert_blocks(Block * dst_p, Block * src_p) {				//-
	// find the last block in the list headed by src_p					//-
	Block * p = src_p;									//-
	while (p->next_p != NULL) {								//-
		p = p->next_p;									//-
	}											//-
												//-
	// insert at head									//-
	if (dst_p == NULL) { // insert at head							//-
		p->next_p = head_p;								//-
		head_p = src_p;									//-
												//-
	// insert after dst_p									//-
	} else { // insert after dst_p								//-
		p->next_p = dst_p->next_p;							//-
		dst_p->next_p = src_p;								//-
	}											//-
}												//-
												//-
// purpose											//-
//	remove the blocks pointed to by start_p and end_p, and all the blocks between		//-
// preconditions										//-
//	start_p and end_p point to blocks in the list headed by head_p				//-
//	end_p points to either the same block as start_p or a block to its right		//-
//	if start_p == head_p									//-
//		pre_start_p == NULL								//-
//	else											//-
//		pre_start_p points to the block immediately preceding start_p			//-
//												//-
void Dynamic_array::remove_blocks(Block * pre_start_p, Block * start_p, Block * end_p) {	//-
	// release the blocks									//-
	while (1) {										//-
		// release start_p								//-
		Block * p = start_p->next_p;							//-
		delete start_p;									//-
												//-
		// advance									//-
		if (start_p == end_p) {								//-
			break;									//-
		} else {									//-
			start_p = p;								//-
		}										//-
	}											//-
												//-
	// link left and right sublists								//-
	if (pre_start_p == NULL) {								//-
		head_p = end_p->next_p;								//-
	} else {										//-
		pre_start_p->next_p = start_p->next_p;						//-
	}											//-
}												//-
