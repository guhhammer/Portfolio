#include "FIFO.h"

FIFO::FIFO(void)
{
	this->head = nullptr;
	this->tail = nullptr;
	this->maximum_size = 10;
}

FIFO::FIFO(int size)
{
	this->head = nullptr;
	this->tail = nullptr;
	this->maximum_size = size;
}

FIFO::~FIFO(void)
{
	this->head = nullptr;
	this->tail = nullptr;
}

void FIFO::setMaxSize(int ms)
{
	this->maximum_size = ms;
}

bool FIFO::isEmpty(void)
{
	return (this->head == nullptr);
}

bool FIFO::isFull(void)
{
	return (this->size() == this->maximum_size);
}

int FIFO::size(void)
{

	if (!this->isEmpty()) {

		ITEM* aux = this->head;
		int size = 0;

		while (aux != nullptr) {
			size++;
			aux = aux->getNext();
		}

		return size;

	}

	return 0;

}

void FIFO::add(ITEM& item)
{
	if (this->maximum_size > this->size()) {
		if (this->isEmpty()) {
			this->head = &item;
			this->tail = &item;
		}
		else {
			this->tail->setNext(item);
			this->tail = this->tail->getNext();
		}
	}
}

ITEM* FIFO::remove(void)
{
	if (!this->isEmpty()) {
		ITEM* aux = this->head;
		this->head = this->head->getNext();
		return aux;
	}
	return new ITEM();
}

void FIFO::display(void)
{
	if (!this->isEmpty()) {

		ITEM* aux = this->head;

		std::cout << "\nFIFO display:\n ";
		while (aux != nullptr) {

			std::cout << "\tITEM ID: " << std::to_string(aux->getID()) << "\n";
			aux = aux->getNext();

		}
		std::cout << "\n";

	}
	else {
		std::cout << "\n\nFIFO queue is empty!\n\n";
	}
}

int FIFO::generateAnItemID(void)
{
	int* ids = new int[this->maximum_size];
	for (int i = 0; i < this->maximum_size; i++) { ids[i] = 0; }

	if (!this->isEmpty()) {
		ITEM* aux = this->head;
		while (aux != nullptr) { ids[aux->getID()] = 1; aux = aux->getNext(); }

		int id = -1, k = 0;
		while (id < 0 && k < 2) {
			int index = rand() % this->maximum_size;
			if (ids[index] == 0) { id = index; }
			k++;
		}

		if (k < 2) {
			return id;
		}

	}

	for (int i = 0; i < this->maximum_size; i++) {
		if (ids[i] == 0) { return i; }
	}

	return -1;

}


std::string FIFO::display_array_format(void)
{
	std::string ret = "[";
	if (!this->isEmpty()) {

		ITEM* aux = this->head;

		while (aux != nullptr) {

			ret += std::to_string(aux->getID());
			if (aux->getNext() != nullptr) { ret += ", "; }
			aux = aux->getNext();

		}
	}
	else { ret += " "; }

	return ret + "]";

}
