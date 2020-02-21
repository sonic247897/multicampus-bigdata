package mapred.exam.air.multiple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class AirMultipleDriver extends Configured implements Tool{
	// run메소드는 사용자가 입력한 모든 옵션에 대한 정보를 String[]으로 전달받는다.
	@Override
	public int run(String[] optionlist) throws Exception {
		// 그냥 설정정보가 아니라 Configured가 갖고 있는 getconf() 전달
		// -D를 입력하고 설정한 옵션과 사용자가 입력한 명령행 매개변수를 분리하여 관리해야 한다.
		GenericOptionsParser parser = new GenericOptionsParser(getConf(), optionlist);
		// getRemainingArgs()를 이용하여 공통옵션(-D와 입력한 값 이외의 입력값)과 
		// 사용자가 입력한 옵션을 따로 분리한다. -> 옵션과 함께 쓰지 않은 입력값만 따로 갖고있겠다.
		String[] otherArgs = parser.getRemainingArgs();
		
		/*Configuration conf = new Configuration();*/
		Job job = new Job(getConf(), "air_multi");
		
		job.setMapperClass(AirMultipleOptionMapper.class);
		job.setReducerClass(AirMultipleReducer.class);
		job.setJarByClass(AirMultipleDriver.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// 사용자가 입력한 명령행 매개변수
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		MultipleOutputs.addNamedOutput(job, "dep", 
				TextOutputFormat.class, Text.class, IntWritable.class);
		MultipleOutputs.addNamedOutput(job, "arr", 
				TextOutputFormat.class, Text.class, IntWritable.class);
		MultipleOutputs.addNamedOutput(job, "NAdep", 
				TextOutputFormat.class, Text.class, IntWritable.class);
		MultipleOutputs.addNamedOutput(job, "NAarr", 
				TextOutputFormat.class, Text.class, IntWritable.class);
		
		job.waitForCompletion(true);
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new AirMultipleDriver(), args);
	}

}
