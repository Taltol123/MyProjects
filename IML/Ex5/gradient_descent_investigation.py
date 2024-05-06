import numpy as np
import pandas as pd
from typing import Tuple, List, Callable, Type

from matplotlib import pyplot as plt

from IMLearn import BaseModule
from IMLearn.desent_methods import GradientDescent, FixedLR, ExponentialLR
from IMLearn.desent_methods.modules import L1, L2
from IMLearn.learners.classifiers.logistic_regression import LogisticRegression
from IMLearn.model_selection import cross_validate
from IMLearn.utils import split_train_test

import plotly.graph_objects as go


def plot_descent_path(module: Type[BaseModule],
                      descent_path: np.ndarray,
                      title: str = "",
                      xrange=(-1.5, 1.5),
                      yrange=(-1.5, 1.5)) -> go.Figure:
    """
    Plot the descent path of the gradient descent algorithm

    Parameters:
    -----------
    module: Type[BaseModule]
        Module type for which descent path is plotted

    descent_path: np.ndarray of shape (n_iterations, 2)
        Set of locations if 2D parameter space being the regularization path

    title: str, default=""
        Setting details to add to plot title

    xrange: Tuple[float, float], default=(-1.5, 1.5)
        Plot's x-axis range

    yrange: Tuple[float, float], default=(-1.5, 1.5)
        Plot's x-axis range

    Return:
    -------
    fig: go.Figure
        Plotly figure showing module's value in a grid of [xrange]x[yrange] over which regularization path is shown

    Example:
    --------
    fig = plot_descent_path(IMLearn.desent_methods.modules.L1, np.ndarray([[1,1],[0,0]]))
    fig.show()
    """

    def predict_(w):
        return np.array([module(weights=wi).compute_output() for wi in w])

    from utils import decision_surface
    return go.Figure([decision_surface(predict_, xrange=xrange, yrange=yrange, density=70, showscale=False),
                      go.Scatter(x=descent_path[:, 0], y=descent_path[:, 1], mode="markers+lines",
                                 marker_color="black")],
                     layout=go.Layout(xaxis=dict(range=xrange),
                                      yaxis=dict(range=yrange),
                                      title=f"GD Descent Path {title}"))


def get_gd_state_recorder_callback() -> Tuple[Callable[[], None], List[np.ndarray], List[np.ndarray]]:
    """
    Callback generator for the GradientDescent class, recording the objective's value and parameters at each iteration

    Return:
    -------
    callback: Callable[[], None]
        Callback function to be passed to the GradientDescent class, recoding the objective's value and parameters
        at each iteration of the algorithm

    values: List[np.ndarray]
        Recorded objective values

    weights: List[np.ndarray]
        Recorded parameters
    """
    values = []
    weights = []

    def get_callback(**kwargs):
        values.append(np.array(kwargs['val']))
        weights.append(np.array(kwargs['weights']))

    return get_callback, values, weights


def compare_fixed_learning_rates(init: np.ndarray = np.array([np.sqrt(2), np.e / 3]),
                                 etas: Tuple[float] = (1, .1, .01, .001)):
    min_val = np.inf
    min_eta = 0
    for model, model_name in zip([L1, L2], ['L1', 'L2']):
        for eta in etas:
            callback, values, weights = get_gd_state_recorder_callback()
            gd = GradientDescent(FixedLR(eta), callback=callback)
            f = model(np.array(init))
            gd.fit(f, np.empty(0), np.empty(0))

            if np.min(values) < min_val:
                min_val = np.min(values)
                min_eta = eta
            fig = plot_descent_path(model, np.array(weights), title=f"Model: {model_name} eta:{eta}")
            fig.show()

            plt.plot(np.arange(1, len(values) + 1), values, label=f"eta:{eta}")

        plt.xlabel('Number of Iteration')
        plt.ylabel('Norm')
        plt.title(f"Model:{model_name} convergence rate")
        plt.legend()
        plt.show()

        print(f"Model-{model_name} lowest loss is for eta-{min_eta}, loss of: {min_val}")


def compare_exponential_decay_rates(init: np.ndarray = np.array([np.sqrt(2), np.e / 3]),
                                    eta: float = .1,
                                    gammas: Tuple[float] = (.9, .95, .99, 1)):
    # Optimize the L1 objective using different decay-rate values of the exponentially decaying learning rate
    raise NotImplementedError()

    # Plot algorithm's convergence for the different values of gamma
    raise NotImplementedError()

    # Plot descent path for gamma=0.95
    raise NotImplementedError()


def load_data(path: str = "../datasets/SAheart.data", train_portion: float = .8) -> \
        Tuple[pd.DataFrame, pd.Series, pd.DataFrame, pd.Series]:
    """
    Load South-Africa Heart Disease dataset and randomly split into a train- and test portion

    Parameters:
    -----------
    path: str, default= "../datasets/SAheart.data"
        Path to dataset

    train_portion: float, default=0.8
        Portion of dataset to use as a training set

    Return:
    -------
    train_X : DataFrame of shape (ceil(train_proportion * n_samples), n_features)
        Design matrix of train set

    train_y : Series of shape (ceil(train_proportion * n_samples), )
        Responses of training samples

    test_X : DataFrame of shape (floor((1-train_proportion) * n_samples), n_features)
        Design matrix of test set

    test_y : Series of shape (floor((1-train_proportion) * n_samples), )
        Responses of test samples
    """
    df = pd.read_csv(path)
    df.famhist = (df.famhist == 'Present').astype(int)
    return split_train_test(df.drop(['chd', 'row.names'], axis=1), df.chd, train_portion)

def _q8_(X_train, y_train, X_test, y_test):
    from sklearn.metrics import roc_curve, auc
    gd = GradientDescent(FixedLR(1e-4), max_iter=20_000)
    estimator = LogisticRegression(solver=gd)
    estimator.fit(X_train, y_train)
    y_prob = estimator.predict_proba(X_train)
    fpr, tpr, thresholds = roc_curve(y_train, y_prob)

    custom = [[0.0, "rgb(165,0,38)"],
              [0.1111111111111111, "rgb(215,48,39)"],
              [0.2222222222222222, "rgb(244,109,67)"],
              [0.3333333333333333, "rgb(253,174,97)"],
              [0.4444444444444444, "rgb(254,224,144)"],
              [0.5555555555555556, "rgb(224,243,248)"],
              [0.6666666666666666, "rgb(171,217,233)"],
              [0.7777777777777778, "rgb(116,173,209)"],
              [0.8888888888888888, "rgb(69,117,180)"],
              [1.0, "rgb(49,54,149)"]]

    c = [custom[0], custom[-1]]

    fig = go.Figure(
        data=[go.Scatter(x=[0, 1], y=[0, 1], mode="lines", line=dict(color="black", dash='dash'),
                         name="Random Class Assignment"),
              go.Scatter(x=fpr, y=tpr, mode='markers+lines', text=thresholds, name="", showlegend=False, marker_size=5,
                         marker_color=c[1][1],
                         hovertemplate="<b>Threshold:</b>%{text:.3f}<br>FPR: %{x:.3f}<br>TPR: %{y:.3f}")],
        layout=go.Layout(title=rf"$\text{{ROC Curve Of Fitted Model - AUC}}={auc(fpr, tpr):.6f}$",
                         xaxis=dict(title=r"$\text{False Positive Rate (FPR)}$"),
                         yaxis=dict(title=r"$\text{True Positive Rate (TPR)}$")))

    fig.show()

    best_alpha = thresholds[np.argmax(tpr - fpr)]
    print("LogisticRegression best alpha is:", best_alpha)
    print("LogisticRegression test error:",
          LogisticRegression(solver=gd, alpha=best_alpha).fit(X_train, y_train).loss(X_test, y_test))


def _q10_(model_name, X_train, y_train, X_test, y_test, penalty):
    from IMLearn.metrics import misclassification_error
    gd = GradientDescent(FixedLR(1e-4), max_iter=20_000)
    lambdas = np.array([0.001, 0.002, 0.005, 0.01, 0.02, 0.05, 0.1])
    train_err, valid_err = np.zeros(len(lambdas)), np.zeros(len(lambdas))

    for i, lam in enumerate(lambdas):
        estimator = LogisticRegression(solver=gd, penalty=penalty, lam=lam)
        train_err[i], valid_err[i] = cross_validate(estimator, X_train, y_train, misclassification_error)

    best_lam_index = valid_err.argmin()
    print(f"Best lambda for model- {model_name} is {lambdas[best_lam_index]}")

    model_best_lam = LogisticRegression(solver=gd, penalty=penalty, lam=lambdas[best_lam_index])
    model_best_lam.fit(X_train, y_train)
    test_error = model_best_lam.loss(X_test, y_test)
    print(f"\tTest Error for ,model - {model_name} is: {test_error}\n")


def fit_logistic_regression():
    # Load and split SA Heard Disease dataset
    X_train, y_train, X_test, y_test = load_data()
    X_train, X_test = X_train.to_numpy(), X_test.to_numpy()
    y_train, y_test = y_train.to_numpy(), y_test.to_numpy()

    # Plotting convergence rate of logistic regression over SA heart disease data
    _q8_(X_train, y_train, X_test, y_test)

    # Fitting l1- and l2-regularized logistic regression models, using cross-validation to specify values
    # of regularization parameter
    _q10_("L1", X_train, y_train, X_test, y_test, 'l1')
    _q10_("L2", X_train, y_train, X_test, y_test, 'l2')


if __name__ == '__main__':
    np.random.seed(0)
    compare_fixed_learning_rates()
    # compare_exponential_decay_rates()
    fit_logistic_regression()











