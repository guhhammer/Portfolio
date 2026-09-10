// Battery process: recharges a shared battery three times (three "lives") and waits, each time,
// until the device drains it. Shared state lives in POSIX shared memory guarded by a named semaphore.
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

	int lives = 3;

	bt->level = 0; bt->end = 0; bt->consume = 0; bt->start = 1;

	cout << "\n\033[36m" << std::string(60, '=')
		 << "\n\nBattery for 3 devices!\n"
		 << "(discharged)\033[0m\n";

	while (lives > 0) {

		cout << "\n\033[31mI have " << lives << " life cycle"
			 << ((lives == 1) ? "" : "s") << " left.\033[0m\n";

		if (bt->consume == 0) {

			mtx.down();

			lives--;

			cout << "\n\033[36mRecharging[" << (lives) << "] [\033[0m";

			while (bt->level < 30) {

				bt->level = bt->level + 1;

				cout << ((bt->level < 10) ? "\033[31m" : ((bt->level) < 20) ? "\033[33m" : "\033[32m")
					 << "|" << "\033[0m" << flush;

				msleep(333);
			}

			cout << "\033[36m]\033[0m\n";

			bt->consume = 1;

			mtx.up();
		}

		cout << "\033[32m\nBattery level: " << bt->level << " (full).\033[0m\n";

		while (bt->consume == 1) { /* busy-wait while the device drains the battery */ }
	}

	mtx.down();  bt->end = 1;  mtx.up();

	cout << "\n\033[36m" << std::string(60, '=') << "\n\nProgram finished!\033[0m\n";

	return 0;
};
