import sys
from board import Board
from car import Car
import helper

OPTIONAL_CAR_NAMES = ['Y', 'B', 'O', 'G', 'W', 'R']
OPTIONAL_ORIENTATION = [0, 1]
MAX_CAR_LENGTH = 4
MIN_CAR_LENGTH = 2
STOP_CHAR = '!'


class Game:
    """
    Manages the game by some rules until winning or until the player decides
     to finish the game earlier.
    """

    __NOT_OPTIONAL_MOVE_MSG = "Sorry, this move can not occur\n"
    __OPTIONAL_DIRECTIONS = ['u', 'd', 'r', 'l']
    __NOT_OPTIONAL_DIRECTION_MSG = "This direction is not an option\n"
    __NOT_OPTIONAL_NAME_MSG = "This name is not an option\n"
    __INPUT_MSG = "Choose a car to move and direction to move the car." \
                  "\n ! to finish the game\n"
    __NOT_VALID_INPUT_MSG = "This input is not a valid one\n"
    __DIRECTION_NOT_VALID_MSG = "This is not an optional direction\n"
    __INPUT_SPLITTER = ","
    __NUM_INPUT_PARAMETERS = 2
    __WINNING_MSG = "Yow win!!!"

    def __init__(self, board):
        """
        Initialize a new Game object.
        :param board: An object of type board
        """
        self.__board = board

    def __single_turn(self):
        """
        Note - this function is here to guide you and it is *not mandatory*
        to implement it. 

        The function runs one round of the game :
            1. Get user's input of: what color car to move, and what 
                direction to move it.
            2. Check if the input is valid.
            3. Try moving car according to user's input.

        Before and after every stage of a turn, you may print additional 
        information for the user, e.g., printing the board. In particular,
        you may support additional features, (e.g., hints) as long as they
        don't interfere with the API.
        """
        pass

    def play(self):
        """
        The main driver of the Game. Manages the game until completion.
        :return: None
        """
        while not self.__is_winning():
            print(self.__board)
            user_input = input(self.__INPUT_MSG)
            if user_input == STOP_CHAR:
                break
            if not self.__check_input_pattern(user_input):
                print(self.__NOT_VALID_INPUT_MSG)
                continue
            car_name, direction = tuple(
                user_input.split(self.__INPUT_SPLITTER))
            if not self.__check_input_validation(car_name, direction):
                continue
            if not self.__board.move_car(car_name, direction):
                print(self.__NOT_OPTIONAL_MOVE_MSG)
                continue
        if self.__is_winning():
            print(self.__WINNING_MSG)

    def __check_input_validation(self, car_name, direction):
        """
        This functions get the input parameters: car name and directions
        and check if the parameters  fit to the game rules
        :return: true if the parameters fit rules and false otherwise
        """
        if car_name not in OPTIONAL_CAR_NAMES:
            print(self.__NOT_OPTIONAL_NAME_MSG )
            return False
        if direction not in self.__OPTIONAL_DIRECTIONS:
            print(self.__NOT_OPTIONAL_DIRECTION_MSG )
            return False
        return True

    def __check_input_pattern(self, input):
        """
        This function checks if the user input is by the pattern string,string
        :return: true if the input as the pattern and false otherwise.
        """
        if self.__INPUT_SPLITTER in input:
            split_result = input.split(self.__INPUT_SPLITTER)
            if len(split_result) == self.__NUM_INPUT_PARAMETERS:
                return True
        return False

    def __is_winning(self):
        """
        This function checks if the player win -> if there is a car on the exit
        :return: true if it is a win and false otherwise.
        """
        target_cell = self.__board.target_location()
        target_cell_content = self.__board.cell_content(target_cell)
        if target_cell_content is not None:
            return True
        return False


def initialize_board(cars_details):
    """
    This function gets cars details and creates a board with the valid cars.
    return the bord.
    """
    game_board = Board()
    for car_name in cars_details:
        car_length = cars_details[car_name][0]
        car_location = cars_details[car_name][1]
        car_orientation = cars_details[car_name][2]
        if add_car_validation_check(car_name, car_length, car_orientation):
            board_car = Car(car_name, car_length, car_location,
                            car_orientation)
            game_board.add_car(board_car)
    return game_board


def add_car_validation_check(car_name, car_length, car_orientation):
    """
    This function gets car name, car length and car orientation and check if
     the car is legal by the game rules.
    :return: true if it legal car and false otherwise.
    """
    if car_name not in OPTIONAL_CAR_NAMES:
        return False

    if car_length > MAX_CAR_LENGTH or car_length < MIN_CAR_LENGTH:
        return False

    if car_orientation not in OPTIONAL_ORIENTATION:
        return False

    return True


if __name__ == "__main__":
    car_config_file = sys.argv[1]
    cars_details = helper.load_json(car_config_file)
    game_board = initialize_board(cars_details)
    game = Game(game_board)
    game.play()
