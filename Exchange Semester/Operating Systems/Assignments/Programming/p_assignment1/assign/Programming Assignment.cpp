#include <iostream>
#include <string>

#include "tester.h"

using namespace std;

int main()
{

    tester execute = tester(); // Object tester that contains the methods below.
      
    execute.introduction(); // Outputs a short introduction to the program.

    execute.firstTest(); // Outputs the first test made on the program.

    execute.secondTest(); // Outputs the second test made on the program.

    return 0;

}