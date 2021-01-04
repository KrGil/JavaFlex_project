package util;

import java.util.Scanner;

public class ScanUtil {
	
	//유틸리티 성향의 메서드인 경우 static을 붙인다.
	//static이 메모리에 올린다는 의미. 그래서 자주 사용하기 위해서.
	// ex)	  Math.random() Math.round()  System.out.println()
	//static이 붙은 경우 class명.메서드명() 으로 사용.
	
	
	//클래스 변수
	private static Scanner s = new Scanner(System.in); 
	
	//클래스 메서드 (String 받기)
	public static String nextLine(){
		return s.nextLine();
	}
	
	//클래스 매서드(int 받기)
	public static int nextInt(){
		return Integer.parseInt(s.nextLine());
	}
	
	
	
	
	
	
}
