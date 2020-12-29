package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import controller.Controller;

public class MovieInfoDao {
	private static MovieInfoDao instance;
	private MovieInfoDao(){} 
	public static MovieInfoDao getInstance(){
		if(instance == null){
			instance = new MovieInfoDao();
		}
		return instance;
	}
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectMovie(String a){
		String sql = "SELECT a.movie_code, a.movie_name, a.movie_viewcnt, a.movie_opendate, a.movie_detail, b.type_name"
				+ " FROM movie a, type b"
				+ " WHERE a.type_lgu = b.type_lgu"
				+ " AND movie_code = ?";
		List<Object> b = new ArrayList<>();
		b.add(Controller.pick_movieCode);
		return jdbc.selectList(sql, b);
	}
	//조회수 ++
	public int updateViewCnt(String movie_code){
		String sql = "UPDATE movie SET MOVIE_VIEWCNT = movie_viewcnt + 1"
				+ " WHERE movie_code = ?";
		List<Object> param = new ArrayList<>();
		param.add(movie_code);
		return jdbc.update(sql, param);
	}
	//좋아요 ++
	public int updateGood(){
		String sql = "";
		return jdbc.update(sql);
	}
}
