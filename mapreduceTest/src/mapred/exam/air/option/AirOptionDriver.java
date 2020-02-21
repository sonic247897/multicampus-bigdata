package mapred.exam.air.option;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * 사용자가 -D 옵션을 이용해서 입력한 옵션값을 프로그램 안에서 사용하기 위해서
 *  즉, Mapper가 사용할 수 있도록 전달 (extends, implements)
 *  1. Configured와 Tool을 상속해야 한다.
 *  	=> Configured는 환경설정 정보를 활용
 *  	=> Tool은 사용자정의 옵션을 사용하기 위해
 *  2. run메소드를 오버라이딩
 *  	=> run메소드 안에 Driver에서 구현했던 모든 코드를 구현
 *  3. run메소드를 main메소드에서 실행되도록 호출해야 한다.
 *  	=> 프레임워크 구조적으로 run메소드를 직접 호출할 수 없고 ToolRunner라는 헬퍼 클래스를 이용해서 호출  
 *  	(application이므로 main에서 호출해야 하므로 run메소드는 혼자서 호출될 수 없다)
 */

public class AirOptionDriver extends Configured implements Tool{
	// run메소드는 사용자가 입력한 모든 옵션에 대한 정보를 String[]으로 전달받는다.
	@Override
	public int run(String[] optionlist) throws Exception {
		// 그냥 설정정보가 아니라 Configured가 갖고 있는 getconf() 전달
		// -D를 입력하고 설정한 옵션과 사용자가 입력한 명령행 매개변수를 분리하여 관리해야 한다.
		GenericOptionsParser parser = new GenericOptionsParser(getConf(), optionlist);
		// getRemainingArgs()를 이용하여 공통옵션(-D와 입력한 값 이외의 입력값)과 
		// 사용자가 입력한 옵션을 따로 분리한다. -> 옵션과 함께 쓰지 않은 입력값만 따로 갖고있겠다.
		String[] otherArgs = parser.getRemainingArgs();
		
		/*Configuration conf = new Configuration();*/
		Job job = new Job(getConf(), "airoption");
		
		job.setMapperClass(AirOptionMapper.class);
		job.setReducerClass(AirOptionReducer.class);
		job.setJarByClass(AirOptionDriver.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// 사용자가 입력한 명령행 매개변수
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		job.waitForCompletion(true);
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new AirOptionDriver(), args);
	}

}
