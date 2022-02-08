#pragma once
#include <iostream>
#include <string>
using namespace std;

/*
	This class represents a O.S. process.
*/
class PCB
{
private:

	// Process's Identifier.
	string ID;

	// Process's state.
	string state;

	// Process's priority.
	int priority = -1;

public:

	// Constructors.
	PCB(void);
	PCB(string id, string state, int p);

	// Deconstructor.
	~PCB(void);

	// Set Functions for the class's attributes.
	void setID(string ID);
	void setState(string state);
	void setPriority(int p);

	// Get Functions for the class's attributes.
	string getID(void);
	string getState(void);
	int getPriority(void);

};

