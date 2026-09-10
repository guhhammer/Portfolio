// Model 3: the whole date is packed into ONE shared integer, so a single store publishes it atomically
// and the reader can never observe a torn date. The value -1 tells the reader to quit.
#include <iostream>
#include <unistd.h>
#include "shared_memory.h"

using namespace std;

int main(int argc, char *argv[])
{
	SharedMemory<int> dt("dmy");  *dt = 10000;   // 10000 = "no date yet"

	cout << "Type values for day, month and year: \n";

	int go_on = 1, temp_d = 0, temp_m = 0, temp_y = 0;
	while (go_on) {

		cout << "\nDay: "; cin >> temp_d;
		cout << "Month: "; cin >> temp_m;
		cout << "Year: "; cin >> temp_y;

		cout << "\nDate typed (dd/mm/yyyy): "
			 << temp_d << "/" << temp_m << "/" << temp_y << ".";

		msleep(500);

		// packed as (y + m + d) * 10000 + m * 100 + d; the reader unpacks it
		*dt = (temp_y + temp_m + temp_d) * 10000 + temp_m * 100 + temp_d;

		cout << "\n\nContinue? (1 = continue, 0 = stop)\n>> ";  cin >> go_on;

		if (go_on == 0) {  *dt = -1;  }
	}

	return 0;
}
