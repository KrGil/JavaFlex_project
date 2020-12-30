package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
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
	private MovieInfoService movieInfoService = MovieInfoService.getInstance();
	
	public int top_page(){
		System.out.println("1.조회수별\t2. 평점별");
		int input = ScanUtil.nextInt();
		if(input == 1){
			return View.TOP_VIEWCNT_LSIT_PAGE;
		}else if(input == 2){
			return View.TOP_GOOD_LIST_PAGE;
		}else{
			System.out.println("제대로 입력해 주세요.");
		}
		return View.TOP_PAGE;
	}
	
	public int viewCnt(){
		System.out.println("================ 조회수별 ================");
		List<Map<String, Object>> movie_top = movieTopDao.selectViewCnt();
		System.out.println("\t"+"NUM\tMOVIENAME");
		for(int i = 0; i < movie_top.size(); i ++){
			Map<String, Object> hash = movie_top.get(i);
			
			System.out.print("\t"+hash.get("ROWNUM")+"\t");
			
			System.out.print(hash.get("MOVIE_NAME"));
			System.out.print("("+ Controller.formY.format(hash.get("MOVIE_OPENDATE"))+")");
			System.out.println();
		}
		System.out.println("===================================");
		System.out.println("1.영화선택"+"\t"+"2.돌아가기\t0.나가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("영화를 선택해주세요.");
			int input1 = ScanUtil.nextInt();
			Controller.pick_movieCode = (String) movie_top.get(input1-1).get("MOVIE_CODE");
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.TOP_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
	}
	
	public int viewScore(){
		System.out.println("================ 좋아요별 ================");
		List<Map<String, Object>> movie_top = movieTopDao.selectScore();
		System.out.println("\t"+"NUM\tMOVIENAME");
		for(int i = 0; i < movie_top.size(); i ++){
			Map<String, Object> hash = movie_top.get(i);
			System.out.print("\t"+hash.get("ROWNUM")+"\t");
			System.out.print(hash.get("MOVIE_NAME"));
			System.out.print("("+ Controller.formY.format(hash.get("MOVIE_OPENDATE"))+")");
			System.out.println();
		}
		System.out.println("===================================");
		System.out.println("1.영화선택"+"\t"+"2.돌아가기\t0.나가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("영화를 선택해주세요.");
			int input1 = ScanUtil.nextInt();
			Controller.pick_movieCode = (String) movie_top.get(input1-1).get("MOVIE_CODE");
			// 전부 insert
//			movieInfoService.movieInfo(); //return타입 인트
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.TOP_PAGE;
		}else{
			return View.MAIN_PAGE;
		}
	}
}
