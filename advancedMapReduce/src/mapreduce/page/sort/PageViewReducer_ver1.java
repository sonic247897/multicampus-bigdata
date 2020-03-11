package mapreduce.page.sort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageViewReducer_ver1 extends Reducer<MyKey, IntWritable, Text, Text>{
	Text resultKey = new Text();
	Text resultVal = new Text();
	@Override
	protected void reduce(MyKey key, 
			Iterable<IntWritable> values,
			Reducer<MyKey, IntWritable, Text, Text>.Context context) 
					throws IOException, InterruptedException {
		// 그룹핑(ProductId)된 데이터 마다 reduce 메소드 한번 호출
		// 제일 먼저 읽은 userId값
		String beforeUserId = key.getUserId();
		int user = 1; // Iterator 안에서만 key를 자동할당 해주므로 첫번째 user수는 카운터할 수 없다.
		int click = 0; //하나의 상품이 클릭된 총 횟수
		for (IntWritable value : values) {
			System.out.println(key.toString());
			if(!beforeUserId.equals(key.getUserId())) {
				user++;
			}
			click = click + value.get();
			beforeUserId = key.getUserId();
		}
		resultKey.set(key.getProductId());
		resultVal.set(user+"\t"+click);
		context.write(resultKey, resultVal);
	}
}
