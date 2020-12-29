package controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import service.MemberService;
import service.MovieOpeningService;
import service.MovieTopService;
import util.ScanUtil;
import util.View;
import dao.MovieTopDao;

public class Controller {
	public static Map<String, Object> loginMember; //로그인정보를 담을 변수
	public static Map<String, Object> loginAlias; //멤버정보를 담을 변수
	public static String pick_movieCode;
	private static MovieTopService movieTopService = MovieTopService.getInstance();
	private static MovieOpeningService movieOpeningService = MovieOpeningService.getInstance();
	private static MemberService memberService = MemberService.getInstance();
	public static SimpleDateFormat form = new SimpleDateFormat("YYYY/MM/DD");
	public static SimpleDateFormat formY = new SimpleDateFormat("YYYY");
	
	void start(){
		movieTopService.top_page();
		movieOpeningService.openingPage();
	}
	
	public static void main(String[] args) {
		Controller con = new Controller();
		con.start();
//		new Controller().start1();
	}
	private void start1(){
		int view = View.HOME;
		while(true){
			switch(view){
			case View.HOME:  view = home();break;
			case View.LOGIN:  view = memberService.login();break;
			case View.JOIN:  view = memberService.join();break;
			case View.ALIAS_LIST:  view = memberService.alias();break;
			case View.MAIN_PAGE:  view = home();break;
			
//					case View.MAIN_PAGE:view =
//					case View.MYPAGE:view =
			}
		}
	}
	private int home() {
		System.out.println("-----------------------------------------");
		System.out.println("1.로그인\t2.회원가입\t3.고객센터\t4.프로그램 종료");
		System.out.println("-----------------------------------------");
		System.out.println("번호 입력>");
		int input = ScanUtil.nextInt();
		
		switch (input){
			case 1: return View.LOGIN;
			case 2: return View.JOIN;
			//0case 3: return View.고객센터 넣어야함 ;
			case 4: 
				System.out.println("프로그램이 종료되었습니다.");
				System.exit(0);
				break;
		}
	return View.HOME;
	}
}
