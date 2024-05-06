#################################################################
# FILE : hello_turtle.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex1 2021
# DESCRIPTION: A simple program that prints garden of flowers
#################################################################
import turtle

def draw_half_petal():
    """
    This function draws an half peatal
    """
    turtle.forward(30)
    turtle.right(45)
    turtle.forward(30)
    turtle.right(135)

def draw_petal():
    """
    This function draws a single peatal
    """
    draw_half_petal()
    draw_half_petal()

def draw_flower():
    """
    This function draws a single flower
    """
    # Add first petal to the flower
    turtle.left(45)
    draw_petal()
    # Add second petal to the flower
    turtle.left(90)
    draw_petal()
    # Add third petal to the flower
    turtle.left(90)
    draw_petal()
    # Add four petal to the flower
    turtle.left(90)
    draw_petal()
    # Add stalk to the flower
    turtle.left(135)
    turtle.forward(150)

def draw_flower_and_advance():
    """
    This function draws a single flower and advance the turtle
    """
    draw_flower()
    turtle.right(90)
    turtle.up()
    turtle.forward(150)
    turtle.right(90)
    turtle.forward(150)
    turtle.left(90)
    turtle.down()

def draw_flower_bed():
    """
    This function draws three flowers
    """
    turtle.up()
    turtle.forward(200)
    turtle.left(180)
    turtle.down()
    draw_flower_and_advance()
    draw_flower_and_advance()
    draw_flower_and_advance()

if __name__ == "__main__":
    draw_flower_bed()
    turtle.done()
