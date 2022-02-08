#include "classevetor.h"
#include <windows.h>  
#include <GL/freeglut.h>  
#include <math.h> 


class vetor3d {

public:
	//
	float posx;
	float posy;
	float posz;
	//

public:

	//
	vetor3d(float psx, float psy, float psz) {
		posx = psx;
		posy = psy;
		posz = psz;
	}
	//


	//
	float getPOSX() {
		return posx;
	}

	float getPOSY() {
		return posy;
	}

	float getPOSZ() {
		return posz;
	}
	//



	//
	void addvetor(vetor3d v) {
		posx += v.getPOSX();
		posy += v.getPOSY();
		posz += v.getPOSZ();
	}

	void setvetor(float psx, float psy, float psz) {
		posx = psx;
		posy = psy;
		posz = psz;
	}
	//

};



class Cubo {

private:
	
	//
	vetor3d pos = vetor3d(0.0f, 0.0f, 0.0f);
	float ar;
	//

public:
	
	//
	Cubo(float t_xpos, float t_ypos, float t_zpos, float t_aresta) {
		pos = vetor3d(t_xpos, t_ypos, t_zpos);
		ar = t_aresta;
	}
	//

	
	//
	void desenha() {
		float i1 = pos.getPOSX();

		glBegin(GL_TRIANGLE_STRIP);
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(-ar / 2 + pos.getPOSX(), ar / 2 + pos.getPOSY(), ar / 2 + pos.getPOSZ());
		glVertex3f(-ar / 2 + pos.getPOSX(), -ar / 2 + pos.getPOSY(), ar / 2 + pos.getPOSZ());
		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex3f(ar / 2 + pos.getPOSX(), ar / 2 + pos.getPOSY(), ar / 2 + pos.getPOSZ());
		glVertex3f(ar / 2 + pos.getPOSX(), -ar / 2 + pos.getPOSY(), ar / 2 + pos.getPOSZ());
		glEnd();


	} 
	//



	//
	vetor3d getPos() {
		return pos;
	}
	void addvetor(vetor3d v) {
		pos.addvetor(v);
	}

	float volume(void) {
		return (3 * ar);
	}
	//

};

