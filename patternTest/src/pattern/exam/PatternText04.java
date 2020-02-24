package pattern.exam;

import java.util.regex.Pattern;

public class PatternText04 {
	public static void main(String[] args) {
		// +: 앞에 패턴문자 안나오면 안됨
		System.out.println(Pattern.matches("[0-9]+", "1234java")); // false
		// =>java가 0-9사이의 값이 아니라 false
		System.out.println(Pattern.matches("[0-9]+", "java")); // false
		System.out.println(Pattern.matches("[0-9]+", "1234")); // true
		System.out.println(Pattern.matches("[0-9]+", "1")); // true
		System.out.println(Pattern.matches("[0-9]+", " ")); // false
		System.out.println(Pattern.matches("[0-9]+", "")); // false
		
		System.out.println("======================================");
		
		// ?: 없거나 1개
		System.out.println(Pattern.matches("[0-9]?", "1234java")); // false
		// =>java가 0-9사이의 값이 아니라 false
		System.out.println(Pattern.matches("[0-9]?", "java")); // false
		System.out.println(Pattern.matches("[0-9]?", "1234")); // false
		System.out.println(Pattern.matches("[0-9]?", "1")); // true
		System.out.println(Pattern.matches("[0-9]?", " ")); // false
		System.out.println(Pattern.matches("[0-9]?", "")); // true
		// => 아예 없으면 true
		
		
		
	}
}
