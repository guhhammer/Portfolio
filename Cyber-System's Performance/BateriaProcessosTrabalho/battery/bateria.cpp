#include <iostream>
#include <unistd.h>
#include "memcomp.h"
#include "semaforo.h"

using namespace std;

/*
	NOMES:
		Gustavo Hammerschmidt;
		João vitor Andrioli de Souza.

*/

struct battery
{
	int level;
	int consume = 0; // flag.	
	int end = 0; // flag.
	int start = 0; // flag.
};

int main(int argc, char *argv[])
{

	MemComp<battery> bt("battery_memory");

	Semaforo mtx("mymutex", 1);

	int lifes = 3;

	bt->level = 0; bt->end = 0; bt->consume = 0; bt->start = 1;

	cout << "\n\033[36m" << std::string(60, '=')
		 << "\n\nBateria para 3 dispositivos!\n" 
		 << "(descarregada)\033[0m\n";

	while (lifes > 0) {

		cout << "\n\033[31mEu tenho " << lifes << " ciclo" 
			 << ((lifes == 1) ? "" : "s") << " de vida.\033[0m\n";
 		
		if (bt->consume == 0){

			mtx.down();

			lifes--;

			cout << "\n\033[36mRecarregando["<< (lifes) << "] [\033[0m";

			while(bt->level < 30){

				bt->level = bt->level + 1;

				cout << ((bt->level < 10) ? "\033[31m" : ((bt->level) < 20) ? "\033[33m" : "\033[32m") 
					 << "|" << "\033[0m" << flush;

				msleep(333);

			}

			cout << "\033[36m]\033[0m\n";
		
			bt->consume = 1;

			mtx.up();

		}
	
		cout << "\033[32m\nNível da Bateria: " << bt->level << "(cheio).\033[0m\n";
	
		while(bt->consume == 1){ 

		}
		
	}

	mtx.down();  bt->end = 1;  mtx.up();

	cout << "\n\033[36m" << std::string(60, '=') << "\n\nPrograma encerrou!\033[0m\n";

	return 0;

};
