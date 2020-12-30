package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class SearchMovieDao {
	private static SearchMovieDao instance;
	private SearchMovieDao(){} 
	public static SearchMovieDao getInstance(){
		if(instance == null){
			instance = new SearchMovieDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> selectSearchMovie(String result){
		String sql = "SELECT ROWNUM, a.*,b.* "
				+ " FROM movie a, type b"
				+ " WHERE a.type_lgu = b.type_lgu"
				+ " AND movie_name like '%'||?||'%'";
		List<Object> param = new ArrayList<>();
		param.add(result);
		
		return jdbc.selectList(sql, param);
	}

}
