package mapred.exam01;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapperExam 
		extends Mapper<LongWritable, Text, Text, IntWritable>{
	static final IntWritable outputVal = new IntWritable(1);
	Text outputKey = new Text();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		StringTokenizer st = new StringTokenizer(value.toString());
		// 하둡 콘솔에는 찍히지 않는다.
		// TaskNode들의 hadoop-1.2.1>logs>userlogs
		System.out.println(key +" : "+value);
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			// 5글자 이상인 것만 내보낸다
			if(token.length()>=5) {
				outputKey.set(token);
				context.write(outputKey, outputVal);
			}
		}
	}
}
