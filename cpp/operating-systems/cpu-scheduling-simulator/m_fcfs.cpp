#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include "MainFCFS.h"

// Main for FCFS.
// Reads the input and passes it to the execution-function call function.
int main(int argc, char* argv[])
{
    
    // Check that input file is provided at command line.
    if (argc < 2) {
        std::cerr << "Usage: " << argv[0] << " <input_file> [<time_quantum>]" << std::endl;
        exit(1);
    }

    // Read the time quantum if provided.
    int time_quantum = 1;
    if (argc >= 3) { time_quantum = atoi(argv[2]); }

    MainFCFS scheduler = MainFCFS();

    scheduler.main(argv[1]); // Execution-function call function.

    return 0;

}