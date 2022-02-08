#pragma once
#include <iostream>
#include <string>
#include "Node.h"
using namespace std;


/*
	This Class is a LinkedList, it is used for stocking processes.
*/
class LinkedList
{
protected:

	// Pointer to the first process of the list.
	Node* head;

public:

	// Constructor.
	LinkedList(void);

	// Deconstructor.
	~LinkedList(void);

	// Set Functions for the class's attributes.
	void setHead(Node& h);
	void setLast(Node& l); // Adds a node to the end of the list.

	// Get Functions for the class's attributes.
	Node* getHead(void);
	Node* getLast(void); // Returns the last node. 

	// Returns true if the list is empty. 
	bool isEmpty(void);

	// Returns the size of the list.
	int size(void);

	// Add Nodes to the list.
	void add(Node& node);

	// Returns a Node on the list by its identifier.
	Node* findNode(string id);

	// Changes the state of a Node.
	void changeState(Node& n, string state);

	// Display the Nodes of the list.
	void display(void);

};
