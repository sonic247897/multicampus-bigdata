package mapred.exam.air.option;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
// 하둡을 실행할 때 사용자가 입력하는 옵션을 Mapper 내부에서 사용할 수 있도록
// 옵션이 어떤 값으로 입력되었냐에 따라 다른 작업을 할 수 있도록 처리
public class AirOptionMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	String jobType; //사용자가 입력하는 옵션값을 저장하기 위한 변수
	
	Text outputKey = new Text(); //output key
	static final IntWritable outputVal = new IntWritable(1);
	// Mapper 실행될 때 한 번만 실행되는 메소드 (init메소드와 비슷함)
	@Override
	protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// 내부적으로 jobConf객체가 만들어지는데, jobConf객체의 "jobType"값(사용자가 입력한 값)을 가져온다.
		/*
		 * -D 옵션과 함께 사용자가 입력하는 jobType이라는 옵션의 지정한 값을
		 * 추출해서 Mapper에 선언된 변수에 저장
		 */
		jobType = context.getConfiguration().get("jobType");
	}

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// user log: byteoff 첫번째 줄 0이므로 첫번째 줄 무시
		if(key.get() > 0) {
			String[] line = value.toString().split(",");
			if(line != null & line.length > 0) {
				if(jobType.equals("departure")) {
					//출발지연
					// String은 '!=' 비교하면 안됨
					// &을 쓰면 false여도 뒤로 넘어가버려서 에러가 뜬다.
					// 	=> &&을 써야 함!
					if(!line[15].equals("NA") && Integer.parseInt(line[15])>0) {
						outputKey.set(line[1]+"월");
						context.write(outputKey, outputVal);
					}
				}else if(jobType.equals("arrival")) {
					// 중복되는 코드 - 클래스 따로 만들기
					if(!line[14].equals("NA") && Integer.parseInt(line[14])>0) {
						outputKey.set(line[1]+"월");
						context.write(outputKey, outputVal);
					}
				}
			}
		}
	}
}


