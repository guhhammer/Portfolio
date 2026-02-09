#include "MainPrAndRR.h"

// Overwrites LinkedList method.
void MainPrAndRR::scheduler(std::string file_name)
{

    this->introduction();
    // Switches to Priority-with-Round-Robin Scheduling Algorithm.
    PriorityAndRR scheduler = PriorityAndRR(this->time_quantum); 
    
    PCB* processes = readFile(file_name);

    for (int k = 0; k < numberProcesses(file_name); k++) {
        scheduler.push(processes[k]);
    }

    scheduler.displaySchedulingTimes(); // Displays for PriorityAndRR S.A.

}

// Execution-function call function.
void MainPrAndRR::main(std::string file_name, int time_quantum_)
{
 
    this->time_quantum = time_quantum_; // Defines time_quantum.
    this->scheduler(file_name); // Calls the execution function.

}
