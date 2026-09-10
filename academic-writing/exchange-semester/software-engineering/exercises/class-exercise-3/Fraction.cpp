#include "Integer.h"
#include "Fraction.h"
#include <typeinfo>
#include <iostream>

using namespace std;

Fraction :: Fraction(int _num, int _den){
    if (_den == 0) {
        cout << "Denominator cannot be zero!" << endl;
        exit(1);
    }
    numerator = _num;
    denominator = _den;
    value = _num/_den;
}

void Fraction :: display(){
    cout << numerator << "/" << denominator << endl;
}

bool Fraction :: operator== (const Number& secNum){
    if (typeid(secNum)==typeid(Integer)){
        return (this->value == ((Integer&)secNum).getValue());
    } else if (typeid(secNum)==typeid(Fraction)){
        return (this->value == ((Fraction&)secNum).getValue());
    } else {
        return false;
    }
}

/*
 * Integer + Fraction = Fraction; Integer + Integer = Integer; Fraction + Fraction = Fraction.
 */
shared_ptr<Number> Fraction :: operator+(const Number& secNum){
    if (typeid(secNum)==typeid(Integer)){
        int sumNumerator = this->getNumerator()+this->getDenominator()*((Integer&)secNum).getValue();
        return NumberPtr(new Fraction (sumNumerator, this->getDenominator()));
    } else if (typeid(secNum)==typeid(Fraction)){
        int sumNumerator = this->getNumerator()*((Fraction&)secNum).getDenominator()+
            this->getDenominator()*((Fraction&)secNum).getNumerator();
        int sumDenominator = this->getDenominator()*((Fraction&)secNum).getDenominator();
        return NumberPtr(new Fraction(sumNumerator, sumDenominator));
    } else {
        return NumberPtr();
    }
}


