from typing import NoReturn
from ...base import BaseEstimator
import numpy as np
from numpy.linalg import det, inv


class LDA(BaseEstimator):
    """
    Linear Discriminant Analysis (LDA) classifier

    Attributes
    ----------
    self.classes_ : np.ndarray of shape (n_classes,)
        The different labels classes. To be set in `LDA.fit`

    self.mu_ : np.ndarray of shape (n_classes,n_features)
        The estimated features means for each class. To be set in `LDA.fit`

    self.cov_ : np.ndarray of shape (n_features,n_features)
        The estimated features covariance. To be set in `LDA.fit`

    self._cov_inv : np.ndarray of shape (n_features,n_features)
        The inverse of the estimated features covariance. To be set in `LDA.fit`

    self.pi_: np.ndarray of shape (n_classes)
        The estimated class probabilities. To be set in `GaussianNaiveBayes.fit`
    """

    def __init__(self):
        """
        Instantiate an LDA classifier
        """
        super().__init__()
        self.classes_, self.mu_, self.cov_, self._cov_inv, self.pi_ = None, None, None, None, None

    def _fit(self, X: np.ndarray, y: np.ndarray) -> NoReturn:
        """
        fits an LDA model.
        Estimates gaussian for each label class - Different mean vector, same covariance
        matrix with dependent features.

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)
            Input data to fit an estimator for

        y : ndarray of shape (n_samples, )
            Responses of input data to fit to
        """
        self.classes_, pi = np.unique(y, return_counts=True)
        self.pi_ = pi / len(y)
        mu = []
        for single_class in self.classes_:
            mu.append(np.mean(X[y == single_class], axis=0))
        self.mu_ = np.array(mu)
        calc = X - self.mu_[y.astype(int)]
        divide_by = (len(X) - len(self.classes_))
        self.cov_ = np.matmul(calc.T, calc) / divide_by
        self.cov_inv_ = inv(self.cov_)

    def _predict(self, X: np.ndarray) -> np.ndarray:
        """
        Predict responses for given samples using fitted estimator

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)
            Input data to predict responses for

        Returns
        -------
        responses : ndarray of shape (n_samples, )
            Predicted responses of given samples
        """
        likelihood = self.likelihood(X)
        max_likelihood = np.argmax(likelihood, axis=1)
        predict = self.classes_[max_likelihood]
        return predict

    def _gaussian(self, X: np.ndarray, class_index: int) -> np.ndarray:
        mu = self.mu_[class_index]
        cov = self.cov_
        cov_inv = self.cov_inv_
        features_len = X.shape[1]
        x_center = X - mu
        factor = np.sqrt(np.power(2 * np.pi, features_len) * det(cov))
        res = []
        for sample in x_center:
            res.append(np.exp(-0.5 * sample.T @ cov_inv @ sample) / factor)
        return np.array(res)


    def likelihood(self, X: np.ndarray) -> np.ndarray:
        """
        Calculate the likelihood of a given data over the estimated model

        Parameters
        ----------
        X : np.ndarray of shape (n_samples, n_features)
            Input data to calculate its likelihood over the different classes.

        Returns
        -------
        likelihoods : np.ndarray of shape (n_samples, n_classes)
            The likelihood for each sample under each of the classes

        """
        if not self.fitted_:
            raise ValueError("Estimator must first be fitted before calling `likelihood` function")

        classes_len = len(self.classes_)
        a = []
        for i in range(classes_len):
            a.append(self._gaussian(X, i) * self.pi_[i])
        likelihood = np.vstack(a).T
        return likelihood

    def _loss(self, X: np.ndarray, y: np.ndarray) -> float:
        """
        Evaluate performance under misclassification loss function

        Parameters
        ----------
        X : ndarray of shape (n_samples, n_features)
            Test samples

        y : ndarray of shape (n_samples, )
            True labels of test samples

        Returns
        -------
        loss : float
            Performance under missclassification loss function
        """
        from ...metrics import misclassification_error
        y_predict = self._predict(X)
        return misclassification_error(y_true=y, y_pred=y_predict)
