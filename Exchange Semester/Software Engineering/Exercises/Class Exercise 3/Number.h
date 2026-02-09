#ifndef ____Number__
#define ____Number__

#include <string>
#include <memory>

typedef std::shared_ptr<class Number> NumberPtr;

class Number{
    public:
        static NumberPtr getInstance(std::string &numType, int a[]);
        virtual void display()=0;
        virtual bool operator== (const Number& secNum)=0;
        virtual NumberPtr operator+(const Number& secNum)=0;
};

#endif
