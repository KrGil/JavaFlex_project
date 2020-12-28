package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;


public class MovieOpeningDao {
	private static MovieOpeningDao instance;
	private MovieOpeningDao(){} 
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	public static MovieOpeningDao getInstance(){
		if(instance == null){
			instance = new MovieOpeningDao();
		}
		return instance;
	}
	
	public List<Map<String, Object>> selectD_10(){
		// d-10일
		String sql = "SELECT movie_name, movie_score, MOVIE_OPENDATE"
				+ " FROM movie"
				+ " WHERE movie_opendate between sysdate AND SYSDATE +10"
				+ " ORDER BY movie_score DESC";
		
		return jdbc.selectList(sql);
	}
	public List<Map<String, Object>> selectD_30(){
		// d-30일
		String sql = "SELECT movie_name, movie_score, MOVIE_OPENDATE"
				+ " FROM movie"
				+ " WHERE movie_opendate between sysdate+10 AND SYSDATE+30"
				+ " ORDER BY movie_score DESC";
		
		return jdbc.selectList(sql);
	}
}
