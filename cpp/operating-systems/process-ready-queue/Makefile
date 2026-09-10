CC = g++
CFLAGS = -O2 -g -Wall -std=c++11
PROG = ready_queue
SRCS = Main.cpp LinkedList.cpp Node.cpp PCB.cpp ReadyQueue.cpp tester.cpp
OBJS = $(SRCS:.cpp=.o)

all: $(PROG)

$(PROG): $(OBJS)
	$(CC) $(CFLAGS) -o $(PROG) $(OBJS)

%.o: %.cpp
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJS) $(PROG)
