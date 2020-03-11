package mapreduce.page.sort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupKeyComparator extends WritableComparator{
	// 기본생성자
	protected GroupKeyComparator() {
		super(MyKey.class, true); //내부에서 처리
	}

	// WritableComparable의 타입이 정확하지 않기 때문에 warning이 발생
	// 1. WritableComparable<CustomKey>로 정의
	// 2. annotation: 제네릭을 지정하지 않은 모든 객체에 대해서 타입체크 하지 않고 처리하겠다는 의미
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable obj1, WritableComparable obj2) {
		MyKey key1 = (MyKey)obj1;
		MyKey key2 = (MyKey)obj2;
		return key1.getProductId().compareTo(key2.getProductId());
	}
	
	

}
