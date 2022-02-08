#pragma once
#include <stdio.h>
#include <iostream>
#include <string>


/*
	This class is used to represent an item in the FIFO buffer.
	The idea is that any item can be represented within the buffer 
	by changing this class only. 
*/
class ITEM
{
	private:
		int id = -1; // Item id.
		ITEM* next = nullptr; // Next item pointer.

	public:
	    // Constructors/Deconstructor:
		ITEM(void);
		ITEM(int id);
		~ITEM(void);
		
		// Get and Set functions:
		void setID(int id);
		int getID(void);
		void setNext(ITEM& item);
		ITEM* getNext(void);
};

