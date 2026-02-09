#pragma once
#include <iostream>
#include <string>
#include "LinkedList.h"
using namespace std;

/*
	This class extends the Linkedlist class, it is used to store the READY state processes.
*/
class ReadyQueue : public LinkedList
{
public:

	// Contructor.
	ReadyQueue(void);

	// Deconstructor.
	~ReadyQueue(void);

	// Method adds a Node and sets its state to READY.
	void add(Node& node);

	// Method that returns the previous Node of a Node.
	Node findPrev(Node& n);

	// Method that removes the highest priority Node of the list.
	Node removeHighest(void);

};

