#################################################################
# FILE : wordsearch.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex5 2021
#################################################################
import sys
import os.path

LINE_SEPARATOR = "\n"
WORDS_SEPARATOR = ","
NOT_VALID_ARGS_MSG = "Not 4 args"
WORDS_FILE_NOT_FOUND_MSG = "Words file doesn't exist"
MATRIX_FILE_NOT_FOUND_MSG = "Matrix file doesn't exist"
NOT_VALID_DIRECTIONS_MSG = "Not an optional direction"
OPTIONAL_DIRECTIONS = "udrlwzyx"
EXPECTED_ARGS_NUM = 4

def read_wordlist(filename):
    """
    This function reads the words from the file
    and return a list of all the words
    """
    words = []
    with open(filename, 'r') as words_file:
        for line in words_file:
            line = line.strip(LINE_SEPARATOR)
            words.append(line)
    return words

def read_matrix(filename):
    """
    This function reads the matrix from the file
    and return a list which represent the matrix
    """
    matrix = []
    with open(filename, 'r') as matrix_file:
        for line in matrix_file:
            single_matrix_line = []
            striped_line = line.strip(LINE_SEPARATOR)
            for letter in striped_line.split(WORDS_SEPARATOR):
                single_matrix_line.append(letter)
            matrix.append(single_matrix_line)
    return matrix

def find_words(word_list, matrix, directions):
    """
    This function checks which words from the list are appear in the directions
    and how many times.
    """
    results = []
    for word in word_list:
        words_count = find_word(word, matrix, directions)
        if words_count > 0:
            results.append((word, words_count))
    return results

def find_word(requested_word, matrix, directions):
    """
    This function checks if the word appears in the matrix in those directions.
    """
    directions_operations = {
        "u": get_matrix_u_words,
        "d": get_matrix_d_words,
        "r": get_matrix_r_words,
        "l": get_matrix_l_words,
        "w": get_matrix_w_words,
        "x": get_matrix_x_words,
        "y": get_matrix_y_words,
        "z": get_matrix_z_words
    }
    counter = 0
    for direction in directions:
        matrix_direction_words = directions_operations[direction](matrix)
        counter += count_word_appearance(requested_word,
                                         matrix_direction_words)
    return counter

def get_matrix_r_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in right direction.
    """
    words = []
    for line in matrix:
        word = ""
        for letter in line:
            word += letter
        words.append(word)
    return words

def get_matrix_l_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in left direction.
    """
    r_words = get_matrix_r_words(matrix)
    l_words = reverse_words(r_words)
    return l_words

def get_matrix_d_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in down direction.
    """
    words = []
    matrix_lines_size = len(matrix)
    if matrix_lines_size != 0:
        matrix_columns_size = len(matrix[0])
        for column_index in range(matrix_columns_size):
            word = ""
            for line_index in range(matrix_lines_size):
                word += matrix[line_index][column_index]
            words.append(word)
    return words

def switch_columns_side(matrix):
    """
    This function gets matrix and switches matrix's columns sides.
    Return the switched matrix.
    """
    switched_matrix = []
    down_words = get_matrix_d_words(matrix)
    for word in down_words:
        reversed_word = word[::-1]
        switched_matrix.append(reversed_word)
    switched_matrix = transpose_matrix(switched_matrix)
    return switched_matrix

def transpose_matrix(matrix):
    """
    This function gets matrix and transform the matrix.
    Return the transformed matrix
    """
    transposed_matrix = []
    if len(matrix) != 0:
        transposed_matrix = [[matrix[line_index][column_index] for line_index
                              in range(len(matrix))]
                             for column_index in range(len(matrix[0]))]
    return transposed_matrix

def get_matrix_y_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in diagonal down right direction.
    """
    switched_matrix = switch_columns_side(matrix)
    y_words = get_matrix_w_words(switched_matrix)
    return y_words

def count_word_appearance(requested_word, matrix_words):
    """
    This function gets requested word and matrix's words and check how many
    times requested word appears in the matrix words.
    Return count of appearances.
    """
    counter = 0
    for matrix_word in matrix_words:
        if requested_word in matrix_word:
            counter += count_word_in_matrix_word(requested_word, matrix_word)
    return counter

def count_word_in_matrix_word(requested_word, matrix_word):
    """
    This function gets requested word and and matrix's word and check how many
    times requested word appears in the matrix word.
    Return count of appearances.
    """
    count = sum(matrix_word[index:].startswith(requested_word)
                for index in range(len(matrix_word)))
    return count

def get_matrix_u_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in up direction.
    """
    d_words = get_matrix_d_words(matrix)
    u_words = reverse_words(d_words)
    return u_words

def get_matrix_z_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in diagonal down left direction.
    """
    w_words = get_matrix_w_words(matrix)
    z_words = reverse_words(w_words)
    return z_words

def reverse_words(words):
    """
    This function gets words and reverse them.
    Return the reversed words.
    """
    reversed_words = []
    for word in words:
        reversed_word = word[::-1]
        reversed_words.append(reversed_word)
    return reversed_words

def get_matrix_x_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in diagonal up left direction.
    """
    y_words = get_matrix_y_words(matrix)
    x_words = reverse_words(y_words)
    return x_words

def get_matrix_w_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in diagonal up right direction.
    """
    words = []
    if len(matrix) != 0:
        words.extend(get_low_half_matrix_w_words(matrix))
        words.extend(get_high_half_matrix_w_words(matrix))
    return words

def get_low_half_matrix_w_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in diagonal up right direction
     from the low half matrix.
    """
    words = []
    matrix_lines_size = len(matrix)
    matrix_columns_size = len(matrix[0])
    for column_index in range(matrix_columns_size):
        word = get_w_diagonal_word(matrix_lines_size - 1, column_index,
                                   matrix)
        words.append(word)
    return words

def get_high_half_matrix_w_words(matrix):
    """
    This function gets matrix.
    Return all the matrix's words in diagonal up right direction
     from the high half matrix.
    """
    words = []
    matrix_lines_size = len(matrix)
    for line_index in range(matrix_lines_size - 1, -1, -1):
        if line_index != (matrix_lines_size - 1):
            word = get_w_diagonal_word(line_index, 0, matrix)
            words.append(word)
    return words

def get_w_diagonal_word(start_line_index, start_column_index, matrix):
    """
    This function gets index for the start_line, index for the start column
     and matrix.
     Check what are the matrix values (word) from the start index until
     the end of the diagonal in up right direction.
     Return the word.
    """
    word = ""
    matrix_columns_size = len(matrix[0])
    line_index = start_line_index
    column_index = start_column_index
    while line_index >= 0 and column_index < matrix_columns_size:
        word += matrix[line_index][column_index]
        line_index -= 1
        column_index += 1
    return word

def write_output(results, filename):
    """
    This function writes the results to the file.
    """
    with open(filename, 'w') as output_file:
        for word, counter in results:
            output_file.write(word + WORDS_SEPARATOR + str(counter)
                              + LINE_SEPARATOR)

def check_input_args(args):
    """
    This function gets args and checks if the given args are valid.
    """
    if len(args) != EXPECTED_ARGS_NUM:
        return False, NOT_VALID_ARGS_MSG
    words_file_name = args[0]
    matrix_file_name = args[1]
    if not os.path.exists(words_file_name) or not os.path.isfile(
            words_file_name):
        return False, WORDS_FILE_NOT_FOUND_MSG
    if not os.path.exists(matrix_file_name) or not os.path.isfile(
            matrix_file_name):
        return False, MATRIX_FILE_NOT_FOUND_MSG
    chosen_directions = args[3]
    is_valid_direction, msg = check_directions_input(chosen_directions)
    if not is_valid_direction:
        return is_valid_direction, msg
    return True, ""

def check_directions_input(chosen_directions):
    """
    This function checks if the directions are valid.
    """
    for chosen_direction in chosen_directions:
        if chosen_direction not in OPTIONAL_DIRECTIONS:
            return False, NOT_VALID_DIRECTIONS_MSG
    return True, ""

def game_starter():
    """
    This function initialize and start the game .
    """
    args = sys.argv
    args.pop(0)
    are_valid_args, args_msg = check_input_args(args)
    if are_valid_args:
        words_list, matrix, output_file_name, directions = \
            initialize_game_parameters(args)
        if len(matrix) != 0:
            results = find_words(words_list, matrix, directions)
            write_output(results, output_file_name)
        else:
            write_output([], output_file_name)
    else:
        print(args_msg)

def initialize_game_parameters(args):
    """
    This function gets args and initialize game's parameters:
    words file, matrix file, output file name and directions.
    Return the games parameters.
    """
    words_file_name = args[0]
    matrix_file_name = args[1]
    output_file_name = args[2]
    directions = args[3]
    directions = "".join(set(directions))
    words_list = read_wordlist(words_file_name)
    matrix = read_matrix(matrix_file_name)
    return words_list, matrix, output_file_name, directions

if __name__ == "__main__":
    game_starter()
