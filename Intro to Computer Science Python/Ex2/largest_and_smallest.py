############################################################################
# FILE : largest_and_smallest.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex2 2021
# DESCRIPTION: A simple program that return thae largest and smallest
# between three numbers
############################################################################
############################################################################
# I chose this two inputs : (-1.5, 4.6, 1.2) and (6, 6, 6)
# (-1.5, 4.6, 1.2) because : i wanted to check that the function return
# the right answer also when the parameters are : float and negative numbers
# (6, 6, 6) because : i wanted to check that the function return
# the right answer also when all the parameters are the same number
############################################################################

def largest_and_smallest(num1, num2, num3):
    """
    This function get three numbers
    and return the the largest and the smallest number between them
    """
    if num1 > num2:
        largest = num1
        smallest = num2
    else:
        largest = num2
        smallest = num1
    if largest < num3:
        largest = num3
    elif smallest > num3:
        smallest = num3
    return largest, smallest

def check_largest_and_smallest():
    are_expected_results = True
    # Change the expected result to false if return values are not :
    # 17-largest, 1-smallest
    largest, smallest = largest_and_smallest(17, 1, 6)
    if largest != 17 or smallest != 1:
        are_expected_results = False
    # Change the expected result to false if return values are not :
    # 17-largest, 1-smallest
    largest, smallest = largest_and_smallest(1, 17, 6)
    if largest != 17 or smallest != 1:
        are_expected_results = False
    # Change the expected result to false if return values are not :
    # 2-largest, 1-smallest
    largest, smallest = largest_and_smallest(1, 1, 2)
    if largest != 2 or smallest != 1:
        are_expected_results = False
    # Change the expected result to false if return values are not :
    # 4.6-largest, -1-smallest
    largest, smallest = largest_and_smallest(-1.5, 4.6, 1.2)
    if largest != 4.6 or smallest != -1.5:
        are_expected_results = False
    # Change the expected result to false if return values are not :
    # 6-largest, 6-smallest
    largest, smallest = largest_and_smallest(6, 6, 6)
    if largest != 6 or smallest != 6:
        are_expected_results = False
    return are_expected_results


