package mapreduce.air.sort;

public class Test {
	public static void main(String[] args) {
		// 문자열 비교(아스키) - compareTo
		/*
		 * compareTo의 결과
		 * 0 		: 기준문자열, 매개변수 문자열이 같은 경우
		 * -1 (음수)	: 기준문자열 < 매개변수 문자열
		 * 1 (양수) 	: 기준문자열 > 매개변수 문자열
		 * 
		 */
		String data1 = "b";
		String data2 = "a";
		System.out.println((int)'b');
		System.out.println((int)'a');
		System.out.println(data1.compareTo(data2)); //기준= data1-data2
		
		// hashcode
		// 해시코드란 객체를 구분할 수 있는 정수값
		//	=> 정수값은 객체가 할당된 주소를 가지고 생성한다.
		//	=> hashcode메소드를 이용하면 이 정수값이 리턴된다.
		CustomKey key1 = new CustomKey();
		CustomKey key2 = new CustomKey();
		// 1. 주소가 아니라 해시코드 출력! 객체를 참조하는 참조값(영문 섞어서 만들어져 있음)
		// 패키지~클래스명 @ 주소로 만든 해시코드(16진수)
		System.out.println(key1);
		System.out.println(key2);
		// 2. (기본)해시코드 메소드 - 구분할 수 있는 정수값 리턴 (1번을 10진수로 풀어낸 것)
		System.out.println(key1.hashCode());
		System.out.println(key2.hashCode());
		
		key1.setYear("1987");
		key2.setYear("1987");
		// 3. 문자열에서 호출하는 해시코드 메소드 - Object의 hashcode메소드가 오버라이딩 되어
		// 주소를 가지고 정수값을 만들지 않고 문자열을 이용해서 구분하는 정수값을 만들어서 리턴한다.
		//	=> 따라서 문자열이 같으면 hashcode가 같다.
		//	=> partitioner가 내부에서 해시코드가 같은 것들을 분류해서 reducer로 전달
		System.out.println(key1.getYear().hashCode());
		System.out.println(key2.getYear().hashCode());
		
	}

}
