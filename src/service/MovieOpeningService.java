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
		System.out.println("1.10일 이내   2.30일 이내");
		int input = ScanUtil.nextInt();
		if(input == 1){
			d_10();
		}else if(input == 2){
			d_30();
		}else{
			System.out.println("제대로 입력해 주세요.");
		}
		return View.OPENING_PAGE;
	}
	public int d_10()	{
		// d-10일
		System.out.println("=========계봉 예정 10일 안의 작품들 ==========");
		List<Map<String, Object>> mod = movieOpeningDao.selectD_10();
		for(int i =0; i < mod.size(); i ++){
			Map<String, Object> movie = mod.get(i);
			
			System.out.println("MOVIE NAME : " + movie.get("MOVIE_NAME"));
			System.out.println("MOVIE SCORE: " + movie.get("MOVIE_SCORE"));
			System.out.println("MOVIE DATE : " + Controller.form.format(movie.get("MOVIE_OPENDATE")));
			System.out.println();
		}
		System.out.println("===================================");
		System.out.println("1.영화선택"+"\t"+"2.돌아가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.OPENING_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
		
	}
	public int d_30(){
		// d-30일
		System.out.println("=========계봉 예정 30일 안의 작품들 ==========");
		List<Map<String, Object>> mod = movieOpeningDao.selectD_30();
		for(int i =0; i < mod.size(); i ++){
			Map<String, Object> movie = mod.get(i);
			
			System.out.println("MOVIE NAME : " + movie.get("MOVIE_NAME"));
			System.out.println("MOVIE SCORE: " + movie.get("MOVIE_SCORE"));
			System.out.println("MOVIE DATE : " + Controller.form.format(movie.get("MOVIE_OPENDATE")));
			System.out.println();
		}
		System.out.println("===================================");
		System.out.println("1.영화선택"+"\t"+"2.돌아가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.OPENING_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
		
		
	}

}
