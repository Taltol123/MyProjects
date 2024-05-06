#################################################################
# FILE : ex3.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex3 2021
#################################################################

def input_list():
    """
    This function gets number inputs from user untul he enter empyt string
    and return list with al the numbers input and the sum of them
    """
    inputs_list = []
    inputs_sum = 0
    user_input = input()
    while user_input:
        inputs_list.append(float(user_input))
        inputs_sum += float(user_input)
        user_input = input()
    inputs_list.append(inputs_sum)
    return inputs_list

def inner_product(vec_1, vec_2):
    """
    This function gets two lists and calculate their inner product.
    Return their inner product
    """
    inner_product_result = 0
    if len(vec_1) != len(vec_2):
        return None
    for index in range(len(vec_1)):
        inner_product_result += vec_1[index] * vec_2[index]
    return inner_product_result

def sequence_monotonicity(sequence):
    """
    This function gets a sequence
    and check which monotonicity kind this sequence
    Return a 4 size list of boolean values :
    0: True if it is up monotonicity sequence or False otherwise
    1: True if it is really up monotonicity sequence or False otherwise
    2: True if it is down monotonicity sequence or False otherwise
    3: True if it is really down monotonicity sequence or False otherwise
    """
    sequence_monotonicity_kind = [is_up_monotonicity_sequence(sequence),
                                  is_really_up_monotonicity_sequence(sequence),
                                  is_down_monotonicity_sequence(sequence),
                                  is_really_down_monotonicity_sequence(
                                      sequence)]
    return sequence_monotonicity_kind

def is_up_monotonicity_sequence(sequence):
    """
    This function gets a sequence,
    and check if it is an up monotonicity sequence.
    Return True if is up monotonicity sequence or False otherwise.
    """
    for index in range(len(sequence) - 1):
        if sequence[index] > sequence[index + 1]:
            return False
    return True

def is_really_up_monotonicity_sequence(sequence):
    """
    This function gets a sequence,
    and check if it is really up monotonicity sequence.
    Return True if is really up monotonicity sequence or False otherwise.
    """
    for index in range(len(sequence) - 1):
        if sequence[index] >= sequence[index + 1]:
            return False
    return True

def is_down_monotonicity_sequence(sequence):
    """
    This function gets a sequence,
    and check if it is down monotonicity sequence.
    Return True if is down monotonicity sequence or False otherwise.
    """
    for index in range(len(sequence) - 1):
        if sequence[index] < sequence[index + 1]:
            return False
    return True

def is_really_down_monotonicity_sequence(sequence):
    """
    This function gets a sequence,
    and check if it is down monotonicity sequence.
    Return True if is down monotonicity sequence or False otherwise.
    """
    for index in range(len(sequence) - 1):
        if sequence[index] <= sequence[index + 1]:
            return False
    return True

def monotonicity_inverse(def_bool):
    """
    This function gets a 4 size boolean sequence.
    Return 4 size numbers sequence which match the four conditions of
    monotonicity kind.
    """
    if def_bool == [True, False, False, False]:
        return [1, 2, 2, 3]
    elif def_bool == [True, False, True, False]:
        return [56, 56, 56, 56]
    elif def_bool == [False, False, True, True]:
        return [7.5, 4, 3.141, 0.111]
    elif def_bool == [True, True, False, False]:
        return [56.5, 57.5, 63, 84]
    elif def_bool == [False, False, False, False]:
        return [1, 0, -1, 1]
    elif def_bool == [False, False, True, False]:
        return [4, 2, 2, 1]
    return None

def primes_for_asafi(n):
    """
    This function gets a number n.
    Return a list of the firsts n prime numbers.
    """
    primes = []
    primes_counter = 0
    current_number = 1
    while n > primes_counter:
        current_number = current_number + 1
        if is_prime(current_number):
            primes.append(current_number)
            primes_counter += 1
    return primes

def is_prime(num):
    """
    This function gets a number and check if it is a prime number.
    Return true if it is a prime number and false otherwise.
    """
    if num == 1:
        return False
    for index in range(2, int(num / 2) + 1):
        if num % index == 0:
            return False
    return True

def sum_of_vectors(vec_lst):
    """
    This function gets a list of vector lists and calculate the vectors sum.
    Return a list that represent the vectors sum.
    """
    vectors_sum = []
    vec_lst_length = len(vec_lst)
    if vec_lst_length == 0:
        return None
    sub_lists_length = len(vec_lst[0])
    for sub_list_index in range(sub_lists_length):
        single_sum = 0
        for vec_lst_index in range(vec_lst_length):
            single_sum += vec_lst[vec_lst_index][sub_list_index]
        vectors_sum.append(single_sum)
    return vectors_sum

def num_of_orthogonal(vectors):
    """
    This function gets a list of vector lists and check how many
    orthogonal pair lists there are in the main list.
    Return the number of orthogonal pair lists in the main list.
    """
    orthogonal_counter = 0
    for vec_index in range(len(vectors)):
        for other_vec_index in range(len(vectors)):
            if vec_index != other_vec_index:
                inner_product_result = inner_product(vectors[vec_index],
                                                     vectors[other_vec_index])
                if inner_product_result == 0:
                    orthogonal_counter += 1
    return orthogonal_counter / 2
