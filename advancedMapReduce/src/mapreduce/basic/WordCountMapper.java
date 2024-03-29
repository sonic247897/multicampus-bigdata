package mapreduce.basic;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper 
		extends Mapper<LongWritable, Text, Text, IntWritable> {
	static final IntWritable outputVal = new IntWritable(1);
	Text outputKey = new Text();
	@Override
	// 디버깅: 키나 value에 물려서 테스트
	// 출력데이터의 IntWritable->Text
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		StringTokenizer st = new StringTokenizer(value.toString(), " ");
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			outputKey.set(token); 
			context.write(outputKey, outputVal);
		}
	}
	
}
