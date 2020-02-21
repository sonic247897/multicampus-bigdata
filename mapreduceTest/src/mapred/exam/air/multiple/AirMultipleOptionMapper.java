package mapred.exam.air.multiple;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
// 하둡을 실행할 때 사용자가 입력하는 옵션을 Mapper 내부에서 사용할 수 있도록
// 옵션이 어떤 값으로 입력되었냐에 따라 다른 작업을 할 수 있도록 처리
public class AirMultipleOptionMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	
	Text outputKey = new Text(); //output key
	static final IntWritable outputVal = new IntWritable(1);

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// user log: byteoff 첫번째 줄 0이므로 첫번째 줄 무시
		if(key.get() > 0) {
			String[] line = value.toString().split(",");
			if(line != null & line.length > 0) {
				// 1. 출발지연
				// NA가 몇 개인지도 구함 (dep, arr 따로)
				multiOutProcess(line, "dep", context);
				multiOutProcess(line, "arr", context);
				
				/*if(!line[15].equals("NA")) {
					if(Integer.parseInt(line[15])>0) {
						outputKey.set("dep,"+line[1]+"월");
						context.write(outputKey, outputVal);
					}
				}else {
					outputKey.set("NAdep,"+line[1]+"월");
					context.write(outputKey, outputVal);
				}
				
				// 2. 도착지연
				if(!line[14].equals("NA")) {
					if(Integer.parseInt(line[14])>0) {
						outputKey.set("arr,"+line[1]+"월");
						context.write(outputKey, outputVal);
					}
				}else {
					outputKey.set("NAarr,"+line[1]+"월");
					context.write(outputKey, outputVal);
				}*/
					
				
				
			}
		}
	}
	
	// context.write 함수 밖으로 빼면
	// 지연(>0)이 없어서 key.set을 안 할 때도 context.write 해버린다. (nullPtr) 
	protected void multiOutProcess(String[] line, String jobType, 
			Mapper<LongWritable, Text, Text, IntWritable>.Context context) 
					throws IOException, InterruptedException{
		String col;
		if(jobType.equals("dep")) col = line[15];
		else col = line[14];
		
		if(!col.equals("NA")) {
			if(Integer.parseInt(col)>0) {
				outputKey.set(jobType +","+line[1]+"월");
				context.write(outputKey, outputVal);
			}
		}else {
			outputKey.set("NA"+jobType+","+line[1]+"월");
			context.write(outputKey, outputVal);
		}
	}
}

			


