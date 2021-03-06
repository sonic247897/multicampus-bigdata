package mapreduce.page.sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

// mapper 실행되기 전에 복합키 알아야 함
// 복합키를 정의 - 사용자정의 키(정렬할 기준을 컬럼으로 갖고 있는 객체)
// 맵리듀스 프레임워크 내부에서 키와 value는 네트워크에서 주고 받는 값이므로
// 네트워크 전송을 하기 위해 제공되는 Writable타입이어야 하므로
// WritableComparable을 상속받아 작성한다.
public class MyKey implements WritableComparable<MyKey>{
	private String productId;
	private String userId;
	
	public MyKey() {
		
	}
	
	public MyKey(String productId, String userId) {
		super();
		this.productId = productId;
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		// +로 연결되는 것은 하나하나가 String객체로 올라오는데,
		// CustomKey는 mapper나 reducer의 input data에 하나하나 매핑되어서 들어오므로
		// 객체가 매우 많이 만들어진다.
		// 	=>StringBuffer로 수정!
		return (new StringBuffer()).append(productId).append(", ").
				append(userId).toString();
	}
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	// <네트워크>
	// 데이터를 쓰고 읽는 작업을 처리
	// 데이터를 쓰기 - 직렬화 (패킷 단위로 구분해서 써야 함)
	// 데이터를 읽기 - 역직렬화
	//	=> 내부적으로 자바의 API를 이용해서 구현 [java.io.DataInput]
	// 하둡의 맵리듀스 내부에서 이런 작업을 처리할 수 있도록 구현된 메소드를 호출해서 처리

	
	// 직렬화될 때 호출되는 메소드
	@Override
	public void write(DataOutput out) throws IOException {
		// 참조형 데이터는 byte단위로 쪼개서 전송해야한다.
		// - 하둡이 제공하는 outputStream(WritableUtils함수?)에 id변수를 내보냄
		WritableUtils.writeString(out, productId);
		WritableUtils.writeString(out, userId);
	}
	
	// 역직렬화될 때 호출되는 메소드
	@Override
	public void readFields(DataInput in) throws IOException {
		productId = WritableUtils.readString(in);
		userId = WritableUtils.readString(in);
	}

	// 사용자가 만들어 놓은 키를 기준으로 정렬하기 위해서 비교하게 할 메소드
	// productId로 비교, productId 같으면 userId로 비교
	
	// 기준을 여러개 정하고 싶지만 키는 1개밖에 못 넘긴다. (mapper나 reducer가 1개 키만 받음) 
	//		=> 클래스 만들어야 함!
	// cf. 배열 - 같은종류 여러개 return/ 객체 - 다른종류 여러개 reutrn
	@Override
	public int compareTo(MyKey obj) {
		int result = productId.compareTo(obj.productId);
		if(result ==0) {
			result = userId.compareTo(obj.userId);
		}
		return result;
	}
	
}
