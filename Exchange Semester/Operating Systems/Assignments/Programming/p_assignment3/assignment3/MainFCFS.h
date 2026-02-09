#pragma once
#include "FCFS.h"

// This class is used to define the execution order for the FCFS Scheduling Algorithm.

class MainFCFS : public FCFS
{
	public:
		void scheduler(std::string file_name); // Overwrites LinkedList function.
		void main(std::string file_name); // Main function.

};

