package controller;

import java.util.Map;

import service.MovieOpeningService;
import service.MovieTopService;
import dao.MovieTopDao;

public class Controller {
	public static Map<String, Object> loginMember; //로그인정보를 담을 변수
	public static Map<String, Object> loginAlias; //멤버정보를 담을 변수
	
	private static MovieTopService movieTopService = MovieTopService.getInstance();
	private static MovieOpeningService movieOpeningService = MovieOpeningService.getInstance();
	void start(){
		movieTopService.viewCnt();
		movieTopService.viewScore();
		movieOpeningService.d_10();
		movieOpeningService.d_30();
		
	}
	
	public static void main(String[] args) {
		Controller con = new Controller();
		con.start();
	}
}
