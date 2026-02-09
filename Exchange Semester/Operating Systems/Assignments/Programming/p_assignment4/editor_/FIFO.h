#pragma once
#include <stdlib.h> 
#include "ITEM.h"

/*
	This class is used to represent a FIFO buffer.
*/
class FIFO
{
	private:
		ITEM* head, * tail; // FIFO pointers(first and last item respectively).
		int maximum_size; // Maximum size of the buffer.

	public:
		// Constructors/Deconstructor:
		FIFO(void); 
		FIFO(int size);
		~FIFO(void);
		
		// Get and Set functions:
		void setMaxSize(int ms);
		int getMaxSize(void);
		int getMaxSizeDefault(void);
		
		bool isEmpty(void); // Returns if fifo buffer is empty.
		bool isFull(void); // Returns if fifo buffer is full.
		int size(void); // Returns fifo buffer actual size.
		void add(ITEM& item); // Adds an item to the fifo buffer.
		ITEM* remove(void); // Removes an item from the fifo buffer.
		void display(void); // Displays the fifo buffer.
		int generateAnItemID(void); // Generates a random item id.
		std::string display_array_format(void); // Displays fifo buffer as an array.
		
};

