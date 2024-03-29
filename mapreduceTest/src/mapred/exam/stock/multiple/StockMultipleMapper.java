package mapred.exam.stock.multiple;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StockMultipleMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	Text outputKey = new Text(); //output key
	static final IntWritable outputVal = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// user log: byteoff 첫번째 줄 0이므로 첫번째 줄 무시
		if(key.get() > 0) {
			//StringTokenizer st = new StringTokenizer(value.toString(), ",");
			// split을 쓰면 배열로 나오므로 편리하다 + 라인 하나에 대해 한번만 실행
			String[] line = value.toString().split(",");
			if(line != null & line.length > 0) {
				String year = line[2].substring(0,4);
				double result = Double.parseDouble(line[6])
						- Double.parseDouble(line[3]);
				if(result>0) { //상승마감
					outputKey.set("up," + year); 
					context.write(outputKey, outputVal);
				}else if(result<0) { //하락마감
					outputKey.set("down," +year);
					context.write(outputKey, outputVal);
				}else {	//동일마감
					outputKey.set("equal," +year);
					context.write(outputKey, outputVal);
				}
			
			}
				
		}
	}
}
