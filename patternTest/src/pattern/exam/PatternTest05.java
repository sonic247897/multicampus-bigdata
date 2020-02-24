package pattern.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest05 {
	public static void main(String[] args) {
		//String str = "tomato jaava tomato prog potato";
		//String patternString = "(tom|pot)ato";
		
		//String str = "aaaaa aaabc iiiii iiibc aiabc ";
		//String patternString = "(a|i){3}bc"; 
		// a가 세번 반복되거나, i가 세번 반복되거나, a와 i가 섞어서 세번 반복되거나 +bc
		
		String str = "adsf111 a1 b5 b55 aaa5 qa5 5a";
		String patternString = "([a-z][0-9])";
		// 그룹으로 묶었기 때문에 5a는 나오지 않는다.
		
		equalsPattern(str, patternString);
	}
	
	public static void equalsPattern(String str, String patternString) {
		// 1. 프로그램 내에서 사용할 수 있게 패턴을 인식시키는 작업
		Pattern pattern = Pattern.compile(patternString);
		// 2. 패턴을 적용하여 문자열을 관리
		// 	- Matcher는 new로 생성하는 것이 아니라(생성자가 따로 없다) 만들어놓은 Pattern클래스의
		//	 matcher메소드로 비교할 문자열을 주면서 만들어야 한다.
		Matcher m = pattern.matcher(str);
		
		// ex) 페이지뷰 만들 때 로그 기록
		while(m.find()) {
			System.out.println(m.group());
			System.out.println(m.start()+" : "+(m.end()-1));
		}
	}
}
