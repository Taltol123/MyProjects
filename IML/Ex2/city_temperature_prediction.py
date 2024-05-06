from IMLearn.learners.regressors import PolynomialFitting
from IMLearn.utils import split_train_test

import numpy as np
import pandas as pd
import plotly.express as px
import plotly.io as pio
pio.templates.default = "simple_white"
from matplotlib import pyplot as plt

def load_data(filename: str) -> pd.DataFrame:
    """
    Load city daily temperature dataset and preprocess data.
    Parameters
    ----------
    filename: str
        Path to house prices dataset

    Returns
    -------
    Design matrix and response vector (Temp)
    """
    df = pd.read_csv(filename, parse_dates=['Date'])
    df = df.dropna().drop_duplicates()
    df = df[df.Temp > -70]
    df['DayOfYear'] =df['Date'].dt.dayofyear

    return df

def plot_temp_day(df):
    color_map = plt.cm.get_cmap('hsv', len(df['Year'].unique()))
    _, ax = plt.subplots()
    for i, year in enumerate(df['Year'].unique()):
        subset_df = df[df['Year'] == year]
        ax.scatter(subset_df['DayOfYear'], subset_df['Temp'], color=color_map(i), label=year)
    ax.legend()
    ax.set_title('Average temperature change as a function of the day of year')
    ax.set_xlabel('Day of Year')
    ax.set_ylabel('Temperature')
    plt.show()
def plot_month_std(df):
    df["Month"] = pd.DatetimeIndex(df["Date"]).month
    groupe_month = df.groupby("Month").agg({"Temp": "std"})
    groupe_month.plot.bar()
    plt.xlabel("Month")
    plt.ylabel("Temperature std")
    plt.title("Std of the daily temperatures for each month")
    plt.show()

def plot_avg_monthly_temp(df):
    df_group_country_month = df.groupby(["Country", "Month"])["Temp"].agg(["mean", "std"]).reset_index()

    for country in df_group_country_month["Country"].unique():
        df_country = df_group_country_month[df_group_country_month["Country"] == country]
        plt.errorbar(df_country["Month"], df_country["mean"], yerr=df_country["std"], label=country)

    plt.xlabel("Month")
    plt.ylabel("Mean temperature ")
    plt.title("Average Monthly Temperature with Standard Deviation")
    plt.legend()
    plt.show()

def plot_error_k(df):
    months = list(range(1, 11))
    losses = np.zeros_like(months, dtype=float)
    train_X, train_y, test_X, test_y = split_train_test(df['DayOfYear'], df['Temp'])
    for i, k in enumerate(months):
        polynomial_fitting = PolynomialFitting(k)
        polynomial_fitting.fit(train_X.to_numpy(), train_y.to_numpy())
        loss = np.round(polynomial_fitting.loss(test_X.to_numpy(), test_y.to_numpy()), 2)
        losses[i] = loss

    plt.bar(months, losses)
    plt.xlabel("k")
    plt.ylabel("Loss")
    plt.title("Test error recorded for each value of k")
    plt.show()

def plot_other_countries(df, israel_samples):
    polynomial_fitting = PolynomialFitting(5)
    polynomial_fitting.fit(israel_samples['DayOfYear'], israel_samples['Temp'])
    countries = df[df['Country'] != 'Israel'].Country.drop_duplicates()
    losses = np.zeros_like(list(range(1,len(countries)+1)), dtype=float)

    for i, country in enumerate(countries):
        df_country = df[df['Country']==country]
        loss =polynomial_fitting.loss(df_country['DayOfYear'], df_country['Temp'])
        losses[i] = loss
    plt.bar(countries, losses)
    plt.xlabel("Country")
    plt.ylabel("Loss")
    plt.title("Error over countries on model fitted on Israel")
    plt.show()


if __name__ == '__main__':
    np.random.seed(0)
    # Question 1 - Load and preprocessing of city temperature dataset
    df = load_data("../datasets/City_Temperature.csv")

    # Question 2 - Exploring data for specific country
    israel_samples = df[df['Country']=='Israel']
    plot_temp_day(israel_samples)
    plot_month_std(israel_samples)

    # Question 3 - Exploring differences between countries
    plot_avg_monthly_temp(df)

    # Question 4 - Fitting model for different values of `k`
    plot_error_k(israel_samples)

    # Question 5 - Evaluating fitted model on different countries
    plot_other_countries(df, israel_samples)