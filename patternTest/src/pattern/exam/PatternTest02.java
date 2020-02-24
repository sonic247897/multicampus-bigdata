package pattern.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest02 {
	public static void main(String[] args) {
		//String str = "jaava programmaingm ";
		//String patternString = "a|m|g"; //1. |는 or을 의미
		// => a이거나 m이거나 g인 문자열을 추출
		//String patternString = "[amg]"; // 2. 1번과 동일
		//String patternString = "[amg][ma]"; // 3. 두 글자에 대해서 2번의 조건 적용
		// => 첫 글자가 a,m,g이거나 두 번째 글자가 m,a인 문자
		//String patternString = "[c-j]"; // 4. c-j사이에 해당하는 문자
		//	=> c, d, e, f, g, h, i, j
		//String patternString = "[C-J]"; //대문자(insensitive라 소문자도 다 나옴)
		//String patternString = "[A-Bc-j]"; // 대문자 A, B 소문자 c에서 j까지 문자 추출
		
		String str =
				"ja1111aCva--@@-@@@@- 한글 --@@@@-- progra44568EmgFmiJng";
		//String patternString = "[C-Jc-j]";
		//String patternString = "[4-8]"; // 4에서 8사이 숫자를 추출
		//String patternString = "[^4-8]"; // ^가 [] 안에 있으면 부정의 의미
		//	=> 숫자 4,5,6,7,8이 아닌 문자
			// ex1) c~j 사이의 영문자가 아닌 것
			//String patternString = "[^c-j]";
			// ex2) 영문자와 숫자만 추출
			//String patternString = "[A-Za-z0-9]";
			// ex3) 영문자와 숫자를 뺀 나머지 문자만 추출
			//String patternString = "[^A-Za-z0-9]";
			// ex4) 한글만 추출
			String patternString = "[가-힣]";
		
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
