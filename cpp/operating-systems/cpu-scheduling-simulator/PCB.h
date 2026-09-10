#pragma once
#include <iostream>
#include <string>

// This class is used to represent a process.

class PCB
{
	private:
		std::string ID; // Identification of the process.
		int priority; 
		int burst_time;
		PCB* next;
	
	public:
		// Constructors/Deconstructor:
		PCB(void);
		PCB(std::string id, int burst_time);
		PCB(std::string id, int burst_time, int priority);
		~PCB(void);

		// Set and get functions:
		void setPriority(int p);
		void setBurst_time(int b);
		void setNext(PCB& p);
		void nextAsNullptr(void);
		std::string getId(void);
		int getPriority(void);
		int getBurst_time(void);
		PCB& getNext(void);

		// Function to display the process's information. 
		void display(void);

};

