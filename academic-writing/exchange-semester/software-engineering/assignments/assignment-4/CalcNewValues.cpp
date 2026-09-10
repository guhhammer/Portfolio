#include <iostream>
#include <stdio.h>
#include <string>


int GRAVITY = 2;
int altitude = 1000;
int fuel = 500;
int velocity = 70;
int time = 0;

int input = 0;
int main(int argc, char* argv[]){
		
	if (argc < 1) { exit(1); }
    else {
		
		char* lines = argv;
		
		std::cout << "%a" << std::to_string(altitude) << "\n" 
				  << "%f" << std::to_string(fuel) << "\n" 
				  << "%v" << std::to_string(velocity) << "\n"
				  << "%t" << std::to_string(time) << "\n";
						
		do{
			
			std::string input_str (lines[input]);
			
			if (input_str.length() > 0){
				
				if(lines[input][0] == '#'){
					std::cout << lines[input] << "\n";
				}
				
				else if(lines[input][0] == '%'){
					
					int burnRate = atoi(lines[input][1]);
					
					if(altitude <= 0){
						std::cout << "The game is over.\n";
					}
			
					else if(burnRate > fuel){
						std::cout << "#Sorry, you don't have that much fuel.\n";
					}
					
					else{
						
						time = time + 1;
						altitude = altitude - velocity;
						velocity = ((velocity + GRAVITY) * 10 - burnRate * 2) / 10;
						fuel = fuel - burnRate;
						
						if(altitude <= 0){
							
							altitude = 0;
							if(velocity <= 5){
								std::cout << "#You have landed safely.\n";
							}
							else{
								std::cout << "#You have crashed.\n";
							}
						}
						
					}
					
					std::cout << "%a" << std::to_string(altitude) << "\n" 
							  << "%f" << std::to_string(fuel) << "\n" 
							  << "%v" << std::to_string(velocity) << "\n"
							  << "%t" << std::to_string(time) << "\n";
					  
				}
			
			}
		}
		while(input < argc && (altitude > 0)){ input++; }
		
	}

}


        
        