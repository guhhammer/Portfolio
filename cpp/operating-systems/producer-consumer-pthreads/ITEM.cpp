#include "ITEM.h"

// Constructor.
ITEM::ITEM(void)
{
}

// Constructor.
ITEM::ITEM(int id)
{
	this->id = id;
	this->next = next;
}

// Deconstructor.
ITEM::~ITEM(void)
{
	this->next = nullptr;
}

// Sets id.
void ITEM::setID(int id)
{
	this->id = id;
}

// Gets id.
int ITEM::getID(void)
{
	return this->id;
}

// Sets next item pointer.
void ITEM::setNext(ITEM& item)
{
	this->next = &item;
}

// Gets next item pointer.
ITEM* ITEM::getNext(void)
{
	return this->next;
}

