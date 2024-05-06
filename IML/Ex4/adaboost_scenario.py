from matplotlib import pyplot as plt

import numpy as np
from typing import Tuple
from IMLearn.metalearners.adaboost import AdaBoost
from IMLearn.learners.classifiers.decision_stump import DecisionStump
from utils import *
import plotly.graph_objects as go
from plotly.subplots import make_subplots


def generate_data(n: int, noise_ratio: float) -> Tuple[np.ndarray, np.ndarray]:
    """
    Generate a dataset in R^2 of specified size

    Parameters
    ----------
    n: int
        Number of samples to generate

    noise_ratio: float
        Ratio of labels to invert

    Returns
    -------
    X: np.ndarray of shape (n_samples,2)
        Design matrix of samples

    y: np.ndarray of shape (n_samples,)
        Labels of samples
    """
    '''
    generate samples X with shape: (num_samples, 2) and labels y with shape (num_samples).
    num_samples: the number of samples to generate
    noise_ratio: invert the label for this ratio of the samples
    '''
    X, y = np.random.rand(n, 2) * 2 - 1, np.ones(n)
    y[np.sum(X ** 2, axis=1) < 0.5 ** 2] = -1
    y[np.random.choice(n, int(noise_ratio * n))] *= -1
    return X, y

def q1_plot(n_learners, train_error, test_error):
    plt.scatter(list(range(1, n_learners + 1)), train_error, marker='o', s=10)
    plt.plot(list(range(1, n_learners + 1)), train_error, linewidth=2)
    plt.scatter(list(range(1, n_learners + 1)), test_error, marker='o', s=10)
    plt.plot(list(range(1, n_learners + 1)), test_error, linewidth=2)
    plt.xlabel('Ensemble size')
    plt.ylabel('MSE')
    plt.title('AdaBoost MSE as function of classifiers size')
    plt.show()

def q3_plot(model, train_X, test_X, test_error, test_y):
    lims = np.array([np.r_[train_X, test_X].min(axis=0), np.r_[train_X, test_X].max(axis=0)]).T + np.array([-.1, .1])
    best_size = 1
    for i in range(1, len(test_error)):
        if test_error[i] < test_error[best_size]:
            best_size = i

    best_size += 1
    accuracy = 1 - test_error[best_size - 1]

    fig = go.Figure(
        [decision_surface(lambda X: model.partial_predict(X, best_size), lims[0], lims[1], density=60, showscale=False),
         go.Scatter(x=test_X[:, 0], y=test_X[:, 1], mode="markers", showlegend=False,
                    marker=dict(color=test_y, symbol=np.where(test_y == 1, "circle", "x")))],
        layout=go.Layout(width=600, height=600, xaxis=dict(visible=False), yaxis=dict(visible=False),
        title=f"Best ensemble Size: {best_size}, Accuracy: {accuracy}"))

    fig.show()

def q2_plot(model, train_X, test_X, test_y):
    model_iterations = [5, 50, 100, 250]
    lims = np.array([np.r_[train_X, test_X].min(axis=0), np.r_[train_X, test_X].max(axis=0)]).T + np.array([-.1, .1])

    fig = make_subplots(rows=1, cols=4, subplot_titles=[rf"$\text{{{t} Classifiers}}$" for t in model_iterations])
    for i, model_iteration in enumerate(model_iterations):
        fig.add_traces(
            [decision_surface(lambda X: model.partial_predict(X, model_iteration), lims[0], lims[1], density=60,
                              showscale=False),
             go.Scatter(x=test_X[:, 0], y=test_X[:, 1], mode="markers", showlegend=False,
                        marker=dict(color=test_y, symbol=np.where(test_y == 1, "circle", "x")))],
            rows=1, cols=i + 1)
    fig.update_layout(height=500, width=2000).update_xaxes(visible=False).update_yaxes(visible=False)
    fig.show()

def q4_plot(model, train_X, train_y, test_X):
    lims = np.array([np.r_[train_X, test_X].min(axis=0), np.r_[train_X, test_X].max(axis=0)]).T + np.array([-.1, .1])

    D = 20 * model.D_ / model.D_.max()
    fig = go.Figure([
        decision_surface(model.predict, lims[0], lims[1], density=60, showscale=False),
        go.Scatter(x=train_X[:, 0], y=train_X[:, 1], mode="markers", showlegend=False,
                   marker=dict(size=D, color=train_y, symbol=np.where(train_y == 1, "circle", "x")))],
        layout=go.Layout(width=500, height=500, xaxis=dict(visible=False), yaxis=dict(visible=False),
                         title=f"AdaBoost Sample Distribution"))
    fig.show()

def fit_and_evaluate_adaboost(noise, n_learners=250, train_size=5000, test_size=500):
    (train_X, train_y), (test_X, test_y) = generate_data(train_size, noise), generate_data(test_size, noise)

    # Question 1: Train- and test errors of AdaBoost in noiseless case
    model = AdaBoost(DecisionStump, n_learners).fit(train_X, train_y)
    train_error = [model.partial_loss(train_X, train_y, t) for t in range(1, n_learners + 1)]
    test_error = [model.partial_loss(test_X, test_y, t) for t in range(1, n_learners + 1)]
    q1_plot(n_learners, train_error, test_error)

    # Question 2: Plotting decision surfaces
    q2_plot(model, train_X, test_X, test_y)

    # Question 3: Decision surface of best performing ensemble
    q3_plot(model, train_X, test_X, test_error, test_y)

    # Question 4: Decision surface with weighted samples
    q4_plot(model, train_X, train_y, test_X)

if __name__ == '__main__':
    np.random.seed(0)
    fit_and_evaluate_adaboost(0)
    fit_and_evaluate_adaboost(0.4)
