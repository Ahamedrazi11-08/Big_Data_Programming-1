import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class FBfriendsreduce extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text Key, Iterable<Text> inval,
			Context context) throws IOException, InterruptedException {
		String outKey = Key.toString();
		Set<String> tokens = new HashSet<>();
		String outVal = " ==>{ ";
		
		for (Text vall : inval) {
			System.out.println(vall.toString());
			for (String token : vall.toString().split(("\\s+"))) {
				System.out.println(token);
				if (tokens.contains(token)) {
					outVal += token + " ";
				}
				// Set cannot have duplicate values, so its okay to add all
				// values. same values will be overwritten
				tokens.add(token);
			}
		}
		outVal += "}";
		outKey = "{ " + outKey + " }";
		context.write(new Text(outKey), new Text(outVal));
		System.out.println(outKey + outVal);

	}

}