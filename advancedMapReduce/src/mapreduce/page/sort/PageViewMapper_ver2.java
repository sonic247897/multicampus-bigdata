package mapreduce.page.sort;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// MyKey에 포함된 컬럼이라고 해도 내보내고 싶다면 또 내보내도 된다.(userId)
// 마지막 매개변수를 IntWritable(1)에서 Text로 변경
public class PageViewMapper_ver2 extends Mapper<LongWritable, Text, MyKey, Text>{
	MyKey outputKey = new MyKey(); //output key
	Text outputVal = new Text();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, MyKey, Text>.Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split("\\t");
		if(line != null & line.length > 0) {
			outputKey.setProductId(line[2]);
			outputKey.setUserId(line[9]);
			outputVal.set(line[9]); //userId가 궁금해서 따로 또 내보내기
			context.write(outputKey, outputVal);
		}
	}
	
}