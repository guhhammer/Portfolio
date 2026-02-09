#include <iostream>
#include <stdio.h>
#include <time.h>
#include <cstdlib>
#include <Windows.h>
#include <thread>

//#include <unistd.h>
//#include <pthread.h>
//#include <semaphore.h>

#include "ITEM.h"
#include "FIFO.h"

void introduction(void);
void producer(int id);
void consumer(int id);


FIFO ff = FIFO();

//pthread_mutex_t mutex;

int n_prod, n_cons, timer;

int base_delay_p = 4000, delay_producer_percent = 0;
int base_delay_c = 6000, delay_consumer_percent = 0;

int main(int argc, char* argv[])
{

    /* //test zone
    std::thread pp = std::thread(producer, 3);
    std::thread pp2 = std::thread(producer, 6);
    std::thread cc = std::thread(consumer, 4);

    pp.join();
    pp2.join();
    cc.join();

    return 0;
    */

    introduction();

    if (argc < 4) {
        std::cerr << "\n\tPlease specify a timer and number of producers and consumers.\n";
        std::cerr << "\tUsage: " << argv[0] << " <timer> <producer> <consumer> <buffer_size flag> <flags>*\n";
        std::cerr << "\t\tflags(there must be a space character betwenn flags): \n"
            << "\t\t\t -bs-X | (bs -> buffer size, X -> int value)\n"
            <<  "\t\t\t -bdp-X | (bdp -> base delay producer, X -> int value(>2000))\n"
            <<  "\t\t\t -bdc-X | (bdc -> base delay consumer, X -> int value(>2500))\n"
            <<  "\t\t\t -dpp-X | (bpp -> delay producer percent, X -> int value(0 to 100))\n"
            <<  "\t\t\t -dcp-X | (bcp -> delay consumer percent, X -> int value(0 to 100))\n";
        //exit(1);
    }
    else {

        timer = atoi(argv[1]);
        n_prod = atoi(argv[2]);
        n_cons = atoi(argv[3]);

        if (argc >= 5) {
            for (int i = 4; i < argc; i++) {
               char* aux = argv[4];

               std::string command = "", xvalue = "";
               int split = 0;
               for (int k = 1; k < sizeof(aux); k++) {
                   if (aux[k] == '-') {
                       split++;
                   }
                   else {
                       if (split) {
                           xvalue += std::to_string(aux[k]);
                       }
                       else {
                           command += std::to_string(aux[k]);
                       }
                   }
               }

               int x = atoi(xvalue.c_str());
               if (command == "bs") { ff.setMaxSize(x); }
               else if (command == "bdp") {  base_delay_p = (x > 2000) ? x : 2000; }
               else if (command == "bdc") {  base_delay_c = (x > 2500) ? x : 2500; }
               else if (command == "dpp") {  delay_producer_percent = (x < 0) ? 0 : ((x > 100) ? 100 : x); }
               else if (command == "dcp") {  delay_consumer_percent = (x < 0) ? 0 : ((x > 100) ? 100 : x); }
               else { }


            }

        }

        //pthread_t producers[n_prod];
        //pthread_t consumers[n_cons];

        //pthread_mutex_init(&mutex, NULL);

        for (int i = 0; i < n_prod; i++) {
            // pthread_create(&producers[i], NULL, producer, i);
        }
        for (int i = 0; i < n_cons; i++) {
            //pthread_create(&consumers[i], NULL, consumer, i);
        }

    }

    //sleep(timer);

    //pthread_mutex_destroy(&mutex); // destroy mutex

    return 0;

}


void introduction(void)
{
    std::cout
        << "\n"
        << "------------------------------------------------------\n"
        << "------------------------------------------------------\n"
        << "\tName: Gustavo Hammerschmidt.\n\n"
        << "\tProgramming Assignment 4\n\n"
        << "\tDescription: This program is a implementation\n"
        << "\tof the producer-consumer problem.\n"
        << "------------------------------------------------------\n"
        << "------------------------------------------------------\n\n";
}


void producer(int id)
{

    int delay = (int)(base_delay_p + (delay_producer_percent/100)*base_delay_p);

    while (true) {

        //pthread_mutex_lock(&mutex);

        if (!ff.isFull()) {

            ITEM* item = new ITEM(ff.generateAnItemID());

            // Maybe add a production delay, passed by variable(bool yes or no).

            ff.add(*item);

            std::cout << "\nProducer " << std::to_string(id) << " added item " 
                << std::to_string(item->getID()) << ". Buffer display: " << ff.display_array_format() << "\n";

        }

        //pthread_mutex_unlock(&mutex);
        Sleep(rand() % delay);
    }

}

void consumer(int id)
{

    int delay = (int)(base_delay_c + (delay_consumer_percent / 100) * base_delay_c);

    while (true) {
        //pthread_mutex_lock(&mutex);
        if (!ff.isEmpty()) {

            ITEM* rem = ff.remove();

            std::cout << "\nConsumer " << std::to_string(id) << " consumed item "
                << std::to_string(rem->getID()) << ". Buffer display: " << ff.display_array_format() << "\n";

        }
        //pthread_mutex_unlock(&mutex);
        Sleep(rand() % delay);

    }

}
