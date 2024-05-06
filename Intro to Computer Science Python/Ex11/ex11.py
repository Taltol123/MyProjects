from itertools import combinations

NOT_VALID_DEPTH_MSG = "depth size is not valid"
DUPLICATED_SYMPTOMS_MSG = "Symptoms list have duplicated symptoms"
NOT_VALID_SYMPTOMS_MSG = "symptoms not a string list"
NOT_VALID_RECORDS_MSG = "records not a records list"
NO_RECORDS_MSG = "There are no records"

class Node:
    def __init__(self, data, positive_child=None, negative_child=None):
        self.data = data
        self.positive_child = positive_child
        self.negative_child = negative_child


class Record:
    def __init__(self, illness, symptoms):
        self.illness = illness
        self.symptoms = symptoms


def parse_data(filepath):
    with open(filepath) as data_file:
        records = []
        for line in data_file:
            words = line.strip().split()
            records.append(Record(words[0], words[1:]))
        return records


class Diagnoser:

    def __init__(self, root):
        self.root = root

    def diagnose(self, symptoms):
        """
        This function gets symptoms list and check which illness have
         those symptoms
        :return: the illness
        """
        return self.__diagnose_helper(symptoms, self.root)

    def __diagnose_helper(self, symptoms, node):
        if not node.positive_child and not node.negative_child:
            return node.data
        if node.data in symptoms:
            return self.__diagnose_helper(symptoms, node.positive_child)
        else:
            return self.__diagnose_helper(symptoms, node.negative_child)

    def calculate_success_rate(self, records):
        """
        This function gets records list and calculate the success rate for all
        the records. success is when the diagnose for record is the correct
         illness
        :return: success rate
        """
        if len(records) == 0:
            raise ValueError(NOT_VALID_RECORDS_MSG)
        success_counter = 0
        for record in records:
            illness = self.diagnose(record.symptoms)
            if illness == record.illness:
                success_counter += 1

        return success_counter / len(records)

    def all_illnesses(self):
        """
        This function returns all the tree illnesses by the illnesses incidence
        """
        illnesses = self.__all_illnesses_helper({}, self.root)
        sorted_illnesses = sorted(illnesses.items(),
                                  key=lambda kv: kv[1], reverse=True)
        return list(map(lambda illness: illness[0], sorted_illnesses))

    def __all_illnesses_helper(self, illnesses, node):
        """
        This function helps the function all_illnesses to check which
         illnesses the tree have.
        """
        if not node.negative_child and not node.positive_child:
            if node.data:
                if illnesses.get(node.data):
                    illnesses[node.data] = illnesses[node.data] + 1
                else:
                    illnesses[node.data] = 1
        if node.negative_child:
            self.__all_illnesses_helper(illnesses, node.negative_child)
        if node.positive_child:
            self.__all_illnesses_helper(illnesses, node.positive_child)
        return illnesses

    def paths_to_illness(self, illness):
        """
        This function gets illness and check what are the paths to the
         leafs with this illness
        :return: the paths represents by true- yes answer and false- no answer.
        """
        return self.__paths_to_illness_helper(illness, [], [], self.root)

    def __paths_to_illness_helper(self, illness, path, paths, node):
        """
        This function helps to the function paths_to_illness to check what are
        the paths to the leafs with this illness
        """
        if not node.negative_child and not node.positive_child:
            if node.data == illness:
                paths.append(path[:])
        else:
            if node.negative_child:
                false_path = path[:]
                false_path.append(False)
                self.__paths_to_illness_helper(
                    illness, false_path, paths, node.negative_child)
            if node.positive_child:
                true_path = path[:]
                true_path.append(True)
                self.__paths_to_illness_helper(
                    illness, true_path, paths, node.positive_child)

        return paths

    def minimize(self, remove_empty=False):
        self.__minimize_helper(self.root)


    def __minimize_helper(self, node):
        if not node.negative_child and not node.positive_child:
            return

        self.__minimize_helper(node.negative_child)

        if self.__check_unnecessary_nodes(node):
            if Diagnoser(node.positive_child).all_illnesses() == Diagnoser(node.negative_child).all_illnesses():
                self.__remove_node(node, self.root, node)

        self.__minimize_helper(node.positive_child)


    def __check_unnecessary_nodes(self, node):
        if not node.negative_child.negative_child and not node.positive_child.positive_child:
            return True
        if node.positive_child.data == node.negative_child.data:
            return self.__check_unnecessary_nodes(node.positive_child) and self.__check_unnecessary_nodes(node.negative_child)
        else:
            return False


    def __remove_node(self,remove_node, node, father):
        if node is remove_node:
            if node is self.root:
                self.root = node.negative_child
            else:
                father.negative_child = node.negative_child
        else:
            if node.negative_child:
                self.__remove_node(remove_node, node.negative_child, node)
            if node.positive_child:
                self.__remove_node(remove_node, node.positive_child, node)


def build_tree(records, symptoms):
    """
    This function gets records and symptoms and build tree by those parameters.
    :return: Diagnoser object
    """
    __build_tree_validation(records, symptoms)
    root = build_tree_helper(records, symptoms, [])
    return Diagnoser(root)

def __build_tree_validation(records, symptoms):
    if not type(symptoms) == list or not __check_string_list_type(symptoms):
        raise TypeError(NOT_VALID_SYMPTOMS_MSG)
    if not type(records) == list or not __check_records_list_type(records):
        raise TypeError(NOT_VALID_RECORDS_MSG)

def __check_string_list_type(symptoms):
    for symptom in symptoms:
        if not type(symptom) == str:
            return False
    return True

def __check_records_list_type(records):
    for record in records:
        if not type(record) == Record:
            return False
    return True

def build_tree_helper(records, symptoms, path):
    """
    This function helps the function build_tree to build the tree
    and return the tree's root.
    """
    if len(symptoms) == 0:
        return Node(__get_common_illness(path, records), None, None)

    path_with_symptom = path[:]
    path_with_symptom.append((True, symptoms[0]))

    path_without_symptom = path[:]
    path_without_symptom.append((False, symptoms[0]))

    yes_child = build_tree_helper(records, symptoms[1:], path_with_symptom)
    no_child = build_tree_helper(records, symptoms[1:], path_without_symptom)

    return Node(symptoms[0], yes_child, no_child)


def __get_common_illness(path, records):
    """
    This function gets path and records and check what is the common illness
    which match the path symptoms
    :return: the common illness which match the path symptoms
    """
    match_records = __get_match_records(path, records)

    if not match_records:
        return None
    else:
        sorted_records = sorted(match_records.items(), key=lambda kv: kv[1])
        return sorted_records[-1][0]


def __get_match_records(path, records):
    """
    This function gets path and records and check which record's symptoms match
    the path symptoms.
    :return: all the records that match the path symptoms.
    """
    match_records = {}
    for record in records:
        if __is_match_record(path, record):
            if match_records.get(record.illness):
                match_records[record.illness] = match_records[
                                                    record.illness] + 1
            else:
                match_records[record.illness] = 1
    return match_records


def __is_match_record(path, record):
    """
    This function gets path and record and checks if the record symptoms
    match the path which represent the illness symptom.
    :return: true if the record match the path or false otherwise.
    """
    for path_index in path:
        answer = path_index[0]
        symptom = path_index[1]
        if answer:
            if symptom not in record.symptoms:
                return False
        else:
            if symptom in record.symptoms:
                return False
    return True


def optimal_tree(records, symptoms, depth):
    """
    This function gets records, symptoms and depth and checks which tree with
    this depth of symptoms as the highest success rate.
    :return: diagnoser object with the highest success rate tree.
    """
    __optimal_tree_validation(records, symptoms, depth)
    symptoms_combinations = __get_symptoms_combinations(symptoms, depth)
    diagnosers = __get_trees(symptoms_combinations, records)
    return __get_highest_success_rate_tree(diagnosers, records)

def __get_symptoms_combinations(symptoms, depth):
    """
    This function gets symptoms and depth and checks what are the possible
    symptoms combinations which their length are septh size.
    :return: symptoms combinations
    """
    symptoms_combinations = []
    for combination in combinations(symptoms, depth):
        symptoms_combinations.append(list(combination))
    return symptoms_combinations

def __get_trees(symptoms_combinations, records):
    """
    This function gets symptoms combinations and records and build tree
     for each combination.
    :return: diagnoser for each tree.
    """
    diagnosers = []
    for symptoms_combination in symptoms_combinations:
        tree = build_tree(records, symptoms_combination)
        diagnosers.append(tree)
    return diagnosers

def __get_highest_success_rate_tree(diagnosers, records):
    """
    This function gets diagnosres and records and check which tree has the
    highest success rate.
    :return: the diagnoser with the highest success rate tree.
    """
    best_diagnoser = None
    max_rate = 0
    for diagnoser in diagnosers:
        rate = diagnoser.calculate_success_rate(records)
        if max_rate < rate:
            max_rate = rate
            best_diagnoser = diagnoser
    return best_diagnoser

def __optimal_tree_validation(records, symptoms, depth):
    """
    This function gets records, symptoms and depth and check validation for
    those parameters.
    """
    __build_tree_validation(records, symptoms)
    if len(symptoms) < depth < 0:
        raise ValueError(NOT_VALID_DEPTH_MSG)
    if not len(symptoms) == len(set(symptoms)):
        raise ValueError(DUPLICATED_SYMPTOMS_MSG)
