package mapred.exam.air;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AirMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	Text outputKey = new Text(); //output key
	static final IntWritable outputVal = new IntWritable(1);
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// user log: byteoff 첫번째 줄 0이므로 첫번째 줄 무시
		if(key.get() > 0) {
			String[] line = value.toString().split(",");
			if(line != null & line.length > 0) {
				// String은 '!=' 비교하면 안됨
				// &을 쓰면 false여도 뒤로 넘어가버려서 에러가 뜬다.
				// 	=> &&을 써야 함!
				if(!line[15].equals("NA") && Integer.parseInt(line[15])>0) {
					// 연도를 연결하지 않으면 10, 11, 12월이 1987, 1988년 합쳐져서 계산된다.
					outputKey.set(line[0]+"년 "+ line[1]+"월");
					context.write(outputKey, outputVal);
				}
			}
		}
	}
}