// Model 1: the writer stores day, month and year straight into three shared integers.
// The reader can observe a torn date (a new day with the old month/year) between the writes.
#include <iostream>
#include <unistd.h>
#include "shared_memory.h"

using namespace std;

int main(int argc, char *argv[])
{
	SharedMemory<int> d("m_day");
	SharedMemory<int> m("m_month");
	SharedMemory<int> y("m_year");

	*d = 0;  *m = 0;  *y = 0;

	cout << "Type values for day, month and year: \n";

	int go_on = 1;
	while (go_on) {

		cout << "\nDay: ";
		cin >> *d;

		cout << "Month: ";
		cin >> *m;

		cout << "Year: ";
		cin >> *y;

		msleep(200);

		cout << "\nDate typed (dd/mm/yyyy): "
			 << *d << "/" << *m << "/" << *y << ".";

		msleep(500);

		cout << "\n\nContinue? (1 = continue, 0 = stop)\n>> ";
		cin >> go_on;

		if (go_on == 0) {  *d = -1;  *m = -1;  *y = -1;  }   // -1 tells the reader to quit
	}

	return 0;
}
