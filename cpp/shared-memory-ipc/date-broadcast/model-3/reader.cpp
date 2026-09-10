// Model 3 reader: unpacks the single shared integer written by the writer.
#include <iostream>
#include <unistd.h>
#include "shared_memory.h"

using namespace std;

string format_date(int);

int main(int argc, char *argv[])
{
	SharedMemory<int> dt("dmy");

	int last_dt = *dt, first = 1;
	string last = "";

	while (*dt != -1) {

		if (last_dt != *dt || first == 1) {  last = format_date(*dt);  first = 0; }

		cout << "Read (dd/mm/yyyy):  " << ((*dt == 10000) ? "0/0/0" : last) << "." << endl;

		sleep(2);
	}

	return 0;
}

// Inverse of the packing done by the writer: digits 0-1 are the day, 2-3 the month,
// the rest is (year + month + day).
string format_date(int packed)
{
	string aux = to_string(packed);
	int d = 0, m = 0, y = 0, n_length = aux.length(), selector = 1;

	for (int i = 0; i < n_length; i++) {

		if (i < 2) {  d = d + ((packed / selector) % 10) * selector; }
		else if (i < 4) {  m = m + ((packed / selector) % 10) * (selector / 100); }
		else {  y = y + ((packed / selector) % 10) * (selector / 10000);  }
		selector = selector * 10;
	}

	return to_string(d) + "/" + to_string(m) + "/" + to_string(y - m - d);
}
