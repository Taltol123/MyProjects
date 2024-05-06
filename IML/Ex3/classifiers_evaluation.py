from matplotlib import pyplot as plt

from IMLearn.learners.classifiers import Perceptron, LDA, GaussianNaiveBayes
from typing import Tuple
from utils import *
import plotly.graph_objects as go
from plotly.subplots import make_subplots
from math import atan2, pi


def load_dataset(filename: str) -> Tuple[np.ndarray, np.ndarray]:
    """
    Load dataset for comparing the Gaussian Naive Bayes and LDA classifiers. File is assumed to be an
    ndarray of shape (n_samples, 3) where the first 2 columns represent features and the third column the class
    Parameters
    ----------
    filename: str
        Path to .npy data file
    Returns
    -------
    X: ndarray of shape (n_samples, 2)
        Design matrix to be used
    y: ndarray of shape (n_samples,)
        Class vector specifying for each sample its class
    """
    data = np.load(filename)
    return data[:, :2], data[:, 2].astype(int)


def _plot_func(losses, set_type):
    plt.scatter(list(range(len(losses))), losses, marker='o', s=10)
    plt.plot(list(range(len(losses))), losses, linewidth=2)
    plt.xlabel('Training iteration')
    plt.ylabel('Losses')
    plt.title(f'Perceptron training error on {set_type} set')
    plt.show()


def run_perceptron():
    """
    Fit and plot fit progression of the Perceptron algorithm over both the linearly separable and inseparable datasets
    Create a line plot that shows the perceptron algorithm's training loss values (y-axis)
    as a function of the training iterations (x-axis).
    """
    for set_type, file in [("Linearly Separable", "linearly_separable.npy"),
                           ("Linearly Inseparable", "linearly_inseparable.npy")]:
        # Load dataset
        X, y = load_dataset(f'../datasets/{file}')

        # Fit Perceptron and record loss in each fit iteration
        losses = []

        def record_loss(p: Perceptron, _, __):
            losses.append(p.loss(X, y))

        perceptron = Perceptron(callback=record_loss)
        perceptron.fit(X, y)

        # Plot figure of loss as function of fitting iteration
        _plot_func(losses, set_type)


def get_ellipse(mu: np.ndarray, cov: np.ndarray):
    """
    Draw an ellipse centered at given location and according to specified covariance matrix
    Parameters
    ----------
    mu : ndarray of shape (2,)
        Center of ellipse
    cov: ndarray of shape (2,2)
        Covariance of Gaussian
    Returns
    -------
        scatter: A plotly trace object of the ellipse
    """
    l1, l2 = tuple(np.linalg.eigvalsh(cov)[::-1])
    theta = atan2(l1 - cov[0, 0], cov[0, 1]) if cov[0, 1] != 0 else (np.pi / 2 if cov[0, 0] < cov[1, 1] else 0)
    t = np.linspace(0, 2 * pi, 100)
    xs = (l1 * np.cos(theta) * np.cos(t)) - (l2 * np.sin(theta) * np.sin(t))
    ys = (l1 * np.sin(theta) * np.cos(t)) + (l2 * np.cos(theta) * np.sin(t))

    return go.Scatter(x=mu[0] + xs, y=mu[1] + ys, mode="lines", marker_color="black")

def _add_elements_to_fig(fig, X, y, gnb_model, lda_model, gnb_predict, lda_predict):
    # Add traces for data-points setting symbols and colors
    fig.add_trace(go.Scatter(x=X[:, 0], y=X[:, 1], mode='markers',
                             marker=dict(color=gnb_predict, symbol=class_symbols[y],
                                         colorscale=[custom[0], custom[1]])), row=1, col=1)
    fig.add_trace(go.Scatter(x=X[:, 0], y=X[:, 1], mode='markers',
                             marker=dict(color=lda_predict, symbol=class_symbols[y],
                                         colorscale=[custom[0], custom[1]])), row=1, col=2)

    # Add `X` dots specifying fitted Gaussians' means
    fig.add_trace(go.Scatter(x=gnb_model.mu_[:, 0], y=gnb_model.mu_[:, 1], mode="markers",
                             marker=dict(symbol="x", color="black", size=10)), row=1, col=1)
    fig.add_trace(go.Scatter(x=lda_model.mu_[:, 0], y=lda_model.mu_[:, 1], mode="markers",
                             marker=dict(symbol="x", color="black", size=10)), row=1, col=2)

def compare_gaussian_classifiers():
    """
    Fit both Gaussian Naive Bayes and LDA classifiers on both gaussians1 and gaussians2 datasets
    """
    for file in ["gaussian1.npy", "gaussian2.npy"]:
        # Load dataset
        X, y = load_dataset(f"../datasets/{file}")
        data = file.split('.', 1)[0]
        # Fit models and predict over training set
        gaussian_naive_bayes = GaussianNaiveBayes()
        lda = LDA()

        gnb_model = gaussian_naive_bayes.fit(X, y)
        lda_model = lda.fit(X, y)

        gnb_predict = gnb_model.predict(X)
        lda_predict = lda_model.predict(X)

        # Plot a figure with two suplots, showing the Gaussian Naive Bayes predictions on the left and LDA predictions
        # on the right. Plot title should specify dataset used and subplot titles should specify algorithm and accuracy
        # Create subplots
        from IMLearn.metrics import accuracy
        lda_percentage_accuracy = round(100 * accuracy(y, lda_predict), 2)
        gnb_percentage_accuracy = round(100 * accuracy(y, gnb_predict), 2)
        fig = make_subplots(rows=1, cols=2,
                            subplot_titles=(
                                rf"$\text{{Gaussian Naive Bayes on {data} data accuracy={gnb_percentage_accuracy}%}}$",
                                rf"$\text{{LDA on {data} data accuracy={lda_percentage_accuracy}%}}$"))

        _add_elements_to_fig(fig, X, y, gnb_model, lda_model, gnb_predict, lda_predict)

        # Add ellipses depicting the covariances of the fitted Gaussians
        for i in range(3):
            fig.add_trace(get_ellipse(gnb_model.mu_[i], np.diag(gnb_model.vars_[i])), row=1, col=1)
            fig.add_trace(get_ellipse(lda_model.mu_[i], lda_model.cov_), row=1, col=2)

        fig.update_yaxes(scaleanchor="x", scaleratio=1)
        fig.update_layout(width=800, height=400, showlegend=False)
        fig.show()


if __name__ == '__main__':
    np.random.seed(0)
    run_perceptron()
    compare_gaussian_classifiers()
