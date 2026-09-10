#pragma once
#include "LinkedList.h"

// This class is used to represent a SJF Scheduling Algorithm.

class SJF : public LinkedList
{
	public:
		void push(PCB& p); // Overwrites LinkedList's function.
		void displaySchedulingTimes(void); // Overwrites LinkedList's function.
};

