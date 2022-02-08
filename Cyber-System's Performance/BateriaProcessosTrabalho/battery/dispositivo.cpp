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

	int minha_contagem = 0;
	
	cout << "\n\033[32m" << std::string(60, '=')
		 << "\nDispositivo: consome 1 nível a cada 10 contagens.\n\nDISPOSITIVO INICIANDO...\033[0m\n";

	while(bt->start == 0){
		
	}

	while(bt->end == 0){

		while(bt->consume == 0){ /* Esperando bateria recarregar */}

		while (bt->consume == 1){

			++minha_contagem;

			cout << "\033[36mContando: " << minha_contagem << " || bateria = \033[0m" 
				 << ((bt->level < 10) ? "\033[31m" : ((bt->level) < 20) ? "\033[33m" : "\033[32m") 
				 << bt->level << "\033[0m.\n";
						
			mtx.down();

				if (bt->consume == 0){ mtx.up(); break; }

				if (minha_contagem % 10 == 0){ // Consumindo 1 nível.

					bt->level = bt->level - 1;
					
					cout << "\n";

					if(bt->level == 0){	
						cout << "\n\033[31mBateria vazia identificada - ligando na tomada\033[0m\n"; 
						bt->consume = 0; 
						cout << "\n";
					}

				}
				
			mtx.up();

			msleep(rand() % 500);

		}

	}

	cout << "\n\n\033[32m" << std::string(60, '=') << "\n\nPrograma encerrou!\033[0m\n";

	return 0;

}