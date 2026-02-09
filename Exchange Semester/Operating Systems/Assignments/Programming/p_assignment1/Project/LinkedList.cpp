#include "LinkedList.h"
#include <string>

// Constructor.
LinkedList::LinkedList(void)
{
	this->head = nullptr;
}

// Deconstructor.
LinkedList::~LinkedList(void)
{
}



// Set Functions for the class's attributes.
void LinkedList::setHead(Node& h)
{
	this->head = &h;
}
// 
void LinkedList::setLast(Node& l)
{
	if (this->isEmpty()) { this->setHead(l); }
	else {
		Node* aux = this->head;

		while (aux->getNext() != nullptr) { aux = aux->getNext(); }

		aux->setNext(l);

	}

}



// Get Functions for the class's attributes.
Node* LinkedList::getHead(void)
{
	return this->head;
}
//
Node* LinkedList::getLast(void)
{
	Node* aux = this->head;

	while (aux->getNext() != nullptr) { aux = aux->getNext(); }

	return aux;
}



// Returns true if the list is empty. 
bool LinkedList::isEmpty(void)
{
	return (this->head == nullptr);
}

// Returns the size of the list.
int LinkedList::size(void)
{
	if (!this->isEmpty()) {
		int size = 0;
		Node* aux = this->head;

		while (aux != nullptr) {

			size++;
			aux = aux->getNext();

		}

		return size;

	}
	return 0;
}

// Add Nodes to the list.
void LinkedList::add(Node& node)
{
	this->setLast(node);
}

// Returns a Node on the list by its identifier.
Node* LinkedList::findNode(string id)
{
	if (this == nullptr) { return nullptr; }

	Node* aux = this->getHead();

	while (aux != nullptr) {
		if (aux->getProcess().getID() == id) { break; }
		aux = aux->getNext();
	}

	return aux;

}

// Changes the state of a Node.
void LinkedList::changeState(Node& n, string state)
{
	n.getProcess().setState(state);
}

// Display the Nodes of the list.
void LinkedList::display(void)
{
	if (!this->isEmpty()) {

		std::cout << "\thead ->\n";
		Node* aux = this->getHead();


		while (aux != nullptr) {

			std::cout << "\tProcess(ID = " << aux->getProcess().getID() << " | Priority = ";
			std::cout << std::to_string(aux->getProcess().getPriority());
			std::cout << " | State = " << aux->getProcess().getState() << ") -> \n";

			aux = aux->getNext();

		}

		std::cout << "\tnull pointer (end of the list)\n";

		return;

	}
	std::cout << "\tList is Empty!\n";

}

