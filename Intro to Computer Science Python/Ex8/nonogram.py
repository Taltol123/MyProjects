#############################################################
# FILE : ex8.py
# WRITERS : Liel Azulay, liel20946, 209462589
# Tal Yehezkel, taltol, 318911088
# EXERCISE : intro2cs2 ex8 2021
# DESCRIPTION :This program solve the nonogram game with backtracking
#############################################################

# when all cells in the current index are either -1 or one of the two (1 or 2)
# we choose the intersection to be the actual value (1 or 2) instead of -1
# the reason for that is to improve the efficiency of our solvers. if we
# were to choose that the intersection value would be -1 we would have to
# do more checks


import copy


def constraint_satisfactions(n, blocks):
    """This function gets a row length and a list of constraints and
    returns all the possible ways to color a row with the constraints"""

    row_option = [0 for _ in range(n)]
    possible_results = []
    constraint_helper(n, blocks, 0, 0, row_option,
                      possible_results)
    return possible_results


def constraint_helper(n, blocks, row_index, block_index, row_option,
                      possible_results):
    """This function helps constraintsatisfactions to find all the
    possible ways to color a line in a specific length with specific
    constraints"""

    if block_index == len(blocks):
        possible_results.append(row_option[:])
        return

    for index in range(row_index, n):
        if index + blocks[block_index] <= n:
            row_option[index:index + blocks[block_index]] = [1] * \
                                                            blocks[block_index]
            constraint_helper(n, blocks, index + blocks[block_index] + 1,
                              block_index + 1, row_option, possible_results)
            row_option[index:] = [0] * (len(row_option) - index)
        else:
            return


def row_variations(row, blocks):
    """This function gets a row and a list of constraints the function
    returns all the possible ways to color the undecided cells of the
    row (cells with -1) in the bound of the constraints"""

    possible_variations = []
    variations_helper(row[:], blocks, 0, 0, possible_variations)
    return possible_variations


def variations_helper(row, blocks, row_index, block_index,
                      possible_variations):
    """This function helps row_variations to find all the possible ways to
    color the undecided cells of the row (cells with -1) in the bound
    of the constraints"""

    if block_index == len(blocks):
        if not is_painted(row, row_index):
            possible_row = row[:]
            zero_undecided_cells(possible_row)
            possible_variations.append(possible_row)
        return

    curr_block = blocks[block_index]

    for index in range(row_index, len(row)):
        if row[index] == -1 or row[index] == 1:
            next_index = index + curr_block
            if next_index <= len(row):
                if is_valid_row(row, index, next_index):
                    start_value = row[index:next_index]
                    row[index:next_index] = [1] * curr_block
                    block_index += 1

                    variations_helper(row, blocks, next_index + 1,
                                      block_index, possible_variations)

                    block_index -= 1  # backtracking
                    row[index:next_index] = start_value

                if row[index] == 1:
                    break
            else:
                break


def is_valid_row(row, curr_index, next_index):
    """This function checks if a constraint is valid in the current
    sate of a row"""

    if next_index < len(row) and row[next_index] == 1:
        return False
    if 0 in row[curr_index: next_index]:
        return False
    return True


def zero_undecided_cells(row):
    """This function gets a row. the function iterate through the row
    (which already have all the constraints) and changes the undecided
    values to 0"""

    for row_index in range(len(row)):
        if row[row_index] == -1:
            row[row_index] = 0


def is_painted(row, start_index):
    """This function gets a row and a starting index. the function checks
    if the row has more colored cells than needed"""

    for index in range(start_index, len(row)):
        if row[index] == 1:
            return True
    return False


def intersection_row(rows):
    """This function gets a list of rows. the function iterate through the
    list and checking if all the cells in a specific index have a pattern
    (have the same value). the function returns a list with the results
    from each index"""

    intersection_list = []
    options = set()
    row_len = len(rows[0])

    for col_index in range(row_len):
        for row_index in range(len(rows)):
            options.add(rows[row_index][col_index])
        intersection_list.append(intersection_checks(list(options)))
        options.clear()
    return intersection_list


def intersection_checks(options):
    """This function gets a set that contains all the values from a specific
    index in all the rows. the function returns returns the value of a pattern
    if it exists or -1 if there isn't a pattern"""

    if len(options) == 1:
        return options[0]
    elif len(options) == 2:
        return intersection_two_options(options)
    else:
        return -1


def intersection_two_options(options):
    """This function checks if a pattern exists when there are only 2
    different values in the values_lst the function will return -1 if there
    isn't a pattern"""

    if -1 in options:
        if options[0] == -1:
            return options[1]
        else:
            return options[0]
    else:
        return -1


def solve_easy_nonogram(constraints):
    """This function mimics a human way of solving a nonogram. the function
    returns the solved board (as much as possible)"""

    rows_changes_indexes = [True] * len(constraints[0])
    col_changes_indexes = [True] * len(constraints[1])
    board = [[-1] * len(constraints[1]) for _ in range(len(constraints[0]))]

    while True in rows_changes_indexes:
        # Handle rows
        change_rows_result = solve_rows(board, constraints,
                                        rows_changes_indexes,
                                        col_changes_indexes)
        if not change_rows_result:
            return None
        rows_changes_indexes = [False] * len(rows_changes_indexes)

        # Handle columns
        change_cols_result = solve_cols(board, constraints,
                                        rows_changes_indexes,
                                        col_changes_indexes)
        if not change_cols_result:
            return None
        col_changes_indexes = [False] * len(col_changes_indexes)

    return board


def solve_rows(board, constraints, rows_changes_indexes, col_changes_indexes):
    """ This function gets the board, constraints and the list that represent
    changes in the columns and changes in the rows. the function paints all
    the row's blocks that must be black or white """

    for row_index in range(len(board)):
        if rows_changes_indexes[row_index]:
            row_options = row_variations(board[row_index],
                                         constraints[0][row_index])
            if not row_options:
                return False
            solve_row(board, row_index, row_options, col_changes_indexes)
    return True


def solve_cols(board, constraints, rows_changes_indexes, col_changes_indexes):
    """ This function gets the board, constraints and the list that represent
    changes in the columns and changes in the rows. the function paints all
    the col's blocks that must be black or white """

    for col_index in range(len(board[0])):
        if col_changes_indexes[col_index]:
            board_col = get_column(board, col_index)
            col_options = row_variations(board_col,
                                         constraints[1][col_index])
            if not col_options:
                return False
            solve_col(board, col_index, col_options, rows_changes_indexes)
    return True


def solve_row(board, row_index, row_options, changed_col_indexes):
    """This function gets the board, possible variations of the row, and
    the list that represent changes in the columns. the function paints rows
    that must be black or white"""

    changed_row = intersection_row(row_options)
    for col_index in range(len(changed_row)):
        if board[row_index][col_index] == -1 and changed_row[col_index] != -1:
            changed_col_indexes[col_index] = 1
            board[row_index][col_index] = changed_row[col_index]


def solve_col(board, col_index, col_options, changed_row_indexes):
    """This function gets the board, possible variations of the column, and
    the list that represent changes in the rows. the function paints cells
    that must be black or white"""

    changed_col = intersection_row(col_options)
    for row_index in range(len(changed_col)):
        if board[row_index][col_index] == -1 and changed_col[row_index] != -1:
            changed_row_indexes[row_index] = 1
            board[row_index][col_index] = changed_col[row_index]


def get_column(board, col_index):
    """This function gets a list containing all the rows of the board and a
    column index. the function returns the column as a list"""

    col = []
    for row_index in range(len(board)):
        col.append(board[row_index][col_index])
    return col


def solve_nonogram(constraints):
    """This function solves harder nonograms. the function uses backtracking
    to solve the nonogram efficiently"""

    solved_boards = []
    board = solve_easy_nonogram(constraints)
    if board is not None:
        undecided_indexes = is_board_unsolved(board)
        if len(undecided_indexes) == 0:
            solved_boards.append(board)
        else:
            solve_hard_helper(board, 0, undecided_indexes, constraints,
                              solved_boards)
    return solved_boards


def is_board_unsolved(board):
    """This function gets board and checks if the board is solved
    (without blank blocks)"""

    undecided_indexes = []
    for row_index in range(len(board)):
        for col_index in range(len(board[0])):
            if board[row_index][col_index] == -1:
                undecided_indexes.append((row_index, col_index))

    return undecided_indexes


def solve_hard_helper(board, index, undecided_lst, constraints,
                      solved_boards):
    """This function helps solve nonogram to solve harder
    nonograms efficiently"""

    if index == len(undecided_lst):
        solved_boards.append(copy.deepcopy(board))
        return

    curr_row, curr_col = undecided_lst[index][0], undecided_lst[index][1]
    for optional_val in [1, 0]:
        board[curr_row][curr_col] = optional_val
        board_col = get_column(board, curr_col)
        if variation_exist(board[curr_row], constraints[0][curr_row], 0, 0) \
                and variation_exist(board_col, constraints[1][curr_col],
                                    0, 0):
            solve_hard_helper(board, index + 1, undecided_lst, constraints,
                              solved_boards)

    board[curr_row][curr_col] = -1


def variation_exist(row, blocks, row_index, block_index):
    """This function checks if a row has at least 1 possible variation the
    function uses the same pattern from row_variation but without actually
    change the row (to increase efficiency)"""

    if block_index == len(blocks):
        if is_painted(row, row_index):
            return False
        else:
            return True

    curr_block = blocks[block_index]

    for index in range(row_index, len(row)):
        if row[index] == -1 or row[index] == 1:
            next_index = index + curr_block
            if next_index <= len(row):
                if is_valid_row(row, index, next_index):
                    block_index += 1
                    if variation_exist(row, blocks, next_index + 1,
                                       block_index):
                        return True
                    block_index -= 1  # backtracking

                if row[index] == 1:
                    return False
            else:
                return False

    return False
