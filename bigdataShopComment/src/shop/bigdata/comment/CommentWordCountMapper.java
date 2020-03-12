package shop.bigdata.comment;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CommentWordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	static final IntWritable outputVal = new IntWritable(1);
	Text outputKey = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split(",");
		if(line != null & line.length > 0) {
			Pattern p = Pattern.compile("은|는|이|가|요|서");
			Matcher m = p.matcher(line[2]);
			//패턴에 일치하지 않는 문자들만 추출해서 저장
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String data = m.group(); //패턴과 일치하는 단어
				System.out.println(data);
				//패턴에 만족하는 문자열을 ""로 치환한 후 전체 문자열을 
				//StringBuffer에 저장 (치환할 때 패턴문자 - \,$ 등은 쓰지 못한다!)
				m.appendReplacement(sb, ""); //""로 치환->지워버린다
				// 패턴이 없는 것들은 무시해서 잘라버린다.
			}
			// 조건에 만족하지 않아도 추가할 수 있도록 구현
			m.appendTail(sb); // 패턴을 못 찾은 나머지 문장을 끝에 추가
			String[] result = sb.toString().split(" ");
			for (int i = 0; i < result.length; i++) {
				outputKey.set(result[i]); 
				context.write(outputKey, outputVal);
			}
		}
	}
}
