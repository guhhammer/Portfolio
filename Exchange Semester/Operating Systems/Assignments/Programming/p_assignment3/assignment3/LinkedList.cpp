#include "LinkedList.h"

// Constructor.
LinkedList::LinkedList(void)
{
	this->head = nullptr;
}

// Deconstructor.
LinkedList::~LinkedList(void)
{
	this->head = nullptr;
}

// Returns head of the list.
PCB* LinkedList::getHead(void)
{
	return this->head;
}

// Returns if list is empty.
bool LinkedList::isEmpty(void)
{
	return (this->head == nullptr);
}

// Returns size of the list.
int LinkedList::size(void)
{
	
	if (!this->isEmpty()){
		PCB* aux = this->head;
		int count = 1;
		while (&aux->getNext() != nullptr) {
			count++;
			aux = &aux->getNext();
		}
		return count;
	}
	
	return 0;
	
}

// Adds process to the list.
void LinkedList::push(PCB& p)
{
	
	if (this->isEmpty()) {
		this->head = &p;
	}
	else {

		PCB* aux = this->head;

		while (&aux->getNext() != nullptr) {
			aux = &aux->getNext();
		}

		aux->setNext(p);

	} // Adds the process to the end of the list.

}

// Removes process from the list(not used or tested in this project).
void LinkedList::pop(void)
{
	
	if (!this->isEmpty()) {

		PCB* aux = this->head;
		PCB* last = this->head;

		while (&aux->getNext() != nullptr) {
			last = aux;
			aux = &aux->getNext();
		}

		last->nextAsNullptr();

	}

}

// Displays the list processes' information.
void LinkedList::display(void)
{
	
	if (this->isEmpty()) {
		std::cout << "\n\nLinkedList Empty!\n\n";
	}
	else {
		
		PCB* aux = this->head;
		std::cout << "LinkedList display:\n";
		while (aux != nullptr) {
			aux->display();
			aux = &aux->getNext();
		}
		std::cout << "\n";

	}

}

// Returns Turn-Around Time for process p.
int LinkedList::getTurnAroundTime(PCB& p)
{

	PCB* aux = this->head;
	int time = 0;

	while (aux != nullptr) {

		if (aux->getId() == p.getId()) {

			break;

		}
		else {

			time += aux->getBurst_time();
			aux = &aux->getNext();

		}

	}

	return (time + p.getBurst_time());

}

// Returns Waiting Time for process p.
int LinkedList::getWaitingTime(PCB& p)
{
	return (this->getTurnAroundTime(p) - p.getBurst_time());
}

// Returns Average Turn-Around Time for the list.
float LinkedList::getAvgTurnAroundTime(void)
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

// Returns Average Waiting Time for the list.
float LinkedList::getAvgWaitingTime(void)
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

// Displays the Scheduling Algorithm's information(the measures above).
void LinkedList::displaySchedulingTimes(std::string nameAlgorithm)
{
	
	if (!this->isEmpty()) {

		PCB* aux = this->head;

		std::cout << "\n" << nameAlgorithm <<" Scheduling Algorithm:\n";

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

	std::cout << "\n\n" << nameAlgorithm <<" is Empty!\n\n";

}

// Displays a brief introduction of the project.
void LinkedList::introduction(void)
{

	std::cout << "\n-------------------------------------------------------------------"
		<< "\n-------------------------------------------------------------------"
		<< "\nIntroduction:"
		<< "\n\nName: Gustavo Hammerschmidt."
		<< "\n\nProgramming Assignment 3"
		<< "\n\nDescription:\n\tThis is a program to test different Scheduling Algorithms."
		<< "\n\tRead the report for more information, or check"
		<< "\n\tthe class documentation for each class."
		<< "\n\n-------------------------------------------------------------------"
		<< "\n-------------------------------------------------------------------"
		<< "\n\n";

}

// Returns the number of processes(lines) in a file.
int LinkedList::numberProcesses(std::string file_name)
{

	std::ifstream count(file_name);
	int size = 0;
	if (count.is_open()) {
		std::string line;
		while (getline(count, line)) {
			size++;
		}
		count.close();
	}

	return size;

}

// Reads a file, turns its lines into processes(PCBs), and returs them in an array.
PCB* LinkedList::readFile(std::string file_name)
{

	PCB* processes = new PCB[this->numberProcesses(file_name)];
	int k = 0;

	std::ifstream file(file_name);
	if (file.is_open()) {
		std::string line;
		while (getline(file, line)) {

			std::string pid = "";
			int burst_time = -1, priority = -1;

			std::string aux = "";
			int comma_number = 0;
			for (int i = 0; i < line.length(); i++) {
				if (line.at(i) == ',') {

					if (comma_number == 0) {

						pid = aux;

					}
					else if (comma_number == 1) {

						priority = atoi(aux.c_str());

					}

					comma_number++;
					aux = "";

				}
				else {

					aux += line.at(i);
				}

			}

			burst_time = atoi(aux.c_str());

			processes[k++] = PCB(pid, burst_time, priority);
			// I'm my project I defined the PCB structure as: (PID, burst_time, priority),
			// But, in the file, the structure is (PID, priority, burst_time). 
			// I've paid attention to it, and adjusted the input to my model.
			// So there is no need to do the input differently.

		}

		file.close();

	}

	return processes;

}

// Schedules input to the Scheduling algorithm.
// This is just a model followed by the derived classes!
void LinkedList::scheduler(std::string file_name) {

	this->introduction();

	//Scheduling_Algorithm_type scheduler = object();

	PCB* processes = this->readFile(file_name);

	for (int k = 0; k < this->numberProcesses(file_name); k++) {
		//scheduler.push(processes[k]);
	}

	//scheduler.displaySchedulingTimes();

}