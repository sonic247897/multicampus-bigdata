package pattern.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest03 {
	public static void main(String[] args) {
		//String str ="-@-ja1- -111aCva--@@-@@@@- 한글 --@@@@-- progra44568EmgFmiJng";
		String str 
			= "a 4 m 7 v 9 amJAVA _java aaaxl  programming and spring , hadoop";
		
		//String patternString = ".*"; // 모든 문자열 포함
		//String patternString = "-@+-"; // -사이에 골뱅이 기호가 1개 이상 
		//String patternString = "-@?-"; // -사이에 골뱅이 기호가 없거나 하나이거나
		//String patternString = "[^ ]"; // 공백이 아닌 문자
		//String patternString = ".{5}"; // 5글자씩 끊어서 나옴
		//String patternString = "[amv]{1,3}"; // a,m,v가 1회,2회,3회인 문자
		//	=> a, m, v,  aa, am, av, mv,  aaa, aam, amv, ...
		//String patternString = "[a-z]{3,}"; // 3글자 이상
		//String patternString = "\\W"; // 대문자, 소문자, 숫자 뺀 모두
		//String patternString = "\\w"; //대문자, 소문자, 숫자 모두
		//String patternString = "\\d"; // 숫자만
		String patternString = "\\D"; // 숫자를 뺀 나머지
		
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
