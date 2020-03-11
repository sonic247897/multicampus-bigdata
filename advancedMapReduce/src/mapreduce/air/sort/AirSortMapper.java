package mapreduce.air.sort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AirSortMapper extends Mapper<LongWritable, Text, CustomKey, IntWritable>{
	CustomKey outputKey = new CustomKey(); //output key
	static final IntWritable outputVal = new IntWritable(1);
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, CustomKey, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// user log: byteoff 첫번째 줄 0이므로 첫번째 줄 무시
		if(key.get() > 0) {
			String[] line = value.toString().split(",");
			if(line != null & line.length > 0) {
				if(!line[15].equals("NA") && Integer.parseInt(line[15])>0) {
					outputKey.setYear(line[0]);
					outputKey.setMonth(new Integer(line[1]));
					// byteoffset 넘기기 
					// map에서 내보낼 때마다 다르다. reducer에서 읽는 map키
					outputKey.setMapkey(key.get()); 
					context.write(outputKey, outputVal);
				}
			}
		}
	}
}