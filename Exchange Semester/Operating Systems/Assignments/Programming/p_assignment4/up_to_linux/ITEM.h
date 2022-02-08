#pragma once
#include <stdio.h>
#include <iostream>
#include <string>

class ITEM
{

private:
	int id = -1;
	ITEM* next = nullptr;

public:
	ITEM(void);
	ITEM(int id);
	~ITEM(void);
	void setID(int id);
	int getID(void);
	void setNext(ITEM& item);
	ITEM* getNext(void);
};

