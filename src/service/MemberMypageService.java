package service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MemberMypageDao;

public class MemberMypageService {
	
	//싱글톤
	private static MemberMypageService instance;
	private MemberMypageService(){}
	public static MemberMypageService getInstance(){
		if(instance == null){
			instance = new MemberMypageService();
		}
		return instance;
	}
	
	
	//초기화 
	private MemberMypageDao memberMypageDao = MemberMypageDao.getInstance();
	static SimpleDateFormat form = new SimpleDateFormat("dd");
	static SimpleDateFormat form1 = new SimpleDateFormat("YYYY");
	
	
	public int myPageList(){
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("1.내 정보 보기\t2.찜 목록 보기\t3.봤던 영화 목록\t0.돌아가기");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호입력>");
		
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: return View.MYINFO_PAGE;
		case 2: return View.PICK_PAGE;
		case 3: return View.MYWATCH_PAGE;
		case 0: return View.MAIN_PAGE;
		}
		return View.MAIN_PAGE;
	}
	
	
	//내정보 
	public int info() {
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		Map<String, Object> selectInfoCard = memberMypageDao.selectInfoCard(memID); //카드값 찾기
		Map<String, Object> selectInfoMembership = memberMypageDao.selectInfoMembership(memID); //멤버십 찾기 
	    List<Map<String, Object>> selectInfoWatchmovie = memberMypageDao.selectInfoWatchmovie(aliasCode); //봤던영화
	    List<Map<String, Object>> selectInfoMovietype = memberMypageDao.selectInfoMovietype(aliasCode); //장르별 갯수 
	    
	    //장르별
	    Map<String, Object> row1 = new HashMap<>();
	    row1 = selectInfoMovietype.get(0);
	    Map<String, Object> row2 = new HashMap<>();
	    row2 = selectInfoMovietype.get(1);
	    Map<String, Object> row3 = new HashMap<>();
	    row3 = selectInfoMovietype.get(2);
	    Map<String, Object> row4 = new HashMap<>();
	    row4 = selectInfoMovietype.get(3);
	    Map<String, Object> row5 = new HashMap<>();
	    row5 = selectInfoMovietype.get(4);
	    Map<String, Object> row6 = new HashMap<>();
	    row6 = selectInfoMovietype.get(5);
	    
		String cardNo = ((selectInfoCard.get("CARD_NO")).toString());
		
		System.out.println("--------------------------- 내정보 ---------------------------");
		System.out.println("▶ ︎아이디 : " + Controller.loginMember.get("MEM_ID"));
		System.out.println("▶ 별명 :  " + Controller.loginAlias.get("ALIAS_NAME"));
		System.out.println("▶ 가입일 : " + Controller.loginMember.get("MEM_JOINDATE"));
		System.out.println("▶ 결제일 : 매월 " + form.format(Controller.loginMember.get("MEM_JOINDATE"))); //확인필요 
		System.out.println("▶ 결제카드 : (" + selectInfoCard.get("CARD_COM") + ")" 
					+ cardNo.substring(0,4) + "-" + cardNo.substring(4,8) + "-" + cardNo.substring(8,12)
					+ " / " + selectInfoCard.get("CARD_NAME"));
		System.out.println("▶ 멤버십 : " + selectInfoMembership.get("MEMBERSHIP"));
		System.out.println();
		System.out.println("▶ 지금까지 본 영화 수  : " + selectInfoWatchmovie.size() + "편");
		System.out.println("▶ 나의 장르선호도");
		System.out.println("1위 : " + row1.get("NAME") + ", 2위 : " + row2.get("NAME") + ", 3위 : " + row3.get("NAME"));
		
		for(int i = 5; i > 0 ; i--) {
			double x = Math.round(((((BigDecimal)row1.get("TNUM")).doubleValue())/selectInfoWatchmovie.size())*5);
			double y = Math.round(((((BigDecimal)row2.get("TNUM")).doubleValue())/selectInfoWatchmovie.size())*5);
			double z = Math.round(((((BigDecimal)row3.get("TNUM")).doubleValue())/selectInfoWatchmovie.size())*5);
			System.out.print("\t");
			if(x>=i) {System.out.print("◼︎");
			}else { System.out.print("◻︎");}
			System.out.print("\t");
			if(y>=i) {System.out.print("◼︎");
			}else { System.out.print("◻︎");}
			System.out.print("\t");
			if(z>=i) {System.out.print("◼︎");
			}else { System.out.print("◻︎");}
			System.out.println();
		}
		System.out.println("    " + row1.get("NAME") + "    " + row2.get("NAME") + "    " + row3.get("NAME"));
		System.out.println("-----------------------------------------------------------");
		System.out.println();
		
		if(aliasCode.substring(aliasCode.length()-1).equals("1")){
			System.out.println("1.별명변경    2.비밀번호변경    3.멤버십변경    4.멤버십해제    5.회원탈퇴    0.돌아가기");
			int input = ScanUtil.nextInt();
			switch(input) {
			case 1 : changeAlias(); break;
			case 2 : changePW(); break;
		    case 3 : chageMembership(); break;
		    case 4 : cancleMembership(); break;
		    case 5 : cancleMeber(); break;
			case 0 : return View.MYPAGE;
			}
		}else{
			System.out.println("1.별명변경   0.돌아가기");
			int input = ScanUtil.nextInt();
			switch(input) {
			case 1 : changeAlias();
			case 0 : return View.MYPAGE;
		}
		
		}
		
		return View.MYINFO_PAGE;
	}
	
	//별명 변경
	public int changeAlias(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		int changeCheck = memberMypageDao.changeAlias(aliasCode);
		
		if(changeCheck == 1){
			System.out.println("변경이 완료되었습니다.");
		}
		return View.MYINFO_PAGE;
	}
	
	//비밀번호 변경
	public int changePW(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		int changeCheck = memberMypageDao.changePW(memID);
		if(changeCheck == 1){
			System.out.println("변경이 완료되었습니다.");
		}
		return View.MYINFO_PAGE;
	}
	
	//멤버십 변경
	public int chageMembership(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		Map<String, Object> selectInfoMembership = memberMypageDao.selectInfoMembership(memID);
		System.out.println("※멤버십은 중도 변경 시 환불 되지 않습니다.");
		System.out.println("▶ 멤버십을 변경하시겠습니까? (1.네, 2.아니요)");
		
		int input = ScanUtil.nextInt();
		String membership;
		
		if(input == 1){
			
			System.out.println("변경할 멤버쉽을 선택해주세요.");
			System.out.println("1.BASIC 2.STANDARD 3.PREMIUM");
			input = ScanUtil.nextInt();
			
			switch(input){
			case 1 : membership = "BASIC"; break;
			case 2 : membership = "STANDARD"; break;
			case 3 : membership = "PREMIUM"; break;
			default: membership = (String)(selectInfoMembership.get("MEMBERSHIP"));
			}
			int changeCheck = memberMypageDao.changeMembership(memID, membership);
			if(changeCheck == 1){
				System.out.println("변경이 완료되었습니다.");
			}
			return View.MYINFO_PAGE;
			
		}else{return View.MYINFO_PAGE;}
	}
	
	//멤버십 해제
	public int cancleMembership(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		System.out.println("▶ 멤버십을 해제하시겠습니까? (1.네, 2.아니요)");
		
		String membership = "NON";
		int input = ScanUtil.nextInt();
		if(input == 1){
			int changeCheck = memberMypageDao.changeMembership(memID, membership);
			if(changeCheck == 1){
				System.out.println("해제가 완료되었습니다.");
			}
			return View.MYINFO_PAGE;
			
		}else{return View.MYINFO_PAGE;}
	}
	
	//회원 탈퇴
	public int cancleMeber(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		System.out.println("▶ 회원탈퇴 하시겠습니까? (1.네, 2.아니요)");
		
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("탈퇴를 하시려면 비밀번호를 입력해주세요 >");
			String pw = ScanUtil.nextLine();
			if(pw.equals((String)Controller.loginMember.get("MEM_PASSWORD"))){
				int changeCheck = memberMypageDao.cancleMem(memID);
				if(changeCheck == 1){
					System.out.println("탈퇴가 완료되었습니다.");
				}
				return View.HOME;
			}else {
				System.out.println("비밀번호가 틀렸습니다.");
				return View.MYINFO_PAGE;
			}
		}else {return View.MYINFO_PAGE;}
	}
	
	//찜목록
	public int pick_chart(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		System.out.println("--------------------------- 내 찜목록 ---------------------------");
		List<Map<String, Object>> selectPick_chart = memberMypageDao.selectPick_chart(aliasCode);
		System.out.println("\tNum\tMovieName\t\tOpeningdate");
		for(int i = 0; i < selectPick_chart.size(); i++){
			Map<String, Object> getPickChart = selectPick_chart.get(i);
			Map<String, Object> getPickChartInfo = memberMypageDao.getPickChartInfo((String)getPickChart.get("MOVIE_CODE"));
			System.out.print("\t" + getPickChart.get("ROWNUM") +"\t");
			System.out.print(getPickChartInfo.get("MOVIE_NAME") + " (" + getPickChartInfo.get("TYPE_NAME")+") \t\t");
			System.out.println("   " + form1.format(getPickChartInfo.get("MOVIE_OPENDATE")));
		}
		System.out.println("--------------------------------------------------------------");
		System.out.println("1.영화선택  0.돌아가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("영화번호를 선택해 주세요 >");
			input = ScanUtil.nextInt();
			input --;
			
			Map<String, Object> getPickChart = selectPick_chart.get(input);
			Map<String, Object> getPickChartInfo = memberMypageDao.getPickChartInfo((String)getPickChart.get("MOVIE_CODE"));
			String pickMovieCode = (String)getPickChart.get("MOVIE_CODE");
			
			System.out.println("===========================================================");
			System.out.println("▶ 영화제목 : " + getPickChartInfo.get("MOVIE_NAME"));
			System.out.println("▶ 영화장르 : " + getPickChartInfo.get("TYPE_NAME"));
			System.out.println("▶ 개봉년도 : " + form1.format(getPickChartInfo.get("MOVIE_OPENDATE")));
			System.out.println("▶ 조회수 : " + getPickChartInfo.get("MOVIE_VIEWCNT"));
			System.out.println("-------------------------- 내용 -----------------------------");
			if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 100)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(40, 60));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(60, 80));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(80, 100));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 80)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(40, 60));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(60, 80));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 60)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(40, 60));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 40)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 20)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
			}else {System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL"));}
			
			System.out.println("===========================================================");
			
			pick_chart_List(pickMovieCode);
			return View.MYPAGE;
		}
		
		return View.MYPAGE;
	}
	
	//찜목록 선택지
	public int pick_chart_List(String pickMovieCode){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		System.out.println("1.찜목록 삭제 하기  2.영화보기  0.돌아가기");
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: 
			int changeCheck = memberMypageDao.deletePickChartInfo(pickMovieCode,aliasCode);
			if(changeCheck == 1){
				System.out.println("내 찜목록에서 삭제가 완료되었습니다");
			}
			break;
		case 2: whaching_movie(pickMovieCode); break;
		case 0: return View.PICK_PAGE;
		}
		return View.PICK_PAGE;
	}
	
	
	//봤던 영화 목록
	public int watch(){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드

		
		List<Map<String, Object>> selectWatch = memberMypageDao.selectWatch(aliasCode);
		
		System.out.println("---------------------------" + Controller.loginAlias.get("ALIAS_NAME") + "님이 시청한콘텐츠 ---------------------------");
		System.out.println("\tNum\tMovieName\t\tOpeningdate");
		for(int i = 0; i < selectWatch.size(); i++){
			Map<String, Object> getWatch = selectWatch.get(i);
			System.out.print("\t"+ getWatch.get("ROWNUM") + "\t");
			String name = getWatch.get("MOVIE_NAME") + " (" + getWatch.get("TYPE_NAME")+")";
			
			if(name.length() < 12 ||getWatch.get("MOVIE_NAME").equals("킹스맨") ){
				System.out.print(getWatch.get("MOVIE_NAME") + " (" + getWatch.get("TYPE_NAME")+") \t\t");
			}else{System.out.print(getWatch.get("MOVIE_NAME") + " (" + getWatch.get("TYPE_NAME")+") \t");}
			
			System.out.println("   " + form1.format(getWatch.get("MOVIE_OPENDATE")));
		}
		System.out.println("--------------------------------------------------------------");
		System.out.println("1.영화선택  0.돌아가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("영화번호를 선택해 주세요 >");
			input = ScanUtil.nextInt();
			input --;
			
			Map<String, Object> getPickChart = selectWatch.get(input);
			Map<String, Object> getPickChartInfo = memberMypageDao.getPickChartInfo((String)getPickChart.get("MOVIE_CODE"));
			String pickMovieCode = getPickChartInfo.get("MOVIE_CODE").toString();
			
			System.out.println("===========================================================");
			System.out.println("▶ 영화제목 : " + getPickChartInfo.get("MOVIE_NAME"));
			System.out.println("▶ 영화장르 : " + getPickChartInfo.get("TYPE_NAME"));
			System.out.println("▶ 개봉년도 : " + form1.format(getPickChartInfo.get("MOVIE_OPENDATE")));
			System.out.println("▶ 조회수 : " + getPickChartInfo.get("MOVIE_VIEWCNT"));
			System.out.println("-------------------------- 내용 -----------------------------");
			if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 100)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(40, 60));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(60, 80));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(80, 100));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 80)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(40, 60));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(60, 80));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 60)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(40, 60));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 40)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(20, 40));
			}else if((getPickChartInfo.get("MOVIE_DETAIL").toString().length() > 20)){
				System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL").toString().substring(0, 20));
			}else {System.out.println("\t\t" + getPickChartInfo.get("MOVIE_DETAIL"));}
			
			System.out.println("===========================================================");
			
			watch_List(pickMovieCode, input);
			return View.MYPAGE; 
		}
		
		return View.MYPAGE;
	}
	
	
	//봤던목록 선택지
	public int watch_List(String pickMovieCode, int sinput){
		//변수
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
		System.out.println("1.목록 삭제하기  2.영화보기  0.돌아가기");
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: 
			List<Map<String, Object>> selectWatch = memberMypageDao.selectWatch(aliasCode);
			Map<String, Object> getWatch = selectWatch.get(sinput-1);
			int changeCheck = memberMypageDao.deleteWatchInfo(pickMovieCode,aliasCode);
			if(changeCheck == 1){
				System.out.println("목록에서 삭제가 완료되었습니다");
			}
			break;
		case 2: whaching_movie(pickMovieCode); break;
		case 0: return View.PICK_PAGE;
		}
		return View.PICK_PAGE;
	}
	
	//영화보기
	public int whaching_movie(String pickMovieCode){
		Map<String, Object> whachingMovie = memberMypageDao.whaching_movie(pickMovieCode);
		System.out.println(whachingMovie.get("MOVIE_NAME") + "을 시청합니다.");
		String[] found = {"영","화","시","청","중",".",".","."};
		String[] found1 ={"(〠_〠   ) ","(  〠_〠) " ,"(〠_〠   ) ","(  〠_〠) ","(〠_〠   ) ","(  〠_〠) ","(〠_〠   ) ","(  〠_〠) " };
		for(int i = 0; i < found.length; i++){
			System.out.print(found[i]);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
		System.out.println();
		for(int i = 0; i < found1.length; i++){
			System.out.print(found1[i]);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
		System.out.println();
		int changeCheck = memberMypageDao.updateViewCnt(pickMovieCode);
		if(changeCheck > 0){
			System.out.println("시청이 완료되었습니다.");
		}else {return View.MYPAGE;}
		return View.MYPAGE;
	}
	
}