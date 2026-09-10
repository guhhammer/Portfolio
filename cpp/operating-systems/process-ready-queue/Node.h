#pragma once
#include <iostream>
#include <string>
#include "PCB.h"
using namespace std;

/*
	This class is used as a package for a process that can be linked in a list.
*/
class Node
{
private:

	// Attribute that holds a process.
	PCB process;

	// Pointer to the a next process.
	Node* next = nullptr;

public:

	// Constructors.
	Node(PCB process);
	Node(void);

	// Deconstructor.
	~Node(void);

	// Set Functions to the class's attributes.
	void setProcess(PCB p);
	void setNext(Node& n);
	void setNextasNull();

	// Get Functions to the class's attributes.
	PCB getProcess(void);
	Node* getNext(void);

};

