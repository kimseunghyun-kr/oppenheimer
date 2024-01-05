import pandas as pd
import numpy as np
from scipy import stats, signal
import plotly.express as px
import plotly.graph_objects as go

import sys
import psycopg2  # Assuming PostgreSQL-specific library if needed
import jaydebeapi  # Assuming H2-specific library if needed


def processSelector(cursor, action, target):
    if action == 1:
        VolumeProfileIndicator(cursor, target)
    if action == 2:
        Histogram(cursor, target)
    if action == 3:
        Prominence(cursor, target)


def Prominence(cursor, target):
    min_prom = 0.3
    xr, kdy, binSize = VolumeProfileIndicator(cursor, target)
    peaks, peak_props = signal.find_peaks(kdy, prominence=min_prom)
    pkx = xr[peaks]
    pky = kdy[peaks]

    return xr, kdy


def peakWidth(cursor, target):
    min_prom = 0.3
    width_range = 1
    xr, kdy, binSize = VolumeProfileIndicator(cursor, target)
    peaks, peak_props = signal.find_peaks(kdy, prominence=min_prom, width=width_range)
    pkx = xr[peaks]
    pky = kdy[peaks]

    return xr, kdy


def VolumeProfileIndicator(cursor, target):
    cursor.execute("SELECT * FROM MARKETDATA m WHERE m.stockTickerId = " + target)
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    df = pd.DataFrame(rows, columns=columns)
    xr, kdy, binSize = KDE(df)
    return xr, kdy, binSize


def KDE(df):
    kde_factor = 0.05
    bins = 500
    close = df['close']
    volume = df['volume']
    kde = stats.gaussian_kde(close, weights=volume, bw_method=kde_factor)
    xr = np.linspace(close.min(), close.max(), bins)
    kdy = kde(xr)
    binSize = (xr.max() - xr.min()) / bins
    return xr, kdy, binSize


def Histogram(cursor, target):
    cursor.execute(
        "SELECT m.close, Sum(m.volume) FROM MARKETDATA m WHERE m.stockTickerID = " + target + "GROUP BY m.close")
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    df = pd.DataFrame(rows, columns=columns)
    return df

def VolumeAreaContinuous(cursor, target):
    return;
def VolumeAreaDiscrete(cursor, target):
    return;
if __name__ == "__main__":
    # Check for the command-line argument indicating the database context
    if len(sys.argv) != 2:
        print("Usage: python_script.py <database_context>")
        sys.exit(1)

    # Extract the database context from the command-line argument
    database_context = sys.argv[1]
    indicatorAction = sys.argv[2]
    target = sys.argv[3]

    cursor = None

    if database_context.lower() != "h2" or database_context.lower() != "postgresql":
        print("Invalid database context. Supported values: h2, postgresql")
        sys.exit(1)

    if database_context.lower() == "h2":
        # Initialize H2-specific resources if needed
        # h2.initialize()
        connection = jaydebeapi.connect(
            "org.h2.Driver",
            "jdbc:h2:tcp://localhost:5234/exoplanets",
            ["SA", ""],
            "../h2-1.4.200.jar")
        cursor = connection.cursor()

    if database_context.lower() == "postgresql":
        # Initialize PostgreSQL-specific resources if needed
        connection = psycopg2.connect(":memory:")
        cursor = connection.cursor()

    processSelector(cursor, indicatorAction, target)

    # output the result for Java to retrieve
