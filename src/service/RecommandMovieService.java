package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MemberMypageDao;
import dao.RecommandMovieDao;

public class RecommandMovieService {
	private static RecommandMovieService instance;
	private RecommandMovieService(){} 
	public static RecommandMovieService getInstance(){
		if(instance == null){
			instance = new RecommandMovieService();
		}
		return instance;
	}
	
	RecommandMovieDao recommandMovieDao = RecommandMovieDao.getInstance();
	private MemberMypageDao memberMypageDao = MemberMypageDao.getInstance();
	
	public int recommandMovie(){//추천영화 보여주기.
		String memID = (String)Controller.loginMember.get("MEM_ID"); //로그인 아이디
		String aliasCode = (String)Controller.loginAlias.get("ALIAS_CODE");//별명코드
		
	    List<Map<String, Object>> selectInfoWatchmovie = memberMypageDao.selectInfoWatchmovie(aliasCode); //봤던영화
	    List<Map<String, Object>> selectInfoMovietype = memberMypageDao.selectInfoMovietype(aliasCode); //장르별 갯수 
		 Map<String, Object> row1 = new HashMap<>();
		    row1 = selectInfoMovietype.get(0);
		    Map<String, Object> row2 = new HashMap<>();
		    row2 = selectInfoMovietype.get(1);
		    Map<String, Object> row3 = new HashMap<>();
		    row3 = selectInfoMovietype.get(2);
		double x = Math.round(((((BigDecimal)row1.get("TNUM")).doubleValue())/selectInfoWatchmovie.size())*10.0)+1;
		double y = Math.round(((((BigDecimal)row2.get("TNUM")).doubleValue())/selectInfoWatchmovie.size())*10.0)+1;
		double z = Math.round(((((BigDecimal)row3.get("TNUM")).doubleValue())/selectInfoWatchmovie.size())*10.0)+1;
//		Set<String> keys = row1.keySet();
//		for(String key : keys){
//			System.out.println(key + " : "+row1.get(key));
//		}
		List<Object> list = new ArrayList<>();
		list.add(row1.get("LGU"));
		list.add(x);
		list.add(row2.get("LGU"));
		list.add(y);
		list.add(row3.get("LGU"));
		list.add(z);
		List<Map<String, Object>> recommandList = recommandMovieDao.selectrecommandMovie(list);
		System.out.println("==========================================================");
		for(int i=0; i < recommandList.size(); i ++){
			Map<String, Object> hash = recommandList.get(i);
			System.out.print("\t"+hash.get("ROWNUM")+"\t");
			System.out.print(hash.get("MOVIE_NAME")+"("+hash.get("TYPE_NAME")+")"+"\t");
			System.out.println(Controller.formY.format(hash.get("MOVIE_OPENDATE")));
		}
		System.out.println("==========================================================");
		System.out.println("\t1.영화선택"+"\t"+"2.돌아가기");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("영화를 선택해주세요.");
			int input1 = ScanUtil.nextInt();
			Controller.pick_movieCode = (String) recommandList.get(input1-1).get("MOVIE_CODE");
			return View.MOVIE_INFO_PAGE;
		}else{
			return View.RECOMMEND_PAGE;
		}
	}
	
}
