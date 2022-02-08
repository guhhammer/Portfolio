
# A Python-written Functional-Calculator Program:

dict = []
dict.append(lambda x,y: x + y)
dict.append(lambda x,y : x - y)
dict.append(lambda x,y : x * y)
dict.append(lambda x,y : x / y if (y != 0) else 0.0)
dict.append(lambda x,y : x ** y)
dict.append(lambda x,y : x % y)


menu = """
==============
Options:
1. Add
2. Subtract
3. Multiply
4. Divide
5. Power
6. Modulo
==============
"""


def funcCalculator():
    
    print("Please Enter the First Number:")
    firstNumber = int(input())
    
    print("Please Enter the Second Number:")
    secondNumber = int(input())
           
    print(menu)
    
    optionSelected = 0
    while True:
        
        print("Please Select the Calculation Option:")
        optionSelected = int(input())
        
        if optionSelected in range(1,7): break
            
        print("\nWrong option!\n",menu)
    
    print("The answer is:", (dict[optionSelected-1])(firstNumber, secondNumber),"\n")
    

funcCalculator()

