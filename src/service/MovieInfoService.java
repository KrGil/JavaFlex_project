package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
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
	
	public int movieInfo(){
		String mc = Controller.pick_movieCode;
		//영화정보 dao에서 가져오기
		//제목, 장르, 날짜, 조횟수, 설명
		List<Map<String, Object>> mid = movieInfoDao.selectMovie(mc);
		System.out.println("================= 영화정보 ===============");
		System.out.println("\t제목\t장르\t\t개봉년도\t조회수");
		String movie_code = null;
		for(int i = 0; i < mid.size(); i++){
			Map<String, Object> hash = mid.get(i);
			System.out.print("\t"+hash.get("MOVIE_NAME")+"\t");
			System.out.print(hash.get("TYPE_NAME")+"\t"+"\t");
			System.out.print(Controller.formY.format(hash.get("MOVIE_OPENDATE"))+"\t");
			System.out.println(hash.get("MOVIE_VIEWCNT")+"\t");
			System.out.println();
			System.out.println("\t"+hash.get("MOVIE_DETAIL"));
			movie_code = hash.get("MOVIE_CODE").toString();
		}
		System.out.println("======================================");
		System.out.println("1.영화보기\t 2.찜하기\t 3.평가(좋아요싫어요)  4.돌아가기");
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: if(movie_watch(movie_code) > 0){
					System.out.println("영화를 보았습니다.");
				}break;
		case 2: moviePick_chart(); break;
		case 3: System.out.println("1.좋아요\t2.싫어요");
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
					System.out.println("제대로 입력해 주세요.");
				}break;
		case 4: return View.MAIN_PAGE;
		}
		return View.TOP_VIEWCNT_LIST_PAGE;
	}
	//조회수
	public int movie_watch(String movie_code){
		return movieInfoDao.updateViewCnt(movie_code);
	}
	public void moviePick_chart(){
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
