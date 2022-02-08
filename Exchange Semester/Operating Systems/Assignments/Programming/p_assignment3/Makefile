###################################
# OS COURSE - CS 433 example
# 
###################################
CC = g++			# use g++ for compiling c++ code or gcc for c code
CFLAGS = -g -Wall -std=c++11	# compilation flags: -g for debugging. Change to -O or -O2 for optimized code.
LIB = -lm			# linked libraries	
LDFLAGS = -L.			# link flags
PROG = sjf fcfs rr priority		# target executables (output)
SRCS = schedule_fcfs.cpp schedule_rr.cpp schedule_sjf.cpp schedule_priority.cpp \
	PCB.cpp # .c or .cpp source files.
OBJ = $(SRCS:.cpp=.o) 	# object files for the target. Add more to this and next lines if there are more than one source files.





all : $(PROG) depend

fcfs: schedule_fcfs.o PCB.o
	$(CC) -o fcfs schedule_fcfs.o PCB.o $(LDFLAGS) $(LIB)

rr: schedule_rr.o PCB.o
	$(CC) -o rr schedule_rr.o PCB.o $(LDFLAGS) $(LIB)

sjf: schedule_sjf.o PCB.o
	$(CC) -o sjf schedule_sjf.o PCB.o $(LDFLAGS) $(LIB)

priority: schedule_priority.o PCB.o
	$(CC) -o priority schedule_priority.o PCB.o $(LDFLAGS) $(LIB)




.cpp.o:
	$(CC) -c $(CFLAGS) $< -o $@
	
depend: .depend

.depend: $(SRCS)
	rm -f ./.depend
	$(CC) $(CFLAGS) -MM $^ > ./.depend;

include .depend

# cleanup
clean:
	/bin/rm -f *.o $(PROG) .depend

# DO NOT DELETE
