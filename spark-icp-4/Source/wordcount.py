import sys
import os

os.environ["SPARK_HOME"] = "C:\spark-2.4.5-bin-hadoop2.7"
os.environ["HADOOP_HOME"] = "C:\winutils"

from pyspark import SparkContext
from pyspark.streaming import StreamingContext

# creating spark context and sparkstreaming context
sparkcontext = SparkContext("local[2]", "NetworkWordCount")
ssparkcontext = StreamingContext(sparkcontext, 5)

# connecting to socket 5000
lines = ssparkcontext.socketTextStream("localhost", 6000)

# Splittin line into words
words = lines.flatMap(lambda line: line.split(" "))

# Counting words in batches
pairs = words.map(lambda word: (word, 1))
wordCounts = pairs.reduceByKey(lambda x, y: x + y)

# print elements  of rdd in dstream to console
wordCounts.pprint()

ssparkcontext.start()  # Start
ssparkcontext.awaitTermination()

