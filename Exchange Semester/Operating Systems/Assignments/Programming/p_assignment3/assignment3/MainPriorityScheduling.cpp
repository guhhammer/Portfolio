#include "MainPriorityScheduling.h"

// Overwrites LinkedList method.
void MainPriorityScheduling::scheduler(std::string file_name)
{

    this->introduction();

    PriorityScheduling scheduler = PriorityScheduling(); // Switches to Priority Scheduling Algorithm.

    PCB* processes = readFile(file_name);

    for (int k = 0; k < numberProcesses(file_name); k++) {
        scheduler.push(processes[k]);
    }

    scheduler.displaySchedulingTimes(); // Displays for Priority S.A.

}

// Execution-function call function.
void MainPriorityScheduling::main(std::string file_name)
{

    this->scheduler(file_name); // Calls the execution function.

}
