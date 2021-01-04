package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {
	public static void main(String[] args) {
		JDBCUtil instance = JDBCUtil.getInstance();
		JDBCUtil instance1 = JDBCUtil.getInstance();
		// 다르게 변수 명을 정해도 항상 주소값이 같다.
		// 인스턴스는 NEW로 객체화(인스턴스화) 시키지 않고 인스턴스를 가져와서 쓴다.
		
	}
	//싱글톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만 사용하는 디자인 패턴
	private JDBCUtil(){
		
	}
	
	//인스턴스를 보관할 변수
	private static JDBCUtil instance;
	//인스턴스를 빌려준다.
	
	//인스턴스를 빌려주는 메서드
	public static JDBCUtil getInstance(){
		if(instance == null){
			instance = new JDBCUtil();
		}
		return instance;
	}
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "JAVAFLEX";
	String password = "java";
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/*
	 * Map<String, Object> selectOne(String sql) //row 한줄만 조회하겠다.
	 * Map<String, Object> selectOne(String sql, List<Object> param) //sql문의 ?의 값들
	 * List<Map<String, Object>> selectList(String sql)
	 * List<Map<String, Object>> selectList(String sql, List<Object> param)
	 * int update(String sql) //return값이 몇 개의 행이 출력될지 설정해줌.
	 * int update(String sql, List<Object> param)
	 */
	public Map<String, Object> selectOne(String sql){
		Map<String, Object> map = new HashMap<>();
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//값받기
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while(rs.next()){
				for(int i = 1; i <=columnCount; i ++){
					String key = md.getColumnName(i);
					Object value = rs.getObject(key);
					map.put(key, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null) try { rs.close(); } catch (SQLException e) {}
			if(ps != null) try { ps.close(); } catch (SQLException e) {}
			if(con != null)	try { con.close(); } catch (SQLException e) {}
		}
		return map;
	}
	
	public Map<String, Object> selectOne(String sql, List<Object> param){
		Map<String, Object> row = null;
//		Map<String, Object> row = new HashMap<>();
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			for(int i = 0; i < param.size(); i ++){
				ps.setObject(i+1, param.get(i)); // ?에 값 넣어주기
			}
			rs = ps.executeQuery();
			
			//값받기	  
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while(rs.next()){
				row = new HashMap<>();
				for(int i = 1; i <=columnCount; i ++){
					String key = md.getColumnName(i);
					Object value = rs.getObject(key);
					row.put(key, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null) try { rs.close(); } catch (SQLException e) {}
			if(ps != null) try { ps.close(); } catch (SQLException e) {}
			if(con != null)	try { con.close(); } catch (SQLException e) {}
		}
		return row;
	}
	
	public List<Map<String, Object>> selectList(String sql){
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			
			while(rs.next()){
				Map<String , Object> row = new HashMap(); 
				for(int i = 1; i <= columnCount; i ++){
					String key = md.getColumnName(i);
					Object value = rs.getObject(key);//인덱스로도 key값으로도 가지고 올 수 있다.
					row.put(key, value); 
				}
				list.add(row);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null) try { rs.close(); } catch (SQLException e) {}
			if(ps != null) try { ps.close(); } catch (SQLException e) {}
			if(con != null)	try { con.close(); } catch (SQLException e) {}
		}
		return list;
	}
	
	public List<Map<String, Object>> selectList(String sql, List<Object> param){
		//쿼리를 조회한 결과
		List<Map<String, Object>> list = new ArrayList<>();
		//list = new LinkedList<>(); 이런식으로  list의 다른 클래스로도 쓸 수 있기 때문에 앞에 타입을 저렇게 해 줌.(다형성)
//		Map<String, Object> s = new HashMap();
//		s.put("1", new ArrayList<>());
		
		try {
			//쿼리 실행  부분~~~~~~~~~~~~~~~~~~~~~~~~~~~
			con = DriverManager.getConnection(url, user, password);
			// sql 담기
			ps = con.prepareStatement(sql);
			// param이 list이기 때문에 for문으로 값을 넣어줘야함. ? 값들을 하나하나 입력하기.
			for(int i = 0 ; i < param.size(); i ++){
				ps.setObject(i+1, param.get(i));
			}
			// 쿼리 실행. 
			//ex) select ?, ?, ? from dept
			// ps.setObject(1, dept_no);
			// ps.setObject(2, dept_name);
			rs = ps.executeQuery();
			
			// 쿼리 실행 후 받아오는 부분~~~~~~~~~~~~~~~~~
			// 메타데이터(받아온 테이블의 세부정보?)
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			
			// 다음 행을 계속 불러오기
			while(rs.next()){
				Map<String, Object> row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++){
					String key = md.getColumnName(i); //column 이름 받아서 key 값으로 넘겨주기.
					Object value = rs.getObject(key); // 해당 key(column명)값으로 row 정보 받아오기.
					row.put(key, value); //키 벨류에 값 집어넣기
				}
				list.add(row);
			}
		} catch (SQLException e) { e.printStackTrace();
			}finally{
				if(rs != null) try { rs.close(); } catch (SQLException e) {}
				if(ps != null) try { ps.close(); } catch (SQLException e) {}
				if(con != null)	try { con.close(); } catch (SQLException e) {}
		}
		return list;
	}
	
	public int update(String sql){
		int result =0;
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			ResultSetMetaData md = ps.getMetaData();
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public int update(String sql, List<Object> param){
		int result = 0;
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			ResultSetMetaData md = ps.getMetaData();
			
			for(int i = 0; i < param.size(); i ++){
				ps.setObject(i+1,param.get(i));
			}
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}







