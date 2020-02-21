package mapred.exam.air.multiple;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class AirMultipleReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable resultVal = new IntWritable();
	Text resultKey = new Text();
	MultipleOutputs<Text, IntWritable> multiOut;
	
	@Override
	protected void setup(Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		multiOut = new MultipleOutputs<Text, IntWritable>(context);
	}
	
	@Override
	protected void cleanup(Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		multiOut.close();
	}

	@Override
	protected void reduce(Text key, 
			Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) 
					throws IOException, InterruptedException {
		
		// key가 안옴
		String[] data = key.toString().split(",");
		resultKey.set(data[1]);
		
		multiReduce(data, "dep", values);
		multiReduce(data, "NAdep", values);
		multiReduce(data, "arr", values);
		multiReduce(data, "NAarr", values);
		
		/*if(data[0].equals("dep")) {
			int sum=0;
			for(IntWritable value: values) {
				sum = sum + value.get();
			}
			resultVal.set(sum);
			// context.write(key, resultVal);
			multiOut.write("dep", resultKey, resultVal);
		}else if(data[0].equals("NAdep")) {
			int sum=0;
			for(IntWritable value: values) {
				sum = sum + value.get();
			}
			resultVal.set(sum);
			// context.write(key, resultVal);
			multiOut.write("NAdep", resultKey, resultVal);
		}else if(data[0].equals("arr")) {
			int sum=0;
			for(IntWritable value: values) {
				sum = sum + value.get();
			}
			resultVal.set(sum);
			multiOut.write("arr", resultKey, resultVal);
		}
		else if(data[0].equals("NAarr")) {
			int sum=0;
			for(IntWritable value: values) {
				sum = sum + value.get();
			}
			resultVal.set(sum);
			// context.write(key, resultVal);
			multiOut.write("NAarr", resultKey, resultVal);
	
		}*/
	}
	
	protected void multiReduce(String[] data, String jobType, Iterable<IntWritable> values) 
			throws IOException, InterruptedException {
		if(data[0].equals(jobType)) {
			int sum=0;
			for(IntWritable value: values) {
				sum = sum + value.get();
			}
			resultVal.set(sum);
			// context.write(key, resultVal);
			multiOut.write(jobType, resultKey, resultVal);
		}
	}
}
