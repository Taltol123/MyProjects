#################################################################
# FILE : ex4.py
# WRITER : Tal Yehezkel , taltol , 318911088
# EXERCISE : intro2cse ex4 2021
#################################################################

import hangman_helper

BLANK_LETTER = "_"
ALREADY_CHOSEN_MSG = "The letter you entered was already chosen."
NOT_LETTER_MSG = "The input is not a letter."
Lose_MSG = "Sorry, you lost the game, the word was: "
Win_MSG = "Congratulations, you won!"
WINNER_PLAY_AGAIN_MSG = ".Want to continue??"
Loser_PLAY_AGAIN_MSG = "Do you want to play another game?"
INVALID_INPUT = "This input is invalid"
DEFAULT_MSG = ""


def run_single_game(words_list, score):
    """
    This function gets list of words,
    Manage a single game,
    Return the final score
    """
    wrong_guesses = []
    previous_inputs = []
    word = hangman_helper.get_random_word(words_list)
    pattern = BLANK_LETTER * len(word)
    msg = DEFAULT_MSG
    while score > 0 and pattern != word:
        hangman_helper.display_state(pattern, wrong_guesses, score, msg)
        input_kind, user_input = hangman_helper.get_input()
        if input_kind == hangman_helper.LETTER:
            score, pattern, wrong_guesses, msg = \
                letter_input_handler(score, word, user_input, wrong_guesses,
                                     pattern, previous_inputs)
        elif input_kind == hangman_helper.WORD:
            score, pattern, msg = \
                word_input_handler(score, word, user_input, pattern)
        elif input_kind == hangman_helper.HINT:
            score, hints = hint_input_handler(words_list, score, wrong_guesses,
                                              pattern)
            hangman_helper.show_suggestions(hints)
        previous_inputs.append(user_input)
    msg = score_checker(score, word)
    hangman_helper.display_state(pattern, wrong_guesses, score, msg)
    return score


def letter_input_handler(score, word, letter_input, wrong_guesses, pattern,
                         previous_inputs):
    """
    This function gets the word, letter input, wrong guesses pattern and
    previous inputs.
    Update the game parameters - pattern, wrong_guesses, msg.
    Return the updated game parameters.
    """
    is_letter_valid, msg = is_valid_letter_input(letter_input, previous_inputs)
    if is_letter_valid:
        score -= 1
        msg = DEFAULT_MSG
        num_letter_in_word = word.count(letter_input)
        if num_letter_in_word > 0:
            pattern = update_word_pattern(word, pattern, letter_input)
            score += num_letter_in_word * (num_letter_in_word+1) // 2
        else:
            wrong_guesses.append(letter_input)
    return score, pattern, wrong_guesses, msg

def is_valid_letter_input(letter_input, previous_inputs):
    """
    This function gets letter input and the previous_inputs.
    Check if the letter is valid.
    Return true if it is a valid letter and false otherwise.
    """
    if not letter_input.islower():
        return False, INVALID_INPUT
    elif len(letter_input) > 1 or not letter_input.isalpha():
        return False, NOT_LETTER_MSG
    elif letter_input in previous_inputs:
        return False, ALREADY_CHOSEN_MSG
    else:
        return True, DEFAULT_MSG

def update_word_pattern(word, pattern, letter):
    """
    This function gets word, pattern and letter.
    Update the pattern - add the letter to the pattern.
    Return the updated pattern.
    """
    if letter in word:
        pattern = list(pattern)
        num_letters_in_word = word.count(letter)
        previous_letter_index = -1
        for i in range(num_letters_in_word):
            previous_letter_index = word.index(letter,
                                               previous_letter_index + 1,
                                               len(word))
            pattern[previous_letter_index] = letter
    pattern = ''.join(pattern)
    return pattern

def word_input_handler(score, word, word_input, pattern):
    """
    This function gets the word, word input, wrong guesses and pattern.
    Update the game parameters - pattern, wrong_guesses, msg.
    Return the updated game parameters.
    """
    msg = DEFAULT_MSG
    score -= 1
    if word_input == word:
        guess_letters_num = pattern.count(BLANK_LETTER)
        score += guess_letters_num * (guess_letters_num+1) // 2
        pattern = word
    return score, pattern, msg

def hint_input_handler(words, score, wrong_guesses, pattern):
    """
    This function gets the score, word, wrong guesses and pattern.
    Update the score and check which hints matches.
    Return the updated score and matches hints.
    """
    hint_length = hangman_helper.HINT_LENGTH
    score -= 1
    hints_options = filter_words_list(words, pattern, wrong_guesses)
    if len(hints_options) > hint_length:
        hints = [hints_options[index * len(hints_options) //
                               hint_length] for index in range(hint_length)]
    else:
        hints = hints_options
    return score, hints

def score_checker(score, word):
    """
    This function get score and word.
    Check if the player won or lose.
    Return a msg which asy if the player won or lose
    """
    if score == 0:
        return Lose_MSG + word
    else:
        return Win_MSG

def filter_words_list(words, pattern, wrong_guess_lst):
    """
    This function get words, pattern and wrong guess list.
    Check which words matches to the pattern and the wrong guess list.
    Return the matches words.
    """
    matches_words = []
    for word in words:
        if len(word) == len(pattern):
            if is_word_match_pattern(word, pattern, wrong_guess_lst):
                matches_words.append(word)
    return matches_words

def is_word_match_pattern(word, pattern, wrong_guess_lst):
    """
    This function gets the word, pattern and wrong guess list.
    Check if the word match the pattern and the wrong guess list.
    Return true if the word match and false otherwise.
    """
    if are_letters_match_pattern(word, pattern):
        if not any_letter_in_wrong_guess(word, wrong_guess_lst):
            return True
    else:
        return False

def are_letters_match_pattern(word, pattern):
    """
    This function gets word and pattern.
    Check if the word match the non blank letters in the pattern and that
    there isn't a letter in the pattern which in the word at different indexes.
    Return true if the word match the pattern and false otherwise.
    """
    index = 0
    for pattern_index_value in pattern:
        if pattern_index_value != BLANK_LETTER:
            if word[index] != pattern_index_value:
                return False
        else:
            if word[index] in pattern:
                return False
        index += 1
    return True

def any_letter_in_wrong_guess(word, wrong_guess_lst):
    """
    This function gets the word and wrong guess list.
    Check if any of the word's letters are in the wrong guess list.
    Return true if there is a letter in the wrong guess list or false otherwise
    """
    for letter in word:
        if letter in wrong_guess_lst:
            return True
    return False

def run_games(words_list, score, start_game):
    """
    This function gets words list and initial score.
    Manage the games until the player don't want another game.
    """
    num_games = 0
    while start_game:
        num_games += 1
        score = run_single_game(words_list, score)
        if score > 0:
            msg = ("You played so far: {0} games. your current score: {1} {2}"
                   .format(num_games, score, WINNER_PLAY_AGAIN_MSG))
            start_game = hangman_helper.play_again(msg)
        else:
            start_game = hangman_helper.play_again("Until you lost you played "
                                                   + str(num_games) + "games "
                                                   + Loser_PLAY_AGAIN_MSG)
            num_games = 0
            score = hangman_helper.POINTS_INITIAL

def main():
    """
    This function initialize the words list and the score and starts the games.
    """
    words_list = hangman_helper.load_words("words.txt")
    score = hangman_helper.POINTS_INITIAL
    start_game = True
    run_games(words_list, score, start_game)

if __name__ == "__main__":
    main()
