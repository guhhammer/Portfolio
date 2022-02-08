#pragma once
#include "SJF.h"

// This class is used to define the execution order for the SJF Scheduling Algorithm.

class MainSJF : public SJF
{
	public:
		void scheduler(std::string file_name); // Overwrites LinkedList function.
		void main(std::string file_name); // Main function.
};

