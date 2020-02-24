package pattern.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest01 {
	public static void main(String[] args) {
		//String str = "java, h1 ~~~ java";
		//String patternString = "java"; // 1. 정확하게 일치하는 것
		//String patternString = "^java"; // 2. ^뒤의 문자열로 시작
		//String patternString = "java$"; // 3. $ 앞의 문자열로 종료
		
		String str = "$100. .한 $20.0 ^^$";
		// 4. 패턴에서 사용하는 기호는 일반 문자열로 인식하지 않는다.
		//String patternString = "^\\$"; // $로 시작하는지 확인
		// 패턴에서 사용되는 기호를 문자열로 인식시키려면 \\와 함께 사용
		// 	(\도 기호로써 인식되므로 한 번 더 써준다.)
		//String patternString = "\\$$"; // $로 끝나는지 확인
		//String patternString = "."; // 6. .은 글자 하나를 의미
		// group() 때문에 한 글자씩 잘라서 리턴됨
		//String patternString = "...."; // 문자길이가 4
		//String patternString = "\\."; // 7. .이 포함된 문자열
		String patternString = "\\..\\."; // 8. .과 . 사이에 한 글자만 있는 문자열
		
		equalsPattern(str, patternString);
	}
	
	public static void equalsPattern(String str, String patternString) {
		// 1. 프로그램 내에서 사용할 수 있게 패턴을 인식시키는 작업  - CASE_INSENSITIVE
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		// 2. 패턴을 적용하여 문자열을 관리
		// 	- Matcher는 new로 생성하는 것이 아니라(생성자가 따로 없다) 만들어놓은 Pattern클래스의
		//	 matcher메소드로 비교할 문자열을 주면서 만들어야 한다.
		Matcher m = pattern.matcher(str);
		/*System.out.println(m.find());
		System.out.println(m.start());
		System.out.println(m.end());
		System.out.println(m.group());*/
		
		// ex) 페이지뷰 만들 때 로그 기록
		while(m.find()) {
			System.out.println(m.group());
			System.out.println(m.start()+" : "+(m.end()-1));
		}
	}
}
