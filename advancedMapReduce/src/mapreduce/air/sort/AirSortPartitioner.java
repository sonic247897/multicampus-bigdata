package mapreduce.air.sort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

// Partitioner클래스에는 Mapper의 key와 value타입을 generic으로 명시
// year를 기준으로 해시코드를 구해서 같은 year를 갖고 있는 데이터를 
// 같은 리듀서에서 작업할 수 있도록 분배
//	=> 같은 것끼리 메모리 버퍼에 쌓았다가 한꺼번에 전송(한번에 하나씩 하는 것보다 부하 감소)
public class AirSortPartitioner 
				extends Partitioner<CustomKey, IntWritable>{
	
	// numPartitions는 계산된 파티션의 개수 = 리듀스태스크의 개수
	@Override
	public int getPartition(CustomKey key, IntWritable value, int numPartitions) {
		// 리듀스태스크의 번호를 구해서 리턴 (0부터 시작)
		return key.getYear().hashCode() % numPartitions;
	}
	
	
}
