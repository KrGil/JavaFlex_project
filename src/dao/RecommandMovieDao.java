package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class RecommandMovieDao {
	private static RecommandMovieDao instance;
	private RecommandMovieDao(){} 
	public static RecommandMovieDao getInstance(){
		if(instance == null){
			instance = new RecommandMovieDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectrecommandMovie(List<Object> list){
		String sql = "SELECT ROWNUM, a.*,  b.type_name"
				+ " FROM("
				+ "    SELECT *"
				+ "    FROM (  SELECT *"
				+ "            FROM MOVIE"
				+ "            WHERE TYPE_LGU = ?"
				+ "            ORDER BY MOVIE_SCORE DESC"
				+ "    )"
				+ "    WHERE ROWNUM <= ?"
				+ "    UNION ALL"
				+ "    SELECT *"
				+ "    FROM (  SELECT *"
				+ "            FROM MOVIE"
				+ "            WHERE TYPE_LGU = ?"
				+ "            ORDER BY MOVIE_SCORE DESC"
				+ "    )"
				+ "    WHERE ROWNUM <= ?"
				+ "    UNION ALL"
				+ "    SELECT *"
				+ "    FROM (  SELECT *"
				+ "            FROM MOVIE"
				+ "            WHERE TYPE_LGU = ?"
				+ "            ORDER BY MOVIE_SCORE DESC"
				+ "    )"
				+ "    WHERE ROWNUM <= ?) a, TYPE b"
				+ " WHERE a.type_lgu = b.type_lgu"
				+ "		AND ROWNUM < 11";
		
		return jdbc.selectList(sql, list);
			
	}
}
