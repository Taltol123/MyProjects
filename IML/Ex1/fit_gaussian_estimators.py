from IMLearn.learners import UnivariateGaussian, MultivariateGaussian
import numpy as np
import plotly.io as pio
from matplotlib import pyplot as plt, colors
import plotly.express as px

pio.templates.default = "simple_white"


def q1(gauss: UnivariateGaussian):
    X = np.random.normal(10, 1, (1, 1000))
    gauss.fit(X)
    print(f"({np.around(gauss.mu_, 3)}, {np.around(gauss.var_, 3)})")
    return X


def q2(X: np.ndarray, model: UnivariateGaussian):
    correct_mu = 10
    n_samples = [i for i in range(10, 1000, 10)]
    est_mu = []

    for sample in n_samples:
        x_values = X[:, 0:sample]
        model.fit(x_values)
        est_mu.append(model.mu_)

    true_dist = np.abs(np.array(est_mu) - correct_mu)
    plt.plot(n_samples, true_dist)
    plt.xlabel("Sample size")
    plt.ylabel("Absolute Distance")
    plt.title("Absolute Distance according to sample size:")
    plt.show()


def q3(X: np.ndarray, model: UnivariateGaussian):
    pdfs = model.pdf(X=X)
    plt.xlabel("sample value")
    plt.ylabel("pdf value")
    plt.title("sample value to pdf value:")
    plt.scatter(x=X, y=pdfs)
    plt.show()


def q4(gauss: MultivariateGaussian):
    mean = np.array([0, 0, 4, 0])
    cov = np.array([[1, 0.2, 0, 0.5], [0.2, 2, 0, 0], [0, 0, 1, 0], [0.5, 0, 0, 1]])
    X = np.random.multivariate_normal(mean, cov, 1000)
    gauss.fit(X)
    print(f"µ = [{np.around(gauss.mu_,3)}]")
    print(f"Σ = [{np.around(gauss.cov_,3)}]")
    return X



def q5(X: np.ndarray, gauss: MultivariateGaussian):
    f1 = np.linspace(-10, 10, 200)
    f3 = np.linspace(-10, 10, 200)
    cov = np.array([[1, 0.2, 0, 0.5], [0.2, 2, 0, 0], [0, 0, 1, 0], [0.5, 0, 0, 1]])
    log_likelihood_results = []
    for f1_value in f1:
        for f3_value in f3:
            mean = np.array([f1_value, 0, f3_value, 0]).T
            log_likelihood_results.append(gauss.log_likelihood(mean, cov, X))
    log_likelihood_results = np.array(log_likelihood_results)
    log_likelihood_results = log_likelihood_results.reshape(200, 200)

    fig = px.imshow(log_likelihood_results, labels=dict(x="f3 value", y='f1 value', color="Log likelihood"),
                    x=np.linspace(-10, 10, 200), y=np.linspace(-10, 10, 200))
    fig.show()

    return log_likelihood_results,f1,f3

def q6( log_likelihood_results: np.ndarray, f1 : np.linspace, f3 : np.linspace):
    max_f1_index, max_f3_index = np.unravel_index(np.argmax(log_likelihood_results), log_likelihood_results.shape)
    max_f1 = f1[max_f1_index]
    max_f3 = f3[max_f3_index]
    print("The maximum log-likelihood has f1 = {:.3f} and f3 = {:.3f}".format(np.round(max_f1,3), np.round(max_f3,3)))

def test_univariate_gaussian():
    # Question 1 - Draw samples and print fitted model
    model = UnivariateGaussian()
    X = q1(model)

    # Question 2 - Empirically showing sample mean is consistent
    q2(X, model)

    # Question 3 - Plotting Empirical PDF of fitted model
    q3(X, model)


def test_multivariate_gaussian():
    # Question 4 - Draw samples and print fitted model
    model = MultivariateGaussian()
    X = q4(model)

    # Question 5 - Likelihood evaluation
    log_likelihood_results,f1,f3= q5(X,model)

    # Question 6 - Maximum likelihood
    q6(log_likelihood_results,f1,f3)


if __name__ == '__main__':
    np.random.seed(0)
    test_univariate_gaussian()
    test_multivariate_gaussian()
