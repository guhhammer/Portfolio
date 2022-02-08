#include "Number.h"
#include "Integer.h"
#include "Fraction.h"

NumberPtr Number :: getInstance(std::string &numType, int a[]){
    //The a array contains the integer or numerator/denominator of fraction.
    //Here we assume it is correctly parepared.
    if (numType == "Integer"){
        return NumberPtr (new Integer(a[0]));
    } else if (numType == "Fraction"){
        return NumberPtr(new Fraction(a[0], a[1]));
    } else {
        return NumberPtr();
    }
}