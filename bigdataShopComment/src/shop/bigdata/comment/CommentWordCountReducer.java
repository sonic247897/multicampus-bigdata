package shop.bigdata.comment;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CommentWordCountReducer 
			extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable resultVal = new IntWritable();

	@Override
	protected void reduce(Text key, 
			Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum =0;
		for (IntWritable value: values) {
			// reduce에 전달된 입력데이터의 값을 꺼내서 모두 더하기
			sum = sum + value.get(); //get메소드 - IntWritable에서 int값만 꺼내 쓸 수 있음
		}
		resultVal.set(sum); // 계산된 결과를 IntWritable에 셋팅
		// reduce의 실행결과를 context에 write (프레임워크가 내부에서 HDFS에 저장)
		context.write(key, resultVal);
	}
}
