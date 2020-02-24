package pattern.exam;

public class EmailCheck {
	public static void main(String[] args) {
		//x{n}=>x를 n번 반복한 문자를 찾는다는 의미로 해석
		// ^[A-z]: 대괄호 밖에 ^가 있으면 ^xxx로 시작하라는 뜻
		// \\.?: .이 한개 있거나 없거나
		// (\\.[A-z]+){1,2}: . 뒤에 영문자가 나오는 것이 한번이나 두번 반복될수 있음
		// $: 뒤에 다른 것이 나오지 않고 딱 끝나도록
		String emailReg="^[A-z]+\\.?[A-z0-9]+@[A-z]+(\\.[A-z]+){1,2}$"; //이메일 패턴을 정의
		
		String[] user = {"heaves@hanmail,net","heaves@hanmail.net",
					"테스트heaves@hanmail.net","sc.com@hanmail.net",
					",heaves@hanmail.net","@hanmail.net"
					,"heaves@hanmail.co.kr"};
		//user에 입력된 email의 형식이 맞는지 true|false로 출력할 수 있도록 작업	
		// false, true, false, true(도메인을 아이디로 사용함), false, false, true
		
		for (String email : user) {
			// String에 있는 matches 메소드
			System.out.println(email +" ===> " +email.matches(emailReg));
			//System.out.println(Pattern.matches(emailReg, email));
		}
	}

}
