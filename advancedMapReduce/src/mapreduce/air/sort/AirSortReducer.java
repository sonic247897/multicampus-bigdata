package mapreduce.air.sort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class AirSortReducer extends Reducer<CustomKey, IntWritable, CustomKey, IntWritable>{
	IntWritable resultVal = new IntWritable();
	CustomKey resultKey = new CustomKey();
	@Override
	protected void reduce(CustomKey key, 
			Iterable<IntWritable> values,
			Reducer<CustomKey, IntWritable, CustomKey, IntWritable>.Context context) 
					throws IOException, InterruptedException {
		int sum=0;
		// month데이터를 추출
		Integer beforeMonth = key.getMonth();
		for (IntWritable value : values) {
			// 같지 않을경우 내보냄
			if(beforeMonth != key.getMonth()) {
				resultKey.setYear(key.getYear());
				resultKey.setMonth(beforeMonth);
				resultVal.set(sum);
				context.write(resultKey, resultVal);
				sum = 0;
			}
			// 같을 경우 계산
			sum = sum + value.get();
			beforeMonth = key.getMonth();
		}
		// values에 전달된 값들을 반복작업하며 마지막에 같은 키를 갖고 있는 값을
		// 한꺼번에 출력 - 키(year, month)가 같은 경우
		if(key.getMonth() == beforeMonth) {
			resultKey.setYear(key.getYear());
			resultKey.setMonth(key.getMonth());
			resultVal.set(sum);
			context.write(resultKey, resultVal);
		}
	}
}
