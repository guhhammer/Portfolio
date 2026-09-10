#include "RoundRobin.h"
#include <math.h>

// Returns the number of times a process was sliced.
int RoundRobin::processExecutionNumberOfSlices(PCB& p)
{
	
	PCB* aux = this->listToLoad.getHead();
	int counter = 0;

	while (aux != nullptr) {
		
		if (aux->getId() == p.getId()) {  counter++; }

		aux = &aux->getNext();

	}

	return counter;

}

// Returns how many processes there will be in the round-robin list.
int RoundRobin::makeEstimation(PCB& p)
{
	return (int)(ceil(((p.getBurst_time() * 1.0f)/(this->timeQuantum * 1.0f))));
}

// Makes a round-robin list.
bool RoundRobin::makeRoundRobinOnList(void)
{

	PCB* aux = this->head;
	
	int estimation = 0;
	while (aux != nullptr) { 
		estimation += this->makeEstimation(*aux);
		aux = &aux->getNext();
	}

	PCB *list = new PCB[estimation]; // Array of processes to added to the round-robin list.
	int i = 0;

	bool allAreProcessed = false;
	while (!allAreProcessed) {

		aux = this->head;
		
		while (aux != nullptr){
			
			if (aux->getBurst_time() > 0) {

				PCB new_ = PCB(aux->getId(), aux->getBurst_time(), aux->getPriority());
				list[i] = new_;

				int new_time = aux->getBurst_time() - this->timeQuantum;
				aux->setBurst_time((new_time > 0) ? new_time : 0);
			
				i++;
			}

			aux = &aux->getNext();
			
		}

		bool allZero = true;
		aux = this->head;
		while (aux != nullptr) {

			if (aux->getBurst_time() > 0) {  allZero = false;  break; }
			
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
RoundRobin::RoundRobin(void)
{
        //LinkedList::LinkedList();
	this->timeQuantum = 1;
	listToLoad = LinkedList();
}

// Constructor.
RoundRobin::RoundRobin(int rr)
{
        //LinkedList::LinkedList();
	this->timeQuantum = rr;
	this->listToLoad = LinkedList();
}

// Deconstructor.
RoundRobin::~RoundRobin(void)
{
        //LinkedList::~LinkedList();
	this->listToLoad.~LinkedList();
}

// Overwrites LinkedList method.
int RoundRobin::getTurnAroundTime(PCB& p)
{
	
	PCB* aux = this->listToLoad.getHead();

	int appearanceTimes = this->processExecutionNumberOfSlices(p);

	int turnAroundTime = 0;

	while (aux != nullptr && appearanceTimes > 0) {

		if (aux->getId() == p.getId()) {
			
			appearanceTimes--;
		}

		turnAroundTime += (aux->getBurst_time() < this->timeQuantum) ? aux->getBurst_time() : this->timeQuantum;
		aux = &aux->getNext();

	}

	return turnAroundTime;

}

// Overwrites LinkedList method.
int RoundRobin::getWaitingTime(PCB& p)
{

	PCB* aux = this->listToLoad.getHead();

	int appearanceTimes = this->processExecutionNumberOfSlices(p);

	int waitingTime = 0;

	while (aux != nullptr && appearanceTimes > 0) {

		if (aux->getId() == p.getId()) {

			appearanceTimes--;
		}
		else {
			waitingTime += (aux->getBurst_time() < this->timeQuantum) ? aux->getBurst_time() : this->timeQuantum;
		}
		aux = &aux->getNext();

	}

	return waitingTime;

}

// Overwrites LinkedList method.
float RoundRobin::getAvgTurnAroundTime(void)
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
float RoundRobin::getAvgWaitingTime(void)
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
void RoundRobin::displaySchedulingTimes(void)
{

	makeRoundRobinOnList();

	std::string nameAlgorithm = "Round-Robin";

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
