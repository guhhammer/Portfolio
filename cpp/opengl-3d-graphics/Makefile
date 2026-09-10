# Needs freeglut: sudo apt install freeglut3-dev   (Debian/Ubuntu)
CXX = g++
CXXFLAGS = -std=c++11 -O2 -Wall
LDLIBS = -lglut -lGLU -lGL

all: scene-editor/scene-editor bouncing-cube/bouncing-cube

scene-editor/scene-editor: scene-editor/main.cpp
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDLIBS)

bouncing-cube/bouncing-cube: bouncing-cube/main.cpp bouncing-cube/geometry.h
	$(CXX) $(CXXFLAGS) -o $@ $< $(LDLIBS)

clean:
	rm -f scene-editor/scene-editor bouncing-cube/bouncing-cube
