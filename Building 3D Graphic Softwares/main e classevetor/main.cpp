#include <windows.h>		
#include <GL\freeglut.h>	
#include "classevetor.cpp"
 


char title[] = "moon g.f.:";

GLfloat aMAX = 1000;
GLfloat angle0 = 0.0f, angle1 = 0.0f;

Cubo c(0.0f, 0.0f, 50.0f, 100);

vetor3d vX(1.5f, 0.0f, 0.0f);
vetor3d vY(0.0f, 1.0f, 0.0f);
vetor3d g(0.0f, -0.0005f, 0.0f);


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
	case GLUT_KEY_LEFT:
		angle1--;
		break;
	case GLUT_KEY_RIGHT:
		angle1++;
		break;
	case GLUT_KEY_UP:
		angle0--;
		break;
	case GLUT_KEY_DOWN:
		angle0++;
		break;
	}
}



void render() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
	glMatrixMode(GL_MODELVIEW);			
	glLoadIdentity();					


	glRotatef(angle0, 1.0f, 0.0f, 0.0f);
	glRotatef(angle1, 0.0f, 1.0f, 0.0f);

	glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

	c.desenha();
	c.addvetor(vX);
	c.addvetor(vY);
	vY.addvetor(g);
	if (c.getPos().getPOSX() > 1000) {
		vX = vetor3d(-0.2f, 0, 0);
	}
	else if (c.getPos().getPOSX() < -1000) {
		vX = vetor3d(0.2f, 0, 0);
	}
	if (c.getPos().getPOSY() < -400) {
		vY = vetor3d(0.0f, 1, 0);
	}

	glutSwapBuffers();  
}


void ajuste(GLsizei a, GLsizei b) {
	if (b == 0) b = 1;
	glViewport(0, 0, a, b);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	if (a <= b)
		glOrtho(-aMAX, aMAX, -aMAX * b / a, aMAX*b / a, -aMAX, aMAX);
	else
		glOrtho(-aMAX * a / b, aMAX*a / b, -aMAX, aMAX, -aMAX, aMAX);
}

int main(int argc, char** argv) {
	glutInit(&argc, argv);             
	glutInitDisplayMode(GLUT_DOUBLE);  
	glutInitWindowSize(640, 480);      
	glutInitWindowPosition(50, 50);    
	glutCreateWindow(title);           
	glutDisplayFunc(render);           
	glutReshapeFunc(ajuste);          
	glutIdleFunc(render);			   
	glutSpecialFunc(processSpecialKeys);  
	initGL();                       
	glutMainLoop();                 
	return 0;
}