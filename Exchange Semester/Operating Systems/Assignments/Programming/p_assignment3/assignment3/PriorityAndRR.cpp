#include "PriorityAndRR.h"
#include <math.h>

// Returns the number of times a process was sliced.
int PriorityAndRR::processExecutionNumberOfSlices(PCB& p)
{
	
	PCB* aux = this->listToLoad.getHead();
	int counter = 0;

	while (aux != nullptr) {

		if (aux->getId() == p.getId()) { counter++; }

		aux = &aux->getNext();

	}

	return counter;

}

// Returns how many processes there will be in the round-robin list.
int PriorityAndRR::makeEstimation(PCB& p)
{

	PCB* aux = this->head;
	
	while (aux != nullptr) {
		
		if (aux->getPriority() == p.getPriority() && aux->getId() != p.getId()) {
		
			return (int)(ceil(((p.getBurst_time() * 1.0f) / (this->timeQuantum * 1.0f))));

		}

		aux = &aux->getNext();

	}

	return 1;

}

// Returns if the process is the only process in the list with the priority.
bool PriorityAndRR::singlePriorityProcess(PCB& p)
{
	
	PCB* aux = this->head;

	while(aux != nullptr){
	
		if (aux->getId() != p.getId() && aux->getPriority() == p.getPriority()) {
			return false;
		}
		
		aux = &aux->getNext();

	}

	return true;

}

// Makes a round-robin list.
bool PriorityAndRR::makeRoundRobinOnList(void)
{

	PCB* aux = this->head;

	int estimation = 0;
	while (aux != nullptr) {
		estimation += this->makeEstimation(*aux);
		aux = &aux->getNext();
	}

	PCB* list = new PCB[estimation]; // An array of processes to be added into the round-robin list.
	int i = 0;
	int currentPriority = -1;

	bool allAreProcessed = false;
	while (!allAreProcessed) {

		aux = this->head;


		while (aux != nullptr ) {

			if (this->singlePriorityProcess(*aux) && currentPriority == -1 && aux->getBurst_time() > 0) {
				PCB new_ = PCB(aux->getId(), aux->getBurst_time(), aux->getPriority());
				list[i] = new_;
				
				aux->setBurst_time(0);
				i++;

			}
			else {

				if (aux->getPriority() == currentPriority || currentPriority == -1) {
					if (aux->getBurst_time() > 0) {

						currentPriority = aux->getPriority();
						PCB new_ = PCB(aux->getId(), aux->getBurst_time(), aux->getPriority());
						list[i] = new_;

						int new_time = aux->getBurst_time() - this->timeQuantum;
						aux->setBurst_time((new_time > 0) ? new_time : 0);

						i++;

					}
				}
				else if (aux->getPriority() != currentPriority) {
					currentPriority = -1;
					
					break;
				}
			}
			
			aux = &aux->getNext();

		}

		bool allZero = true;
		aux = this->head;
		while (aux != nullptr) {

			if (aux->getBurst_time() > 0) { allZero = false; break; }

			aux = &aux->getNext();
		}

		if (allZero) { allAreProcessed = true; }

	}

	for (int j = 0; j < estimation; j++) {
		this->listToLoad.push(list[j]);
	}

	return true;

}

// Constructor.
PriorityAndRR::PriorityAndRR(void)
{
	LinkedList::LinkedList();
	this->timeQuantum = 1;
	this->listToLoad = LinkedList();
}

// Constructor.
PriorityAndRR::PriorityAndRR(int time_quantum)
{
	LinkedList::LinkedList();
	this->timeQuantum = time_quantum;
	this->listToLoad = LinkedList();
}

// Deconstructor.
PriorityAndRR::~PriorityAndRR(void)
{
	LinkedList::~LinkedList();
}

// Overwrites LinkedList method.
void PriorityAndRR::push(PCB& p)
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
	// Pushes processes into the list according to its priority.
	// It inserts processes into the list in a decreasing priority order.

}

// Overwrites LinkedList method.
int PriorityAndRR::getTurnAroundTime(PCB& p)
{
	
	PCB* aux = this->listToLoad.getHead();

	int appearanceTimes = this->processExecutionNumberOfSlices(p);
	int turnAroundTime = 0;

	while (aux != nullptr && appearanceTimes > 0) {

		if (aux->getId() == p.getId()) {
			appearanceTimes--;
		}

		if (this->singlePriorityProcess(*aux)) {
			turnAroundTime += aux->getBurst_time();
		}
		else {
			turnAroundTime += (aux->getBurst_time() < this->timeQuantum) ? aux->getBurst_time() : this->timeQuantum;
		}

		aux = &aux->getNext();

	}

	return turnAroundTime;

}

// Overwrites LinkedList method.
int PriorityAndRR::getWaitingTime(PCB& p)
{
	
	PCB* aux = this->listToLoad.getHead();

	int appearanceTimes = this->processExecutionNumberOfSlices(p);

	int waitingTime = 0;

	while (aux != nullptr && appearanceTimes > 0) {

		if (aux->getId() == p.getId()) {

			appearanceTimes--;
		}
		else {

			if (this->singlePriorityProcess(*aux)) {
				waitingTime += aux->getBurst_time();
			}
			else {
				waitingTime += (aux->getBurst_time() < this->timeQuantum) ? aux->getBurst_time() : this->timeQuantum;
			}

		}
		aux = &aux->getNext();

	}

	return waitingTime;

}

// Overwrites LinkedList method.
float PriorityAndRR::getAvgTurnAroundTime(void)
{
	
	if (!this->isEmpty()) {

		PCB* aux = this->head;
		float count = 0.0f;

		while (aux != nullptr) {
			count += (float)(this->getTurnAroundTime(*aux) * 1.0f);
			aux = &aux->getNext();
		}

		return (float)((count) / (this->size() * 1.0f));

	}

	return 0.0f;

}

// Overwrites LinkedList method.
float PriorityAndRR::getAvgWaitingTime(void)
{

	if (!this->isEmpty()) {

		PCB* aux = this->head;
		float count = 0.0f;

		while (aux != nullptr) {
			count += (float)(this->getWaitingTime(*aux) * 1.0f);
			aux = &aux->getNext();
		}

		return (float)((count) / (this->size() * 1.0f));

	}

	return 0.0f;

}

// Overwrites LinkedList method.
void PriorityAndRR::displaySchedulingTimes(void)
{

	makeRoundRobinOnList();

	std::string nameAlgorithm = "Priority-with-Round-Robin";

	if (!this->isEmpty()) {

		PCB* aux = this->head;

		std::cout << "\n" << nameAlgorithm << " Scheduling Algorithm:\n";

		while (aux != nullptr) {

			std::cout << "\n\tProcess ID: " << aux->getId()
				<< ",\tTurn-Around Time: " << std::to_string(this->getTurnAroundTime(*aux))
				<< ",\tWaiting Time: " << std::to_string(this->getWaitingTime(*aux))
				<< "\n";

			aux = &aux->getNext();

		}

		std::cout << "\n\n\tAverage Turn-Around Time: " << std::to_string(this->getAvgTurnAroundTime())
			<< "\n\tAverage Waiting Time: " << std::to_string(this->getAvgWaitingTime())
			<< "\n\n";

		return;

	}

	std::cout << "\n\n" << nameAlgorithm << " is Empty!\n\n";
	
}
