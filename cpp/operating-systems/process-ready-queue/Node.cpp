#include "Node.h"

// Constructors.
Node::Node(PCB process)
{
	this->process = process;
	this->next = nullptr;
}
// 
Node::Node()
{
	this->process = process;
	this->next = nullptr;
}

// Deconstructor.
Node::~Node()
{
}



// Set Functions to the class's attributes.
void Node::setProcess(PCB p)
{
	this->process = p;
}
//
void Node::setNext(Node& n)
{
	this->next = &n;
}

void Node::setNextasNull()
{
	this->next = nullptr;
}



// Get Functions to the class's attributes.
PCB Node::getProcess(void)
{
	return this->process;
}
//
Node* Node::getNext(void)
{
	return (this == nullptr) ? nullptr : this->next;
}
