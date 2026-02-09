#pragma once
#include <stdlib.h> 
#include "ITEM.h"

class FIFO
{
private:
	ITEM* head, * tail;
	int maximum_size;

public:
	FIFO(void);
	FIFO(int size);
	~FIFO(void);
	void setMaxSize(int ms);
	bool isEmpty(void);
	bool isFull(void);
	int size(void);
	void add(ITEM& item);
	ITEM* remove(void);
	void display(void);
	int generateAnItemID(void);
	std::string display_array_format(void);
};

