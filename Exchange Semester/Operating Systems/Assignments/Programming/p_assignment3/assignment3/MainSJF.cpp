#include "MainSJF.h"

// Overwrites LinkedList method.
void MainSJF::scheduler(std::string file_name)
{

    this->introduction();

    SJF scheduler = SJF(); // Switches to SJF Scheduling Algorithm.

    PCB* processes = readFile(file_name);

    for (int k = 0; k < numberProcesses(file_name); k++) {
        scheduler.push(processes[k]);
    }

    scheduler.displaySchedulingTimes(); // Displays for SJF S.A.

}

// Execution-function call function.
void MainSJF::main(std::string file_name)
{

    this->scheduler(file_name); // Calls execution function.
    
}
