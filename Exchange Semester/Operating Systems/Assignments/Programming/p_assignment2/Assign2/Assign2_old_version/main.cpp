
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


int read_buffer(char buffer[], const int maxSize, std::string cmds[]);

bool execute_commands(std::string cmds[], std::string lastCmds[], int numberCommands, int numberOfCommandsLast, bool lastCommand);

bool save_last_commands(std::string cmds[], std::string last[], int maxSize);

bool history_command(std::string cmds[]);

void execution_cmds(std::string cmds[], int maxSize);


int main()
{

    const int maxSize = 85;
    char commands[maxSize];

    std::string cmds[maxSize];
    int numberOfCommands = 0;

    std::string lastCommand[maxSize];
    int numberOfCommandsLast = 0;
    bool there_is_a_last_command = false;

    bool on = true;
    while (on) {


        std::cout << "osh>>";
        std::cin.getline(commands, maxSize);

        numberOfCommands = read_buffer(commands, maxSize, cmds);

        on = execute_commands(cmds, lastCommand, numberOfCommands, numberOfCommandsLast, there_is_a_last_command);
        there_is_a_last_command = save_last_commands(cmds, lastCommand, maxSize);
        if (!history_command(cmds)) {
            numberOfCommandsLast = numberOfCommands;
        }

        std::cout << "\n";

    }

    return 0;

}


int read_buffer(char buffer[], const int maxSize, std::string cmds[]) {

    const char* endingChar = "";

    std::string cmd = "";

    int k = 0;

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

    for (int i = 0; i < numberCommands; i++) {

        if (cmds[i] == "exit") { return false; }

        if (cmds[i] == "!!") {

            if (lastCommand) {
                history = true;
                break;
            }
            else {
                std::cout << "\nNo history command found!\n";
            }

        }

    }
    
    execution_cmds(cmds, numberCommands);

    if (history) {
        return execute_commands(lastCmds, lastCmds, numberOfCommandsLast, numberOfCommandsLast, lastCommand);
    }

    return true;

}


bool save_last_commands(std::string cmds[], std::string last[], int maxSize) {

    if (cmds[0] == "!!") { return true; }

    for (int i = 0; i < maxSize; i++) {
        last[i] = cmds[i];
    }

    return true;

}


bool history_command(std::string cmds[]) { return (cmds[0] == "!!"); }


void execution_cmds(std::string cmds[], int maxSize) {

    std::string scmd = "";
    for (int i = 0; i < maxSize; i++) {
        scmd += cmds[i];
        scmd += " ";
    }

    pid_t pid = fork();

    if (pid < 0) {
       std::cout << "\nfork faile!\n";
    }
    else if(pid == 0){
        int except = execl(scmd.c_str(),(const char*)scmd.c_str());
	if(except == 0){
	  std::cout << "Command not found";
	}
        
    }
    else {
        wait(NULL);
	exit(0);
    }

}
