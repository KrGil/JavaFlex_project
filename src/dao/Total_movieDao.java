package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class Total_movieDao {
	private static Total_movieDao instance;
	private Total_movieDao(){}
	public static Total_movieDao getInstance(){
		if(instance == null){
			instance = new Total_movieDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> selectTotalMovie(int param){
		String sql = "SELECT *"
				+ " FROM"
				+ "     (SELECT ROWNUM rn, A.*"
				+ "     FROM"
				+ "        (SELECT a.movie_name, a.movie_opendate, b.type_name, a.movie_viewcnt, a.movie_code"
				+ "         FROM movie a, type b"
				+ "         WHERE a.type_lgu = b.type_lgu"
				+ "            AND movie_opendate is not null"
				+ "         ORDER BY movie_viewcnt desc) A )"
				+ " WHERE rn BETWEEN (? -1) * 10 + 1 AND (? * 10)";
		List<Object> p = new ArrayList<>();
		p.add(param);
		p.add(param);
		return jdbc.selectList(sql, p);
	}
	
}	
