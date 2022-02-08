#include <iostream>
#include <unistd.h>
#include "memcomp.h"

using namespace std;

string format_data(int);

int main(int argc, char *argv[])
{
	
	MemComp<int> dt("dma");

	int last_dt = *dt, first = 1;
	string last = "";
	
	while (*dt != -1) {

		if (last_dt != *dt || first==1){  last = format_data(*dt);  first=0; }

		cout << "Lido(dd/mm/aaaa):  " << ((*dt == 10000) ? "0/0/0" : last) << "." << endl; 

		sleep(2);

	}

	return 0;

}

string format_data(int data){
	
	string aux = to_string(data);
	int d=0, m=0, a=0, n_length = aux.length(), seletor = 1;

	for(int i = 0; i < n_length; i++){

		if (i < 2){  d = d + ((data / seletor) % 10) * seletor; }
		else if (i < 4){  m = m + ((data / seletor) % 10) * (seletor/100); }
		else{  a = a + ((data / seletor) % 10) * (seletor/10000);  }
		seletor = seletor * 10;

	}

	return to_string(d)+"/"+to_string(m)+"/"+to_string((a - m - d));

}