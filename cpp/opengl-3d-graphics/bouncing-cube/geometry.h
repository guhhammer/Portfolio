// A tiny 3D vector and a cube drawn with a triangle strip, used by the bouncing-cube animation.
#pragma once
#ifdef _WIN32
#include <windows.h>
#endif
#include <GL/freeglut.h>
#include <math.h>

class Vector3D {

public:
	float x, y, z;

	Vector3D(float px, float py, float pz) : x(px), y(py), z(pz) {}

	float getX() const { return x; }
	float getY() const { return y; }
	float getZ() const { return z; }

	void add(const Vector3D& v) {
		x += v.getX();
		y += v.getY();
		z += v.getZ();
	}

	void set(float px, float py, float pz) {
		x = px;
		y = py;
		z = pz;
	}
};

class Cube {

private:
	Vector3D pos = Vector3D(0.0f, 0.0f, 0.0f);
	float edge;

public:
	Cube(float px, float py, float pz, float edgeLength) : pos(px, py, pz), edge(edgeLength) {}

	// one face of the cube as a two-colour triangle strip
	void draw() {
		glBegin(GL_TRIANGLE_STRIP);
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(-edge / 2 + pos.getX(), edge / 2 + pos.getY(), edge / 2 + pos.getZ());
		glVertex3f(-edge / 2 + pos.getX(), -edge / 2 + pos.getY(), edge / 2 + pos.getZ());
		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex3f(edge / 2 + pos.getX(), edge / 2 + pos.getY(), edge / 2 + pos.getZ());
		glVertex3f(edge / 2 + pos.getX(), -edge / 2 + pos.getY(), edge / 2 + pos.getZ());
		glEnd();
	}

	Vector3D getPos() const { return pos; }

	void move(const Vector3D& v) { pos.add(v); }

	float volume(void) const { return edge * edge * edge; }
};
