package service;

import util.ScanUtil;
import util.View;

public class FnAService {
	private static FnAService instance;
	private FnAService(){}
	public static FnAService getInstance(){
		if(instance == null){
			instance = new FnAService();
		}
		return instance;
	}
	public int fna_page(){
		System.out.println("======================고객센터=========================┓");
		System.out.println("\t1.공지사항\t2.F&A\t3.돌아가기");
		System.out.println("=====================================================┛");
		int input = ScanUtil.nextInt();
		if(input == 1){
			fna();
		}else if(input == 2){
			notice();
		}
		return View.HOME;
	}
	public int fna(){
		System.out.println("1.가입은 어떻게 하나요?    \t2.비밀번호를 잃어버렸어요\t3.앱스토어에서 앱을 찾을 수 없어요\t 0.돌아가기");
		int input = ScanUtil.nextInt();
		switch(input){
			case 1: System.out.println("자바플렉스는 회원가입 기능을 아주아주 손쉽게 제공합니다.");
			 		System.out.println("그냥 회원가입 하십시오.");
			 		break;
			case 2: System.out.println("자바플렉스는 관리지가 직접 대답까지 해 줍니다.");
			  		System.out.println("물어보십시오.");
			  		break;
			case 3: System.out.println("당연히 찾을 수 없습니다. 왜냐하면 우리는 앱스토어에 등록한 적이 없기 때문이지요!");
			 		System.out.println("희망차고 아름다운 하루 되시길 바랍니다.");
			 		break;
			case 0: return View.FNA_PAGE;
		}
		return View.HOME;
	}
	public int notice(){
		System.out.println("================================================================┓");
		System.out.println("\t\t\t맴버십 및 요금");
		System.out.println("\t\t\t\t\t베이식\t스탠다드\t프리미엄 "); 
		System.out.println("----------------------------------------------------------------");
		System.out.println("월간요급*(한국 원화)\t\t\t\t9,500\t12,500\t14,000");
		System.out.println("----------------------------------------------------------------");
		System.out.println("동시접속 가능한 명수  \t\t\t\t1\t2\t4");
		System.out.println("----------------------------------------------------------------");
		System.out.println("콘텐츠를 저장할 수 있는 스마트폰 또는 태블릿의 수  \t1\t2\t4");
		System.out.println("----------------------------------------------------------------");
		System.out.println("영화와 TV 프로그램 무제한 시청  \t\t\t✓\t✓\t✓");
		System.out.println("----------------------------------------------------------------");
		System.out.println("노트북, TV, 스마트폰 또는 태블릿으로 시청  \t✓\t✓\t✓");
		System.out.println("----------------------------------------------------------------");
		System.out.println("HD 화질 이용 가능  \t\t\t\t\t✓\t✓");
		System.out.println("----------------------------------------------------------------");
		System.out.println("UHD 화질 이용 가능  \t\t\t\t\t\t✓");
		System.out.println("================================================================┛");
		System.out.println("");
		return View.HOME;
	}
}
