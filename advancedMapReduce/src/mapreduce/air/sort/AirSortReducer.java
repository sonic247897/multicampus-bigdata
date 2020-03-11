package mapreduce.air.sort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AirSortReducer extends Reducer<CustomKey, IntWritable, CustomKey, IntWritable>{
	IntWritable resultVal = new IntWritable();
	CustomKey resultKey = new CustomKey();
	Text appenddata = new Text();
	String data = "";
	// 리듀스는 그룹별로 하나씩만 호출된다!
	@Override
	protected void reduce(CustomKey key, 
			Iterable<IntWritable> values,
			Reducer<CustomKey, IntWritable, CustomKey, IntWritable>.Context context) 
					throws IOException, InterruptedException {
		// 프레임워크  - reduce 메소드에서는 CustomKey 하나만 매개변수로 받지만
		// 리듀스를 생성해서 전달하는 객체에서 Grouping한 데이터를 다 보낸다.
		int sum=0;
		// month데이터를 추출
		data = data + "reduce호출";
		appenddata.set(data);
		// 제일 먼저 읽은 month값
		Integer beforeMonth = key.getMonth();
		int count=0;
		for (IntWritable value : values) {
			if(count<10) {
				System.out.println("reduce=>"+key);
				count++; // 리듀스가 내부적으로 병렬로 돌기 때문에 정확히 10번이 찍히지는 않는다.
			}
			// (map출력이 계속 달라진다)같지 않을경우 내보냄 - 월 데이터가 바뀌는 시점
			if(beforeMonth != key.getMonth()) {
				// CustomKey의 toString에 의해 year가 출력되므로 year에 붙여서 디버깅
				resultKey.setYear(key.getYear()+", "+appenddata+"(테스트문자열)"
						+key.hashCode()+"(해쉬코드), "+key.getMapkey()+"(map에서 전달한 키)");
				// 해쉬코드가 다 똑같다. - 동일한 CustomKey객체 사용(지우고 다시 할당하지x)
				// but. 키는 계속 달라진다(위에 if count문)
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
