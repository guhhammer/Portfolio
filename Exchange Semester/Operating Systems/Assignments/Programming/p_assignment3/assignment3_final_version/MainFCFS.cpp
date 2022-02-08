#include "MainFCFS.h"

// Overwrites LinkedList method.
void MainFCFS::scheduler(std::string file_name)
{

    this->introduction();

    FCFS scheduler = FCFS(); // Switches to FCFS Scheduling Algorithm. 

    PCB* processes = readFile(file_name);

    for (int k = 0; k < numberProcesses(file_name); k++) {
        scheduler.push(processes[k]);
    }

    scheduler.displaySchedulingTimes(); // Displays for FCFS S.A.

}

// Execution-function call function.
void MainFCFS::main(std::string file_name)
{

    this->scheduler(file_name); // Calls the execution function.

}
