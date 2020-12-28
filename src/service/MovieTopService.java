package service;

import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MovieTopDao;

public class MovieTopService {
	private static MovieTopService instance;
	private MovieTopService(){} 
	public static MovieTopService getInstance(){
		if(instance == null){
			instance = new MovieTopService();
		}
		return instance;
	}
	private MovieTopDao movieTopDao = MovieTopDao.getInstance();
	
	public int top_page(){
		System.out.println("1.조회수별 or 2. 평점별");
		int input = ScanUtil.nextInt();
		if(input == 1){
			viewCnt();
		}else if(input == 2){
			viewScore();
		}else{
			System.out.println("제대로 입력해 주세요.");
		}
		return View.TOP_PAGE;
	}
	
	public int viewCnt(){
		System.out.println("================ 조회수별 ================");
		List<Map<String, Object>> movie_top = movieTopDao.selectViewCnt();
		for(int i = 0; i < movie_top.size(); i ++){
			System.out.println(movie_top.get(i));
		}
		System.out.println("===================================");
		System.out.println("1.영화선택"+"\t"+"2.돌아가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			return View.MOVIE_INFO_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
	}
	
	public int viewScore(){
		System.out.println("================ 좋아요별 ================");
		List<Map<String, Object>> movie_top = movieTopDao.selectScore();
		for(int i = 0; i < movie_top.size(); i ++){
			System.out.println(movie_top.get(i));
		}
		return View.TOP_PAGE;
	}
}
