// Scene editor: reads a scene file (solid id, position and colour per line), draws the solids with
// freeglut under one light, and lets the user rotate the scene, resize the solids and recolour the
// selected one. Computer Graphics course, PUCPR (2019). Build: see ../README.md
#ifdef _WIN32
#include <windows.h>
#endif
#include <GL/freeglut.h>
#include <fstream>
#include <iostream>
#include <cmath>
using namespace std;

GLfloat angleV, fAspect;
GLfloat angle = 0.0f, angle1 = 0.0f;
int numObjects;

char title[] = "Scene editor";

int const SizeList = 10;
int ObjectList[SizeList];       // solid id per object, 10 at most
int x[10], y[10], z[10];        // object coordinates
float R[10], G[10], B[10];      // object colours (red, green, blue)
int size = 1;                   // size factor applied to every solid
int selected = 0;               // index of the solid the colour keys act on

// Reads the scene file
void DisplayFileRead(const char * fileName)
{
	fstream inStream;
	inStream.open(fileName, ios::in);
	if (inStream.fail()) { cout << "could not open " << fileName << endl; return; }
	cout << "Scene file opened ..." << endl;
	inStream >> numObjects;                       // first line: number of objects
	cout << numObjects << " objects in the scene ..." << endl;
	for (int i = 1; i <= numObjects && i < SizeList; i++) {   // one line per object: id, position, colour
		inStream >> ObjectList[i] >> x[i] >> y[i] >> z[i] >> R[i] >> G[i] >> B[i];
	}
	inStream.close();
	/*
	Scene file layout:
	  line 1 -> number of objects
	  then one line per object:
	    column 1    -> solid id (1 cube, 2 sphere, 3 cone, 4 octahedron, 5 cylinder, 6 icosahedron)
	    columns 2-4 -> x, y, z position
	    columns 5-7 -> red, green, blue (0.0 to 1.0)
	*/
}

void cube(double x, double y, double z, float r, float g, float b) {
	glPushMatrix();
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glutSolidCube(20 * size);
	glPopMatrix();
}

void sphere(double x, double y, double z, float r, float g, float b) {
	glPushMatrix();
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glutSolidSphere(20 * size, 50, 50);
	glPopMatrix();
}

void cone(double x, double y, double z, float r, float g, float b) {
	glPushMatrix();
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glutSolidCone(20 * size, 30 * size, 50, 50);
	glPopMatrix();
}

void cylinder(double x, double y, double z, float r, float g, float b) {
	glPushMatrix();
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glutSolidCylinder(20 * size, 30 * size, 50, 50);
	glPopMatrix();
}

void octahedron(double x, double y, double z, float r, float g, float b) {
	glPushMatrix();
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glutSolidOctahedron();
	glPopMatrix();
}

void icosahedron(double x, double y, double z, float r, float g, float b) {
	glPushMatrix();
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glutSolidIcosahedron();
	glPopMatrix();
}

// Cube built by hand from triangle strips (instead of glutSolidCube)
void stripCube(float x, float y, float z, float r, float g, float b) {
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(-x * size / 2, y * size / 2, z * size / 2);
	glVertex3f(-x * size / 2, -y * size / 2, z * size / 2);
	glVertex3f(x * size / 2, y * size / 2, z * size / 2);
	glVertex3f(x * size / 2, -y * size / 2, z * size / 2);
	glVertex3f(x * size / 2, y * size / 2, -z * size / 2);
	glVertex3f(x * size / 2, -y * size / 2, -z * size / 2);
	glVertex3f(-x * size / 2, y * size / 2, -z * size / 2);
	glVertex3f(-x * size / 2, -y * size / 2, -z * size / 2);
	glVertex3f(-x * size / 2, y * size / 2, z * size / 2);
	glVertex3f(-x * size / 2, -y * size / 2, z * size / 2);
	glEnd();
	glColor3f(r, g, b);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(-x * size / 2, y * size / 2, -z * size / 2);
	glVertex3f(-x * size / 2, y * size / 2, z * size / 2);
	glVertex3f(x * size / 2, y * size / 2, -z * size / 2);
	glVertex3f(x * size / 2, y * size / 2, z * size / 2);
	glEnd();
	glColor3f(r, g, b);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(x * size / 2, -y * size / 2, -z * size / 2);
	glVertex3f(x * size / 2, -y * size / 2, z * size / 2);
	glVertex3f(-x * size / 2, -y * size / 2, -z * size / 2);
	glVertex3f(-x * size / 2, -y * size / 2, z * size / 2);
	glEnd();
}

// Cone built by hand from a triangle fan (instead of glutSolidCone)
void fanCone(float x, float y, float z, float r, float g, float b) {
	const float pi = 3.1415926535897932384626433832795;

	glBegin(GL_TRIANGLE_FAN);   // base
	for (float _angle = 0.0; _angle < (2.0 * pi); _angle += (2.0 * pi / 36.0))
	{
		x = z * sin(_angle);
		y = z * cos(_angle);
		float x1 = z * sin(_angle + (2.0 * pi / 36.0));
		float y1 = z * cos(_angle + (2.0 * pi / 36.0));
		glColor3f(r, g, b);
		glVertex3f(0.0, 0.0, 0.0);   // centre
		glVertex3f(x * size, 0, y * size);
		glVertex3f(x1 * size, 0, y1 * size);
		glVertex3f(0, z * size, 0);
	}
	glEnd();
}

// Display callback
void render(void)
{
	//DisplayFileRead("scene.txt");  // reading here would reload the scene on every redraw (live editing)
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glPushMatrix();
	glRotatef(angle1, 0.0f, 1.0f, 0.0f);   // rotation around y (changed by the keyboard)
	glRotatef(angle, 1.0f, 0.0f, 0.0f);    // rotation around x
	glutWireCube(600);                     // scene bounds

	for (int i = 1; i < SizeList; i++) {
		if (ObjectList[i] == 1) {
			stripCube(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 2) {
			sphere(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 3) {
			fanCone(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 4) {
			octahedron(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 5) {
			cylinder(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 6) {
			icosahedron(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
	}

	glPopMatrix();

	glutSwapBuffers();
}

// Rendering set-up: depth test, Gouraud shading and one light
void initGL(void)
{
	angleV = 45;

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);   // black, opaque background
	glClearDepth(1.0f);
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LEQUAL);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

	GLfloat ambientLight[4] = { 0.2, 0.2, 0.2, 1.0 };
	GLfloat diffuseLight[4] = { 0.7, 0.7, 0.7, 1.0 };     // "colour"
	GLfloat specularLight[4] = { 1.0, 1.0, 1.0, 1.0 };    // "shine"
	GLfloat lightPosition[4] = { 0.0, 50.0, 50.0, 1.0 };

	GLfloat specularity[4] = { 1.0, 1.0, 1.0, 1.0 };      // how shiny the material is
	GLint shininess = 60;

	glShadeModel(GL_SMOOTH);                              // Gouraud shading

	glMaterialfv(GL_FRONT, GL_SPECULAR, specularity);     // material reflectance
	glMateriali(GL_FRONT, GL_SHININESS, shininess);       // highlight concentration

	glLightModelfv(GL_LIGHT_MODEL_AMBIENT, ambientLight);

	glLightfv(GL_LIGHT0, GL_AMBIENT, ambientLight);       // light number 0
	glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLight);
	glLightfv(GL_LIGHT0, GL_SPECULAR, specularLight);
	glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);

	glEnable(GL_COLOR_MATERIAL);   // take the material colour from the current colour
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
}

// Viewing volume: perspective projection and camera position
void setVisParam(void)
{
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(angleV, fAspect, 0.1, 500);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(0, 80, 200, 0, 0, 0, 0, 1, 0);   // eye, target, up
}

// Window resize callback
void reshape(GLsizei w, GLsizei h)
{
	if (h == 0) h = 1;                    // avoid a division by zero
	glViewport(0, 0, w, h);
	fAspect = (GLfloat)w / (GLfloat)h;    // aspect correction
	setVisParam();
}

// Mouse callback: left button zooms in, right button zooms out
void mouse(int button, int state, int x, int y)
{
	if (button == GLUT_LEFT_BUTTON)
		if (state == GLUT_DOWN) {
			if (angleV >= 10) angleV -= 5;
		}
	if (button == GLUT_RIGHT_BUTTON)
		if (state == GLUT_DOWN) {
			if (angleV <= 130) angleV += 5;
		}
	setVisParam();
	glutPostRedisplay();
}

// Special keys: size, selection, colour and rotation
void processSpecialKeys(int key, int xx, int yy) {

	switch (key) {

	case GLUT_KEY_SHIFT_L:
		if (size == 5) { size = 1; }
		else { size += 1; }
		break;
	case GLUT_KEY_CTRL_L:
		if (size > 1) { size -= 1; }
		break;

	case GLUT_KEY_F1: selected = 1; cout << "solid 1 selected" << endl; break;
	case GLUT_KEY_F2: selected = 2; cout << "solid 2 selected" << endl; break;
	case GLUT_KEY_F3: selected = 3; cout << "solid 3 selected" << endl; break;
	case GLUT_KEY_F4: selected = 4; cout << "solid 4 selected" << endl; break;
	case GLUT_KEY_F5: selected = 5; cout << "solid 5 selected" << endl; break;
	case GLUT_KEY_F6: selected = 6; cout << "solid 6 selected" << endl; break;

	case GLUT_KEY_F7:                       // cycle the red component of the selected solid
		if (R[selected] >= 1.0) { R[selected] = 0.0; }
		else { R[selected] += 0.1; }
		break;
	case GLUT_KEY_F8:                       // green
		if (G[selected] >= 1.0) { G[selected] = 0.0; }
		else { G[selected] += 0.1; }
		break;
	case GLUT_KEY_F9:                       // blue
		if (B[selected] >= 1.0) { B[selected] = 0.0; }
		else { B[selected] += 0.1; }
		break;
	case GLUT_KEY_F10:                      // white
		R[selected] = 1.0;
		G[selected] = 1.0;
		B[selected] = 1.0;
		break;

	case GLUT_KEY_LEFT:  angle1--; break;
	case GLUT_KEY_RIGHT: angle1++; break;
	case GLUT_KEY_UP:    angle--;  break;
	case GLUT_KEY_DOWN:  angle++;  break;
	}

	glutPostRedisplay();
}

int main(int argc, char** argv) {
	DisplayFileRead(argc > 1 ? argv[1] : "scene.txt");   // read once

	cout << "Arrow keys - rotate the scene" << endl;
	cout << "Mouse buttons - zoom in / out" << endl;
	cout << "Left Shift / Left Ctrl - grow / shrink the solids" << endl;
	cout << "F1-F6 - select the 1st to 6th solid of the scene" << endl;
	cout << "F7/F8/F9 - cycle the red/green/blue of the selected solid; F10 - white" << endl;

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE);
	glutInitWindowSize(640, 480);
	glutInitWindowPosition(50, 50);
	glutCreateWindow(title);
	glutDisplayFunc(render);
	glutSpecialFunc(processSpecialKeys);
	glutReshapeFunc(reshape);
	glutMouseFunc(mouse);
	initGL();
	glutMainLoop();
	return 0;
}
