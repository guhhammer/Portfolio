#ifndef ____Fraction__
#define ____Fraction__

#include "Number.h"

class Fraction : public Number{
private:
    int numerator, denominator;
    float value;
public:
    Fraction(int _num, int _den);
    void display();
    bool operator== (const Number& secNum);
    NumberPtr operator+(const Number& secNum);
    int getNumerator(){return numerator;}
    int getDenominator(){return denominator;}
    float getValue(){return value;}
};

#endif