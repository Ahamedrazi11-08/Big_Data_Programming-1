import os
from pyspark import SparkContext
from pyspark.streaming import StreamingContext




os.environ["SPARK_HOME"] = "C:\spark-2.4.5-bin-hadoop2.7"
os.environ["HADOOP_HOME"] = "C:\winutils"
spcontext = SparkContext("local[2]", "sparkstreaming")
logger = spcontext._jvm.org.apache.log4j
logger.LogManager.getLogger("org").setLevel(logger.Level.OFF)
logger.LogManager.getLogger("akka").setLevel(logger.Level.OFF)
sspcontext = StreamingContext(spcontext, 3)
lines = sspcontext.textFileStream("log")
words = lines.flatMap(lambda line: line.split(' '))
wordtup = words.map(lambda word: (word, 1))
wordcount = wordtup.reduceByKey(lambda x, y: x + y)
wordcount.pprint()
#print(wordcount)
sspcontext.start()
sspcontext.awaitTermination()
