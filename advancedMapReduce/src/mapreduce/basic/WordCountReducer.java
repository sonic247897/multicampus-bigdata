package mapreduce.basic;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer 
		extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable resultVal = new IntWritable();
	// 디버깅 - (key로 디버깅)키 개수만큼 reduce메소드 호출
	// : 리듀스 객체는 한번만 만들어진 상태에서 reduce메소드가 호출되는데 
	// 	키(여기서는 토큰) 개수만큼 호출되고 있다.
	Text appenddata = new Text();
	String data = "";
	Text resultKey = new Text();
	@Override
	protected void reduce(Text key, 
			Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		// value로 디버깅 하려면?
		// 	:reducer의 출력데이터를 IntWritable -> Text로 바꾸고 Mapper와 Driver도 바꿔주면 된다.
		int sum =0;
		data = data + "reduce호출";
		appenddata.set(data);
		for (IntWritable value: values) {
			sum = sum + value.get(); 
		}
		resultVal.set(sum);
		resultKey.set(key+":"+appenddata);
		context.write(resultKey, resultVal);
	}
	
}
