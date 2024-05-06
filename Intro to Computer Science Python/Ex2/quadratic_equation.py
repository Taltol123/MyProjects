#################################################################
# FILE : quadratic_equation.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex2 2021
# DESCRIPTION: A simple program that calculate quadratic equation
#################################################################
import math

def quadratic_equation(a, b, c):
    """
    This function get a b and c
    and return the result of quadratic equation
    """
    delta = math.pow(b, 2) - 4*a*c
    if delta < 0:
        return None, None
    elif delta == 0:
        return -b / (2*a), None
    result1 = (-b+math.sqrt(delta)) / (2*a)
    result2 = (-b-math.sqrt(delta)) / (2*a)
    return result1, result2

def quadratic_equation_user_input():
    """
    This function get input : a b and c from the user
    and return the result of quadratic equation
    """
    a, b, c = input("Insert coefficients a, b, and c: ").split()
    if float(a) == 0:
        print("The parameter 'a' may not equal 0")
        return
    result1, result2 = quadratic_equation(float(a), float(b), float(c))
    if result1 == None and result2 == None:
        print("The equation has no solutions")

    elif result1 == None:
        print("The equation has 1 solution: " + str(result2))

    elif result2 == None:
        print("The equation has 1 solution: " + str(result1))
    else:
        print("The equation has 2 solutions: " + str(result1) + " and "
              + str(result2))
