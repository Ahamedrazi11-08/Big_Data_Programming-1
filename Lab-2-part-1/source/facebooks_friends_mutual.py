from pyspark import SparkContext


def mapping_eachline(each_line):

    each_line = each_line.split(" ")
    profile = each_line[0]
    friends = each_line[1]
    return profile, friends


def reduce(accum, value):
    return list(set(accum) & set(value))


def map_mapped(tuple):

    key = tuple[0]
    final = []
    for value in tuple[1]:
        if key < value:
            finalkey = key + "," + value
        else:
            finalkey = value + "," + key
        val = finalkey, list(tuple[1])
        final.append(val)
    return final


def run(input_file, output_file):
    spark_context = SparkContext.getOrCreate()
    lines_tobe_mapped = spark_context.textFile(input_file, 1)
    mapped = lines_tobe_mapped.map(mapping_eachline).groupByKey()
    grouped = mapped.flatMap(map_mapped)
    final = grouped.reduceByKey(reduce).filter(lambda x: len(x[1]) > 0)
    print(final.collect())
    final.coalesce(1).saveAsTextFile(output_file)


if __name__ == "__main__":
    run("facebook_combined.txt", "facebook_output_list_mutual")