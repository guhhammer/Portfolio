
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string.h>
#include <string>
#include <fcntl.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/wait.h>
// All the imports used. 


/* 
 This function takes a buffer of commands typed,
 saves the commands in the cmds string array.
 
 It saves only an amount of commands under the 
 maxSize defined for the buffer, so basically the 
 string array cmds has the same size of the buffer.

 It returns an integer value that is the number of cmds typed in
 the buffer. It is also the same number of commands in string
 arrays cmds.
*/
int read_buffer(char buffer[], const int maxSize, std::string cmds[]);

/*
  This function takes a string array of commands, a last
  commands array, the number of commands on both arrays and
  a bool variable defining if there was a last command 
  executed on the shell.
  
  It returns false if the command exit was typed and stops
  the shell execution.
*/
bool execute_commands(std::string cmds[], std::string lastCmds[], int numberCommands, int numberOfCommandsLast, bool lastCommand);


/*
  It saves the last commands executed into the string array last.
  It returns if there was a last command saved(always true).
*/
bool save_last_commands(std::string cmds[], std::string last[], int maxSize);


/*
  It checks an array of comands to find if there was
  a '!!' command. It returns true if it finds the 
  command.
*/
bool history_command(std::string cmds[]);

/*
  It takes an array of comands and execute them with the 
  unix execpv(2); command.
*/
void execution_cmds(std::string cmds[], int maxSize);

/*
  This function is used to make a brief introduction.
*/
void introduction(void);

// Main function of the program.
int main()
{

    const int maxSize = 85; // Defines a size for the commands array below.
    char commands[maxSize];

    std::string cmds[maxSize]; 
    int numberOfCommands = 0;

    std::string lastCommand[maxSize];
    int numberOfCommandsLast = 0;
    bool there_is_a_last_command = false;

    introduction();
    
    bool on = true;  // Execution loop:
    while (on) {


        std::cout << "osh>>";
        std::cin.getline(commands, maxSize); // Gets the input.

	// Saves the input into cmds and returns number of commands.
        numberOfCommands = read_buffer(commands, maxSize, cmds);

	// Executes the commands and returns execution loop validation.
        on = execute_commands(cmds, lastCommand, numberOfCommands, numberOfCommandsLast, there_is_a_last_command);

	// Saves the commands into lastCommand.
	if(!history_command(cmds)){
	  there_is_a_last_command = save_last_commands(cmds, lastCommand, maxSize);
	}
	// If command was !!, it keeps the number of
	// commands of the previous command.
	if (!history_command(cmds)) {
            numberOfCommandsLast = numberOfCommands;
        }

        std::cout << "\n";

    }

    return 0;

}


void introduction(void){
  std::cout << "---------------------------------"
	    << "\n\nName: Gustavo Hammerschmidt.\n\n"
	    << "Programming Assignment 2\n\n"
	    << "Description: A shell interface program "
	    << "\nthat accepts user commands and then\n"
	    << "executes each command in a separate process."
	    << "\n\n----------------------------------\n";
}


int read_buffer(char buffer[], const int maxSize, std::string cmds[]) {

    const char* endingChar = "";

    std::string cmd = "";

    int k = 0;

    // Saves buffer cmds into a string and
    // saves the strings into cmds.
    for (int i = 0; i < maxSize; i++) {

        if (buffer[i] == *" " || buffer[i] == *(endingChar)) {

            cmds[k++] = cmd;
            cmd = "";

        }

        if (buffer[i] != *(endingChar)) {

            if (cmd == "" && buffer[i] == *(" ")) {

            }
            else {
                cmd += buffer[i];
            }

        }
        else { break; }

    }

    return k;

}


bool execute_commands(std::string cmds[], std::string lastCmds[], int numberCommands, int numberOfCommandsLast, bool lastCommand) {

    bool history = false;

    // Checks if there is any 'exit' or '!!' command.
    for (int i = 0; i < numberCommands; i++) {

        if (cmds[i] == "exit") { return false; }// Ends while loop.

        if (cmds[i] == "!!") {

            if (lastCommand) {
                history = true;
                break;
            }
            else {
	        history = true;
                std::cout << "\nNo history command found!\n";
            }

        }

    }

    //If command is not !!.
    if(!history){  
        execution_cmds(cmds, numberCommands);
    }
    else{ // If command is !!, it passes lastCmds as cmds.
        return execute_commands(lastCmds, lastCmds, numberOfCommandsLast, numberOfCommandsLast, lastCommand);
    }

    return true; // It keeps while loops on.

}


bool save_last_commands(std::string cmds[], std::string last[], int maxSize) {
    // If command is !!, it does not save it as last command.
    if (cmds[0] == "!!") { return true; }

    // Saves command as last command.
    for (int i = 0; i < maxSize; i++) {
        last[i] = cmds[i];
    }

    return true; // Returns that there is a last command.

}

// Returns if command is !!.
bool history_command(std::string cmds[]) { return (cmds[0] == "!!"); }


void execution_cmds(std::string cmds[], int maxSize) {

    // Saves string array in a char* array.
    char* args[85];
    int j = 0;
    for(int i = 0; i < maxSize; i++){
      if(cmds[i] != (char *)" " && cmds[i] != (char *)""){
	args[j++] = (char *) strdup(cmds[i].c_str());
      }
    }

    args[j] = NULL; // Last argument is NULL.

    pid_t pid = fork();
    
    if (pid < 0) {
       std::cout << "\nfork failed!\n";
    }
    else if(pid == 0){

        int flag = 0; // Check if there is a '>' or '<' command.
        char* file_ = NULL;  
	for(int i = 0; i < j; i++){
          if(*args[i] == '>'){
	    flag = 1;
	    file_ = args[i + 1]; // Gets the name of the file.
	    args[i] = args[i+1]; // Removes the '>' command.
	    args[i+1] = NULL; 
	    break;
	  }
 	  if(*args[i] == '<'){
	    flag = 2;
	    file_ = args[i + 1]; // Gets the name of the file.
	    args[i] = args[i+1]; // Removes the '<' command.
	    args[i+1] = NULL;
	    break;
 	  }
        }

	int except = 0;
	
	if(flag == 1){ //output
	  int fd = open(file_, O_WRONLY|O_TRUNC|O_CREAT, 0644);
	  dup2(fd, 1); // Creates file, duplicates its descriptor.
	  close(fd); // Closes its fd.
	  except = execvp(args[0], args); // Executes the commands.
	}
	else if(flag == 2){ //input
	  int fd = open(file_, O_RDONLY|O_TRUNC, 0644);
	  dup2(fd, 2); // Creates file, duplicates its descriptor.
	  close(fd); // Closes its fd.
	  except = execvp(args[0], args); // Executes the commands.
	}
	else{ // For all the other commands:

          except = execvp(args[0], args); // Executes the command.
      
	}

	if(except == -1){
  
	   std::cout <<"\nCommand not found: "
		     << std::to_string(except) << "\n";
	} // If command was not found.
	
	exit(0); // Terminates child process.
	
    }
    else {
        wait(NULL);
	if(args[0] == (char *)"exit"){
	   exit(0);
	} // Terminates parent process.
    }

}
