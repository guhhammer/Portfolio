"""Number base conversion exercises - Cyber-Physical Systems Fundamentals, class 01 (PUCPR, 2018).

dec_to_hex() converts a decimal integer to hexadecimal by repeated division,
printing each quotient and remainder; base_to_decimal() reads a 3-digit number
in a given base (2 to 16) and converts it to decimal.
"""

def dec_to_hex(dividend):
    divisor = 16
    digits = '0123456789ABCDEF'
    result = ''

    while True:
        quotient = int(dividend / divisor)
        remainder = dividend % divisor
        print("Quotient: {}  Remainder: {}".format(quotient, remainder))
        result = digits[remainder] + result
        dividend = quotient
        if quotient == 0:
            break

    print("Result: {}h".format(result))

# dec_to_hex(421)

def base_to_decimal(base):
    num = input("enter the number to convert (3 digits): ").upper()

    if len(num) != 3:
        print("Error: at most three digits: ")
        exit()

    # base = int(input("Enter the base: "))
    if base < 2 or base > 16:
        print("The base must be between 2 and 16.")
        exit()


    digits = "0123456789ABCDEF"
    a = (digits.find(num[0])) * (base * base)
    b = (digits.find(num[1])) * (base)
    c = (digits.find(num[2])) * (1)
    total = a + b + c

    print("Result: {}".format(total))

base_to_decimal(10)
