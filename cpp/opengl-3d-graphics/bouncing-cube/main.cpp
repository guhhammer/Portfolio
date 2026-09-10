// Bouncing cube: a cube moves with a velocity vector under a small gravity vector, bouncing off the
// walls and the floor of an orthographic view; the arrow keys rotate the view.
// Computer Graphics course, PUCPR (2019). Build: see ../README.md
#include "geometry.h"

char title[] = "Bouncing cube";

GLfloat range = 1000;
GLfloat angle0 = 0.0f, angle1 = 0.0f;

Cube cube(0.0f, 0.0f, 50.0f, 100);

Vector3D vX(1.5f, 0.0f, 0.0f);      // horizontal velocity
Vector3D vY(0.0f, 1.0f, 0.0f);      // vertical velocity
Vector3D g(0.0f, -0.0005f, 0.0f);   // gravity

void initGL() {
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	glClearDepth(1.0f);
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LEQUAL);
	glShadeModel(GL_SMOOTH);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
}

void processSpecialKeys(int key, int xx, int yy) {
	switch (key) {
	case GLUT_KEY_LEFT:  angle1--; break;
	case GLUT_KEY_RIGHT: angle1++; break;
	case GLUT_KEY_UP:    angle0--; break;
	case GLUT_KEY_DOWN:  angle0++; break;
	}
}

void render() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	glRotatef(angle0, 1.0f, 0.0f, 0.0f);
	glRotatef(angle1, 0.0f, 1.0f, 0.0f);

	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

	cube.draw();
	cube.move(vX);
	cube.move(vY);
	vY.add(g);                                   // gravity pulls the vertical velocity down
	if (cube.getPos().getX() > 1000) {           // bounce off the side walls
		vX = Vector3D(-0.2f, 0, 0);
	}
	else if (cube.getPos().getX() < -1000) {
		vX = Vector3D(0.2f, 0, 0);
	}
	if (cube.getPos().getY() < -400) {           // bounce off the floor
		vY = Vector3D(0.0f, 1, 0);
	}

	glutSwapBuffers();
}

void reshape(GLsizei w, GLsizei h) {
	if (h == 0) h = 1;
	glViewport(0, 0, w, h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	if (w <= h)
		glOrtho(-range, range, -range * h / w, range * h / w, -range, range);
	else
		glOrtho(-range * w / h, range * w / h, -range, range, -range, range);
}

int main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE);
	glutInitWindowSize(640, 480);
	glutInitWindowPosition(50, 50);
	glutCreateWindow(title);
	glutDisplayFunc(render);
	glutReshapeFunc(reshape);
	glutIdleFunc(render);
	glutSpecialFunc(processSpecialKeys);
	initGL();
	glutMainLoop();
	return 0;
}
