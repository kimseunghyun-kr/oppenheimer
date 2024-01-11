import pandas as pd
import numpy as np
from scipy import stats, signal
import plotly.express as px
import plotly.graph_objects as go
import os
import sys
import psycopg2  # Assuming PostgreSQL-specific library if needed
import jaydebeapi  # Assuming H2-specific library if needed


def processSelector(cursor, action, target, kargs):
    if action == 0:
        dbTestCallIndicator(cursor,target)
    if action == 1:
        VolumeProfileIndicator(cursor, target)
    if action == 2:
        Histogram(cursor, target)
    if action == 3:
        Prominence(cursor, target)


    print("python script successfully ran")

def dbTestCallIndicator(cursor, target):
    cursor.execute("SELECT * FROM MARKET_DATA")
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    df = pd.DataFrame(rows, columns=columns)
    # display(df)
    print(df.to_markdown())
    cursor.execute("SELECT * FROM STOCK_DATA")
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    df = pd.DataFrame(rows, columns=columns)
    # display(df)
    print(df.to_markdown())


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
    print("volumeProfileCompleted")
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
        "SELECT m.close, Sum(m.volume) FROM MARKET_DATA m WHERE m.stockTickerID = " + target + "GROUP BY m.close")
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    df = pd.DataFrame(rows, columns=columns)
    return df


def VolumeAreaContinuous(cursor, target):
    xr, kdy, binSize = VolumeProfileIndicator(cursor, target)
    p_values = [0.3, 0.7]
    # Calculate the cumulative density
    cumulative_density = np.cumsum(kdy) * binSize

    # Find values in xr corresponding to given percentiles
    percentile_values = []
    for p in p_values:
        index = np.argmax(cumulative_density >= p)
        percentile_values.append(xr[index])

    return percentile_values


def VolumeAreaDiscrete(cursor, target):
    df = Histogram(cursor, target);

    # Calculate the cumulative density
    cumulative_density = np.cumsum(df['volume']) / np.sum(df['volume'])
    p_values = [0.3, 0.7]
    # Find values in 'close' corresponding to given percentiles
    percentile_values = []
    for p in p_values:
        index = np.argmax(cumulative_density >= p)
        percentile_values.append(df['close'].iloc[index])

    return percentile_values


if __name__ == "__main__":
    print("start")
    BASE_DIR = os.path.dirname(os.path.realpath(__file__))
    print(BASE_DIR)
    # Check for the command-line argument indicating the database context
    if len(sys.argv) < 4:
        print("Usage: python_script.py <database_context>")
        sys.exit(1)

    # Extract the database context from the command-line argument
    database_context = sys.argv[1]
    indicatorAction = int(sys.argv[2])
    target = sys.argv[3]
    additionalArgs = sys.argv[4:]

    cursor = None

    if database_context.lower() not in ["h2", "postgresql"]:
        print("Invalid database context. Supported values: h2, postgresql")
        sys.exit(1)

    try:
        if database_context.lower() == "h2":
            # Initialize H2-specific resources if needed
            h2_jar_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "h2-2.1.214.jar")
            # "C:/Users/aski-/java/oppenheimer/src/main/java/com/stock/oppenheimer/indicators/h2-2.1.214.jar"
            url = "jdbc:h2:/data/test;AUTO_SERVER=TRUE"
            driver = "org.h2.Driver"
            user = ""
            password = ""

            # Attempt to connect to the H2 database
            conn = jaydebeapi.connect(driver, url, [user, password], h2_jar_path)
            cursor = conn.cursor()
            print("Connected to H2 database.")
            # Additional print statements or actions can be added here

        elif database_context.lower() == "postgresql":
            # Initialize PostgreSQL-specific resources if needed
            connection = psycopg2.connect(":memory:")
            cursor = connection.cursor()
            print("Connected to PostgreSQL database.")
            # Additional print statements or actions can be added here

        # Call the processSelector function
        processSelector(cursor, indicatorAction, target, additionalArgs)

    except Exception as e:
        print(f"Error connecting to the database: {str(e)}")

    finally:
        # Close the database connection
        if cursor:
            cursor.close()
        if 'conn' in locals():
            conn.close()
        if 'connection' in locals():
            connection.close()