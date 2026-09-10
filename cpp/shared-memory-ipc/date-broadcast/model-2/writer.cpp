// Model 2: the writer reads the whole date into local variables first and only then copies the three
// values to shared memory, which narrows (but does not close) the window in which the reader sees a torn date.
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
	int temp_d = *d, temp_m = *m, temp_y = *y;

	while (go_on) {

		cout << "\nDay: ";
		cin >> temp_d;

		cout << "Month: ";
		cin >> temp_m;

		cout << "Year: ";
		cin >> temp_y;

		*d = temp_d; *m = temp_m; *y = temp_y;

		cout << "\nDate typed (dd/mm/yyyy): "
			 << *d << "/" << *m << "/" << *y << ".";

		msleep(500);

		cout << "\n\nContinue? (1 = continue, 0 = stop)\n>> ";
		cin >> go_on;

		if (go_on == 0) {  *d = -1;  *m = -1;  *y = -1;  }
	}

	return 0;
}
