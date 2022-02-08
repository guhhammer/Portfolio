#include "SJF.h"

// Overwrites LinkedList method.
void SJF::push(PCB& p)
{
	if (this->isEmpty()) {
		this->head = &p;
	}
	else {

		PCB* aux = this->head;
		
		if (aux->getBurst_time() > p.getBurst_time()) {
			p.setNext(*aux);
			this->head = &p;
		}
		else {

			while (&aux->getNext() != nullptr &&
				aux->getNext().getBurst_time() < p.getBurst_time()) {
				aux = &aux->getNext();
			}

			p.setNext(aux->getNext());
			aux->setNext(p);
		
		}

	} 
	// Pushes processes into the list according to its burst time.
	// It inserts processes in an increasing burst-time order.

}

// Overwrites LinkedList method.
void SJF::displaySchedulingTimes(void)
{
	LinkedList::displaySchedulingTimes("SJF"); // Displays the name as "SJF".
}
