#include "MainRoundRobin.h"

// Overwrites LinkedList method.
void MainRoundRobin::scheduler(std::string file_name)
{

    this->introduction();

    RoundRobin scheduler = RoundRobin(this->time_quantum); // Switches to Round-Robin Scheduling Algorithm.
    
    PCB* processes = readFile(file_name);

    for (int k = 0; k < numberProcesses(file_name); k++) {
        scheduler.push(processes[k]);
    }

    scheduler.displaySchedulingTimes(); // Displays for Round-Robin S.A. 

}

// Execution-function call function.
void MainRoundRobin::main(std::string file_name, int time_quantum_)
{

    this->time_quantum = time_quantum_; // Defines time_quantum.
    this->scheduler(file_name); // Calls execution function.

}
