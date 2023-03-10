import pandas as pd
df = pd.read_csv('data/web-Google.txt', sep='\s+', header=None, skiprows=4, usecols=[0,1])
df.to_csv('data/web-Google.csv', header=None, index=False)