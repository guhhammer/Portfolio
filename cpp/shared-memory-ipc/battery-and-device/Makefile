CXX = g++
CXXFLAGS = -std=c++11 -O2 -Wall -I../include
LDLIBS = -lrt -pthread

all: battery device

battery: battery.cpp ../include/shared_memory.h ../include/named_semaphore.h ../include/util.h
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDLIBS)

device: device.cpp ../include/shared_memory.h ../include/named_semaphore.h ../include/util.h
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDLIBS)

clean:
	rm -f battery device
