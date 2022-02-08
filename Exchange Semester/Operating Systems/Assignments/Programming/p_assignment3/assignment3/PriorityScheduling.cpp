#include "PriorityScheduling.h"

// Overwrites LinkedList method.
void PriorityScheduling::push(PCB& p)
{
	if (this->isEmpty()) {
		this->head = &p;
	}
	else {

		PCB* aux = this->head;

		if (aux->getPriority() < p.getPriority()) {
			p.setNext(*aux);
			this->head = &p;
		}
		else {

			while (&aux->getNext() != nullptr &&
				aux->getNext().getPriority() > p.getPriority()) {
				aux = &aux->getNext();
			}

			p.setNext(aux->getNext());
			aux->setNext(p);

		}

	}
	// Pushes process into the list according to its priority.
	// It inserts processes in an decreasing priority order.

}

// Overwrites LinkedList method.
void PriorityScheduling::display(void)
{
	if (this->isEmpty()) {
		std::cout << "\n\nEmpty!\n\n";
	}
	else {

		PCB* aux = this->head;
		std::cout << "Queue display:\n";
		while (aux != nullptr) {
			std::cout << "\nID: " << aux->getId()
				<< "\tBurst_time: " << std::to_string(aux->getBurst_time())
				<< "\tPriority: " << std::to_string(aux->getPriority())
				<< "\n";
			aux = &aux->getNext();
		}
		std::cout << "\n";

	}
	// Displays the priority too.

}

// Overwrites LinkedList method.
void PriorityScheduling::displaySchedulingTimes(void)
{
	LinkedList::displaySchedulingTimes("Priority"); // Displays with the name: "Priority".
}