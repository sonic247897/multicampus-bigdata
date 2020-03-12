package pattern.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAPIText {
	public static void main(String[] args) {
		String value = "배송이 너무 느려요 상품가 좋아서 예뻐요 좋아 좋아"; 
		Pattern p = Pattern.compile("은|는|이|가|요|서");
		Matcher m = p.matcher(value);
		//패턴에 일치하지 않는 문자들만 추출해서 저장
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String data = m.group(); //패턴과 일치하는 단어
			System.out.println(data);
			//패턴에 만족하는 문자열을 ""로 치환한 후 전체 문자열을 
			//StringBuffer에 저장 (치환할 때 패턴문자 - \,$ 등은 쓰지 못한다!)
			m.appendReplacement(sb, ""); //""로 치환->지워버린다
			// 마지막 "좋아 좋아" 패턴이 없는 것들은 무시해서 잘라버린다.
		}
		// 조건에 만족하지 않아도 추가할 수 있도록 구현
		m.appendTail(sb); // 패턴을 못 찾은 나머지 문장을 끝에 추가
		System.out.println(sb);
		String[] result = sb.toString().split(" ");
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
}
