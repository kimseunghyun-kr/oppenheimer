import pandas as pd
import numpy as np
from scipy import stats, signal
import plotly.express as px
import plotly.graph_objects as go

import sys
import psycopg2  # Assuming PostgreSQL-specific library if needed
import h2        # Assuming H2-specific library if needed

def processSelector(cursor, action, target):
    if action == 1:
        VolumeProfileIndicator(cursor, target)

def VolumeProfileIndicator(cursor , target, prominence = 0.3):
    cursor.execute("SELECT * FROM MARKETDATA m WHERE m.stockTickerId = " + target);
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description]
    df = pd.DataFrame(rows, columns = columns)
    xr, kdy, samplePerBins = KDE(df)
    min_prom = kdy.max() * prominence
    max_width_pips = 20
    pipsize = 0.00001
    width_range=(1, max_width_pips * pipsize / samplePerBins)
    peaks, peak_props = signal.find_peaks(kdy, prominence=min_prom, width = width_range)
    pkx = xr[peaks]
    pky = kdy[peaks]


def KDE(df):
    kde_factor = 0.05
    bins = 500
    close = df['close']
    volume = df['volume']
    kde = stats.gaussian_kde(close, weights = volume , bw_method = kde_factor)
    xr = np.linspace(close.min(), close.max(), bins)
    kdy = kde(xr)
    samplePerBins = (xr.max()- xr.min()) / bins
    return xr, kdy, samplePerBins
    
def Histogram(cursor, target):
    data = cursor.execute("SELECT m.close, Sum(m.volume) FROM MARKETDATA m WHERE m.stockTickerID = " + target + "GROUP BY m.close")
    rows = cursor.fetchall()
    columns = [desc[0] for desc in cursor.description];
    df = pd.DataFrame(rows, columns = columns)
    return df

    
if __name__ == "__main__":
    # Check for the command-line argument indicating the database context
    if len(sys.argv) != 2:
        print("Usage: python_script.py <database_context>")
        sys.exit(1)

    # Extract the database context from the command-line argument
    database_context = sys.argv[1]
    indicatorAction = sys.argv[2]
    target = sys.argv[3]
    
    if database_context.lower() == "h2":
        # Initialize H2-specific resources if needed
        # h2.initialize()
        connection = h2.dbapi.connect(":memory:")
        cursor = connection.cursor()


    elif database_context.lower() == "postgresql":
        # Initialize PostgreSQL-specific resources if needed
        # psycopg2.connect(...)

    else:
        print("Invalid database context. Supported values: h2, postgresql")
        sys.exit(1)

    # Print the result for Java to retrieve
    print(result)
