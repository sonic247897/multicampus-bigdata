package pattern.exam;

import java.util.regex.Pattern;

// 8글자 이상, 대문자, 소문자, 특수문자, 숫자가 모두 포함된 pattern 연습하면서 작업했던 코드 rename
public class PasswordCheck {
	public static boolean isPassword(String str) {
		// \\w: 대문자, 소문자, 숫자 모두
		// \\W: 대문자, 소문자, 숫자 빼고 모두(한글, 특수문자) - 한글 = 특수문자
		// 공백, 한글 제외
		// 순서 상관x
		String passreg= "((\\W[^ㄱ-ㅎ가-힣]^ )+[0-9]+[A-Z]+[a-z]+){8,}";
		return Pattern.matches(passreg, str);
	}
	
	public static void main(String[] args) {
    	System.out.println(isPassword("2345"));//false
    	System.out.println(isPassword("2345한글"));//false
    	System.out.println(isPassword("2345****"));//true
    	System.out.println("***********isEng테스트*************");
    	System.out.println(isPassword("aasdfgasdf"));//false
    	System.out.println(isPassword("aasdfgas df"));//false
    	System.out.println(isPassword("aasdfgas**df"));//false
    	System.out.println(isPassword("aasdfgas1221df"));//false
    	System.out.println(isPassword("aasdfgas한글df"));//false
    	System.out.println("***********isKor테스트*************");
    	System.out.println(isPassword("aasdfgas한글df"));//false
    	System.out.println(isPassword(" 한글"));//true
    	System.out.println(isPassword("한글 한긆 ㄴㅇㄻㅁㄹ"));//true
    	System.out.println(isPassword("123한글"));//false
    	
    	System.out.println("***********isEngNum테스트*************");
    	System.out.println(isPassword("aasdfgas한글df"));//false
    	System.out.println(isPassword(" 한글"));//false
    	System.out.println(isPassword("   111   asdfa"));//true
    	System.out.println(isPassword("dfgad998"));//true
    	  	System.out.println("***********isKorNum테스트*************");
    	System.out.println(isPassword("aasdfgas한글df"));//false
    	System.out.println(isPassword(" 한글"));//true
    	System.out.println(isPassword("   111   asdfa"));//false
    	System.out.println(isPassword("한글998"));//true
		
	}

}
