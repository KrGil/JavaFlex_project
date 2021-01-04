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
	public int updateGood(String movie_code){
		String sql = "UPDATE movie SET movie_score = movie_score+1"
				+ " WHERE MOVIE_CODE = ?";
		List<Object> param = new ArrayList<>();
		param.add(movie_code);
		return jdbc.update(sql,param);
	}
	//좋아요 --
	public int updateBad(String movie_code){
		String sql = "UPDATE movie SET movie_score ="
				+ " CASE"
				+ " WHEN movie_score > 0 THEN movie_score-1"
				+ " ELSE movie_score END"
				+ " WHERE MOVIE_CODE = ?"; 
		List<Object> param = new ArrayList<>();
		param.add(movie_code);
		return jdbc.update(sql,param);
	}
	// 좋아요 score
	public int insertGoodScore(Map<String, Object> info){
		String sql = "INSERT INTO score "
				+ " VALUES (?, ?, ?, 1)";
		List<Object> param = new ArrayList<>();
		
		param.add(info.get("SCORE_CODE"));
		param.add(info.get("MOVIE_CODE"));
		param.add(info.get("ALIAS_CODE"));
		
		return jdbc.update(sql, param);
	}
	// 시러요 score
	public int insertBadScore(Map<String, Object> info){
		String sql = "UPDATE score SET score_good = 0"
				+ " WHERE score_code = ?"
				+ " AND alias_code = ?";
		List<Object> param = new ArrayList<>();
		
		param.add(info.get("SCORE_CODE"));
		param.add(info.get("ALIAS_CODE"));
		
		return jdbc.update(sql, param);
	}
	public int updateMoviePick_chart(Map<String, Object> info){
//		System.out.println(info.get("MOVIE_CODE"));
//		System.out.println(info.get("MOVIE_CODE"));
		String sql = "INSERT INTO pick_chart"
				+ " VALUES (?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(info.get("MOVIE_CODE"));
		param.add(info.get("ALIAS_CODE"));
		return jdbc.update(sql, param);
	}
}
