#include <stdio.h>
#include <stdlib.h>
#include <random>
#include <ctime>

#include "tester.h"


/*

	Introduction, firstTest and secondTest:
		- Methods to separate the tests and the introduction.

*/

void tester::introduction(void)
{
	std::cout << "\n\n-------------";
	std::cout << "\n-------------";
	std::cout << "\nProgramming Assigment 1";
	std::cout << "\n\nName: Gustavo Hammerschmidt.";
	std::cout << "\n\n\tDescription:";
	std::cout << "\n\n\t\tThis program is formed of a linkedlist data structure,"
		<< " its main goal is \n\t\tto test the efficiency of it on a simulated environment,"
		<< " where process are \n\t\tattached to these lists to be further manipulated.\n";
	std::cout << "\n-------------";
	std::cout << "\n-------------";
	std::cout << "\n\n";
}



void tester::firstTest(void)
{
	std::cout << "\n\n-------------";
	std::cout << "\n-------------";
	std::cout << "\n\nThis is the First Test: \n";

	/*

		In the first test, you make a PCB_table of 30 entries with PCB ID from 1 to 30. Assume the
		priority values range from 1 to 50, where the higher value means the higher priority, i.e.
		priority = 1 means the lowest priority and 50 means the highest. Assume in this test the
		process of PID i has its initial priority = i, i.e. the lower PID means lower priority. When you
		add a process to the ReadyQueue you should change its state in the PCB_table to READY;
		you should change its state to RUNNING if you remove a process from the ReadyQueue.

	*/

	LinkedList ll = LinkedList(); // Creating LinkedList(ll) Object.

	ReadyQueue rq = ReadyQueue(); // Creating ReadyQueue(rq) Object.

	Node nodes[30]; // Quantity of nodes to be added to the linkedlist.


	// Creating 30 Processes that have the priority equal to the id.  
	for (int i = 0; i < 30; i++) {

		nodes[i] = Node(PCB(std::to_string(i + 1), "NEW", i + 1));
		ll.add(nodes[i]);

	} // Processes were added to the linkedlist.

	std::cout << "\nDisplay of LinkedList with 30 processes:\n\n";

	ll.display();

	std::cout << "\n---------------\n";

	// -------------------------------------------------------------------------


	// 1) add processes 15, 6, 23, 29 and 8 to q1. Display the content of q1

	// Adding the processes.
	Node nodesToAdd_1p[5], nodesToAdd_1a[5];
	nodesToAdd_1p[0] = *ll.findNode("15"),
		nodesToAdd_1p[1] = *ll.findNode("6"),
		nodesToAdd_1p[2] = *ll.findNode("23"),
		nodesToAdd_1p[3] = *ll.findNode("29"),
		nodesToAdd_1p[4] = *ll.findNode("8");

	for (int i = 0; i < (sizeof(nodesToAdd_1p) / sizeof(nodesToAdd_1p[0])); i++) {


		Node aux = nodesToAdd_1p[i];

		ll.findNode(aux.getProcess().getID())->setProcess(PCB(aux.getProcess().getID(), "READY", aux.getProcess().getPriority()));

		nodesToAdd_1a[i] = Node(PCB(aux.getProcess().getID(), aux.getProcess().getState(), aux.getProcess().getPriority()));
		rq.add(nodesToAdd_1a[i]);

	}

	std::cout << "\n\nTest 1.1)\n\n\tDisplay Contents:";

	std::cout << "\n\n\tReadyQueue content:\n\n";

	rq.display();

	std::cout << "\n\n\tLinkedList content:\n\n";

	ll.display();

	std::cout << "\n---------------\n";



	// -------------------------------------------------------------------------


	// 2) remove the process with the highest priority from q1 and display q1.

	Node highest_n1 = rq.removeHighest();
	ll.findNode(highest_n1.getProcess().getID())->setProcess(PCB(highest_n1.getProcess().getID(),
		"RUNNING", highest_n1.getProcess().getPriority()));

	std::cout << "\n\nTest 1.2)\n\n\tDisplay Contents:\n\n";

	std::cout << "\n\n\tReadyQueue content:\n\n";

	rq.display();

	std::cout << "\n\n\tLinkedList content:\n\n";

	ll.display();

	std::cout << "\n---------------\n";

	// -------------------------------------------------------------------------


	// 3) remove the process with the highest priority from q1 and display q1.

	Node highest_n2 = rq.removeHighest();

	ll.findNode(highest_n2.getProcess().getID())->setProcess(PCB(highest_n2.getProcess().getID(),
		"RUNNING", highest_n2.getProcess().getPriority()));

	std::cout << "\n\nTest 1.3)\n\n\tDisplay Contents:\n\n";

	std::cout << "\n\n\tReadyQueue content:\n\n";

	rq.display();

	std::cout << "\n\n\tLinkedList content:\n\n";

	ll.display();

	std::cout << "\n---------------\n";



	// -------------------------------------------------------------------------


	// 4) add processes 3, 17, 22, 12, 19 to q1 and display q1.

	// Adding the processes.
	Node nodesToAdd_2p[5], nodesToAdd_2a[5];
	nodesToAdd_2p[0] = *ll.findNode("3"),
		nodesToAdd_2p[1] = *ll.findNode("17"),
		nodesToAdd_2p[2] = *ll.findNode("22"),
		nodesToAdd_2p[3] = *ll.findNode("12"),
		nodesToAdd_2p[4] = *ll.findNode("19");

	for (int i = 0; i < (sizeof(nodesToAdd_2p) / sizeof(nodesToAdd_2p[0])); i++) {
		Node aux = nodesToAdd_2p[i];

		ll.findNode(aux.getProcess().getID())->setProcess(PCB(aux.getProcess().getID(), "READY", aux.getProcess().getPriority()));

		nodesToAdd_2a[i] = Node(PCB(aux.getProcess().getID(), aux.getProcess().getState(), aux.getProcess().getPriority()));
		rq.add(nodesToAdd_2a[i]);
	}


	std::cout << "\n\n\tReadyQueue content:\n\n";

	rq.display();

	std::cout << "\n\n\tLinkedList content:\n\n";

	ll.display();

	std::cout << "\n---------------\n";

	// -------------------------------------------------------------------------


	/*
		5) One by one remove the process with the highest priority from the queue q1 and
		display the queue after each removal.
	*/
	std::cout << "\n\nTest 1.5)";
	std::cout << "\n\n\tDisplay contents(Before Removing):\n\n";

	std::cout << "\n\n\tReadyQueue content:\n\n";

	rq.display();

	std::cout << "\n\n\tLinkedList content:\n\n";

	ll.display();

	std::cout << "\n\n";

	while (!rq.isEmpty()) {

		Node removed = rq.removeHighest();

		ll.findNode(removed.getProcess().getID())->setProcess(PCB(removed.getProcess().getID(),
			"RUNNING", removed.getProcess().getPriority()));

		std::cout << "\n\tDisplay Contents:\n\n";

		std::cout << "\n\n\tReadyQueue content:\n\n";

		rq.display();

		std::cout << "\n\n\tLinkedList content:\n\n";

		ll.display();

		std::cout << "\n---------------\n";

	}

	std::cout << "\n---------------\n";

	// -------------------------------------------------------------------------

	std::cout << "\n-------------";
	std::cout << "\n-------------";
	std::cout << "\n\n";

}



void tester::secondTest(void)
{

	// -----------------------------------

	LinkedList ll = LinkedList(); // Creating a linkedList(ll) Object.
	ReadyQueue rq = ReadyQueue(); // Creating a ReadyQueue(rq) Object.

	// Quantity of nodes to add further.
	const int sizeList = 30;
	Node nodes[sizeList];


	// This array will be used further on as a memory reference to the processes used in RQ.
	Node nodesInRQ[sizeList];

	// This array is uses to keep track of processes's ID.
	bool indexInRQ[sizeList]; // Process-in-RQ bool array.


	// Creating 30 processes with random priorities(between 1 and 50).
	for (int k = 0; k < sizeList; k++) {
		nodes[k] = Node(PCB(std::to_string(k + 1), "NEW", (rand() % 50 + 1)));
		ll.add(nodes[k]);
	}

	// Initializing nodesInRQ. 
	// P.S.: nodesInRQ is an array nodes that could be in the list, it doesn't mean they are in the RQ.
	for (int i = 0; i < sizeList; i++) {
		Node* aux = ll.findNode(std::to_string(i + 1));
		nodesInRQ[i] = Node(PCB(aux->getProcess().getID(), aux->getProcess().getState(), aux->getProcess().getPriority()));
	}

	// Initializes indexInRQ values as false.
	for (int i = 0; i < sizeList; i++) { indexInRQ[i] = false; }

	// --------------------------------------

	// -----------------------------------


	// Shuflling Nodes and Selecting 15 of them:

	// Creating an array with indexes from 1 to the number of nodes to be added in ll.
	const int numberOfNodes = sizeList;
	int randomNodes[numberOfNodes];

	// Initializing the array with its indexes.
	for (int k = 0; k < numberOfNodes; k++) { randomNodes[k] = k; }

	// Shuffling the values in the array.
	std::random_shuffle(randomNodes, randomNodes + numberOfNodes);
	// --------------------------------------



	// Defining the quantity(15) of nodes to add in RQ.
	const int numberSelected = numberOfNodes / 2;
	const int sizeListRQ = numberSelected;
	Node listRQ[sizeListRQ], listRQ_added[sizeListRQ];

	// Getting nodes from the ll and adding to array listRQ.
	for (int k = 0; k < numberSelected; k++) {
		listRQ[k] = *ll.findNode(std::to_string(randomNodes[k] + 1));
	}

	// Adding Nodes of listRQ to RQ.
	for (int k = 0; k < sizeListRQ; k++) {
		Node aux = listRQ[k];
		listRQ_added[k] = Node(PCB(aux.getProcess().getID(), aux.getProcess().getState(), aux.getProcess().getPriority()));

		int index = std::atoi(listRQ_added[k].getProcess().getID().c_str());

		indexInRQ[index - 1] = true;

		rq.add(listRQ_added[k]);

	}


	// -----------------------------------



	// Defining number of loops.
	int numberOfSteps = 1000000;

	// Defining execution time counter.
	long totalTime = 0L;

	// Defining variable to count PCB number of times added and removed. 
	// 0 is for # of times added and 1 for # of times removed; second index.
	int timesAddAndRem[numberOfNodes][2]; // [Process index - 1][0(added) or 1(removed)].

	// Initializing array.
	for (int i = 0; i < numberOfNodes; i++) {
		for (int j = 0; j < 2; j++) {
			timesAddAndRem[i][j] = 0;
		}
	}

	std::cout << "\n\n-------------";
	std::cout << "\n-------------";
	std::cout << "\n\nThis is the Second Test: \n\n";

	// Execution time loop.
	std::cout << "Loading... (" << std::to_string(numberOfSteps) << "-loops execution):\n";
	for (int k = 0; k < numberOfSteps; k++) {

		// Prints the completed percentage of the loop.
		if (k % 100000 == 0) { std::cout << "\n" << std::to_string((k / 10000)) << "% "; }

		if (k % 100000 == 0) { std::cout << " (" << totalTime << " ms) \n"; }


		// Loop Process time: Start.
		clock_t start = clock();

		int dropOrAdd = rand() % 2;  // Randomly selects either to drop or to add a process to RQ.

		// Removing...
		if (dropOrAdd == 0) {

			// It drops only if the ReadyQueue is not empty.
			if (!rq.isEmpty()) {

				Node removed = rq.removeHighest(); // Removes the process with highest priority in RQ.

				int index = std::atoi(removed.getProcess().getID().c_str()) - 1; // Gets the Process's ID.

				indexInRQ[index] = false; // Indicates that node is not in RQ.
				nodesInRQ[index].setNextasNull(); // Sets node pointer to null.


				ll.findNode(std::to_string(index + 1))->setProcess(
					PCB(nodes[index].getProcess().getID(), "RUNNING",
						nodes[index].getProcess().getPriority()));

				timesAddAndRem[index][1]++; // Increments number of times removed.

			}

		}

		// Adding...
		else {

			// Adds only if the ReadyQueue is not full.
			if (rq.size() < numberOfNodes) {

				int rand_i = rand() % numberOfNodes; // Randomly selects an index.

				while (indexInRQ[rand_i]) { rand_i = rand() % numberOfNodes; } // Checks if node is not in the rq.


				rq.add(nodesInRQ[rand_i]); // Adds the cloned process to the RQ.


				ll.findNode(std::to_string(rand_i + 1))->setProcess(
					PCB(nodes[rand_i].getProcess().getID(), "READY", nodes[rand_i].getProcess().getPriority()));


				indexInRQ[rand_i] = true; // Indicates that node is in rq.

				timesAddAndRem[rand_i][0]++; // Increments number of times added.


			}


		}

		// Loop Process time: End.
		clock_t stop = clock();

		totalTime += (stop - start); // Updates the execution-loop time.

	}


	// -----------------------------------


	// Indicates that the execution loop has been finished.
	std::cout << "\n100% (" << totalTime << " ms)" << "\n\nLoaded.\n\n";

	std::cout << "\n\nTime: " << std::to_string((double)(totalTime * 1.0 / 1000))
		<< " seconds(" << std::to_string(totalTime) << " ms).\n\n"; // Prints total time.


// Prints added-and-removed-times Process list. 
	std::cout << "Number of times each PCB was added and removed: \n\n";

	// Prints PCB Table.
	for (int i = 0; i < numberOfNodes; i++) {

		std::cout << "\t PCB ID " << ((i + 1 < 10) ? " " : "")
			<< std::to_string(i + 1)
			<< " added " << std::to_string(timesAddAndRem[i][0]) << " times, "
			<< "removed " << std::to_string(timesAddAndRem[i][1]) << " times.\n";

	}


	std::cout << "\n\n-------------";
	std::cout << "\n-------------";
	std::cout << "\n\n";

	// -----------------------------------

}
