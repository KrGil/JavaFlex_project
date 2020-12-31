package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MemberMypageDao;
import dao.MovieInfoDao;

public class MovieInfoService {
	private static MovieInfoService instance;
	private MovieInfoService(){} 
	public static MovieInfoService getInstance(){
		if(instance == null){
			instance = new MovieInfoService();
		}
		return instance;
	}
	private MovieInfoDao movieInfoDao = MovieInfoDao.getInstance();
	private MemberMypageDao memberMypageDao = MemberMypageDao.getInstance();
	
	public int movieInfo(){ //MOVIE_INFO_PAGE
		String mc = Controller.pick_movieCode;
		//영화정보 dao에서 가져오기
		//제목, 장르, 날짜, 조횟수, 설명
		
		List<Map<String, Object>> mid = movieInfoDao.selectMovie(mc);
		System.out.println("===========================================================┓");
		String movie_code = null;
		for(int i = 0; i < mid.size(); i++){
			Map<String, Object> hash = mid.get(i);
			System.out.println("\t▶ 영화제목 : " +hash.get("MOVIE_NAME")+"\t");
			System.out.println("\t▶ 영화장르 : " + hash.get("TYPE_NAME")+"\t"+"\t");
			System.out.println("\t▶ 개봉년도 : " +Controller.formY.format(hash.get("MOVIE_OPENDATE"))+"\t");
			System.out.println("\t▶ 조회수 : " + hash.get("MOVIE_VIEWCNT")+"\t");
			System.out.println("-------------------------- 내용 -----------------------------");
			if((hash.get("MOVIE_DETAIL").toString().length() > 100)){
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(40, 60));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(60, 80));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(80, 100));
			}else if((hash.get("MOVIE_DETAIL").toString().length() > 80)){
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(40, 60));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(60, 80));
			}else if((hash.get("MOVIE_DETAIL").toString().length() > 60)){
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(20, 40));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(40, 60));
			}else if((hash.get("MOVIE_DETAIL").toString().length() > 40)){
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(0, 20));
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(20, 40));
			}else if((hash.get("MOVIE_DETAIL").toString().length() > 20)){
				System.out.println("\t\t" + hash.get("MOVIE_DETAIL").toString().substring(0, 20));
			}else {System.out.println("\t\t" + hash.get("MOVIE_DETAIL"));}
			System.out.println("===========================================================┛");
			System.out.println("\t1.영화보기\t2.찜하기\t3.평가하기\t0.돌아가기");
			movie_code = hash.get("MOVIE_CODE").toString();
		}
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: 
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
			if(movie_watch(movie_code) > 0){
				int changeCheck = memberMypageDao.updateViewCnt(movie_code);
				int something = memberMypageDao.insertWatch(movie_code, Controller.loginAlias.get("ALIAS_CODE").toString());
				System.out.println("🚩영화 시청을 완료하였습니다:");
			}break;
		case 2: 
			Map<String,Object> info = new HashMap<>();
			info.put("ALIAS_CODE", Controller.loginAlias.get("ALIAS_CODE"));
			info.put("MOVIE_CODE", movie_code);
			String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
			List<Map<String, Object>> selectPick_chart = memberMypageDao.selectPick_chart(aliasCode);
			for(int i = 0; i < selectPick_chart.size(); i ++){
				Map<String, Object> hash = selectPick_chart.get(i);
				if (movie_code.equals(hash.get("MOVIE_CODE"))){
					System.out.println("이미 찜 목록에 있는 영화입니다.");
					return View.MOVIE_INFO_PAGE;
				}
			}
			if(moviePick_chart(info) > 0){
				System.out.println("찜 목록에 추가되었습니다.");
			}
			break;
		case 3: 
			System.out.println("1.좋아요\t2.싫어요");
			int input1 = ScanUtil.nextInt();
			if(input1 == 1){
				if(movieScoreGood(movie_code) > 0){
					System.out.println("평가를 성공하였습니다.");
				}
			}else if(input1 == 2){
				if(movieScoreBad(movie_code) > 0){
					System.out.println("싫어요는 싫은데 ㅠ");
				}else{
					System.out.println("싫어요는 거절하겠어.");
				}
			}else{
				System.out.println("올바르게 다시 입력해 주세요.");
			}break;
		case 0: return View.MAIN_PAGE;
		}
		return View.MAIN_PAGE;
	}
	//조회수
	public int movie_watch(String movie_code){
		return movieInfoDao.updateViewCnt(movie_code);
	}
	public int moviePick_chart(Map<String, Object> info){
		return movieInfoDao.updateMoviePick_chart(info);
	}
	//조아요++
	public int movieScoreGood(String movie_code){
		//score, movie
		Map<String, Object> param = new HashMap<>();
		
		param.put("ALIAS_CODE", Controller.loginAlias.get("ALIAS_CODE"));
		param.put("MOVIE_CODE", movie_code);
		String score_code = "GOOD"+movie_code+Controller.loginAlias.get("ALIAS_CODE");
		param.put("SCORE_CODE", score_code);
		movieInfoDao.updateGood(movie_code);
		
		return movieInfoDao.insertGoodScore(param);
	}
	//조아요--
	public int movieScoreBad(String movie_code){
		//score, movie
		Map<String, Object> param = new HashMap<>();
		
		param.put("ALIAS_CODE", Controller.loginAlias.get("ALIAS_CODE"));
		param.put("MOVIE_CODE", movie_code);
		String score_code = "GOOD"+movie_code+Controller.loginAlias.get("ALIAS_CODE");
		param.put("SCORE_CODE", score_code);
		movieInfoDao.updateBad(movie_code);
		
		return movieInfoDao.insertBadScore(param);
	}
	
}
