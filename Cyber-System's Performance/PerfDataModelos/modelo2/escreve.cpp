#include <iostream>
#include <unistd.h>
#include "memcomp.h"


using namespace std;

int main(int argc, char *argv[])
{
	
	MemComp<int> d("m_dia");
	MemComp<int> m("m_mes");
	MemComp<int> a("m_ano");

	*d = 0;  *m = 0;  *a = 0;

	cout << "Digite valores para dia, mês e ano: \n";
	
	int stop = 1;

	int temp_d = *d, temp_m = *m, temp_a = *a;

	while(stop){
		
		cout << "\nDia: ";
		cin >> temp_d;
		
		cout << "Mês: ";
		cin	>> temp_m;

		cout << "Ano: ";
		cin >> temp_a;

		*d = temp_d; *m = temp_m; *a = temp_a;

		cout << "\nData digitada(dd/mm/aaaa): " 
			 << *d << "/" << *m << "/" << *a << "."; 

		msleep(500);

		cout << "\n\nParar? (1 = continuar, 0 = parar)\n>> ";
		cin >> stop;

		if(stop == 0){  *d = -1;  *m = -1;  *a = -1;  }

	}
	
	return 0;

}