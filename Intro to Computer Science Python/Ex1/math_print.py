#################################################################
# FILE : math_print.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex1 2021
# DESCRIPTION: A simple program that do mathematical functions
# WEB PAGES I USED: https://docs.python.org/3/library/math.html
#################################################################
import math

def golden_ratio():
    """
    This function prints the golden ratio
    """
    print((1 + math.sqrt(5)) / 2)

def six_squared():
    """
    This function prints the square of six
    """
    print(math.pow(6, 2))

def hypotenuse():
    """
    This function prints the hypotenuse length when the triangle side's length are 5 and 12
    """
    print(math.hypot(5, 12))

def pi():
    """
    This function prints the mathematical constant pi value
    """
    print(math.pi)

def e():
    """
    This function prints the mathematical constant e value
    """
    print(math.e)

def squares_area():
    """
    This function prints squares area which their sides are from 1 to 10 include
    """
    print(1 * 1, 2 * 2, 3 * 3, 4 * 4, 5 * 5, 6 * 6, 7 * 7, 8 * 8, 9 * 9, 10 * 10)

if __name__ == "__main__":
    golden_ratio()
    six_squared()
    hypotenuse()
    pi()
    e()
    squares_area()
