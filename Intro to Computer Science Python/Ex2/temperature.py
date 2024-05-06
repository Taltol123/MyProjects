#################################################################
# FILE : temperature.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex2 2021
# DESCRIPTION: A simple program that check if its summer now
#################################################################

def is_it_summer_yet(max_temperature, first_day_temperature,
                     second_day_temperature, third_day_temperature):
    """
    This function check if two days or more have higher temperature than the
    given max temperature
    """
    counter = 0
    if max_temperature < first_day_temperature:
        counter += 1
    if max_temperature < second_day_temperature:
        counter += 1
    if max_temperature < third_day_temperature:
        counter += 1
    return counter >= 2
