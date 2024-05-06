# from matplotlib import pyplot as plt
from matplotlib import pyplot as plt

from IMLearn.utils import split_train_test
from IMLearn.learners.regressors import LinearRegression

from typing import NoReturn, Optional
import numpy as np
import pandas as pd
import plotly.graph_objects as go
import plotly.express as px
import plotly.io as pio
import matplotlib.pyplot as plt
import numpy as np

pio.templates.default = "simple_white"
import os

X_mid = pd.DataFrame()
zipcode_example = 0

def process_train_data(X: pd.DataFrame, y: Optional[pd.Series] = None):
    df = X.copy()
    df['price'] = y

    df = df.drop(columns=["id", "lat", "long", "date", "sqft_lot15", "sqft_living15"])

    for col in df.columns:
        df[col].replace({'NA': np.nan}, inplace=True)
        df[col].replace({'N/A': np.nan}, inplace=True)

    df = df.dropna()
    df = df.drop_duplicates()


    for col in ["price", "sqft_living", "sqft_lot", "sqft_above", "yr_built"]:
        df = df[df[col] > 0]


    for col in ["bedrooms", "bathrooms", "sqft_basement", "yr_renovated", "floors"]:
        df = df[df[col] >= 0]

    df = df[df["waterfront"].isin([0, 1])]
    df = df[df["view"].isin(range(5))]
    df = df[df["condition"].isin(range(1, 6))]
    df = df[df["grade"].isin(range(1, 14))]

    global zipcode_example
    zipcode_example = df['zipcode'].value_counts().argmax()
    df = pd.get_dummies(df, prefix='zipcode', columns=['zipcode'])

    for col in df.columns:
        if not col.startswith('zipcode'):
            col_vals = df[col].dropna()
            median = col_vals.median()
            X_mid[col] = [median]

    return df.drop("price", axis=1), df.price


def process_test_data(X: pd.DataFrame):
    union = X
    union = union.drop(["id", "lat", "long", "date", "sqft_lot15", "sqft_living15"], axis=1)

    for col in union.columns:
        union[col].replace({'NA': np.nan}, inplace=True)
        union[col].replace({'N/A': np.nan}, inplace=True)

    null_values = union.isnull()

    for col in union.columns:
        if col.startswith('zipcode'):
            union[col].fillna(zipcode_example, inplace=True)
        else:
            if null_values[col].any():
                union[col].fillna(X_mid[col][0], inplace=True)

    union = pd.get_dummies(union, prefix='zipcode', columns=['zipcode'])

    for col in ["sqft_living", "sqft_lot", "sqft_above", "yr_built"]:
        mask = union[col] <= 0
        union.loc[mask, col] = X_mid[col][0]

    for col in ["bedrooms", "bathrooms", "sqft_basement", "yr_renovated", "floors"]:
        mask = union[col] < 0
        union.loc[mask, col] = X_mid[col][0]

    bad_waterfront_vals = ~union['waterfront'].isin([0, 1])
    union.loc[bad_waterfront_vals, 'waterfront'] = 0

    bad_view_vals = ~union['view'].isin(range(5))
    union.loc[bad_view_vals, 'view'] = X_mid['view'][0]

    bad_condition_vals = ~union['condition'].isin(range(1, 6))
    union.loc[bad_condition_vals, 'condition'] = X_mid['condition'][0]

    bad_grade_vals = ~union['grade'].isin(range(1, 14))
    union.loc[bad_grade_vals, 'grade'] = X_mid['grade'][0]

    return union


def preprocess_data(X: pd.DataFrame, y: Optional[pd.Series] = None):
    """
    preprocess data
    Parameters
    ----------
    X : DataFrame of shape (n_samples, n_features)
        Design matrix of regression problem
    y : array-like of shape (n_samples, )
        Response vector corresponding given samples
    Returns
    -------
    Post-processed design matrix and response vector (prices) - either as a single
    DataFrame or a Tuple[DataFrame, Series]
    """

    if y is None:
        return process_test_data(X)
    else:
        return process_train_data(X, y)


def price_checking(test_X, test_y):
    df = test_X
    df['price'] = test_y
    df = df.dropna(subset=['price'])
    return df.drop("price", axis=1), df.price


def correlation_calc(x_col: pd.Series, y: pd.Series) -> float:
    cov = np.cov(x_col, y)[0, 1]
    if np.isnan(cov):
        return 0
    std_x = np.std(x_col)
    std_y = np.std(y)
    return cov / (std_x * std_y)


def plot_one_feature(feature_values, feature_name, y, output_path):
    corr = correlation_calc(feature_values, y)

    plt.scatter(feature_values, y)
    plt.plot(feature_values, np.poly1d(np.polyfit(feature_values, y, 1))(feature_values), label="Correlation")
    plt.xlabel(feature_name)
    plt.ylabel('price')
    plt.title(f'Correlation between {feature_name} and the price is {corr:.3f}')
    img_path = output_path + feature_name + '.png'
    plt.savefig(img_path)
    plt.clf()


def feature_evaluation(X: pd.DataFrame, y: pd.Series, output_path: str = ".") -> NoReturn:
    """
    Create scatter plot between each feature and the response.
        - Plot title specifies feature name
        - Plot title specifies Pearson Correlation between feature and response
        - Plot saved under given folder with file name including feature name
    Parameters
    ----------
    X : DataFrame of shape (n_samples, n_features)
        Design matrix of regression problem
    y : array-like of shape (n_samples, )
        Response vector to evaluate against
    output_path: str (default ".")
        Path to folder in which plots are saved
    """
    X_cols = X.columns
    for single_col in X_cols:
        plot_one_feature(feature_values=X[single_col], feature_name=single_col, y=y, output_path=output_path)



def plot_avg_loss_as_training_size(mean, std, percentages):
    fig, ax = plt.subplots()
    ax.plot(percentages, mean - 2 * std, color='lightgrey', label=None)
    ax.plot(percentages, mean + 2 * std, color='lightgrey', label=None)
    ax.fill_between(percentages, mean - 2 * std, mean + 2 * std, alpha=0.2, color='lightgrey')
    ax.plot(percentages, mean, 'o-', color='black', label=None)
    ax.set(title='Mse as function of training Size',
           xlabel='Percentage of training Set',
           ylabel='MSE over Test Set')
    ax.legend()
    plt.show()


if __name__ == '__main__':
    np.random.seed(0)
    _df = pd.read_csv("../datasets/house_prices.csv")

    # Question 1 - split data into train and test sets
    y = _df['price']
    _df.drop('price', axis=1, inplace=True)
    train_X, train_y, test_X, test_y = split_train_test(_df, y)

    # Question 2 - Preprocessing of housing prices dataset
    train_X, train_y = preprocess_data(train_X, train_y)

    # Question 3 - Feature evaluation with respect to response
    os.mkdir('../datasets/Plots/')
    feature_evaluation(train_X, train_y, '../datasets/Plots/')

    # Question 4 - Fit model over increasing percentages of the overall training data
    # For every percentage p in 10%, 11%, ..., 100%, repeat the following 10 times:
    #   1) Sample p% of the overall training data
    #   2) Fit linear model (including intercept) over sampled set
    #   3) Test fitted model over test set
    #   4) Store average and variance of loss over test set
    # Then plot average loss as function of training size with error ribbon of size (mean-2*std, mean+2*std)

    test_X = preprocess_data(test_X)
    test_X, test_y = price_checking(test_X, test_y)
    test_X = test_X.reindex(columns=train_X.columns, fill_value=0)

    percentages = list(range(10, 101))
    mean_results = []
    std_results = []
    for i, p in enumerate(percentages):
        loss_results = []
        for _ in range(10):
            linear_regression = LinearRegression(include_intercept=True)
            curr_X = train_X.sample(frac=p / 100.0)
            curr_y = train_y.loc[curr_X.index]
            linear_regression.fit(curr_X, curr_y)
            loss = linear_regression.loss(test_X.to_numpy(), test_y.to_numpy())
            loss_results.append(loss)
        ndarray_loss_results = np.array(loss_results)
        mean_results.append(ndarray_loss_results.mean())
        std_results.append(ndarray_loss_results.std())
    plot_avg_loss_as_training_size(mean=np.array(mean_results), std=np.array(std_results), percentages=percentages)
