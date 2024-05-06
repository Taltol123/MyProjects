from __future__ import annotations
import numpy as np
import pandas as pd
import sklearn
from matplotlib import pyplot as plt
from sklearn import datasets
from sklearn.model_selection import train_test_split

from IMLearn.metrics import mean_square_error
from IMLearn.utils import split_train_test
from IMLearn.model_selection import cross_validate
from IMLearn.learners.regressors import PolynomialFitting, LinearRegression, RidgeRegression
from sklearn.linear_model import Lasso

from utils import *
import plotly.graph_objects as go
from plotly.subplots import make_subplots


def select_polynomial_degree(n_samples: int = 100, noise: float = 5):
    """
    Simulate data from a polynomial model and use cross-validation to select the best fitting degree

    Parameters
    ----------
    n_samples: int, default=100
        Number of samples to generate

    noise: float, default = 5
        Noise level to simulate in responses
    """
    # Question 1 - Generate dataset for model f(x)=(x+3)(x+2)(x+1)(x-1)(x-2) + eps for eps Gaussian noise
    # and split into training- and testing portions
    raise NotImplementedError()


    # Question 2 - Perform CV for polynomial fitting with degrees 0,1,...,10
    raise NotImplementedError()

    # Question 3 - Using best value of k, fit a k-degree polynomial model and report test error
    raise NotImplementedError()


def select_regularization_parameter(n_samples: int = 50, n_evaluations: int = 500):
    """
    Using sklearn's diabetes dataset use cross-validation to select the best fitting regularization parameter
    values for Ridge and Lasso regressions

    Parameters
    ----------
    n_samples: int, default=50
        Number of samples to generate

    n_evaluations: int, default = 500
        Number of regularization parameter values to evaluate for each of the algorithms
    """
    # Question 1 - Load diabetes dataset and split into training and testing portions
    X, y = datasets.load_diabetes(return_X_y=True)
    indices = np.random.permutation(X.shape[0])
    train_X = X[indices[:n_samples]]
    train_y = y[indices[:n_samples]]
    test_X = X[indices[n_samples:]]
    test_y = y[indices[n_samples:]]


    # Question 2 - Perform CV for different values of the regularization parameter for Ridge and Lasso regressions
    ridge_reg_param = np.linspace(0.001, 0.5, num=n_evaluations)

    ridge_train_scores = np.zeros(n_evaluations)
    ridge_validation_scores = np.zeros(n_evaluations)

    for i, lam in enumerate(ridge_reg_param):
        res = cross_validate(RidgeRegression(lam), train_X, train_y, mean_square_error)
        ridge_train_scores[i] = res[0]
        ridge_validation_scores[i] = res[1]


    lasso_reg_param = np.linspace(.001, 2, num=n_evaluations)
    lasso_train_scores = np.zeros(n_evaluations)
    lasso_validation_scores = np.zeros(n_evaluations)

    for i, lam in enumerate(lasso_reg_param):
        res = cross_validate(Lasso(lam, max_iter=5000), train_X, train_y, mean_square_error)
        lasso_train_scores[i] = res[0]
        lasso_validation_scores[i] = res[1]


    fig, (ax1, ax2) = plt.subplots(1, 2)
    fig.suptitle('Lasso VS Ridge Train and Validation Errors')

    ax1.set_title("Ridge")
    ax1.plot(ridge_reg_param, ridge_train_scores, label="Ridge Train Error")
    ax1.plot(ridge_reg_param, ridge_validation_scores, label="Ridge Validation Error")
    ax1.set_xlabel("Ridge Regularization Parameter")
    ax1.set_ylabel("Mean Square Error")

    ax2.set_title("Lasso")
    ax2.plot(lasso_reg_param, lasso_train_scores, label="Lasso Train Error")
    ax2.plot(lasso_reg_param, lasso_validation_scores, label="Lasso Validation Error")
    ax2.set_xlabel("Lasso Regularization Parameter")

    fig.legend()
    fig.show()



    # Question 3 - Compare best Ridge model, best Lasso model and Least Squares model

    ridge_best_reg_param = ridge_reg_param[np.argmin(ridge_validation_scores)]
    print(f"Ridge: The best regularization parameter is {ridge_best_reg_param: .3f}\n")
    lasso_best_reg_param = lasso_reg_param[np.argmin(lasso_validation_scores)]
    print(f"Lasso: The best regularization parameter is {lasso_best_reg_param: .3f}\n")


    ridge = RidgeRegression(lam=ridge_best_reg_param).fit(train_X, train_y)
    y_pred = ridge.predict(test_X)
    ridge_loss = mean_square_error(test_y, y_pred)
    print(f"Ridge: The test error with the best regularization parameter is {ridge_loss: .3f}\n")

    lasso = Lasso(lasso_best_reg_param).fit(train_X, train_y)
    y_pred = lasso.predict(test_X)
    lasso_loss = mean_square_error(test_y, y_pred)
    print(f"Lasso: The test error with the best regularization parameter is {lasso_loss: .3f}\n")

    linear_regression = LinearRegression().fit(train_X, train_y)
    y_pred = linear_regression.predict(test_X)
    linear_regression_loss = mean_square_error(test_y, y_pred)
    print(f"Linear regression: The test error is {linear_regression_loss: .3f}\n")


if __name__ == '__main__':
    np.random.seed(0)
    select_regularization_parameter()