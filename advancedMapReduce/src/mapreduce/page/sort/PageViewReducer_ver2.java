package mapreduce.page.sort;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageViewReducer_ver2 extends Reducer<MyKey, Text, Text, Text>{
	Text resultVal = new Text();
	Text resultKey = new Text();
	@Override
	protected void reduce(MyKey key, 
			Iterable<Text> values,
			Reducer<MyKey, Text, Text, Text>.Context context) 
					throws IOException, InterruptedException {
		// 그룹핑(ProductId)된 데이터 마다 reduce 메소드 한번 호출
		int user = 0; // value가 userId이므로 user를 반복문 처음부터 셀 수 있다.
		int click = 0; //하나의 상품이 클릭된 총 횟수
		String beforeUser = "";
		for (Text value : values) {			
			String currentUser = value.toString();
			if(!beforeUser.equals(currentUser)) {
				user++; //사용자가 다른 경우
			}				
			click++; //하나의 상품에 접속한 모든 클릭횟수
			beforeUser = currentUser;
		}
		//상품코드가 바뀔때마다 출력
		resultKey.set(key.getProductId());			
		StringBuffer data = new StringBuffer();
		data.append(user).append("\t").append(click);
		resultVal.set(data.toString());
		context.write(resultKey, resultVal);
	}
}
