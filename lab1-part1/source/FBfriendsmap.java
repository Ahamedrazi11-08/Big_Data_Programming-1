
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;



public class FBfriendsmap extends
		Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable Key, Text Value, Context context)
			throws IOException, InterruptedException {
		Text outText;
		String Line = Value.toString();
		String[] KvArray = Line.split("=>");
		

		if (KvArray.length >= 2) {
			String key = KvArray[0].trim();
			KvArray[1] = KvArray[1].trim();
			Text outVal = new Text(KvArray[1]);
			//System.out.println("Key: " + key + " Value: " + KvArray[1]);
			
			for (String nextKey : KvArray[1].split("\\s+")) {
				if (key.compareToIgnoreCase(nextKey) < 0) {
					String outKey = key + " " + nextKey;
					outText = new Text(outKey);
					context.write(outText, outVal);
				} else {
					String outKey = nextKey + " " + key;
					outText = new Text(outKey);
					context.write(outText, outVal);
				}
				//System.out.println(outText.toString() + " ->  " + outVal.toString());
			}

		}
	}
}