package mapreduce.air.combiner;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AirCombinerReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable resultVal = new IntWritable();

	@Override
	protected void reduce(Text key, 
			Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) 
					throws IOException, InterruptedException {
		int sum=0;
		for(IntWritable value: values) {
			sum = sum + value.get();
		}
		resultVal.set(sum);
		context.write(key, resultVal);
		// reducer가 call 될 때마다 하나씩 증가한다.
		// map메소드나 reduce메소드는 input데이터 개수만큼 실행된다!
		context.getCounter("mycounter", "combiner_wordcount").increment(1);
	}
}
