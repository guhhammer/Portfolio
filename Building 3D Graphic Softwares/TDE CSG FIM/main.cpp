#include <windows.h>		// for MS Windows
#include <GL\freeglut.h>	// GLUT, include glu.h and gl.h
#include <fstream>		    // File library
#include <iostream>
using namespace std;


//........................................... angle variables
GLfloat angleV, fAspect;
GLfloat angle = 0.0f, angle1 = 0.0f;
int numObjects;


//............................................ variables used in read process.
int const SizeList = 10;
int ObjectList[SizeList];         // Lista de Objetos, 10 é o número máximo
int x[10], y[10], z[10];	// coordenadas dos objetos
float R[1], G[1], B[1];     // coordenadas cor      R-> red, G-> green, B-> blue.
int tamanho[1];             // coeficiente de tamanho    



							//............................................. nome rotação
char title[] = "TDE figuras";
float rAngle = 0.0;


//.............................................................................. color variables
float corCuboR, corCuboG, corCuboB;
float corSphereR, corSphereG, corSphereB;
float corConeR, corConeG, corConeB;                          // R-> red, G-> green, B-> blue.
float corOctahedronR, corOctahedronG, corOctahedronB;
float corCylinderR, corCylinderG, corCylinderB;
float corIcosahedronR, corIcosahedronG, corIcosahedronB;
//.............................................................................. color variables



//................................................................................ dimension variables for using in adjustments
GLdouble dx1; GLdouble dx2; GLdouble dx3;
GLdouble dy1; GLdouble dy2; GLdouble dy3;
GLdouble dz1; GLdouble dz2; GLdouble dz3;
GLdouble dx4; GLdouble dx5; GLdouble dx6;
GLdouble dy4; GLdouble dy5; GLdouble dy6;
GLdouble dz4; GLdouble dz5; GLdouble dz6;
//................................................................................ dimension variables for using in adjustments




//.............................................................................. size variables
//s stands for size.
GLdouble s1; GLdouble s2; GLdouble s3;    // s6 and s4 won't make chances in the objects. 
GLdouble s4; GLdouble s5; GLdouble s6;    //do not force it to be zero.

										  // Função para leitura do arquivo de cena
void DisplayFileRead(const char * fileName)
{
	fstream inStream;
	inStream.open(fileName, ios::in); // abre o arquivo
	if (inStream.fail()) return;      //falha na abertura do arquivo
	cout << "Abertura do arquivo com sucesso ..." << endl;
	inStream >> numObjects;			  // lê primeira linha do arquivo, número de objetos 
	cout << numObjects << " Objetos na cena ..." << endl;
	for (int i = 0; i <= numObjects; i++) { // leitura das demais linhas, ID dos objetos, posição e cor
		inStream >> ObjectList[i] >> x[i] >> y[i] >> z[i] >> R[i] >> G[i] >> B[i] >> tamanho[i];
	}
	inStream.close();				// fecha o arquivo
									/*
									Sobre a leitura do arquivo:
									1. linha -> número de objetos.

									1. coluna ->  número objeto.
									2. col. -> posição x.
									3. col. -> posição y.
									4. col. -> posição z.
									5. col. -> cor vermelha.
									6. col. -> cor verde.
									7. col. -> cor azul.
									8. col. -> coeficiente de tamanho.

									*/
}







// Função callback chamada para fazer o desenho
void render(void)
{
	//	DisplayFileRead("df.txt");  // se estiver aqui, lê a cada redesenho da tela (dá para alterar o arquivo em tempo de execução)
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpa a tela e o buffer de profundidade
	glPushMatrix();
	glRotatef(angle, 1.0f, 0.0f, 0.0f);    // rotação em x (angle alterado pelo teclado)
	glRotatef(angle1, 0.0f, 1.0f, 0.0f);   // idem y
	glColor3f(1.0f, 1.0f, 1.0f);
	glutWireCube(1000);  // simula os limites da cena

						 //aqui seu código de desenho



	for (int i = 1; i < SizeList; i++) {
		ObjectList[i];
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
		if (ObjectList[i] == 1) {
			glPushMatrix();
			glutSolidCube(20 * tamanho[i]);
			glColor3f((float)corCuboR, (float)corCuboG, (float)corCuboB);
			glTranslated(x[i], y[i], z[i]);
			dx1 = x[i];
			dy1 = y[i];
			dz1 = z[i];
			s1 = tamanho[i];
			glTranslated(dx1, dy1, dz1);
			glPopMatrix();
		}
		if (ObjectList[i] == 2) {
			glPushMatrix();
			glutSolidSphere(40 * tamanho[i], 50, 50);
			glColor3f((float)corSphereR, (float)corSphereG, (float)corSphereB);
			glTranslated(x[i], y[i], z[i]);
			dx2 = x[i];
			dy2 = y[i];
			dz2 = z[i];
			s2 = tamanho[i];
			glTranslated(dx2, dy2, dz2);
			glPopMatrix();
		}
		if (ObjectList[i] == 3) {
			glPushMatrix();
			glutSolidCone(20 * tamanho[i], 30 * tamanho[i], 50, 50);
			glColor3f((float)corConeR, (float)corConeG, (float)corConeB);
			glTranslated(x[i], y[i], z[i]);
			dx3 = x[i];
			dy3 = y[i];
			dz3 = z[i];
			s3 = tamanho[i];
			glTranslated(dx3, dy3, dz3);
			glPopMatrix();
		}
		if (ObjectList[i] == 4) {
			glPushMatrix();
			glutSolidOctahedron();
			glColor3f((float)corOctahedronR, (float)corOctahedronG, (float)corOctahedronB);
			glTranslated(x[i], y[i], z[i]);
			dx4 = x[i];
			dy4 = y[i];
			dz4 = z[i];
			s4 = tamanho[i];
			glTranslated(dx4, dy4, dz4);
			glPopMatrix();
		}
		if (ObjectList[i] == 5) {
			glPushMatrix();
			glutSolidCylinder(20 * tamanho[i], 30 * tamanho[i], 50, 50);
			glColor3f((float)corCylinderR, (float)corCylinderG, (float)corCylinderB);
			glTranslated(x[i], y[i], z[i]);
			dx5 = x[i];
			dy5 = y[i];
			dz5 = z[i];
			s5 = tamanho[i];
			glTranslated(dx5, dy5, dz5);
			glPopMatrix();
		}
		if (ObjectList[i] == 6) {
			glPushMatrix();
			glutSolidIcosahedron();
			glColor3f((float)corIcosahedronR, (float)corIcosahedronG, (float)corIcosahedronB);
			glTranslated(x[i], y[i], x[i]);
			dx6 = x[i];
			dy6 = y[i];
			dz6 = z[i];
			s6 = tamanho[i];
			glTranslated(dx6, dy6, dz6);
			glPopMatrix();
		}
		glFlush();
	}

	glPopMatrix();

	glutSwapBuffers();
}

// Inicializa parâmetros de rendering
void initGL(void)
{
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
	glClearDepth(1.0f);                   // Set background depth to farthest
	glEnable(GL_DEPTH_TEST);   // Enable depth testing for z-culling
	glDepthFunc(GL_LEQUAL);    // Set the type of depth-test
	glShadeModel(GL_SMOOTH);   // Enable smooth shading
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);  // Nice perspective corrections
}

// Função usada para especificar o volume de visualização
void setVisParam(void)
{
	// Especifica sistema de coordenadas de projeção
	glMatrixMode(GL_PROJECTION);
	// Inicializa sistema de coordenadas de projeção
	glLoadIdentity();
	// Especifica a projeção perspectiva
	gluPerspective(angleV, fAspect, 0.1, 500);
	// Especifica sistema de coordenadas do modelo
	glMatrixMode(GL_MODELVIEW);
	// Inicializa sistema de coordenadas do modelo
	glLoadIdentity();
	// Especifica posição do observador e do alvo
	gluLookAt(0, 80, 200, 0, 0, 0, 0, 1, 0);
}

// Função callback chamada quando o tamanho da janela é alterado 
void reshape(GLsizei w, GLsizei h)
{
	// Para previnir uma divisão por zero
	if (h == 0) h = 1;
	// Especifica o tamanho da viewport
	glViewport(0, 0, w, h);
	// Calcula a correção de aspecto
	fAspect = (GLfloat)w / (GLfloat)h;
	setVisParam();
}

// Função callback chamada para gerenciar eventos do mouse
void mouse(int button, int state, int x, int y)
{
	if (button == GLUT_LEFT_BUTTON)
		if (state == GLUT_DOWN) {  // Zoom-in
			if (angleV >= 10) angleV -= 5;
		}
	if (button == GLUT_RIGHT_BUTTON)
		if (state == GLUT_DOWN) {  // Zoom-out
			if (angleV <= 130) angleV += 5;
		}
	setVisParam();
	glutPostRedisplay();  // redraw
}
// Keyboard ...
void processSpecialKeys(int key, int xx, int yy) {

	int contador = 0;
	int selected = 0;
	switch (key) {

		//...............................................................Keys for angle 
	case GLUT_KEY_SHIFT_L:
		angle1--;
		break;
	case GLUT_KEY_SHIFT_R:
		angle1++;
		break;
	case GLUT_KEY_CTRL_L:
		angle--;
		break;
	case GLUT_KEY_CTRL_R:
		angle++;
		break;
		//...............................................................Keys for angle 


		//................................................................. Keys for selecting
	case GLUT_KEY_F1:
		selected = 1;
		cout << "CUBO Selecionado" << endl;
		break;
	case GLUT_KEY_F2:
		selected = 2;
		cout << "ESFERA Selecionada" << endl;
		break;
	case GLUT_KEY_F3:
		selected = 3;
		cout << "CONE Selecionado" << endl;
		break;
	case GLUT_KEY_F4:
		selected = 4;
		cout << "Octaedro Selecionado" << endl;
		break;
	case GLUT_KEY_F5:
		selected = 5;
		cout << "Cilindro Selecionada" << endl;
		break;
	case GLUT_KEY_F6:
		selected = 6;
		cout << "Icosaedro Selecionado" << endl;
		break;
		//................................................................. Keys for selecting









		//.................................................................Keys for color changes
	case GLUT_KEY_F7:
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
		if (selected == 1) {
			corCuboR = 0.0;
			corCuboG = 1.0;
			corCuboB = 1.0;
		}
		if (selected == 2) {
			corSphereR = 0.0;
			corSphereG = 1.0;
			corSphereB = 1.0;
		}
		if (selected == 3) {
			corConeR = 0.0;
			corConeG = 1.0;
			corConeB = 1.0;
		}
		if (selected == 4) {
			corOctahedronR = 0.0;
			corOctahedronG = 1.0;
			corOctahedronB = 1.0;
		}
		if (selected == 5) {
			corCylinderR = 0.0;
			corCylinderG = 1.0;
			corCylinderB = 1.0;
		}
		if (selected == 6) {
			corIcosahedronR = 0.0;
			corIcosahedronG = 1.0;
			corIcosahedronB = 1.0;
		}
		glutSwapBuffers();
		cout << "You chose color yellow" << endl;
		break;

	case GLUT_KEY_F8:
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
		if (selected == 1) {
			corCuboR = 1.0;
			corCuboG = 0.0;
			corCuboB = 1.0;
		}
		if (selected == 2) {
			corSphereR = 1.0;
			corSphereG = 0.0;
			corSphereB = 1.0;
		}
		if (selected == 3) {
			corConeR = 1.0;
			corConeG = 0.0;
			corConeB = 1.0;
		}
		if (selected == 4) {
			corOctahedronR = 1.0;
			corOctahedronG = 0.0;
			corOctahedronB = 1.0;
		}
		if (selected == 5) {
			corCylinderR = 1.0;
			corCylinderG = 0.0;
			corCylinderB = 1.0;
		}
		if (selected == 6) {
			corIcosahedronR = 1.0;
			corIcosahedronG = 0.0;
			corIcosahedronB = 1.0;
		}
		glutSwapBuffers();
		cout << "You chose color purple" << endl;
		break;

	case GLUT_KEY_F9:
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
		if (selected == 1) {
			corCuboR = 1.0;
			corCuboG = 1.0;
			corCuboB = 1.0;
		}
		if (selected == 2) {
			corSphereR = 1.0;
			corSphereG = 1.0;
			corSphereB = 1.0;
		}
		if (selected == 3) {
			corConeR = 1.0;
			corConeG = 1.0;
			corConeB = 1.0;
		}
		if (selected == 4) {
			corOctahedronR = 1.0;
			corOctahedronG = 1.0;
			corOctahedronB = 1.0;
		}
		if (selected == 5) {
			corCylinderR = 1.0;
			corCylinderG = 1.0;
			corCylinderB = 1.0;
		}
		if (selected == 6) {
			corIcosahedronR = 1.0;
			corIcosahedronG = 1.0;
			corIcosahedronB = 1.0;
		}
		glutSwapBuffers();
		cout << "You chose color white" << endl;
		break;

	case GLUT_KEY_F10:
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
		if (selected == 1) {
			corCuboR = 1.0f;
			corCuboG = 0.6f;
			corCuboB = 0.0f;
		}
		if (selected == 2) {
			corSphereR = 1.0f;
			corSphereG = 0.6f;
			corSphereB = 0.0f;
		}
		if (selected == 3) {
			corConeR = 1.0f;
			corConeG = 0.6f;
			corConeB = 0.0f;
		}
		if (selected == 4) {
			corOctahedronR = 1.0f;
			corOctahedronG = 0.6f;
			corOctahedronB = 0.0f;
		}
		if (selected == 5) {
			corCylinderR = 1.0f;
			corCylinderG = 0.6f;
			corCylinderB = 0.0f;
		}
		if (selected == 6) {
			corIcosahedronR = 1.0f;
			corIcosahedronG = 0.6f;
			corIcosahedronB = 0.0f;
		}
		glutSwapBuffers();
		cout << "You chose color orange" << endl;
		break;
		//.................................................................Keys for color changes








		//............................................................Keys for position moviment
	case GLUT_KEY_UP:
		if (selected == 1) dx1--;
		if (selected == 2) dx2--;
		if (selected == 3) dx3--;
		if (selected == 4) dx4--;
		if (selected == 5) dx5--;
		if (selected == 6) dx6--;
		break;
	case GLUT_KEY_DOWN:
		if (selected == 1) dy1++;
		if (selected == 2) dy2++;
		if (selected == 3) dy3++;
		if (selected == 4) dy4++;
		if (selected == 5) dy5++;
		if (selected == 6) dy6++;
		break;
	case GLUT_KEY_LEFT:
		if (selected == 1) dy1--;
		if (selected == 2) dy2--;
		if (selected == 3) dy3--;
		if (selected == 4) dy4--;
		if (selected == 5) dy5--;
		if (selected == 6) dy6--;
		break;
	case GLUT_KEY_RIGHT:
		if (selected == 1) dx1++;
		if (selected == 2) dx2++;
		if (selected == 3) dx3++;
		if (selected == 4) dx4++;
		if (selected == 5) dx5++;
		if (selected == 6) dx6++;
		break;
		//............................................................Keys for position moviment







		//................................................................ Keys for size change
	case GLUT_KEY_PAGE_UP:
		if (selected == 1) s1++;
		if (selected == 2) s2++;
		if (selected == 3) s3++;
		if (selected == 4) s4++;
		if (selected == 5) s5++;
		if (selected == 6) s6++;

	case GLUT_KEY_PAGE_DOWN:
		if (selected == 1) s1--;
		if (selected == 2) s2--;
		if (selected == 3) s3--;
		if (selected == 4) s4--;
		if (selected == 5) s5--;
		if (selected == 6) s6--;
		//................................................................ Keys for size change





		//................................................................. Keys for selecting
	case GLUT_KEY_END:
		selected = 0;
		cout << "NADA Selecnionado" << endl;
		break;
		//................................................................. Keys for selecting

	}
	glutPostRedisplay();   // redraw
}

// Programa Principal
int main(int argc, char** argv) {


	DisplayFileRead("df.txt");              // se estiver aqui, lê somente uma vez

	cout << "Setas - movem o objeto" << endl;
	cout << "Mouse Click - zoom" << endl;
	cout << "Commands: F1-> select cube; F2-> select sphee; F3-> select cone; F4-> slect octahedron; F5-> select cylinder; F6-> select icosahedron" << endl;
	cout << "Commands:  F7-> color yellow; F8-> color purple; F9-> color white; F10-> color orange" << endl;
	cout << "Commands: PAGE_UP-> size increment; PADE_DOWN -> size decrement " << endl;

	glutInit(&argc, argv);					// Initialize GLUT
	glutInitDisplayMode(GLUT_DOUBLE);		// Enable double buffered mode
	glutInitWindowSize(640, 480);			// Set the window's initial width & height
	glutInitWindowPosition(50, 50);			// Position the window's initial top-left corner
	glutCreateWindow(title);				// Create window with the given title
	glutDisplayFunc(render);				// Regster callback for render function
	glutSpecialFunc(processSpecialKeys);	// Register callback handler for arrow keys (keyborad)
	glutReshapeFunc(reshape);				// Register callback for window resize event
	glutMouseFunc(mouse);					// Register callback for mouse event
											//	glutIdleFunc(render);					// no animation
	initGL();								// Initialization function
	glutMainLoop();							// event loop
	return 0;



}   //arrumar função pra cores