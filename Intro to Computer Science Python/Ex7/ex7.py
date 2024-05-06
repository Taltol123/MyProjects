from typing import Any, List, Tuple

def print_to_n(n: int) -> None:
    """
    This function gets n and prints all the numbers from 1 to n.
    """
    if n < 1:
        return
    print_to_n(n - 1)
    print(n)

def digit_sum(n: int) -> int:
    """
    This function gets n.
     Return the sum between all the digits in the number.
    """
    if n < 1:
        return n
    return n % 10 + digit_sum(n // 10)

def is_prime(n: int) -> bool:
    """
    This function gets a number n.
     Return if the number is prime or not.
    """
    if n <= 1:
        return False
    return not _has_divisor_smaller_than(n, n - 1)

def _has_divisor_smaller_than(n: int, i: int) -> bool:
    """
    This function gets n - number and i - optional divisor.
    Return if there is a divisor for the n number.
    """
    if i == 1:
        return False
    if n % i == 0:
        return True
    return _has_divisor_smaller_than(n, i - 1)

def play_hanoi(hanoi: Any, n: int, src: Any, dst: Any, temp: Any) -> None:
    """
    This function gets hanoi object, n-number of disks, src-source rod,
     dst-destination rod and temp road.
     Move all disks from one rod to another.
    """
    if n <= 0:
        return
    play_hanoi(hanoi, n - 1, src, temp, dst)
    hanoi.move(src, dst)
    play_hanoi(hanoi, n - 1, temp, dst, src)

def print_sequences(char_list: List[str], n: int) -> None:
    """
    This function gets a list of chars and number which represent the sequence
     length.
     Call helper function which prints all the optional sequences from the
      char list in n length.
    """
    _print_sequences_helper(char_list, n, "")

def print_no_repetition_sequences(char_list: List[str], n: int) -> None:
    """
    This function gets a list of chars and number which represent the sequence
    length.
     Call helper function which prints all the optional sequences with the
      chars in char list in n length without repetition.
    """
    _print_sequences_helper(char_list, n, "", False)

def _print_sequences_helper(char_list: List[str], n: int, word: str,
                            allow_repetition: bool = True) -> None:
    """
    This function gets a list of chars, number which represent the sequence
     length, word and boolean parameter if chars in sequence can be duplicated.
    Print all the optional sequences with the chars in char list in
     n length with/without repetition.
    """
    if len(word) == n:
        print(word)
        return

    for char in char_list:
        if char not in word or allow_repetition:
            _print_sequences_helper(char_list, n, word + char,
                                    allow_repetition)

def parentheses(n: int) -> List[str]:
    """
    This function gets n - number of parentheses couples to be in each string.
    Call helper function which return the legal parentheses results.
    """
    parentheses_list: List[str] = []
    _parentheses_helper(n, "", parentheses_list)
    return parentheses_list

def _parentheses_helper(n: int, parentheses_string: str,
                        parentheses_list: List[str]) -> None:
    """
    This function gets n - number of parentheses couples to be in each string,
    parentheses string and parentheses list.
    Return a list of all the legal parentheses strings which have equal number
     of open parentheses and closed parentheses while parentheses
      wont closed until it opened.
    """
    if len(parentheses_string) == n * 2:
        parentheses_list.append(parentheses_string)
        return
    if parentheses_string.count("(") <= parentheses_string.count(")"):
        _parentheses_helper(n, parentheses_string + "(", parentheses_list)
    elif (n * 2 - parentheses_string.count("(")) <= (
            parentheses_string.count("(")):
        _parentheses_helper(n, parentheses_string + ")", parentheses_list)
    else:
        for i in "()":
            _parentheses_helper(n, parentheses_string + i, parentheses_list)

def flood_fill(image: List[List[str]], start: Tuple[int, int]) -> None:
    """
    This function gets a matrix of '*' and '.' chars and the start line and
     start column.
     Change the matrix from the start point that all the '.' Linked chars
      would changed to '*' .
    """
    line, column = start
    image[line][column] = "*"

    if image[line + 1][column] == "*" and image[line][column + 1] == "*" and \
            image[line][column - 1] == "*" and image[line - 1][column] == "*":
        return

    if image[line + 1][column] == ".":
        flood_fill(image, (line + 1, column))
    if image[line][column + 1] == ".":
        flood_fill(image, (line, column + 1))
    if image[line][column - 1] == ".":
        flood_fill(image, (line, column - 1))
    if image[line - 1][column] == ".":
        flood_fill(image, (line - 1, column))
