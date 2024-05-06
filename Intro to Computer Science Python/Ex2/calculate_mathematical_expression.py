#################################################################
# FILE : calculate_mathematical_expression.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex2 2021
# DESCRIPTION: A simple program that calculate mathematical expression
#################################################################

def calculate_mathematical_expression(num1, num2, elementary_arithmetic):
    """
    This function get two numbers int or float and elementary arithmetic
    and return the result
    """
    if elementary_arithmetic == "+":
        return num1 + num2
    elif elementary_arithmetic == "-":
        return num1 - num2
    elif elementary_arithmetic == ":":
        if num2 != 0:
            return num1 / num2
    elif elementary_arithmetic == "*":
        return num2 * num1
    return None

def calculate_from_string(mathematical_expression):
    """
    This function get a message which contains mathematical expression
    and return the result
    """
    num1, elementary_arithmetic, num2 = mathematical_expression.split()
    result = calculate_mathematical_expression(float(num1), float(num2),
                                               elementary_arithmetic)
    return result


