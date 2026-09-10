CXX = g++
CXXFLAGS = -std=c++11 -O2 -Wall -I../include
LDLIBS = -lrt -pthread

MODELS = model-1 model-2 model-3
BINS = $(foreach m,$(MODELS),$(m)/writer $(m)/reader)

all: $(BINS)

%/writer: %/writer.cpp ../include/shared_memory.h ../include/util.h
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDLIBS)

%/reader: %/reader.cpp ../include/shared_memory.h ../include/util.h
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDLIBS)

clean:
	rm -f $(BINS)
