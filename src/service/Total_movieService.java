package service;

import java.util.List;
import java.util.Map;




import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.Total_movieDao;

public class Total_movieService {
	private static Total_movieService instance;
	private Total_movieService(){}
	public static Total_movieService getInstance(){
		if(instance == null){
			instance = new Total_movieService();
		}
		return instance;
	}
	private int cnt = 1;
	
	Total_movieDao tmd = Total_movieDao.getInstance();
	public int totalMovie(){
			int input = 0;
//		do{
			System.out.println("----------------- 페이지: "+cnt+" -------------------");
			List<Map<String, Object>> listMap = tmd.selectTotalMovie(cnt);
			
			for(int i = 0; i < listMap.size(); i++){
				Map<String, Object> hash = listMap.get(i);
				System.out.print("\t"+hash.get("RN")+"\t");
				if(hash.get("MOVIE_NAME").toString().length() >4){
					System.out.print(hash.get("MOVIE_NAME").toString().substring(0, 3)+".."); 
				}else{
					System.out.print(hash.get("MOVIE_NAME"));
				}
				System.out.print("("+hash.get("TYPE_NAME")+")"+"\t"+"\t");
				System.out.println(Controller.formY.format(hash.get("MOVIE_OPENDATE")));
			}
			System.out.println("-------------------[ "+cnt+" ]--------------------");
			System.out.println("1.영화선택\t\t◁\t▷\t\t0.나가기");
			
			input = ScanUtil.nextInt();
			try{
			switch(input){
			case 1:
				System.out.println("🚩영화를 선택해주세요:");
				int input1 = ScanUtil.nextInt();
				Controller.pick_movieCode = (String) listMap.get(input1-1).get("MOVIE_CODE");
				return View.MOVIE_INFO_PAGE;
			case 2: if(cnt >1){
					cnt--;
					return View.TOTAL_MOVIE;
				}else{
					System.out.println("첫번째 페이지입니다.");
					return View.TOTAL_MOVIE;
				}
			case 3:     
				if(cnt<6){
					cnt++; 
					return View.TOTAL_MOVIE;
				}else{
					System.out.println("마지막 페이지 입니다.");
					cnt = 6;
					return View.TOTAL_MOVIE;
				}
			
			case 0:
				System.exit(0);
			}
			}catch(Exception e){
				System.out.println("다시 입력해 주세요!");
				return View.TOTAL_MOVIE;
			}
//		}while(input ==0);
		return View.MAIN_PAGE;
	}
}
