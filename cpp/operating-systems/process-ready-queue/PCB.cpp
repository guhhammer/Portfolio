#include "PCB.h"

// Constructors.
PCB::PCB(void)
{
}
//
PCB::PCB(string id, string state, int p)
{
	this->ID = id;
	this->state = state;
	this->priority = p;
}



// Deconstructor.
PCB::~PCB(void)
{
}



// Set Functions for the class's attributes.
void PCB::setID(string ID)
{
	this->ID = ID;
}
//
void PCB::setState(string state)
{
	this->state = state;
}
//
void PCB::setPriority(int p)
{
	this->priority = p;
}



// Get Functions for the class's attributes.
string PCB::getID(void)
{
	return this->ID;
}
//
string PCB::getState(void)
{
	return this->state;
}
//
int PCB::getPriority(void)
{
	return this->priority;
}

