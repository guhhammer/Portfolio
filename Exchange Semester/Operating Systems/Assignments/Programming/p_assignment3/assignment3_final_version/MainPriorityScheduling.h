#pragma once
#include "PriorityScheduling.h"

// This class is used to define the execution order for the Priority Scheduling Algorithm.

class MainPriorityScheduling : public PriorityScheduling
{
	public:
		void scheduler(std::string file_name); // Overwrites LinkedList function.
		void main(std::string file_name); // Main function.
};

