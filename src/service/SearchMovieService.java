package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.SearchMovieDao;

public class SearchMovieService {
	private static SearchMovieService instance;
	private SearchMovieService(){} 
	public static SearchMovieService getInstance(){
		if(instance == null){
			instance = new SearchMovieService();
		}
		return instance;
	}
	SearchMovieDao searchMovieDao = SearchMovieDao.getInstance();
	
	public int searchMovie(){
		System.out.println("🚩검색할 영화를 입력해 주세요:");
//		String result = "";
		String result = ScanUtil.nextLine();
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = searchMovieDao.selectSearchMovie(result);
		System.out.println("==========================================================┓");
		if(!list.isEmpty()){
			for(int i =0; i < list.size(); i ++){
				Map<String,Object> hash = list.get(i);
				System.out.print("\t"+hash.get("ROWNUM")+"\t");
				if(hash.get("MOVIE_NAME").toString().length() >4){
					System.out.print(hash.get("MOVIE_NAME").toString().substring(0, 3)+".."); 
				}else{
					System.out.print(hash.get("MOVIE_NAME"));
				}
				System.out.print("("+hash.get("TYPE_NAME")+")"+"\t");
				System.out.println(Controller.formY.format(hash.get("MOVIE_OPENDATE")));
			}
			System.out.println("==========================================================┛");
			System.out.println("\t1.영화선택"+"\t"+"2.돌아가기");
		}else{
			System.out.println("검색 결과가 없습니다.");
			return View.SEARCH_PAGE;
		}
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("🚩영화를 선택해주세요:");
			int input1 = ScanUtil.nextInt();
			Controller.pick_movieCode = (String) list.get(input1-1).get("MOVIE_CODE");
			return View.MOVIE_INFO_PAGE;
		}else if(input == 2) {
			return View.MAIN_PAGE;
		}else{
			return View.SEARCH_PAGE;
		}
	}
}
