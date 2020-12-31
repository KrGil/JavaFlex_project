package service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.MovieOpeningDao;

public class MovieOpeningService {
	private static MovieOpeningService instance;
	private MovieOpeningService(){}
	public static MovieOpeningService getInstance(){
		if(instance == null){
			instance = new MovieOpeningService();
		}
		return instance;
	}
	private MovieOpeningDao movieOpeningDao = MovieOpeningDao.getInstance();
	
	
	public int openingPage(){
		System.out.println("========================┓");
		System.out.println("1.10일 이내   2.30일 이내");
		System.out.println("========================┛");
		int input = ScanUtil.nextInt();
		if(input == 1){
			return View.D10_LIST_PAGE;
		}else if(input == 2){
			return View.D30_LIST_PAGE;
		}else{
			System.out.println("올바르게 다시 입력해 주세요.");
		}
		return View.OPENING_PAGE;
	}
	public int d_10()	{
		// d-10일
		System.out.println("==================== 개봉 예정 DAY0-10일 작품들 ====================┓");
		List<Map<String, Object>> mod = movieOpeningDao.selectD_10();
		for(int i =0; i < mod.size(); i ++){
			Map<String, Object> movie = mod.get(i);
			
			System.out.println("\tMOVIE NAME : " + movie.get("MOVIE_NAME"));
			System.out.println("\tMOVIE SCORE: " + movie.get("MOVIE_SCORE"));
			System.out.println("\tMOVIE DATE : " + Controller.form.format(movie.get("MOVIE_OPENDATE")));
		}
		System.out.println("==============================================================┛");
		System.out.println("1.영화선택"+"\t"+"2.돌아가기\t0.나가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("🚩영화를 선택해주세요:");
			int input1 = ScanUtil.nextInt();
			Controller.pick_movieCode = (String) mod.get(input1-1).get("MOVIE_CODE");
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.OPENING_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
		
	}
	public int d_30(){
		// d-30일
		System.out.println("==================== 계봉 예정 30일 안의 작품들 ====================┓");
		List<Map<String, Object>> mod = movieOpeningDao.selectD_30();
		for(int i =0; i < mod.size(); i ++){
			Map<String, Object> movie = mod.get(i);
			
			System.out.println("\tMOVIE NAME : " + movie.get("MOVIE_NAME"));
			System.out.println("\tMOVIE SCORE: " + movie.get("MOVIE_SCORE"));
			System.out.println("\tMOVIE DATE : " + Controller.form.format(movie.get("MOVIE_OPENDATE")));
			System.out.println();
		}
		System.out.println("==============================================================┛");
		System.out.println("\t1.영화선택"+"\t"+"2.돌아가기\t0.나가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("🚩영화를 선택해주세요:");
			int input1 = ScanUtil.nextInt();
			Controller.pick_movieCode = (String) mod.get(input1-1).get("MOVIE_CODE");
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.OPENING_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
		
		
	}

}
