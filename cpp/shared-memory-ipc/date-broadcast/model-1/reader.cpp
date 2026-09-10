// Model 1 reader: prints the three shared integers every two seconds until the writer sets them to -1.
#include <iostream>
#include <unistd.h>
#include "shared_memory.h"

using namespace std;

int main(int argc, char *argv[])
{
	SharedMemory<int> d("m_day");
	SharedMemory<int> m("m_month");
	SharedMemory<int> y("m_year");

	while (*d != -1 && *m != -1 && *y != -1) {

		cout << "Read (dd/mm/yyyy):  " << *d << "/" << *m
			 << "/" << *y << "." << endl;

		sleep(2);
	}

	return 0;
}
