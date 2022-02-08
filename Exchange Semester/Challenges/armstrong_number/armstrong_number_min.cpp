#include <iostream>
#include <string>
using namespace std;
char* int_to_char(int number); 
int powerof(int n, int base, int res);
int power(int n, int base);
int char_to_int(char ch);
bool armstrong_number(int number, int base);
int main(){ cout << (armstrong_number(153,3)==1 ? "true" : "false") << "\n"; return 0; }
char* int_to_char(int number){ string number_ = to_string(number);  char* res = new char[number_.length()]; for (int i = 0; i < number_.length(); i++) { res[i] = number_[i]; } return res; }
int powerof(int n, int base, int res){ return (base == 0) ? res : powerof(n, base-1, res*n); } 
int power(int n, int base){ return powerof(n, base, 1); }
int char_to_int(char ch){ for (int i = 0; i < 10; i++) { if (ch == to_string(i)[0]) { return i; } } return -1; }
bool armstrong_number(int number, int base){ string nn = to_string(number); char* res = int_to_char(number); int i = 0, sum = 0; while (i < nn.length()) { sum += power(char_to_int(res[i]), base); i++; } return number == sum; }