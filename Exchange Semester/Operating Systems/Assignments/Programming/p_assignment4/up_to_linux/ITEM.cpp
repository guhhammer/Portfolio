#include "ITEM.h"

ITEM::ITEM(void)
{
}

ITEM::ITEM(int id)
{
	this->id = id;
	this->next = next;
}

ITEM::~ITEM(void)
{
	this->next = nullptr;
}

void ITEM::setID(int id)
{
	this->id = id;
}

int ITEM::getID(void)
{
	return this->id;
}

void ITEM::setNext(ITEM& item)
{
	this->next = &item;
}

ITEM* ITEM::getNext(void)
{
	return this->next;
}
