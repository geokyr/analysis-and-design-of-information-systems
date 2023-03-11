import pandas as pd

# Read the txt file with space as delimiter, skip first 4 rows (comments), only keep the source and target vertices and convert it to csv
df = pd.read_csv('web-Google.txt', sep='\s+', header=None, skiprows=4, usecols=[0,1])
df.to_csv('web-Google.csv', header=None, index=False)