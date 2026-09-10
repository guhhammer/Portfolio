#pragma once
#include "RoundRobin.h"

// This class is used to define the execution order for the Round-Robin Scheduling Algorithm.

class MainRoundRobin : public RoundRobin
{
	private:
		int time_quantum = 1; // Time Quantum.
	public:
		void scheduler(std::string file_name); // Overwrites LinkedList function.
		void main(std::string file_name, int time_quantum_); // Main function.
		
};

