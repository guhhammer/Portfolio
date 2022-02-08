#pragma once
#include "LinkedList.h"

// This class is used to represent a Priority Scheduling Algorithm.

class PriorityScheduling : public LinkedList
{
	public:
		void push(PCB& p); // Overwrites LinkedList's function.
		void display(void); // Overwrites LinkedList's function.
		void displaySchedulingTimes(void); // Overwrites LinkedList's function.
};

