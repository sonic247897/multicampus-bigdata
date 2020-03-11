package mapreduce.page.sort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageViewMapper_ver1 extends Mapper<LongWritable, Text, MyKey, IntWritable>{
	MyKey outputKey = new MyKey(); //output key
	static final IntWritable one = new IntWritable(1);
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, MyKey, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split("\\t"); //탭키로 구분(스페이스바 x)
		if(line != null & line.length > 0) {
			// data[2] = productId, data[9] = userId
			outputKey.setProductId(line[2]);
			outputKey.setUserId(line[9]);
			context.write(outputKey, one);
		}
	}
	
}