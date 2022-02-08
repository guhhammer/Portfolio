#pragma once
#include "LinkedList.h"

// This class is used to represent a Priority-with-Round-Robin Scheduling Algorithm.

class PriorityAndRR : public LinkedList
{

	protected:
		int timeQuantum; // Time Quantum.
		LinkedList listToLoad; // A priority-with-round-robin list made from the original list.

		int processExecutionNumberOfSlices(PCB& p);  // Returns the number of times a process was "sliced".
		int makeEstimation(PCB& p); // Returns how many slices there will be on the list.
		bool singlePriorityProcess(PCB& p); // Returns if the process is the only process with the priority.
		bool makeRoundRobinOnList(void); // Makes a priority-with-round-robin list.
	
	public:
		// Constructors/Deconstructor:
		PriorityAndRR(void);
		PriorityAndRR(int time_quantum);
		~PriorityAndRR(void);
		
		void push(PCB& p); // Overwrites LinkedList's function.
		int getTurnAroundTime(PCB& p); // Overwrites LinkedList's function.
		int getWaitingTime(PCB& p); // Overwrites LinkedList's function.
		float getAvgTurnAroundTime(void); // Overwrites LinkedList's function.
		float getAvgWaitingTime(void); // Overwrites LinkedList's function.
		void displaySchedulingTimes(void); // Overwrites LinkedList's function.
		
};

