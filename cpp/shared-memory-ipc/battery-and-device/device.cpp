// Device process: counts while the battery has charge and drains one level every ten counts.
// When the level reaches zero it "plugs the battery in" by clearing the consume flag.
// Pair assignment with João Vitor Andrioli de Souza.
#include <iostream>
#include <unistd.h>
#include "shared_memory.h"
#include "named_semaphore.h"

using namespace std;

struct battery
{
	int level;
	int consume = 0; // flag: 1 while the device may drain the battery
	int end = 0;     // flag: 1 when the battery has no lives left
	int start = 0;   // flag: 1 once the battery process is up
};

int main(int argc, char *argv[])
{
	SharedMemory<battery> bt("battery_memory");

	NamedSemaphore mtx("mymutex", 1);

	int my_count = 0;

	cout << "\n\033[32m" << std::string(60, '=')
		 << "\nDevice: drains 1 level every 10 counts.\n\nDEVICE STARTING...\033[0m\n";

	while (bt->start == 0) { /* wait for the battery process */ }

	while (bt->end == 0) {

		while (bt->consume == 0) { /* waiting for the battery to recharge */ }

		while (bt->consume == 1) {

			++my_count;

			cout << "\033[36mCounting: " << my_count << " || battery = \033[0m"
				 << ((bt->level < 10) ? "\033[31m" : ((bt->level) < 20) ? "\033[33m" : "\033[32m")
				 << bt->level << "\033[0m.\n";

			mtx.down();

				if (bt->consume == 0) { mtx.up(); break; }

				if (my_count % 10 == 0) { // drain one level

					bt->level = bt->level - 1;

					cout << "\n";

					if (bt->level == 0) {
						cout << "\n\033[31mEmpty battery detected - plugging it in\033[0m\n";
						bt->consume = 0;
						cout << "\n";
					}
				}

			mtx.up();

			msleep(rand() % 500);
		}
	}

	cout << "\n\n\033[32m" << std::string(60, '=') << "\n\nProgram finished!\033[0m\n";

	return 0;
}
