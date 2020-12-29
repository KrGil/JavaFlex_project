package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
	public class MemberDao {
		//싱글톤 패턴으로 만듦.
		private static MemberDao instance;
		private MemberDao(){} 
		public static MemberDao getInstance(){
			if(instance == null){
				instance = new MemberDao();
			}
			return instance;
		}
		
		private JDBCUtil jdbc = JDBCUtil.getInstance();
		
		public int insertUser(Map<String, Object> param){
			String sql = "INSERT INTO TB_JDBC_USER VALUES (?,?,?)";
			
			
			List<Object> p = new ArrayList<>();
			p.add(param.get("USER_ID"));
			p.add(param.get("PASSWORD"));
			p.add(param.get("USER_NAME"));
			
			return jdbc.update(sql,p);
		}
		public Map<String, Object> selectUser(String userId, String password) {
			String sql = "SELECT USER_ID, PASSWORD, USER_NAME "
						+ "FROM TB_JDBC_USER "
						+ "WHERE USER_ID = ? "
						+ "AND PASSWORD = ?";
			List<Object> param = new ArrayList<>();
			param.add(userId);
			param.add(password);
			
			return jdbc.selectOne(sql, param);
		}
/////////////////////////////////////////////////////////////////////

//싱글톤 패턴

	public int insertMember(Map<String, Object> param){
		String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?)";
		List<Object> p = new ArrayList<>();
		p.add(param.get("MEM_ID"));
		p.add(param.get("PASSWORD"));
		p.add(param.get("MEM_NAME"));
		p.add(param.get("MEM_SHIP"));
		p.add(param.get("CARD"));
		p.add(param.get("ALIAS"));
		p.add(param.get("LIKE_TYPE"));
		
		return jdbc.update(sql,p);
	}
	
	public List<Map<String, Object>> selectAlias(){
		Map<String,Object> member= Controller.loginMember;
		String sql = "SELECT * "
				+ " FROM ALIAS"
				+ " WHERE MEM_ID = ? ";
		List<Object> param = new ArrayList<>();
		param.add(member.get("MEM_ID"));
		return jdbc.selectList(sql, param);
	}
	//public int insertLikeType(){
	//
	//}
	//이건로그인화면 헷갈리면 안됨 
	public Map<String, Object> selectLogin(String memId, String password){ 
		String sql = "SELECT *"
				+ " FROM MEMBER"
				+ " WHERE mem_id = ?"
				+ " AND mem_password = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(memId);
		param.add(password);
		return jdbc.selectOne(sql, param);
	}
}
