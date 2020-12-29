package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MovieTopDao {
	private static MovieTopDao instance;
	private MovieTopDao(){} 
	public static MovieTopDao getInstance(){
		if(instance == null){
			instance = new MovieTopDao();
		}
		return instance;
	}
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectViewCnt(){
		// 조회수별
		String sql = "SELECT ROWNUM, a.*"
				+ " FROM (SELECT movie_name, MOVIE_VIEWCNT, MOVIE_OPENDATE, MOVIE_CODE"
				+ " FROM MOVIE"
				+ " ORDER BY MOVIE_VIEWCNT DESC) a"
				+ " WHERE ROWNUM <=10";
        
		return jdbc.selectList(sql);
	}
	public List<Map<String, Object>> selectScore(){
		// 좋아요별
		String sql = "SELECT ROWNUM, a.*"
				+ " FROM (SELECT movie_name, movie_score, MOVIE_OPENDATE"
				+ " FROM MOVIE"
				+ " ORDER BY movie_score DESC) a"
				+ " WHERE ROWNUM <=10";    
       
		return jdbc.selectList(sql);
	}
}
