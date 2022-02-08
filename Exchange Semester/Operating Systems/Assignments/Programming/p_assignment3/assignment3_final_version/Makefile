###################################
# OS COURSE - CS 433 example
# 
###################################
CC = g++			# use g++ for compiling c++ code or gcc for c code
CFLAGS =  -O3 -g -Wall -std=c++11		# compilation flags: -g for debugging. Change to -O2 or -O3 for optimized code.
LIB = -lm -static-libstdc++			# linked libraries	
LDFLAGS = -L.			# link flags
PROG = fcfs sjf priority rr priorityWithRR	# target executable (output)
SRCS = m_fcfs.cpp m_sjf.cpp m_priority.cpp m_rr.cpp m_priority_and_rr.cpp \
	PCB.cpp LinkedList.cpp FCFS.cpp SJF.cpp PriorityScheduling.cpp RoundRobin.cpp PriorityAndRR.cpp MainFCFS.cpp MainSJF.cpp MainPriorityScheduling.cpp MainRoundRobin.cpp MainPrAndRR.cpp    # .c or .cpp source files.
OBJ = $(SRCS:.cpp=.o) 	# object files for the target. Add more to this and next lines if there are more than one source files.

all : $(PROG) depend

fcfs: m_fcfs.o PCB.o LinkedList.o FCFS.o MainFCFS.o
	$(CC) -o fcfs m_fcfs.o PCB.o LinkedList.o FCFS.o MainFCFS.o $(LDFLAGS) $(LIB)

sjf: m_sjf.o PCB.o LinkedList.o SJF.o MainSJF.o
	$(CC) -o sjf m_sjf.o PCB.o LinkedList.o SJF.o MainSJF.o $(LDFLAGS) $(LIB)

priority: m_priority.o PCB.o LinkedList.o PriorityScheduling.o MainPriorityScheduling.o
	$(CC) -o priority m_priority.o PCB.o LinkedList.o PriorityScheduling.o MainPriorityScheduling.o $(LDFLAGS) $(LIB)

rr: m_rr.o PCB.o LinkedList.o RoundRobin.o MainRoundRobin.o
	$(CC) -o rr m_rr.o PCB.o LinkedList.o RoundRobin.o MainRoundRobin.o $(LDFLAGS) $(LIB)

priorityWithRR: m_priority_and_rr.o PCB.o LinkedList.o PriorityAndRR.o MainPrAndRR.o
	$(CC) -o priorityWithRR m_priority_and_rr.o PCB.o LinkedList.o PriorityAndRR.o MainPrAndRR.o $(LDFLAGS) $(LIB)

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
