class Board:
    """
    Responsible of the movements in the board
     and the status of each cell in board
    """
    __BOARD_LENGTH = 7
    __BOARD_WIDTH = 7
    __EXIT_COL = (3, 7)
    __TARGET_SYMBOL = 'E'
    __EMPTY_CELL_SYMBOL = '_'
    __EDGE_SYMBOL = '*'

    def __init__(self):
        self.__cars = {}

    def __str__(self):
        """
        This function is called when a board object is to be printed.
        :return: A string of the current status of the board
        """
        board_representation = ""
        for row_index in range(-1, self.__BOARD_LENGTH + 1):
            board_representation += "\n"
            for col_index in range(-1, self.__BOARD_WIDTH + 1):
                board_representation += self.__cel_representation(row_index,
                                                                  col_index)
        return board_representation

    def __cel_representation(self, row, col):
        """
        This function gets a cell indexes: row and col
        :return: the representation for the cell
        """
        if (row, col) == self.target_location():
            return self.__TARGET_SYMBOL
        if col == -1 or row == -1 or row == self.__BOARD_LENGTH \
                or col == self.__BOARD_WIDTH:
            return self.__EDGE_SYMBOL
        cell_content = self.cell_content((row, col))
        if cell_content:
            return cell_content
        else:
            return self.__EMPTY_CELL_SYMBOL

    def cell_list(self):
        """ This function returns the coordinates of cells in this board
        :return: list of coordinates
        """
        cells = []
        for row_index in range(self.__BOARD_LENGTH):
            for col_index in range(self.__BOARD_WIDTH):
                cells.append((row_index, col_index))
        cells.append(self.__EXIT_COL)
        return cells

    def possible_moves(self):
        """ This function returns the legal moves of all cars in this board
        :return: list of tuples of the form (name,movekey,description)
                 representing legal moves
        """
        legal_moves = []
        for car_name in self.__cars:
            car_possible_moves = self.__car_possible_moves(
                self.__cars[car_name])
            if car_possible_moves:
                legal_moves.extend(car_possible_moves)
        return legal_moves

    def __car_possible_moves(self, car):
        """
        This function gets a car and checks what are the options for the car
         to move on bord.
        :return: list of the possible moves
        """
        legal_moves = []
        optionals_directions = car.possible_moves()
        if not optionals_directions:
            return legal_moves
        for optional_direction in optionals_directions:
            if self.__is_optional_car_direction(car, optional_direction):
                description = car.get_name() + " can move  " \
                              + optional_direction
                legal_moves.append(
                    (car.get_name(), optional_direction, description))
        return legal_moves

    def __is_optional_car_direction(self, car, optional_direction):
        """
        This function gets car and optional direction and checks if the
         direction is an optional movement for the car in board.
        :return: true if it is an optional direction and false otherwise.
        """
        optional_cells = car.movement_requirements(optional_direction)
        for cell in optional_cells:
            if not self.__is_board_coordinate(cell):
                return False
            if self.cell_content(cell):
                return False
        return True

    def target_location(self):
        """
        This function returns the coordinates of the location
         which is to be filled for victory.
        :return: (row,col) of goal location
        """
        return self.__EXIT_COL

    def cell_content(self, coordinate):
        """
        Checks if the given coordinates are empty.
        :param coordinate: tuple of (row,col) of the coordinate to check
        :return: The name if the car in coordinate, None if empty
        """
        for car_name in self.__cars:
            car_coordinates = self.__cars[car_name].car_coordinates()
            for car_coordinate in car_coordinates:
                if car_coordinate == coordinate:
                    return car_name
        return None

    def add_car(self, car):
        """
        Adds a car to the game.
        :param car: car object of car to add
        :return: True upon success. False if failed
        """
        car_coordinates = car.car_coordinates()
        if len(car_coordinates) == 0:
            return False
        if self.__cars.get(car.get_name()):
            return False
        if not self.__is_board_coordinates(car_coordinates):
            return False
        if not self.__are_coordinates_empty(car_coordinates):
            return False
        self.__cars[car.get_name()] = car
        return True

    def __are_coordinates_empty(self, coordinates):
        """
        This function gets coordinates and check if they empty.
        :return: true if the coordinates are empty and false otherwise.
        """
        for coordinate in coordinates:
            if self.cell_content(coordinate) is not None:
                return False
        return True

    def move_car(self, name, movekey):
        """
        moves car one step in given direction.
        :param name: name of the car to move
        :param movekey: Key of move in car to activate
        :return: True upon success, False otherwise
        """
        car_to_move = self.__cars.get(name)
        if not car_to_move:
            return False
        possible_moves = self.__car_possible_moves(car_to_move)
        for possible_move in possible_moves:
            possible_direction = possible_move[1]
            if possible_direction == movekey:
                car_to_move.move(movekey)
                return True
        return False

    def __is_board_coordinates(self, coordinates):
        """
        This function checks if coordinates exist in board.
        :return: true if all the coordinates exist in board an false otherwise.
        """
        for coordinate in coordinates:
            if not self.__is_board_coordinate(coordinate):
                return False
        return True

    def __is_board_coordinate(self, coordinate):
        """
        This function checks if a single coordinate exist in board.
        :return: true if the coordinate exist in board an false otherwise.
        """
        board_coordinates = self.cell_list()
        if coordinate not in board_coordinates:
            return False
        return True
