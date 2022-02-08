/*
* OGL01Shape3D.cpp: 3D Shapes
*/
#include <windows.h>  // for MS Windows
#include <GL/freeglut.h>  // GLUT, include glu.h and gl.h

/* Global variables */
char title[] = "OpenGL-PUC PR";

/* Initialize OpenGL Graphics */
void initGL() {
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Set background color to black and opaque
}

/* Handler for window-repaint event. Called back when the window first appears and
whenever the window needs to be re-painted. */
void render() {
		glClear(GL_COLOR_BUFFER_BIT ); // Clear color 
		glMatrixMode(GL_MODELVIEW);     // To operate on model-view matrix
		glLoadIdentity();                  // Reset the model-view matrix

		glFlush();  // Swap the front and back frame buffers (double buffering)
}


/* Main function: GLUT runs as a console application starting at main() */
int main(int argc, char** argv) {
	glutInit(&argc, argv);            // Initialize GLUT
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB); // Enable double buffered mode
	glutInitWindowSize(640, 480);   // Set the window's initial width & height
	glutInitWindowPosition(50, 50); // Position the window's initial top-left corner
	glutCreateWindow(title);          // Create window with the given title
	glutDisplayFunc(render);       // Register callback handler for window re-paint event
	initGL();                       // Our own OpenGL initialization
	glutMainLoop();                 // Enter the infinite event-processing loop
	return 0;
}