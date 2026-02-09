#include "Integer.h"
#include "Fraction.h"
#include <typeinfo>
#include <iostream>

using namespace std;

Integer :: Integer(int n){
    value = n;
}

void Integer :: display(){
    cout << value <<endl;
}

bool Integer :: operator== (const Number& secNum){
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
NumberPtr Integer :: operator+(const Number& secNum){
    if (typeid(secNum)==typeid(Integer)){
        return NumberPtr(new Integer(this->value + ((Integer&)secNum).getValue()));
    } else if (typeid(secNum)==typeid(Fraction)){
        int sumNumerator = ((Fraction&)secNum).getNumerator()+((Fraction&)secNum).getDenominator()*this->value;
        return NumberPtr(new Fraction (sumNumerator, ((Fraction&)secNum).getDenominator()));
    } else {
        return NumberPtr();
    }
}
