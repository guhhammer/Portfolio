#include <iostream>
#include <stdio.h> 
#include <time.h>
#include <cstdlib>
#include <unistd.h>
#include <pthread.h>

#include "ITEM.h"
#include "FIFO.h"


void introduction(void); // Prints a short introduction to the program.
void *producer(void*); // Calls a producer procedure.
void *consumer(void*); // Calls a consumer procedure.


FIFO ff_buffer = FIFO(); // Global fifo buffer.

pthread_mutex_t mutex; // Mutex thread.

int n_prod, n_cons, timer; // Global variables(#producers, #consumers and execution time respectively).


// Delays:
// A base delay is the maximum delay a procedure can have.
// A percent delay is the amount of guaranteed delay a procedure will have.
// A percent delay increases the amount of base delay to a maximum of twice as the defined base value .
int base_delay_p = 2, delay_producer_percent = 0;
int base_delay_c = 3, delay_consumer_percent = 0;


// Ids for every producer and consumer.
int producer_id_ = 0;
int consumer_id_ = 0;


// Main:
int main(int argc, char* argv[])
{

    introduction(); // Short introduction.

    
	// If not sufficient arguments.
    if (argc < 4) {
          std::cout << "\033[1;32m" << "Please specify a timer and number of producers and consumers.\n" << "Usage: " << argv[0] << " <timer> <producer> <consumer> <flags(optional)>*\n" << "\nflags(there must be a space character betwenn flags): \n"
            << "\t -bs-X | (bs -> buffer size, X -> int value)\n"
            <<  "\t -bdp-X | (bdp -> base delay producer, X -> int value(>2))\n"
            <<  "\t -bdc-X | (bdc -> base delay consumer, X -> int value(>3))\n"
            <<  "\t -dpp-X | (bpp -> delay producer percent, X -> int value(0 to 100))\n"
            <<  "\t -dcp-X | (bcp -> delay consumer percent, X -> int value(0 to 100))\n"
	    <<"\nExamples of proper calls:\n\t./Assign4 10 5 3 -bs-5 -dpp-50\n\t./Assign4 10 5 3\n" <<  "\033[0m\n";
        exit(1);
    }
    else {
		
		// Defining global variables.
        timer = atoi(argv[1]);
        n_prod = atoi(argv[2]);
        n_cons = atoi(argv[3]);
	
		// Catching additional flags if any.
        if (argc >= 5) {
            for (int i = 4; i < argc; i++) {
               char* aux = argv[i];

	       std::string aux_s(aux);
               std::string command = "", xvalue = "";
               int split = 0;
	       
	       int size_s = aux_s.length();
	       size_s = (size_s > 0) ? size_s : size_s * -1;

	       for (int k = 1; k < size_s; k++) {
                   if (aux_s[k] == '-') {
                       split++;
                   }
                   else {
                       if (split) {
                           xvalue += aux_s[k];
                       }
                       else {
                           command += aux_s[k];
                       }
                   }
               }
	       
               int x = atoi(xvalue.c_str());
               if (command == "bs") { ff_buffer.setMaxSize(x); }
               else if (command == "bdp") { base_delay_p = (x > 2) ? x : 2;}
               else if (command == "bdc") { base_delay_c = (x > 3) ? x : 3;}
               else if (command == "dpp") { delay_producer_percent = (x < 0) ? 0 : ((x > 100) ? 100 : x); }
               else if (command == "dcp") {  delay_consumer_percent = (x < 0) ? 0 : ((x > 100) ? 100 : x); }
               else { }

            }

        }
	
		// Prints the parameters' values.
		std::cout 
			<< "\033[1;34m" <<"Timer: " << std::to_string(timer) << " seconds.\n"
			<< "Number of Producers: " << std::to_string(n_prod) << ".\n"
			<< "Number of Consumers: " << std::to_string(n_cons) << ".\n"
			<< "Buffer size: " << ff_buffer.getMaxSize() <<" (default " << ff_buffer.getMaxSizeDefault() << ").\n"
			<< "Base delay Producer: " << std::to_string(base_delay_p) << " seconds(default 2s).\n"
			<< "Base delay Consumer: " << std::to_string(base_delay_c) << " seconds(default 3s).\n"
			<< "Delay producer percent: " << std::to_string(delay_producer_percent) << " %(default 0%).\n"
			<< "Delay consumer percent: " << std::to_string(delay_consumer_percent) << " %(default 0%).\n\n" << "\033[0m\n";
		
		// Arrays with the defined number of procedures.
		pthread_t producers[n_prod];
		pthread_t consumers[n_cons];
		
		// Initializing the mutex thread.
		pthread_mutex_init(&mutex, NULL);
		
		// Locking it for the creation of each procedure.
		pthread_mutex_lock(&mutex);
		
		// Creating a thread for each producer procedure.
		for (int p = 0; p < n_prod; p++) {
			pthread_create(&producers[p], NULL, producer, NULL);
		}
			
		// Creating a thread for each consumer procedure.
		for (int c = 0; c < n_cons; c++) {
			pthread_create(&consumers[c], NULL, consumer, NULL);
		}
		
		// Unlocking it after the creation step.
		pthread_mutex_unlock(&mutex);
	
    }

    sleep(timer); // Finishes after timer expires.

    std::cout << "\033[1;33m" << "\n\nTimer has expired.\n\n\n"
	      << "\033[0m\n";


    // Destroys mutex thread.
    pthread_mutex_destroy(&mutex);
			
    return 0;

}


// A short introduction.
void introduction(void)
{
    std::cout 
        << "\n\n"
        << "------------------------------------------------------\n"
        << "------------------------------------------------------\n"
        << "\tName: Gustavo Hammerschmidt.\n\n"
        << "\tProgramming Assignment 4\n\n"
        << "\tDescription: This program is an implementation\n"
        << "\tof the producer-consumer problem.\n"
        << "------------------------------------------------------\n"
        << "------------------------------------------------------\n\n";
}

// Producer procedure.
void *producer(void*)
{
	
	// Calculates maximum delay.
    int delay = (int)(base_delay_p + (delay_producer_percent/100)*base_delay_p);

    
    // Defining an id for this producer.
    pthread_mutex_lock(&mutex);
    
    int my_id = producer_id_;
    producer_id_++;

    pthread_mutex_unlock(&mutex);
    
    
	// While timer doesn't expire.
    while (true) {

        pthread_mutex_lock(&mutex); // Acquires mutex.

        if (!ff_buffer.isFull()) { // If buffer is not full.

            ITEM* item = new ITEM(ff_buffer.generateAnItemID()); // Gets an id and creates an item.

            ff_buffer.add(*item); // Adds the item to the buffer.
			
			// Prints producer's info.
            std::cout << "\033[1;32m" << "Producer " << std::to_string(my_id) << " added item " 
		      << std::to_string(item->getID()) << ". Buffer display: " << ff_buffer.display_array_format()
		      << ". Buffer current size: "
		      << ff_buffer.size() << " item"
		      << ((ff_buffer.size() == 1) ? "." : "s.")
		      << "\033[0m\n" << "\n";

        }

        pthread_mutex_unlock(&mutex); // Releases mutex.
		
		// Waits percent delay time of the delay plus a random time less than maximum delay time. 
        sleep(rand() % delay - (int)((delay_producer_percent/100)*base_delay_p));
	
    }

}

// Consumer procedure.
void *consumer(void*)
{

	// Calculates maximum delay.
    int delay = (int)(base_delay_c + (delay_consumer_percent / 100) * base_delay_c);


    // Defining an id for this consumer.
    pthread_mutex_lock(&mutex);
    
    int my_id = consumer_id_;
    consumer_id_++;

    pthread_mutex_unlock(&mutex);

    
    
	// While timer doesn't expire.
    while (true) {

        pthread_mutex_lock(&mutex); // Acquires mutex.

		if (!ff_buffer.isEmpty()) { // If the buffer is not empty.

			ITEM* rem = ff_buffer.remove(); // Removes the first item.
	
			// Prints consumer's info.
			std::cout << "\033[1;31m" << "Consumer " << std::to_string(my_id) << " consumed item "
			  << std::to_string(rem->getID()) << ". Buffer display: " << ff_buffer.display_array_format()
                          << ". Buffer current size: "
		          << ff_buffer.size() << " item"
		          << ((ff_buffer.size() == 1) ? "." : "s.")
		  	  << "\033[0m\n" << "\n";

		}
		
		pthread_mutex_unlock(&mutex); // Releases mutex.
		
		// Waits percent delay time of the delay plus a random time less than maximum delay time. 
		sleep(rand() % delay -(int)((delay_consumer_percent/100)*base_delay_c));

    }

}
