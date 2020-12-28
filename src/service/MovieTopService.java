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
	
	public int viewCnt(){
		System.out.println("================ 조회수별 ================");
		List<Map<String, Object>> movie_top = movieTopDao.selectViewCnt();
		for(int i = 0; i < movie_top.size(); i ++){
			System.out.println(movie_top.get(i));
		}
		return View.TOP_PAGE;
	}
	
	public int score(){
		System.out.println("================ 좋아요별 ================");
		List<Map<String, Object>> movie_top = movieTopDao.selectScore();
		for(int i = 0; i < movie_top.size(); i ++){
			System.out.println(movie_top.get(i));
		}
		return View.TOP_PAGE;
	}
}
