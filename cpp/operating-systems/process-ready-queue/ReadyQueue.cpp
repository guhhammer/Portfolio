#include "ReadyQueue.h"

// Contructor.
ReadyQueue::ReadyQueue(void) : LinkedList()
{
}

// Deconstructor.
ReadyQueue::~ReadyQueue(void)
{
}

// Method adds a Node and sets its state to READY.
// It adds a node according to its priority value.
void ReadyQueue::add(Node& n)
{

	n.setProcess(PCB(n.getProcess().getID(), "READY", n.getProcess().getPriority())); // Changing state of the Process to READY.

	if (this->isEmpty()) { this->setHead(n); } // If list is empty, adds as head.
	else {

		if (this->head->getProcess().getPriority() < n.getProcess().getPriority()) {

			n.setNext(*this->head);
			this->head = &n;

			return; // If node's priority is greater than the head's.
		}

		Node* aux = this->head;

		while (aux->getNext() != nullptr) {

			if (aux->getNext()->getProcess().getPriority() < n.getProcess().getPriority()) {

				Node* temp = aux->getNext();
				aux->setNext(n);
				n.setNext(*temp);

				return; // Adds node to a spot in the middle of the list.

			}

			aux = aux->getNext();

		}

		aux->setNext(n); // Adds node to the end of the list.

	}
}

// Method that returns the previous Node of a Node.
Node ReadyQueue::findPrev(Node& n)
{
	Node* aux = this->head;

	while (aux->getNext() != nullptr) {

		if (aux->getNext()->getProcess().getID() == n.getProcess().getID()) {
			break;
		}
		aux = aux->getNext();
	}

	return *aux;

}

// Method that removes the highest priority Node of the list.
// Removes the head of the list, which is always the highest-priority node. 
Node ReadyQueue::removeHighest(void)
{
	// If list is empty;
	if (this->isEmpty()) { return *this->head; }

	Node ret = *this->head;
	this->head = this->head->getNext();
	ret.setProcess(PCB(ret.getProcess().getID(), "RUNNING", ret.getProcess().getPriority())); // Changing Process's state to RUNNING. 
	ret.setNextasNull();
	return ret;

}
