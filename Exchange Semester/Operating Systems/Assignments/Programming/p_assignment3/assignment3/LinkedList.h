#pragma once
#include <iostream>
#include <string.h>
#include <string>
#include <fstream>
#include "PCB.h"

// This class is used to represent a list of processes.

class LinkedList
{
	protected:
		PCB* head; // Head of the list.

	public:
		// Constructor/Deconstructor:
		LinkedList(void);
		~LinkedList(void);
		
		// Basic functions:
		PCB* getHead(void); // Returns list head.
		bool isEmpty(void); // Returns if list is empty.
		int size(void); // Returns list's size.
		void push(PCB& p); // Adds a process to the list.
		void pop(void); // Removes a process from the list. 
		void display(void); // Displays the list.

		// Functions to calculate time measures:
 		int getTurnAroundTime(PCB& p); // Turn-Around Time.
		int getWaitingTime(PCB& p); // Waiting Time.
		float getAvgTurnAroundTime(void); // Average Turn-Around Time.
		float getAvgWaitingTime(void); // Average Waiting Time.
		void displaySchedulingTimes(std::string); // Displays all the measures above.


		void introduction(void); // Prints a small introduction.
		int numberProcesses(std::string file_name); // Returns number of processes in a file.
		PCB* readFile(std::string file_name); // Reads the file and returns the processes.
		void scheduler(std::string file_name); // Executes all the functions above to display a result to the file's data case.

};

