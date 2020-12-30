package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import service.FnAService;
import service.MemberMypageService;
import service.MemberService;
import service.MovieInfoService;
import service.MovieOpeningService;
import service.MovieTopService;
import service.RecommandMovieService;
import service.SearchMovieService;
import util.ScanUtil;
import util.View;
import dao.MemberMypageDao;

public class Controller {
	public static Map<String, Object> loginMember; //로그인정보를 담을 변수
	public static Map<String, Object> loginAlias = new HashMap<String, Object>(); //멤버정보를 담을 변수
	public static String pick_movieCode;
	private static MovieTopService movieTopService = MovieTopService.getInstance();
	private static MovieOpeningService movieOpeningService = MovieOpeningService.getInstance();
	private static MemberService memberService = MemberService.getInstance();
	private MemberMypageService memberMypageService = MemberMypageService.getInstance();
	private MemberMypageDao memberMypageDao = MemberMypageDao.getInstance();
	private MovieInfoService movieInfoService = MovieInfoService.getInstance();
	private RecommandMovieService recommandMovieService = RecommandMovieService.getInstance();
	public SearchMovieService searchMovieService = SearchMovieService.getInstance();
	public FnAService fnaService = FnAService.getInstance();
	public static SimpleDateFormat form = new SimpleDateFormat("YYYY/MM/DD");
	public static SimpleDateFormat formY = new SimpleDateFormat("YYYY");
	void start(){
		loginAlias.put("ALIAS_CODE", "flex1");
		
		movieTopService.top_page();
		movieOpeningService.openingPage();
	}
	
	public static void main(String[] args) {
//		Controller con = new Controller();
////		con.start();
		try{
		new Controller().start1();
		}catch(Exception e){
			System.out.println("제대로 입력해 주세요.");
		}
//		new Controller().start();
	}
	private void start1(){
		int view = View.HOME;
		
		while(true){
			switch(view){
			//HOME화면
			case View.HOME: view = home();break;
			//LOGIN화면
			case View.LOGIN: view = memberService.login();break;
			case View.JOIN: view = memberService.join();break;
			case View.INSERT_MEMBERSHIP_PAGE:view = memberService.membership();break;
			case View.INSERT_CARD_PAGE:view = memberService.card();break;
			case View.INSERT_TYPE:view = memberService.likeType();break;
			case View.INSERT_DATA:view = memberService.insert();break;
			case View.ALIAS_LIST: view = memberService.aliasList();break;
			case View.MAIN_PAGE: view = mainPage();break;
			//2DEPTH
			case View.MYPAGE: view = memberMypageService.myPageList();break; 
			case View.SEARCH_PAGE: view = searchMovieService.searchMovie();break; 
			case View.RECOMMEND_PAGE: view = recommandMovieService.recommandMovie();break; 
			case View.TOP_PAGE: view = movieTopService.top_page();break; 
			case View.OPENING_PAGE: view = movieOpeningService.openingPage();break; 
			case View.FNA_PAGE: view = fnaService.fna_page();break; 
			//3DEPTH
				//MYPAGE
			case View.MYINFO_PAGE: view = memberMypageService.info();break; 
			case View.PICK_PAGE: view = memberMypageService.pick_chart();break; 
			case View.MYWATCH_PAGE: view = memberMypageService.watch();break;
				//나머지
//			case View.SEARCH_LIST_PAGE: view = memberMypageService.watch();break;
				//top10 영화보기
			case View.TOP_GOOD_LIST_PAGE: view = movieTopService.viewScore();break;
			case View.TOP_VIEWCNT_LSIT_PAGE: view = movieTopService.viewCnt();break;
				//개봉예정작
			case View.D10_LIST_PAGE: view = movieOpeningService.d_10();break;
			case View.D30_LIST_PAGE: view = movieOpeningService.d_30();break;
				//공지사항 // FNA
//			case View.NOTICE_LIST_PAGE: view = memberMypageService.watch();break;
			case View.FNA_LIST_PAGE: view = memberMypageService.watch();break;
			//4DEPTH
			//movieInfo
			case View.MOVIE_INFO_PAGE: view = movieInfoService.movieInfo();break;
			//5DEPTH
//			case View.SHOW_MOVIE_PAGE: view = memberMypageService.watch();break;
			}
		}
	}
	private int home() {
		System.out.println("-----------------------------------------");
		System.out.println("1.로그인\t2.회원가입\t3.고객센터\t0.프로그램 종료");
		System.out.println("-----------------------------------------");
		System.out.println("번호 입력>");
		try{
			int input = ScanUtil.nextInt();
			switch (input){
			case 1: return View.LOGIN;
			case 2: return View.JOIN;
			case 3: return View.FNA_PAGE;
			case 0: 
				System.out.println("프로그램이 종료되었습니다.");
				System.exit(0);
				break;
			}
		}catch(Exception e){
			System.out.println("번호를 입력해 주세요.");
		}
		return View.HOME;
	}
	
	private int mainPage() {
		Map<String, Object> selectInfoMembership = memberMypageDao.selectInfoMembership((String)Controller.loginMember.get("MEM_ID"));
		try{
			if(selectInfoMembership.get("MEMBERSHIP").equals("NON")){
				System.out.println("멤버십 결제 후 사용가능합니다~ 멤버십 가입 후 이용해주세요^^");
				System.out.println("1.멤버십 가입 2.돌아가기");
				int input = ScanUtil.nextInt();
				if(input == 1){
					System.out.println("멤버십을 선택해주세요.");
					System.out.println("1.BASIC 2.STANDARD 3.PRIMIUM");
					input = ScanUtil.nextInt();
					String membership = "NON";
					switch(input){
						case 1: membership = "BASIC"; break;
						case 2: membership = "STANDARD"; break;
						case 3: membership = "PRIMIUM"; break;
					}
					memberMypageDao.changeMembership((String)Controller.loginMember.get("MEM_ID"), membership);
					return View.MAIN_PAGE;
				}else{return View.HOME;}
			}else{
				System.out.println("----------------------------------------------------------------------------------");
				System.out.println("1.마이페이지\t2.영화검색\t3.추천영화보기\t4.Top10영화보기\t5.개봉예정작\t0.돌아가기");
				System.out.println("----------------------------------------------------------------------------------");
				System.out.println("번호 입력>");
				int input = ScanUtil.nextInt();
				
				switch (input){
				case 1: return View.MYPAGE;
				case 2: return View.SEARCH_PAGE;
				case 3: return View.RECOMMEND_PAGE;
				case 4: return View.TOP_PAGE;
				case 5: return View.OPENING_PAGE;
				case 0: return View.HOME;
				}
			}
		}catch(Exception e){
			System.out.println("제대로 입력해 주세요");
		}
		return View.MAIN_PAGE;
	}
}
