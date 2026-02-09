#include <iostream>
#include <unistd.h>
#include "memcomp.h"


using namespace std;

int main(int argc, char *argv[])
{
	
	MemComp<int> mc("minha_mem");

	MemComp<int> d("m_dia");
	MemComp<int> m("m_mes");
	MemComp<int> a("m_ano");
	
	while (*d != -1 && *m != -1 && *a != -1) {

		cout << "Lido(dd/mm/aaaa):  " << *d << "/" << *m 
			 << "/" << *a << "." << endl;

		sleep(2);

	}

	return 0;

}