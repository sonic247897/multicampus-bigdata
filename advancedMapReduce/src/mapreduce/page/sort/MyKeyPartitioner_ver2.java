package mapreduce.page.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

// Partitioner클래스에는 Mapper의 key와 value타입을 generic으로 명시
public class MyKeyPartitioner_ver2
				extends Partitioner<MyKey, Text>{
	
	// numPartitions는 계산된 파티션의 개수 = 리듀스태스크의 개수
	@Override
	public int getPartition(MyKey key, Text value, int numPartitions) {
		// 리듀스태스크의 번호를 구해서 리턴 (0부터 시작)
		return key.getProductId().hashCode() % numPartitions;
	}
	
	
}
