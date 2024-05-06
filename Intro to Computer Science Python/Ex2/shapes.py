#################################################################
# FILE : shapes.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex2 2021
# DESCRIPTION: A simple program that calculate the area of the chosen shape
#################################################################
import math

def shape_area():
    """
    This function takes input from user - which shape to calculate is area
    and return the area
    """
    msg = "Choose shape (1=circle, 2=rectangle, 3=triangle): "
    chosen_num = float(input(msg))
    if chosen_num != 1 and chosen_num != 2 and chosen_num != 3:
        return None
    elif chosen_num == 1:
        return circle_area()
    elif chosen_num == 2:
        return rectangle_area()
    return triangle_area()

def circle_area():
    """
    This function calculate circle area by taking radius length input from user
    and return athe area
    """
    radius = float(input())
    return math.pi * math.pow(radius, 2)

def rectangle_area():
    """
    This function calculate rectangle area by taking two rectangle's sides
    length from user input and return athe area
    """
    side1 = float(input())
    side2 = float(input())
    return side1 * side2

def triangle_area():
    """
    This function calculate triangle area by taking triangle's side length
    from user input and return athe area
    """
    side = float(input())
    return (math.sqrt(3)/4) * math.pow(side, 2)
