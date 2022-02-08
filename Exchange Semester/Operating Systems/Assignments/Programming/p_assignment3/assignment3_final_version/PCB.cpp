#include <string>
#include "PCB.h"

// Constructor.
PCB::PCB(void)
{
	this->ID = "ND";
	this->priority = -1;
	this->burst_time = 0;
	this->next = nullptr;
}

// Constructor.
PCB::PCB(std::string id, int burst_time)
{
	this->ID = id;
	this->burst_time = burst_time;
	this->priority = -1;
	this->next = nullptr;
}

// Constructor.
PCB::PCB(std::string id, int burst_time, int priority)
{
	this->ID = id;
	this->burst_time = burst_time;
	this->priority = priority;
	this->next = nullptr;
}

// Deconstructor.
PCB::~PCB(void)
{
	this->next = nullptr;
}

// Sets process's priority.
void PCB::setPriority(int p)
{
	this->priority = p;
}

// Sets process's burst time.
void PCB::setBurst_time(int b)
{
	this->burst_time = b;
}

// Sets process's next process.
void PCB::setNext(PCB& p)
{
	this->next = &p;
}

// Sets process's next process as null.
void PCB::nextAsNullptr(void)
{
	this->next = nullptr;
}

// Returns process's id.
std::string PCB::getId(void)
{
	return this->ID;
}

// Returns process's priority.
int PCB::getPriority(void)
{
	return this->priority;
}

// Returns process's burst time.
int PCB::getBurst_time(void)
{
	return this->burst_time;
}

// Returns process's next process.
PCB& PCB::getNext(void)
{
	return *this->next;
}

// Displays process's information.
void PCB::display(void)
{
	std::cout << "\nID: " << this->ID <<
		",\tburst_time: " << this->burst_time
		<< ",\tpriority: " << this->priority
		<< " \n";
}
