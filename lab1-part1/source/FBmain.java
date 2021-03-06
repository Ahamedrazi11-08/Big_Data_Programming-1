

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.util.GenericOptionsParser;

public class FBmain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration c = new Configuration();

		//String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		//Path input = new Path(files[0]);
		//Path output = new Path(files[1]);
		Job job = new Job(c, "Facebook Common Friends");
		job.setJarByClass(FBmain.class);
		job.setMapperClass(FBfriendsmap.class);
		job.setReducerClass(FBfriendsreduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}