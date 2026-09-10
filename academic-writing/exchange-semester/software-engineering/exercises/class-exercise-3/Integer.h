#ifndef ____Integer__
#define ____Integer__

#include "Number.h"

class Integer : public Number{
private:
    int value;
public:
    Integer(int n);
    void display();
    bool operator== (const Number& secNum);
    NumberPtr operator+(const Number& secNum);
    int getValue(){return value;}
};

#endif
