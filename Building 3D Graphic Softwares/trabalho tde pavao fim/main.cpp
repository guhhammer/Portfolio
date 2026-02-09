#include <windows.h>		// for MS Windows
#include <GL\freeglut.h>	// GLUT, include glu.h and gl.h
#include <fstream>		    // File library
#include <iostream>
using namespace std;

GLfloat angleV, fAspect;
GLfloat angle = 0.0f, angle1 = 0.0f;
int numObjects;

char title[] = " TDE figuras ";

int const SizeList = 10;
int ObjectList[SizeList];         // Lista de Objetos, 10 é o número máximo
int x[10], y[10], z[10];	// coordenadas dos objetos
float R[10], G[10], B[10];     // coordenadas cor      R-> red, G-> green, B-> blue.
int tamanho = 1;             // coeficiente de tamanho
float rAngle = 0.0;


float corCuboR = 0.0, corCuboG = 0.0, corCuboB = 0.0;
float corSphereR = 0.0, corSphereG = 0.0, corSphereB = 0.0;
float corConeR = 0.0, corConeG = 0.0, corConeB = 0.0;                          // R-> red, G-> green, B-> blue.
float corOctahedronR = 0.0, corOctahedronG = 0.0, corOctahedronB = 0.0;
float corCylinderR = 0.0, corCylinderG = 0.0, corCylinderB = 0.0;
float corIcosahedronR = 0.0, corIcosahedronG = 0.0, corIcosahedronB = 0.0;
int selected = 0;



// Função para leitura do arquivo de cena
void DisplayFileRead(const char * fileName)
{
	fstream inStream;
	inStream.open(fileName, ios::in); // abre o arquivo
	if (inStream.fail()) return;      //falha na abertura do arquivo
	cout << "Abertura do arquivo com sucesso ..." << endl;
	inStream >> numObjects;			  // lê primeira linha do arquivo, número de objetos 
	cout << numObjects << " Objetos na cena ..." << endl;
	for (int i = 1; i <= numObjects; i++) { // leitura das demais linhas, ID dos objetos, posição e cor
		inStream >>ObjectList[i]>>x[i]>>y[i]>>z[i] >> R[i] >> G[i] >> B[i];
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



void cubo(double x, double y, double z, float r, float g, float b) {

	glPushMatrix();

	glColor3f(r, g, b);

	glTranslatef(x, y, z);

	glutSolidCube(20 * tamanho);

	glPopMatrix();

}

void esfera(double x, double y, double z, float r, float g, float b) {
	
	glPushMatrix();

	glColor3f(r, g, b);

	glTranslatef(x, y , z);

	glutSolidSphere(20 * tamanho, 50, 50);

	glPopMatrix();

}

void cone(double x, double y, double z, float r, float g, float b) {

	glPushMatrix();

	glColor3f(r, g, b);

	glTranslatef(x, y, z);

	glutSolidCone(20 * tamanho, 30 * tamanho, 50, 50);

	glPopMatrix();

}

void cylinder(double x, double y, double z, float r, float g, float b) {

	glPushMatrix();

	glColor3f(r, g, b); //mudar com o movimento ...  Arrumar cores. adicionar: angle-- + angle++
	glTranslatef(x, y, z);

	glutSolidCylinder(20 * tamanho, 30 * tamanho, 50, 50);

	glPopMatrix();

}

void Octaedro(double x, double y, double z, float r, float g, float b) {

	glPushMatrix();

	glColor3f(r, g, b);

	glTranslatef(x, y, z);

	glutSolidOctahedron();

	glPopMatrix();

}

void Icosa(double x, double y, double z, float r, float g, float b) {
	
	glPushMatrix();
	glColor3f(r, g, b);

	glTranslatef(x, y, z);

	glutSolidIcosahedron();

	glPopMatrix();

}


// Cubo com triangle stripes
void cubo2(float x, float y, float z, float r, float g, float b) {
	
	glColor3f(r, g, b);
	glTranslatef(x, y, z);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(-x * tamanho/ 2, y * tamanho / 2, z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, -y * tamanho / 2, z * tamanho / 2);
	glVertex3f(x * tamanho / 2, y * tamanho / 2, z * tamanho / 2);
	glVertex3f(x * tamanho / 2, -y * tamanho / 2, z * tamanho / 2);
	glVertex3f(x * tamanho / 2, y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(x * tamanho / 2, -y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, -y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, y * tamanho / 2, z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, -y * tamanho / 2, z * tamanho / 2);
	glEnd();
	glColor3f(r, g, b);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(-x * tamanho / 2, y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, y * tamanho / 2, z * tamanho / 2);
	glVertex3f(x * tamanho / 2, y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(x * tamanho / 2, y * tamanho / 2, z * tamanho / 2);
	glEnd();
	glColor3f(r, g, b);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(x * tamanho / 2, -y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(x * tamanho / 2, -y * tamanho / 2, z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, -y * tamanho / 2, -z * tamanho / 2);
	glVertex3f(-x * tamanho / 2, -y * tamanho / 2, z * tamanho / 2);
	glEnd();
}

// Cone com triangle stripes
void cone2(float x, float y, float z, float r, float g, float b) {
	
	const float pi = 3.1415926535897932384626433832795;


	glBegin(GL_TRIANGLE_FAN); //BASE
	for (float _angle = 0.0; _angle < (2.0*pi); _angle += (2.0* pi/ 36.0))
	{
		x = z * sin(_angle);
		y = z * cos(_angle);
		float x1 = z * sin(_angle + (2.0* pi / 36.0));
		float y1 = z * cos(_angle + (2.0* pi / 36.0));
		glColor3f(r, g, b);
		glVertex3f(0.0, 0.0, 0.0); // centro
		glVertex3f(x * tamanho, 0, y * tamanho);
		glVertex3f(x1 * tamanho, 0, y1 * tamanho);
		glVertex3f(0, z * tamanho, 0);
	}
	glEnd();
}

// Função callback chamada para fazer o desenho
void render(void)
{
	//DisplayFileRead("df.txt");  // se estiver aqui, lê a cada redesenho da tela (dá para alterar o arquivo em tempo de execução)
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpa a tela e o buffer de profundidade
	glPushMatrix();
	glRotatef(angle1, 0.0f, 1.0f, 0.0f);   // idem y
	glRotatef(angle, 1.0f, 0.0f, 0.0f);    // rotação em x (angle alterado pelo teclado)
	glutWireCube(600);  // simula os limites da cena

	cout << selected << endl;

	for (int i = 1; i <= SizeList; i++) {
		float k = angle + angle1;
		if (ObjectList[i] == 1) {
			cubo2(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 2) {
			esfera(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 3) {
			cone2(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 4) {
			Octaedro(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 5) {
			cylinder(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		if (ObjectList[i] == 6) {
			Icosa(x[i], y[i], z[i], R[i], G[i], B[i]);
		}
		cout << "+";
	}

	glPopMatrix();

	glutSwapBuffers();
}

// Inicializa parâmetros de rendering
void initGL(void)
{
	angleV = 45;

	//aqui seu código de inicialização

	angleV = 45;

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
	glClearDepth(1.0f);                   // Set background depth to farthest
	glEnable(GL_DEPTH_TEST);   // Enable depth testing for z-culling
	glDepthFunc(GL_LEQUAL);    // Set the type of depth-test
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);  // Nice perspective corrections

	GLfloat luzAmbiente[4] = { 0.2,0.2,0.2,1.0 };
	GLfloat luzDifusa[4] = { 0.7,0.7,0.7,1.0 };	   // "cor" 
	GLfloat luzEspecular[4] = { 1.0, 1.0, 1.0, 1.0 };// "brilho" 
	GLfloat posicaoLuz[4] = { 0.0, 50.0, 50.0, 1.0 };

	// Capacidade de brilho do material
	GLfloat especularidade[4] = { 1.0,1.0,1.0,1.0 };
	GLint especMaterial = 60;

	// Especifica que a cor de fundo da janela será preta
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	// Habilita o modelo de colorização de Gouraud
	glShadeModel(GL_SMOOTH);

	// Define a refletância do material 
	glMaterialfv(GL_FRONT, GL_SPECULAR, especularidade);
	// Define a concentração do brilho
	glMateriali(GL_FRONT, GL_SHININESS, especMaterial);

	// Ativa o uso da luz ambiente 
	glLightModelfv(GL_LIGHT_MODEL_AMBIENT, luzAmbiente);

	// Define os parâmetros da luz de número 0
	glLightfv(GL_LIGHT0, GL_AMBIENT, luzAmbiente);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, luzDifusa);
	glLightfv(GL_LIGHT0, GL_SPECULAR, luzEspecular);
	glLightfv(GL_LIGHT0, GL_POSITION, posicaoLuz);

	// Habilita a definição da cor do material a partir da cor corrente
	glEnable(GL_COLOR_MATERIAL);
	//Habilita o uso de iluminação
	glEnable(GL_LIGHTING);
	// Habilita a luz de número 0
	glEnable(GL_LIGHT0);
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
		if (state == GLUT_DOWN) {  // Zoom-in Botão Esquerdo do mouse
			if (angleV >= 10) angleV -= 5;
		}
	if (button == GLUT_RIGHT_BUTTON)
		if (state == GLUT_DOWN) {  // Zoom-out Botão Direito do mouse
			if (angleV <= 130) angleV += 5;
		}
	setVisParam();
	glutPostRedisplay();
}



// Keyboard ...
void processSpecialKeys(int key, int xx, int yy) {
	
	switch (key) {

	case GLUT_KEY_SHIFT_L:
		if (tamanho == 5) { tamanho = 1; }
		else { tamanho += 1; }
		break;
	case GLUT_KEY_CTRL_L:
		if (tamanho == 1) { ; }
		else { tamanho -= 1; }
		break;


	case GLUT_KEY_F1:
		selected = 1;
		cout << "1º sólido selecionado" << endl;
		break;
	case GLUT_KEY_F2:
		selected = 2;
		cout << "2º sólido selecionada" << endl;
		break;
	case GLUT_KEY_F3:
		selected = 3;
		cout << "3º sólido selecionado" << endl;
		break;
	case GLUT_KEY_F4:
		selected = 4;
		cout << "4º sólido selecionado" << endl;
		break;
	case GLUT_KEY_F5:
		selected = 5;
		cout << "5º sólido selecionado" << endl;
		break;
	case GLUT_KEY_F6:
		selected = 6;
		cout << "6º sólido selecionado" << endl;
		break;


	case GLUT_KEY_F7:
		if (R[selected] >= 1.0) { R[selected] = 0.0; }
		else { R[selected] += 0.1; }
		break;
	case GLUT_KEY_F8:
		if (G[selected] >= 1.0) { G[selected] = 0.0; }
		else { G[selected] += 0.1; }
		break;
	case GLUT_KEY_F9:
		if (B[selected] >= 1.0) { B[selected] = 0.0; }
		else { B[selected] += 0.1; }
		break;
	case GLUT_KEY_F10:
		R[selected] = 1.0;
		G[selected] = 1.0;
		B[selected] = 1.0;
		break;

	case GLUT_KEY_LEFT:
		angle1--;
		break;
	case GLUT_KEY_RIGHT:
		angle1++;
		break;
	case GLUT_KEY_UP:
		angle--;
		break;
	case GLUT_KEY_DOWN:
		angle++;
		break;

	}

	glutPostRedisplay();   // redraw
}

// Programa Principal
int main(int argc, char** argv) {
	DisplayFileRead("df.txt");              // se estiver aqui, lê somente uma vez

	cout << "Setas - rotaciona a cena" << endl;
	cout << "Mouse Click - zoom" << endl;
	cout << "Shift_l e CTRL_L -> modificam tamanho" << endl;
	cout << "F(1,2,3,4,5,6) <- selecionam os objetos da cena conforme sua ordem." << endl;
	cout << "F(7,8,9,10) mudam a cor do objeto selecionado." << endl;

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
}
