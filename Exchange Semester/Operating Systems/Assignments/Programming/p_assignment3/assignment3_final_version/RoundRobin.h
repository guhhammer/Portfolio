#pragma once
#include "PCB.h"
#include "LinkedList.h"

// This class is used to represent a Round-Robin Scheduling Algorithm.

class RoundRobin : public LinkedList
{
	
	protected:
		int timeQuantum; // Time Quantum.
		LinkedList listToLoad; // A priority-with-round-robin list made from the original list.

		int processExecutionNumberOfSlices(PCB& p); // Returns the number of times a process was "sliced".
		int makeEstimation(PCB& p); // Returns how many slices there will be on the list.
		bool makeRoundRobinOnList(void); // Makes a priority-with-round-robin list.

	public:
		// Constructors/Deconstructor:
		RoundRobin(void);
		RoundRobin(int rr);
		~RoundRobin(void);

		int getTurnAroundTime(PCB& p); // Overwrites LinkedList's function.
		int getWaitingTime(PCB& p); // Overwrites LinkedList's function. 
		float getAvgTurnAroundTime(void); // Overwrites LinkedList's function.
		float getAvgWaitingTime(void); // Overwrites LinkedList's function.
		void displaySchedulingTimes(void); // Overwrites LinkedList's function.

};

