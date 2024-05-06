class Car:
    """
    Represent the car's attributes and movements
    """

    __VERTICAL = 0
    __HORIZONTAL = 1
    __UP = 'u'
    __UP_DESCRIPTION = "cause vertical car to go up one step"
    __DOWN = 'd'
    __DOWN_DESCRIPTION = "cause vertical car to go up one step"
    __LEFT = 'l'
    __LEFT_DESCRIPTION = "cause horizontal car to go left one step"
    __RIGHT = 'r'
    __RIGHT_DESCRIPTION = "cause horizontal car to go right one step"
    __NUM_MOVES = 1

    def __init__(self, name, length, location, orientation):
        """
        A constructor for a Car object
        :param name: A string representing the car's name
        :param length: A positive int representing the car's length.
        :param location: A tuple representing the car's head (row, col) location
        :param orientation: One of either 0 (VERTICAL) or 1 (HORIZONTAL)
        """
        self.__name = name
        self.__length = length
        self.__location = location
        self.__orientation = orientation

    def car_coordinates(self):
        """
        :return: A list of coordinates the car is in
        """
        coordinates = []
        for index in range(self.__length):
            if self.__orientation == self.__VERTICAL:
                coordinates.append(
                    (self.__location[0] + index, self.__location[1]))

            elif self.__orientation == self.__HORIZONTAL:
                coordinates.append(
                    (self.__location[0], self.__location[1] + index))

        return coordinates

    def possible_moves(self):
        """
        :return: A dictionary of strings describing possible movements
         permitted by this car.
        """
        if self.__orientation == self.__VERTICAL:
            return {
                self.__UP: self.__UP_DESCRIPTION,
                self.__DOWN: self.__DOWN_DESCRIPTION
            }
        elif self.__orientation == self.__HORIZONTAL:
            return {
                self.__RIGHT: self.__RIGHT_DESCRIPTION,
                self.__LEFT: self.__LEFT_DESCRIPTION
            }
        else:
            return {}

    def movement_requirements(self, movekey):
        """ 
        :param movekey: A string representing the key of the required move.
        :return: A list of cell locations which must be empty in order for this
         move to be legal.
        """
        empty_cells = []
        coordinates = self.car_coordinates()
        last_coordinate = coordinates[-1]
        first_coordinate = coordinates[0]

        if movekey == self.__UP:
            empty_cells.append((first_coordinate[0] - self.__NUM_MOVES,
                                first_coordinate[1]))

        elif movekey == self.__DOWN:
            empty_cells.append((last_coordinate[0] + self.__NUM_MOVES,
                                last_coordinate[1]))

        elif movekey == self.__LEFT:
            empty_cells.append(
                (first_coordinate[0], first_coordinate[1] - self.__NUM_MOVES))

        elif movekey == self.__RIGHT:
            empty_cells.append(
                (last_coordinate[0], last_coordinate[1] + self.__NUM_MOVES))

        return empty_cells

    def move(self, movekey):
        """ 
        :param movekey: A string representing the key of the required move.
        :return: True upon success, False otherwise
        """
        if movekey not in self.possible_moves():
            return False
        else:
            if movekey == self.__UP:
                self.__location = (
                    self.__location[0] - self.__NUM_MOVES, self.__location[1])

            elif movekey == self.__DOWN:
                self.__location = (
                    self.__location[0] + self.__NUM_MOVES, self.__location[1])

            elif movekey == self.__LEFT:
                self.__location = (
                    self.__location[0], self.__location[1] - self.__NUM_MOVES)

            elif movekey == self.__RIGHT:
                self.__location = (
                    self.__location[0], self.__location[1] + self.__NUM_MOVES)
            return True

    def get_name(self):
        """
        :return: The name of this car.
        """
        return self.__name
