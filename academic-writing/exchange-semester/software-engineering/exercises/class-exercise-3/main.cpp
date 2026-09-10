#include "Number.h"
#include <iostream>

using namespace std;

int main(){
    
    string s1 ("Fraction");
    int a1[] = {3,2};
    shared_ptr<Number> n1 = Number::getInstance(s1, a1);
    n1->display();

    string s2 ("Integer");
    int a2[] = {3};
    shared_ptr<Number> n2 = Number::getInstance(s2, a2);
    n2->display();
    
    if (*n1==*n2)
        cout << "Equal" << endl;
    else
        cout << "Not Equal" << endl;

    //Fraction + Fraction = Fraction
    shared_ptr<Number> n3 = (*n1) + (*n1);
    n3->display();
    //Integer + Integer = Integer
    shared_ptr<Number> n4 = (*n2) + (*n2);
    n4->display();
    //Integer + Fraction = Fraction
    shared_ptr<Number> n5 = (*n1) + (*n2);
    n5->display();
    
    return 0;
}
