package hdfs.exam;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/*
 *  - hadoop hdfs에 api를 이용해서 저장된 파일을 읽어서 콘솔에 출력하기
 */

public class HDFSExam02 {
	public static void main(String[] args) {
		// 1. hdfs를 제어하기 위해서 설정 파일을 읽어서 사용해야 하므로
		//	  hadoop 설치 폴더의 설정파일을 접근하기 위해 제공되는 클래스
		Configuration conf = new Configuration();
		// 2. HDFS를 접근하기 위해서 제공되는 객체 생성 - HDFS객체
		FileSystem hdfs = null;
		// 3. HDFS로부터 input하기 위한 스트림 객체
		FSDataInputStream hdfsIn = null;
		
		try {
			hdfs = FileSystem.get(conf);
			// 4. HDFS의 경로를 표현할 수 있는 객체
			//	=> HDFS로부터 읽을 파일의 경로를 명령행 매개변수로 받아서 적용하겠다는 의미
			Path path = new Path(args[0]);
			// 5. HDFS에 저장된 파일을 읽어야 하므로 스트림 생성하기
			hdfsIn = hdfs.open(path);
			// 6. 입력스트림을 통해 데이터를 읽는다.
			// 리눅스 콘솔에서 찍힘
			String data = hdfsIn.readUTF();
			System.out.println("hdfs에서 읽은 데이터: " + data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
