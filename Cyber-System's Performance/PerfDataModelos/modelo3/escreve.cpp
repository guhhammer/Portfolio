#include <iostream>
#include <unistd.h>
#include "memcomp.h"

using namespace std;

int main(int argc, char *argv[])
{
	
	MemComp<int> dt("dma");  *dt = 10000;

	cout << "Digite valores para dia, mês e ano: \n";
	
	int stop = 1, temp_d = 0, temp_m = 0, temp_a = 0;
	while(stop){
		
		cout << "\nDia: "; cin >> temp_d;
		cout << "Mês: "; cin >> temp_m;
		cout << "Ano: "; cin >> temp_a;

		cout << "\nData digitada(dd/mm/aaaa): " 
			 << temp_d << "/" << temp_m << "/" << temp_a << "."; 

		msleep(500);

		*dt = (temp_a+temp_m+temp_d) * 10000 + temp_m * 100 + temp_d;

		cout << "\n\nParar? (1 = continuar, 0 = parar)\n>> ";  cin >> stop;

		if(stop == 0){  *dt = -1;  }

	}
	
	return 0;

}